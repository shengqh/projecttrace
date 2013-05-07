package edu.vanderbilt.cqs.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import edu.vanderbilt.cqs.ModuleType;
import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
import edu.vanderbilt.cqs.bean.ProjectCostCenter;
import edu.vanderbilt.cqs.bean.ProjectFile;
import edu.vanderbilt.cqs.bean.ProjectFileData;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.ProjectUser;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.FileUploadForm;
import edu.vanderbilt.cqs.form.ModuleForm;
import edu.vanderbilt.cqs.form.ProjectCostCenterForm;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTechnologyForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ProjectController extends RootController {
	@Autowired
	private ProjectService service;

	@Autowired
	private Validator validator;

	protected boolean hasProject(long projectId) {
		return !getUserType(projectId).equals(UserType.NONE);
	}

	protected boolean isFaculty(long projectId) {
		return getUserType(projectId).equals(UserType.VANGARD_FACULTY);
	}

	private void initializeProjectForm(ProjectForm form) {
		form.setTechnologyList(service.listValidTechnology());

		List<User> users = service.listValidUser();

		Set<User> contactSet = new HashSet<User>();
		Set<User> piSet = new HashSet<User>();
		Set<User> facultySet = new HashSet<User>();
		Set<User> staffSet = new HashSet<User>();

		for (User user : users) {
			if (user.hasRole(Role.ROLE_USER)) {
				contactSet.add(user);
				piSet.add(user);
			}
			if (user.hasRole(Role.ROLE_VANGARD_FACULTY)) {
				facultySet.add(user);
			}
			if (user.hasRole(Role.ROLE_VANGARD_STAFF)) {
				staffSet.add(user);
			}
		}

		List<User> contactList = new ArrayList<User>(contactSet);
		List<User> piList = new ArrayList<User>(piSet);
		List<User> facultyList = new ArrayList<User>(facultySet);
		List<User> staffList = new ArrayList<User>(staffSet);

		Collections.sort(contactList);
		Collections.sort(piList);
		Collections.sort(facultyList);
		Collections.sort(staffList);

		form.setContactList(contactList);
		form.setStudyPIList(piList);
		form.setFacultyList(facultyList);
		form.setStaffList(staffList);

		for (ProjectUser user : form.getProject().getUsers()) {
			if (checkUser(facultyList, user, UserType.VANGARD_FACULTY)) {
				continue;
			}
			if (checkUser(staffList, user, UserType.VANGARD_STAFF)) {
				continue;
			}
			if (checkUser(piList, user, UserType.STUDYPI)) {
				continue;
			}
			if (checkUser(contactList, user, UserType.CONTACT)) {
				continue;
			}
		}
	}

	private boolean checkUser(List<User> userList, ProjectUser user,
			Integer uType) {
		if (!user.getUserType().equals(uType)) {
			return false;
		}

		if (!hasUser(userList, user.getUser())) {
			userList.add(user.getUser());
		}

		return true;
	}

	private boolean hasUser(List<User> userList, User user) {
		for (User item : userList) {
			if (item.getId().equals(user.getId())) {
				return true;
			}
		}

		return false;
	}

	@Secured({ Permission.ROLE_PROJECT_VIEW })
	@RequestMapping("/project")
	public String listProject(ModelMap model) {
		addUserLogInfo("list project.", false);

		model.put("projectList", service.listProject());

		return "/project/list";
	}

	@RequestMapping("/addproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String addProject(ModelMap model) {
		addUserLogInfo("try to add project.", false);

		ProjectForm form = new ProjectForm();
		form.setProject(new Project());
		form.setUserType(UserType.VANGARD_FACULTY);

		initializeProjectForm(form);

		// set current user as faculty
		form.getFaculty().add(currentUser().getId());

		model.put("projectForm", form);

		return "/project/edit";
	}

	@RequestMapping("/editproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProject(@RequestParam("id") Long projectId, ModelMap model) {
		addUserLogInfo("try to edit project.", false);

		Project project = service.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setProject(project);
			form.setUserType(getUserType(projectId));

			initializeProjectForm(form);

			form.setContact(getIdListFromUserList(project.getUsers(),
					UserType.CONTACT));
			form.setStudyPI(getIdListFromUserList(project.getUsers(),
					UserType.STUDYPI));
			form.setFaculty(getIdListFromUserList(project.getUsers(),
					UserType.VANGARD_FACULTY));
			form.setStaff(getIdListFromUserList(project.getUsers(),
					UserType.VANGARD_STAFF));

			model.put("projectForm", form);

			return "/project/edit";
		} else {
			return "redirect:/project";
		}
	}

	private List<Long> getIdListFromUserList(List<ProjectUser> users,
			Integer userType) {
		List<Long> result = new ArrayList<Long>();
		for (ProjectUser u : users) {
			if (u.getUserType().equals(userType))
				result.add(u.getUser().getId());
		}
		return result;
	}

	private List<ProjectUser> getUserListFromIdList(Project project,
			List<Long> ids, Integer userType) {
		if (ids == null) {
			return new ArrayList<ProjectUser>();
		}

		List<ProjectUser> result = new ArrayList<ProjectUser>();
		for (Long id : ids) {
			User user = service.findUser(id);
			if (user != null) {
				ProjectUser pu = new ProjectUser(project, user, userType);
				result.add(pu);
			}
		}
		return result;
	}

	private void initializeUsers(Project project, ProjectForm form) {
		Set<ProjectUser> newUsers = new HashSet<ProjectUser>();
		newUsers.addAll(getUserListFromIdList(project, form.getContact(),
				UserType.CONTACT));
		newUsers.addAll(getUserListFromIdList(project, form.getStudyPI(),
				UserType.STUDYPI));
		newUsers.addAll(getUserListFromIdList(project, form.getStaff(),
				UserType.VANGARD_STAFF));
		newUsers.addAll(getUserListFromIdList(project, form.getFaculty(),
				UserType.VANGARD_FACULTY));

		Set<ProjectUser> oldUsers = new HashSet<ProjectUser>(project.getUsers());

		for (ProjectUser ou : oldUsers) {
			if (!newUsers.contains(ou)) {
				project.getUsers().remove(ou);
			} else {
				newUsers.remove(ou);
			}
		}

		for (ProjectUser nu : newUsers) {
			if (!oldUsers.contains(nu)) {
				project.getUsers().add(nu);
			}
		}
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProject(
			@Valid @ModelAttribute("projectForm") ProjectForm form,
			BindingResult result) {
		if (!result.hasErrors()) {
			if (Status.COMPLETE.equals(form.getProject().getStatus())) {
				if (StringUtils.isNullOrEmpty(form.getProject().getStudyPI())) {
					result.addError(new FieldError("Project", "StudyPI",
							"Study PI cannot be empty"));
				}

				if (null == form.getProject().getQuoteAmount()
						|| 0.0 == form.getProject().getQuoteAmount()) {
					result.addError(new FieldError("Project", "QuoteAmount",
							"Quote amount cannot be zero"));
				}

				if (null == form.getFaculty() || form.getFaculty().size() == 0) {
					result.addError(new FieldError("Project", "Faculty",
							"Assigned to (faculty) cannot be empty"));
				}
			}
		}

		if (result.hasErrors()) {
			initializeProjectForm(form);
			form.setUserType(getUserType(form.getProject().getId()));
			return "/project/edit";
		}

		Project project;
		if (form.getProject().getId() != null) {
			project = service.findProject(form.getProject().getId());
			if (project == null) {
				return "redirect:/project";
			}

			if (!hasProject(project.getId())) {
				return "access/denied";
			}

			BeanUtils.copyProperties(form.getProject(), project);

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				initializeProjectForm(form);
				return "/project/edit";
			}

			service.updateProject(project);
			addUserLogInfo(String.format("updated project %s.",
					project.getProjectName()));
		} else {
			project = new Project();
			BeanUtils.copyProperties(form.getProject(), project);
			project.setCreateDate(new Date());
			project.setCreator(currentUser().getUsername());

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				initializeProjectForm(form);
				return "/project/edit";
			}

			service.addProject(project);
			addUserLogInfo(String.format("created project %s.",
					project.getProjectName()));
		}

		return toProject(project.getId());
	}

	@RequestMapping("/deleteproject/{projectid}")
	@Secured(Permission.ROLE_PROJECT_EDIT)
	public String deleteProject(@PathVariable Long projectid) {
		Project project = service.findProject(projectid);
		if (project != null) {
			if (!isFaculty(project.getId())) {
				return "/access/denied";
			}
			service.removeProject(projectid);

			addUserLogInfo(String.format("deleted project %s.",
					project.getProjectName()));
		}

		return "redirect:/project";
	}

	@RequestMapping("/showproject")
	@Secured({ Permission.ROLE_PROJECT_VIEW })
	public String showProject(@RequestParam("id") Long projectId, ModelMap model) {
		Integer userType = getUserType(projectId);
		if (UserType.NONE.equals(userType) && !isVangardUser()) {
			return "/access/denied";
		}

		Project project = service.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setTechnologyList(service.listTechnology());
			form.setProject(project);
			form.setUserType(userType);
			model.addAttribute("projectForm", form);

			addUserLogInfo(
					String.format("viewed project %s.",
							project.getProjectName()), false);

			return "/project/show";
		} else {
			return "redirect:/project";
		}
	}

	private Integer getUserType(Long projectid) {
		if (isAdmin()) {
			return UserType.VANGARD_FACULTY;
		}

		if (isAdStaff()) {
			return UserType.VANGARD_ADSTAFF;
		}

		Integer result = service.getUserType(currentUser().getId(), projectid);
		if (null == result) {
			result = UserType.NONE;
		}

		return result;
	}

	@RequestMapping("/editprojectcostcenter")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectCostCenter(@RequestParam("id") Long projectId,
			ModelMap model) {
		Project project = service.findProject(projectId);
		if (project == null) {
			return "redirect:/project";
		}

		ProjectCostCenterForm form = new ProjectCostCenterForm();
		form.setProjectId(projectId);
		form.getCostCenters().addAll(project.getCostCenters());
		form.getCostCenters().add(new ProjectCostCenter());
		while (form.getCostCenters().size() < 5) {
			form.getCostCenters().add(new ProjectCostCenter());
		}

		model.addAttribute("costForm", form);

		return "/project/editcostcenter";
	}

	@RequestMapping("/saveprojectcostcenter")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProjectCostCenter(
			@Valid @ModelAttribute("costForm") ProjectCostCenterForm form,
			ModelMap model) {
		Project project = service.findProject(form.getProjectId());
		if (project == null) {
			return "redirect:/project";
		}

		if (!hasProject(project.getId())) {
			return "access/denied";
		}

		List<ProjectCostCenter> centers = new ArrayList<ProjectCostCenter>(
				form.getCostCenters());
		for (int i = centers.size() - 1; i >= 0; i--) {
			if (centers.get(i).getName() == null
					|| centers.get(i).getName().trim().length() == 0) {
				centers.remove(i);
				continue;
			} else if (centers.get(i).getPercentage() == null) {
				centers.get(i).setPercentage(0.0);
			}

			if (centers.get(i).getId() != null) {
				for (ProjectCostCenter pcc : project.getCostCenters()) {
					if (pcc.getId().equals(centers.get(i).getId())) {
						BeanUtils.copyProperties(centers.get(i), pcc);
						centers.set(i, pcc);
					}
				}
			}

			centers.get(i).setProject(project);
		}

		if (centers.size() > 0) {
			ProjectCostCenter remainPCC = null;
			int remainCount = 0;
			for (ProjectCostCenter pcc : centers) {
				if (pcc.getIsRemainingCost()) {
					remainCount++;
					remainPCC = pcc;
				}
			}
			if (remainCount > 1) {
				model.addAttribute("message",
						"Only one cost center allowed for remaining cost!");
				return "/project/editcostcenter";
			}

			double totalPercentage = 0;
			for (ProjectCostCenter pcc : centers) {
				if (!pcc.getIsRemainingCost()) {
					totalPercentage += pcc.getPercentage();
				}
			}
			if (totalPercentage > 100) {
				model.addAttribute("message",
						"Total percentage cannot larger than 100!");
				return "/project/editcostcenter";
			}

			if (totalPercentage < 100 && remainCount == 0) {
				model.addAttribute("message",
						"Total percentage is less than 100 but no remaining cost center defined!");
				return "/project/editcostcenter";
			}

			if (remainPCC != null) {
				remainPCC.setPercentage(100.0 - totalPercentage);
			}
		}

		project.getCostCenters().clear();
		project.getCostCenters().addAll(centers);
		service.updateProject(project);

		return toProject(project.getId());
	}

	@RequestMapping("/addprojecttechnology")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String addProjectTechnology(
			@ModelAttribute("projectForm") ProjectForm form, ModelMap model) {
		Project project = service.findProject(form.getProject().getId());
		if (project != null) {
			Technology tec = service.findTechnology(form.getNewTechnology());
			if (tec != null) {
				ProjectTechnologyForm tecform = new ProjectTechnologyForm();

				ProjectTechnology pt = new ProjectTechnology();
				pt.setTechnologyId(tec.getId());
				pt.setTechnology(tec.getName());

				tecform.setProjectId(project.getId());
				tecform.setTechnology(pt);
				tecform.setReference(tec);

				model.addAttribute("technologyForm", tecform);

				addUserLogInfo(String.format(
						"try to add technology to project %s.",
						project.getProjectName()), false);

				return "/project/edittechnology";
			} else {
				return toProject(project.getId());
			}
		}
		return "redirect:/project";
	}

	@RequestMapping("/editprojecttechnology")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectTechnology(@RequestParam("id") Long id,
			ModelMap model) {
		ProjectTechnology pt = service.findProjectTechnology(id);
		if (pt == null) {
			return "redirect:/project";
		}

		Technology tec = service.findTechnology(pt.getTechnologyId());
		if (tec == null) {
			return "redirect:/project";
		}

		ProjectTechnologyForm tecform = new ProjectTechnologyForm();

		tecform.setProjectId(pt.getProject().getId());
		tecform.setTechnology(pt);
		tecform.setReference(tec);

		List<Long> oldModules = new ArrayList<Long>();
		for (ProjectTechnologyModule ptm : pt.getModules()) {
			oldModules.add(ptm.getModuleId());
		}
		tecform.setModules(oldModules);

		model.addAttribute("technologyForm", tecform);

		addUserLogInfo(
				String.format("try to edit technology %s of project %s",
						pt.getTechnology(), pt.getProject().getProjectName()),
				false);

		return "/project/edittechnology";
	}

	@RequestMapping("/saveprojecttechnology")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProjectTechnology(
			@ModelAttribute("technologyForm") ProjectTechnologyForm form,
			ModelMap model) {
		Project project = service.findProject(form.getProjectId());
		if (project == null) {
			return "redirect:/project";
		}

		if (!hasProject(project.getId())) {
			return "access/denied";
		}

		ProjectTechnology pt;
		if (form.getTechnology().getId() == null) {
			pt = form.getTechnology();
		} else {
			pt = service.findProjectTechnology(form.getTechnology().getId());
			BeanUtils.copyProperties(form.getTechnology(), pt,
					new String[] { "modules" });
		}

		pt.setProject(project);

		List<ProjectTechnologyModule> newModules = new ArrayList<ProjectTechnologyModule>();
		if (null != form.getModules()) {
			int mindex = 0;
			for (Long moduleid : form.getModules()) {
				Module mod = service.findModule(moduleid);
				if(mod == null){
					continue;
				}
				mindex++;
				boolean bfound = false;
				for (ProjectTechnologyModule ptm : pt.getModules()) {
					if (ptm.getModuleId().equals(moduleid)) {
						ptm.setModuleIndex(mindex);
						ptm.setName(mod.getName());
						ptm.setModuleType(mod.getModuleType());
						ptm.setDescription(mod.getDescription());

						if (ptm.getSampleNumber() == null) {
							ptm.setSampleNumber(pt.getSampleNumber());
						}

						if (ptm.getModuleType().equals(ModuleType.PerUnit)
								&& ptm.getOtherUnit() == null) {
							ptm.setOtherUnit(1);
						}

						if (ptm.getPricePerProject() == null) {
							ptm.setPricePerProject(mod.getPricePerProject());
						}

						if (ptm.getPricePerUnit() == null) {
							ptm.setPricePerUnit(mod.getPricePerUnit());
						}

						newModules.add(ptm);
						bfound = true;
						break;
					}
				}

				if (!bfound) {
					ProjectTechnologyModule ptm = new ProjectTechnologyModule();

					ptm.setName(mod.getName());
					ptm.setModuleType(mod.getModuleType());
					ptm.setDescription(mod.getDescription());
					ptm.setPricePerProject(mod.getPricePerProject());
					ptm.setPricePerUnit(mod.getPricePerUnit());

					ptm.setModuleId(mod.getId());
					ptm.setModuleIndex(mindex);
					ptm.setSampleNumber(pt.getSampleNumber());
					ptm.setTechnology(pt);

					if (ptm.getModuleType() != null
							&& ptm.getModuleType().equals(ModuleType.PerUnit)
							&& ptm.getOtherUnit() == null) {
						ptm.setOtherUnit(1);
					}

					newModules.add(ptm);
				}
			}
		}
		pt.getModules().clear();
		pt.getModules().addAll(newModules);

		if (pt.getPlatformId() != null) {
			Platform pf = service.findPlatform(pt.getPlatformId());
			if (pf != null) {
				pt.setPlatform(pf.getName());
			}
		}

		if (form.getTechnology().getId() == null) {
			service.addProjectTechnology(pt);
			addUserLogInfo(String.format("added technology %s for project %s",
					pt.getTechnology(), pt.getProject().getProjectName()));
		} else {
			service.updateProjectTechnology(pt);
			addUserLogInfo(String.format("updated technology %s of project %s",
					pt.getTechnology(), pt.getProject().getProjectName()));
		}

		return toProject(form.getProjectId());
	}

	@RequestMapping("/deleteprojecttechnology/{ptid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String deleteProjectTechnology(@PathVariable Long ptid,
			ModelMap model) {
		ProjectTechnology pt = service.findProjectTechnology(ptid);
		if (pt == null) {
			return "redirect:/project";
		}

		if (isFaculty(pt.getProject().getId())) {
			service.removeProjectTechnology(pt);
			addUserLogInfo(String.format("deleted technology %s of project %s",
					pt.getTechnology(), pt.getProject().getProjectName()));
		}

		return toProject(pt.getProject().getId());
	}

	@RequestMapping("/saveprojectcomment")
	@Secured({ Permission.ROLE_PROJECT_VIEW })
	public String saveProjectComment(
			@ModelAttribute("projectForm") ProjectForm form, ModelMap model) {
		if (form.getComment() == null || form.getComment().trim().length() == 0) {
			return "redirect:/project";
		}

		Project project = service.findProject(form.getProject().getId());
		if (project == null) {
			return "redirect:/project";
		}

		ProjectComment comment = new ProjectComment();
		comment.setProject(project);
		comment.setComment(form.getComment().trim());
		comment.setCommentDate(new Date());
		comment.setCommentUser(currentUser().getEmail());

		service.addProjectComment(comment);

		addUserLogInfo(String.format("added comment to project %s",
				project.getProjectName()));

		return toProject(project.getId());
	}

	private String toProject(Long projectId) {
		return "redirect:/showproject?id=" + projectId.toString();
	}

	@RequestMapping("/deleteprojectcomment/{commentid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String deleteProjectComment(@PathVariable Long commentid,
			ModelMap model) {
		ProjectComment comment = service.findProjectComment(commentid);
		if (comment == null) {
			return "redirect:/project";
		}

		if (isFaculty(comment.getProject().getId())) {
			service.removeProjectComment(comment);
			addUserLogInfo(String.format("deleted comment from project %s",
					comment.getProject().getProjectName()));
		}

		return toProject(comment.getProject().getId());
	}

	@RequestMapping("/editptm")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectTechnologyModule(@RequestParam("id") Long id,
			ModelMap model) {
		ProjectTechnologyModule ptm = service.findProjectTechnologyModule(id);
		Project project = ptm.getTechnology().getProject();

		model.put("ptmForm", ptm);

		addUserLogInfo(String.format(
				"try to edit module %s of technology %s of project %s",
				ptm.getName(), ptm.getTechnology().getTechnology(),
				project.getProjectName()), false);

		return "/project/editptm";
	}

	@RequestMapping("/saveptm")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProjectTechnologyModule(
			@Valid @ModelAttribute("ptmForm") ProjectTechnologyModule ptm,
			ModelMap model, BindingResult result) {
		if (result.hasErrors()) {
			return "/project/editptm";
		}

		ProjectTechnologyModule oldptm = service
				.findProjectTechnologyModule(ptm.getId());
		if (oldptm == null) {
			return "/access/error?message=cannot find module "
					+ ptm.getId().toString();
		}

		if (!hasProject(oldptm.getTechnology().getProject().getId())) {
			return "access/denied";
		}

		oldptm.setSampleNumber(ptm.getSampleNumber());
		oldptm.setOtherUnit(ptm.getOtherUnit());
		service.updateProjectTechnologyModule(oldptm);
		addUserLogInfo(String.format(
				"updated module %s of technology %s of project %s",
				oldptm.getName(), oldptm.getTechnology().getTechnology(),
				oldptm.getTechnology().getProject().getProjectName()));

		return toProject(oldptm.getTechnology().getProject().getId());
	}

	@RequestMapping("/editptms")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectTechnologyModules(
			@RequestParam("id") Long projectId, ModelMap model) {
		Project project = service.findProject(projectId);
		if (project == null) {
			return "/project";
		}

		List<ProjectTechnologyModule> modules = service
				.getModuleInProject(projectId);
		ModuleForm form = new ModuleForm();
		form.setModules(modules);
		form.setProjectId(projectId);
		form.setEstimatedPrice(String.format("%.0f",
				estimateProjectPrice(modules)));
		form.setEstimateOnly(false);
		model.put("ptmForm", form);

		addUserLogInfo(
				String.format("try to edit modules of project %s",
						project.getProjectName()), false);

		return "/project/editptms";
	}

	@RequestMapping("/saveptms")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProjectTechnologyModules(
			@Valid @ModelAttribute("ptmForm") ModuleForm form, ModelMap model,
			BindingResult result) {
		if (result.hasErrors()) {
			model.put("ptmForm", form);
			return "/project/editptms";
		}

		Project project = service.findProject(form.getProjectId());
		if (!isFaculty(project.getId())) {
			return "/access/denied";
		}

		if (form.getModules() != null && form.getModules().size() > 0) {
			List<ProjectTechnologyModule> oldptms = new ArrayList<ProjectTechnologyModule>();
			for (ProjectTechnologyModule ptm : form.getModules()) {
				ProjectTechnologyModule oldptm = service
						.findProjectTechnologyModule(ptm.getId());
				if (oldptm == null) {
					return "/access/error?message=cannot find module "
							+ ptm.getId().toString();
				}
				oldptm.setSampleNumber(ptm.getSampleNumber());
				oldptm.setOtherUnit(ptm.getOtherUnit());
				oldptms.add(oldptm);
				if (project == null) {
					project = oldptm.getTechnology().getProject();
				}
			}
			service.updateProjectTechnologyModules(oldptms);
			addUserLogInfo(String.format("update modules of project %s",
					project.getProjectName()));

			return toProject(project.getId());
		} else {
			return toProject(form.getProjectId());
		}
	}

	@RequestMapping(value = "/estimateptms", method = RequestMethod.GET)
	@Secured({ Permission.ROLE_ESTIMATION })
	public String estimatePrice(ModelMap model) {
		ModuleForm form = new ModuleForm();

		List<ProjectTechnologyModule> lst = new ArrayList<ProjectTechnologyModule>();

		List<Technology> tecs = service.listTechnology();
		for (Technology tec : tecs) {
			ProjectTechnology pt = new ProjectTechnology();
			pt.setTechnology(tec.getName());
			BeanUtils.copyProperties(tec, pt, new String[] {});
			for (Module mod : tec.getModules()) {
				ProjectTechnologyModule ptm = new ProjectTechnologyModule();
				BeanUtils.copyProperties(mod, ptm, new String[] { "id",
						"technology" });
				ptm.setTechnology(pt);
				lst.add(ptm);
			}
		}

		form.setModules(lst);
		form.setEstimatedPrice("0.0");
		form.setEstimateOnly(true);
		model.put("ptmForm", form);
		return "/project/editptms";
	}

	@RequestMapping(value = "/estimateptms", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_ESTIMATION })
	public String estimateProjectTechnologyModules(ModuleForm form,
			ModelMap model, BindingResult result) {
		if (!result.hasErrors()) {
			form.setEstimatedPrice("0");
			List<ProjectTechnologyModule> modules = form.getModules();

			double totalValue = estimateProjectPrice(modules);
			form.setEstimatedPrice(String.format("%.0f", totalValue));

			if (form.getEstimateOnly()) {
				HashSet<String> tecs = new HashSet<String>();
				for (ProjectTechnologyModule mod : modules) {
					if (mod.getTotalFee() > 0) {
						tecs.add(mod.getTechnology().getTechnology());
					}
				}

				if (tecs.size() > 0) {
					for (int i = modules.size() - 1; i >= 0; i--) {
						if (!tecs.contains(modules.get(i).getTechnology()
								.getTechnology())) {
							modules.remove(i);
						}
					}

					form.setModules(modules);
				}
			}
		}
		model.put("ptmForm", form);
		return "/project/editptms";
	}

	private double estimateProjectPrice(List<ProjectTechnologyModule> modules) {
		double result = 0.0;
		if (modules != null && modules.size() > 0) {
			for (ProjectTechnologyModule ptm : modules) {
				result += ptm.getTotalFee();
			}
		}
		return result;
	}

	@RequestMapping("/showlog")
	@Secured({ Role.ROLE_ADMIN })
	public String listLog(ModelMap model) {
		model.put("logs", service.listLog());
		return "/log/list";
	}

	@RequestMapping(value = "/getEstimatePrice", method = RequestMethod.POST)
	public @ResponseBody
	String getEstimatePrice(@RequestParam("projectid") Long projectId) {
		List<ProjectTechnologyModule> modules = service
				.getModuleInProject(projectId);
		double value = estimateProjectPrice(modules);
		return String.format("%.0f", value);
	}

	@RequestMapping(value = "/addfiles", method = RequestMethod.GET)
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String inputfiles(@RequestParam("projectid") Long projectId,
			ModelMap model) {
		FileUploadForm uploadForm = new FileUploadForm();
		uploadForm.setProjectId(projectId);
		model.addAttribute("uploadForm", uploadForm);
		return "/project/uploadfile";
	}

	@RequestMapping(value = "/savefiles", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveFiles(
			@ModelAttribute("uploadForm") FileUploadForm uploadForm,
			ModelMap model) {
		Project project = service.findProject(uploadForm.getProjectId());
		if (project == null) {
			return "redirect:/project";
		}

		if (!hasProject(project.getId())) {
			return "access/denied";
		}

		List<MultipartFile> files = uploadForm.getFiles();
		if (null != files && files.size() > 0) {
			List<ProjectFile> pfiles = new ArrayList<ProjectFile>();
			for (MultipartFile multipartFile : files) {
				if (!multipartFile.isEmpty()) {
					ProjectFile file = new ProjectFile();
					file.setProject(project);
					file.setFileName(multipartFile.getOriginalFilename());
					file.setCreateDate(new Date());
					file.setCreateUser(currentUser().getEmail());
					file.setContentType(multipartFile.getContentType());
					file.setFileSize(multipartFile.getSize());
					try {
						ProjectFileData data = new ProjectFileData();
						data.setFileData(multipartFile.getBytes());
						data.setFile(file);
						file.setData(data);
					} catch (Exception ex) {
						addUserLogError(String.format(
								"upload file %s to project %s failed : %s",
								file.getFileName(), project.getProjectName(),
								ex.getMessage()), false);
						uploadForm.setMessage(ex.getMessage());
						model.addAttribute("uploadForm", uploadForm);
						return "/project/uploadfile";
					}
					pfiles.add(file);
				}
			}

			if (pfiles.size() > 0) {
				try {
					service.addProjectFiles(pfiles);
					for (int i = 0; i < pfiles.size(); i++) {
						addUserLogInfo(String.format("uploaded "
								+ pfiles.get(i).getFileName()
								+ " for project %s", pfiles.size(),
								project.getProjectName()), true);
					}
				} catch (Exception ex) {
					addUserLogError(String.format(
							"upload %d files to project %s failed : %s",
							pfiles.size(), project.getProjectName(),
							ex.getMessage()), false);
					uploadForm.setMessage(ex.getMessage());
					model.addAttribute("uploadForm", uploadForm);
					return "/project/uploadfile";
				}
			}
		}

		return toProject(project.getId());
	}

	@RequestMapping("/deletefile/{fileid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String deleteProjectFile(@PathVariable Long fileid, ModelMap model) {
		ProjectFile file = service.findProjectFile(fileid);
		if (file == null) {
			return "redirect:/project";
		}

		if (isFaculty(file.getProject().getId())) {
			service.removeProjectFile(file);
			addUserLogInfo(String.format("deleted file %s from project %s",
					file.getFileName(), file.getProject().getProjectName()));
		}

		return toProject(file.getProject().getId());
	}

	@RequestMapping("/downloadfile/{fileid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public void downloadfile(@PathVariable Long fileid,
			HttpServletResponse response) throws IOException {
		ProjectFile file = service.findProjectFile(fileid);
		if (file == null) {
			return;
		}

		Project project = file.getProject();
		Integer userType = getUserType(project.getId());
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.VANGARD_ADSTAFF)) {
			response.setContentType(file.getContentType());
			response.setHeader("Content-Disposition", "inline; filename="
					+ file.getFileName());

			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(file.getData().getFileData(), out);
			out.close();

			addUserLogInfo(String.format("downloaded file %s of project %s",
					file.getFileName(), project.getProjectName()));
		}
	}

	@RequestMapping(value = "/assignmoduleprices", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String assignModulePrices(@RequestParam("projectid") Long projectId,
			ModelMap model) {
		if (!isFaculty(projectId)) {
			return "/access/denied";
		}

		service.assignModulePrice(projectId);
		return toProject(projectId);
	}
}