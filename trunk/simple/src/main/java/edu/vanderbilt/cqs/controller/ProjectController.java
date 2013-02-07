package edu.vanderbilt.cqs.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
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
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTechnologyForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ProjectController extends RootController {
	@Autowired
	private ProjectService service;

	@Autowired
	private Validator validator;

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
		if (user.getUserType() != uType) {
			return false;
		}

		if (!hasUser(userList, user.getUser())) {
			userList.add(user.getUser());
		}

		return true;
	}

	private boolean hasUser(List<User> userList, User user) {
		for (User item : userList) {
			if (item.getId() == user.getId()) {
				return true;
			}
		}

		return false;
	}

	@RequestMapping("/editproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProject(@RequestParam("id") Long projectId, ModelMap model) {
		addUserLogInfo("try to edit project.", false);
		Integer userType = getUserType(projectId);
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

		Project project = service.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setProject(project);
			form.setUserType(userType);

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
		if (result.hasErrors()) {
			initializeProjectForm(form);
			form.setUserType(getUserType(form.getProject().getId()));
			return "/project/edit";
		}

		Project project;
		if (form.getProject().getId() != null) {
			project = service.findProject(form.getProject().getId());
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
		if (userType == UserType.NONE) {
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
		if (isCurrentPowerUser()) {
			return UserType.VANGARD_FACULTY;
		}

		if (currentUser().hasRole(Role.ROLE_VANGARD_ADSTAFF)) {
			return UserType.VANGARD_ADSTAFF;
		}

		return service.getUserType(currentUser().getId(), projectid);
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
		int mindex = 0;
		for (Long moduleid : form.getModules()) {
			Module mod = service.findModule(moduleid);
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
					
					if(ptm.getPricePerProject() == null){
						ptm.setPricePerProject(mod.getPricePerProject());
					}
					
					if(ptm.getPricePerUnit() == null){
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
				newModules.add(ptm);
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
		if (ptid == null) {
			return "redirect:/project";
		}

		Long projectId = pt.getProject().getId();
		Integer userType = getUserType(projectId);
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.ADMIN)) {
			service.removeProjectTechnology(pt);
			addUserLogInfo(String.format("deleted technology %s of project %s",
					pt.getTechnology(), pt.getProject().getProjectName()));
		}

		return toProject(projectId);
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

		Project project = comment.getProject();
		Integer userType = getUserType(project.getId());
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.ADMIN)) {
			service.removeProjectComment(comment);
			addUserLogInfo(String.format("deleted comment from project %s",
					project.getProjectName()));
		}

		return toProject(project.getId());
	}

	@RequestMapping("/editptm")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectTechnologyModule(@RequestParam("id") Long id,
			ModelMap model) {
		ProjectTechnologyModule ptm = service.findProjectTechnologyModule(id);
		Project project = ptm.getTechnology().getProject();
		Integer userType = getUserType(project.getId());
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

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

		Integer userType = getUserType(project.getId());
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

		List<ProjectTechnologyModule> modules = service
				.getModuleInProject(projectId);
		ModuleForm form = new ModuleForm();
		form.setModules(modules);
		form.setProjectId(projectId);
		form.setEstimatedPrice(String.format("%.0f",
				estimateProjectPrice(modules)));
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
		Integer userType = getUserType(project.getId());
		if (userType != UserType.VANGARD_FACULTY) {
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

	@RequestMapping("/estimateptms")
	private String estimateProjectTechnologyModules(ModuleForm form,
			ModelMap model, BindingResult result) {
		if (!result.hasErrors()) {
			form.setEstimatedPrice("0");
			List<ProjectTechnologyModule> modules = form.getModules();

			double totalValue = estimateProjectPrice(modules);
			form.setEstimatedPrice(String.format("%.0f", totalValue));
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

		Project project = file.getProject();
		Integer userType = getUserType(project.getId());
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.ADMIN)) {
			service.removeProjectFile(file);
			addUserLogInfo(String.format("deleted file %s from project %s",
					file.getFileName(), project.getProjectName()));
		}

		return toProject(project.getId());
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
		Integer userType = getUserType(projectId);
		if (userType != UserType.VANGARD_FACULTY) {
			return "/access/denied";
		}

		service.assignModulePrice(projectId);
		return toProject(projectId);
	}

	@RequestMapping("/export")
	@Secured({ Role.ROLE_ADMIN, Role.ROLE_VANGARD_ADSTAFF })
	public void export(
			@RequestParam(value = "projectid", required = false) Long projectid,
			HttpServletResponse response) throws IOException {
		List<Project> projects = service.listProject();
		String daystr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = "VANGARD_Project_To_" + daystr + ".xls";
		String loginfo = "export project until " + daystr;

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename="
				+ filename);
		response.setHeader("extension", "xls");

		OutputStream out = response.getOutputStream();

		Workbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);

		// Create cell style for the body
		Font bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 10);

		CellStyle bodyStyle = getStringStyle(workbook, bodyFont);

		Sheet sheet = workbook.createSheet();

		Row row = sheet.createRow(0);
		createCell(createHelper, headerStyle, row, 0, "ID");
		createCell(createHelper, headerStyle, row, 1, "Study");
		createCell(createHelper, headerStyle, row, 2, "Study PI");
		createCell(createHelper, headerStyle, row, 3, "Faculty");
		createCell(createHelper, headerStyle, row, 4, "Staff");
		createCell(createHelper, headerStyle, row, 5, "Completed");
		createCell(createHelper, headerStyle, row, 6, "BV-data?");
		createCell(createHelper, headerStyle, row, 7, "BV-data to PI");
		createCell(createHelper, headerStyle, row, 8, "BV-sample?");
		createCell(createHelper, headerStyle, row, 9, "BV-redeposit");
		createCell(createHelper, headerStyle, row, 10, "Granted?");
		createCell(createHelper, headerStyle, row, 11, "Billed");
		createCell(createHelper, headerStyle, row, 12, "Status");

		int rowNo = 1;
		for (Project project : projects) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);

			createCell(createHelper, bodyStyle, row, 0,
					project.getProjectName());

			createCell(createHelper, bodyStyle, row, 1, project.getName());
			createCell(createHelper, bodyStyle, row, 2, project.getStudyPI());
			createCell(createHelper, bodyStyle, row, 3,
					project.getFacultyName());
			createCell(createHelper, bodyStyle, row, 4, project.getStaffName());
			createCell(createHelper, bodyStyle, row, 5,
					project.getWorkCompleted());
			createCell(createHelper, bodyStyle, row, 6,
					project.getIsBioVUDataRequest());
			createCell(createHelper, bodyStyle, row, 7,
					project.getBioVUDataDeliveryDate());
			createCell(createHelper, bodyStyle, row, 8,
					project.getIsBioVUSampleRequest());
			createCell(createHelper, bodyStyle, row, 9,
					project.getBioVURedepositDate());
			createCell(createHelper, bodyStyle, row, 10, project.getIsGranted());
			createCell(createHelper, bodyStyle, row, 11,
					project.getBilledInCORES());
			createCell(createHelper, bodyStyle, row, 12, project.getStatus());
			rowNo++;
		}
		sheet.autoSizeColumn(0);

		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);

		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);

		sheet.setColumnWidth(11, 13 * 256);

		sheet.autoSizeColumn(12);

		workbook.write(out);

		out.close();

		addUserLogInfo(loginfo);
	}

	@RequestMapping("/exportproject")
	@Secured({ Role.ROLE_ADMIN, Role.ROLE_VANGARD_ADSTAFF })
	public void exportProject(@RequestParam(value = "id") Long projectid,
			HttpServletResponse response) throws IOException {
		Project project = service.findProject(projectid);
		if (project == null) {
			return;
		}

		String daystr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = project.getProjectName() + "_" + daystr + ".xls";
		String loginfo = "export " + filename;

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename="
				+ filename);
		response.setHeader("extension", "xls");

		OutputStream out = response.getOutputStream();

		Workbook workbook = new HSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);

		// Create cell style for the body
		Font bodyFont = workbook.createFont();
		bodyFont.setFontHeightInPoints((short) 10);

		CellStyle stringStyle = getStringStyle(workbook, bodyFont);
		CellStyle wrapStringStyle = getWrapStringStyle(workbook, bodyFont);
		CellStyle leftMoneyStyle = getLeftAlignMoneyStyle(workbook, bodyFont);
		CellStyle moneyStyle = getMoneyStyle(workbook, bodyFont);
		CellStyle intStyle = getIntegerStyle(workbook, bodyFont);
		CellStyle dateStyle = getDateStyle(workbook, bodyFont);
		CellStyle dateTimeStyle = getDateTimeStyle(workbook, bodyFont);

		Sheet sheet = workbook.createSheet("Overview");
		int rowNo = 0;
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Project",
				stringStyle, project.getProjectName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Contact date",
				dateStyle, project.getContactDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Contact",
				stringStyle, project.getContact());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU data request?", stringStyle,
				project.getIsBioVUDataRequest());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU sample request?", stringStyle,
				project.getIsBioVUSampleRequest());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"VANTAGE project?", stringStyle, project.getIsVantage());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Outside project?", stringStyle, project.getIsOutside());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Granted?",
				stringStyle, project.getIsGranted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Study descriptive name", stringStyle, project.getName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Study PI",
				stringStyle, project.getStudyPI());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Quote amount",
				leftMoneyStyle, project.getQuoteAmount());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Contract date", dateStyle, project.getContractDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Assigned to (faculty)", stringStyle, project.getFacultyName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Study status",
				stringStyle, project.getStatus());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Assigned to (staff)", stringStyle, project.getStaffName());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle, "Work started",
				dateStyle, project.getWorkStarted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Work completed", dateStyle, project.getWorkCompleted());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU data delivery to investigator (date)", dateStyle,
				project.getBioVUDataDeliveryDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"BioVU redeposit (date)", dateStyle,
				project.getBioVURedepositDate());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Cost center to bill", stringStyle,
				project.getCostCenterToBill());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Request cost center setup in CORES (date)", dateStyle,
				project.getRequestCostCenterSetupInCORES());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Requested by (name)", stringStyle, project.getRequestedBy());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed in CORES (date)", dateStyle, project.getBilledInCORES());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed by (name)", stringStyle, project.getBilledBy());
		rowNo = addRow(createHelper, sheet, rowNo, headerStyle,
				"Billed amount", leftMoneyStyle, project.getBilledAmount());

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		sheet = workbook.createSheet("Details");
		Row row = sheet.createRow(0);

		int colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Technology");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Platform");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"Sample number");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Other unit");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Module");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Per project");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Per unit");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Module type");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"Project setup fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Unit fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Total fee");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Notes");

		int totalColCount = colNo - 1;

		rowNo = 1;
		for (ProjectTechnology tec : project.getTechnologies()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);

			colNo = 0;
			colNo = createCell(createHelper, stringStyle, row, colNo,
					tec.getTechnology());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					tec.getPlatform());

			boolean bFirst = true;
			for (ProjectTechnologyModule mod : tec.getModules()) {
				int lastColNo = colNo;
				if (!bFirst) {
					rowNo++;
					row = sheet.createRow(rowNo);
				} else {
					bFirst = false;
				}
				lastColNo = createCell(createHelper, intStyle, row, lastColNo,
						mod.getSampleNumber());
				lastColNo = createCell(createHelper, intStyle, row, lastColNo,
						mod.getOtherUnit());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getName());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getPricePerProject());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getPricePerUnit());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getModuleTypeString());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getProjectSetupFee());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getUnitFee());
				lastColNo = createCell(createHelper, moneyStyle, row,
						lastColNo, mod.getTotalFee());
				lastColNo = createCell(createHelper, stringStyle, row,
						lastColNo, mod.getDescription());
			}
			rowNo++;
		}

		row = sheet.createRow(rowNo);
		row.setHeight((short) -1);
		colNo = createCell(createHelper, stringStyle, row, 0,
				"Amount estimated");
		Cell c = doCreateCell(moneyStyle, row, totalColCount - 3);
		c.setCellFormula("SUM(I2:I" + String.valueOf(rowNo) + ")");
		c = doCreateCell(moneyStyle, row, totalColCount - 2);
		c.setCellFormula("SUM(J2:J" + String.valueOf(rowNo) + ")");
		c = doCreateCell(moneyStyle, row, totalColCount - 1);
		c.setCellFormula("SUM(K2:K" + String.valueOf(rowNo) + ")");

		for (int i = 0; i <= totalColCount; i++) {
			sheet.autoSizeColumn(i);
		}

		// Export files
		sheet = workbook.createSheet("Files");
		row = sheet.createRow(0);
		colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Upload date");
		colNo = createCell(createHelper, headerStyle, row, colNo, "Upload user");
		colNo = createCell(createHelper, headerStyle, row, colNo, "File name");
		colNo = createCell(createHelper, headerStyle, row, colNo, "File size");
		rowNo = 1;
		for (ProjectFile file : project.getFiles()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);
			colNo = 0;
			colNo = createCell(createHelper, dateTimeStyle, row, colNo,
					file.getCreateDate());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getCreateUser());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getFileName());
			colNo = createCell(createHelper, stringStyle, row, colNo,
					file.getFileSizeString());
			rowNo++;
		}

		for (int i = 0; i < 4; i++) {
			sheet.autoSizeColumn(i);
		}

		// Export comments
		sheet = workbook.createSheet("Comments");
		row = sheet.createRow(0);
		colNo = 0;
		colNo = createCell(createHelper, headerStyle, row, colNo, "Date");
		colNo = createCell(createHelper, headerStyle, row, colNo,
				"User/Comment");
		rowNo = 1;
		for (ProjectComment comment : project.getComments()) {
			row = sheet.createRow(rowNo);
			row.setHeight((short) -1);
			colNo = 0;
			colNo = createCell(createHelper, dateTimeStyle, row, colNo,
					comment.getCommentDate());
			colNo = createCell(
					createHelper,
					wrapStringStyle,
					row,
					colNo,
					comment.getCommentUser() + "wrote:\n"
							+ comment.getComment());
			rowNo++;
		}

		for (int i = 0; i < 2; i++) {
			sheet.autoSizeColumn(i);
		}

		workbook.write(out);

		out.close();

		addUserLogInfo(loginfo);
	}

	private CellStyle getDateTimeStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		result.setDataFormat(df.getFormat("yyyy-MM-dd hh:mm:ss"));
		return result;
	}

	private CellStyle getStringStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		return result;
	}

	private CellStyle getWrapStringStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(true);
		return result;
	}

	private CellStyle getIntegerStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setAlignment(CellStyle.ALIGN_RIGHT);
		result.setDataFormat((short) 1);
		return result;
	}

	private CellStyle getLeftAlignMoneyStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setDataFormat((short) 4);
		return result;
	}

	private CellStyle getMoneyStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = getStringStyle(workbook, bodyFont);
		result.setAlignment(CellStyle.ALIGN_RIGHT);
		result.setDataFormat((short) 4);
		return result;
	}

	private CellStyle getDateStyle(Workbook workbook, Font bodyFont) {
		CellStyle result = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		result.setFont(bodyFont);
		result.setAlignment(CellStyle.ALIGN_LEFT);
		result.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		result.setWrapText(false);
		result.setDataFormat(df.getFormat("yyyy-MM-dd"));
		return result;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			List<String> values) {
		return addRow(createHelper, sheet, rowNo, headerStyle, name, bodyStyle,
				convertList(values));
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			Double value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle, Date value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			Boolean value) {
		return addRow(createHelper, sheet, rowNo, headerStyle, name, bodyStyle,
				getBoolString(value));
	}

	private int addRow(CreationHelper createHelper, Sheet sheet, int rowNo,
			CellStyle headerStyle, String name, CellStyle bodyStyle,
			String value) {
		Row row = sheet.createRow(rowNo);
		createCell(createHelper, headerStyle, row, 0, name);
		createCell(createHelper, bodyStyle, row, 1, value);
		return rowNo + 1;
	}

	private String convertList(List<String> aList) {
		StringBuilder sb = new StringBuilder();
		if (aList != null) {
			boolean isFirst = true;
			for (String name : aList) {
				if (isFirst) {
					sb.append(name);
					isFirst = false;
				} else {
					sb.append("\n").append(name);
				}
			}
		}
		String value = sb.toString();
		return value;
	}

	private Cell doCreateCell(CellStyle bodyStyle, Row row, int colNo) {
		Cell result = row.createCell(colNo);
		result.setCellStyle(bodyStyle);
		return result;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, String value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(createHelper.createRichTextString(value));
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Double value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Integer value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Boolean value) {
		return createCell(createHelper, bodyStyle, row, colNo,
				getBoolString(value));
	}

	private String getBoolString(Boolean isBioVUDataRequest) {
		return (isBioVUDataRequest != null) && isBioVUDataRequest ? "yes"
				: "no";
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, Date value) {
		Cell c = doCreateCell(bodyStyle, row, colNo);
		if (value != null) {
			c.setCellValue(value);
		}
		return colNo + 1;
	}

	private int createCell(CreationHelper createHelper, CellStyle bodyStyle,
			Row row, int colNo, List<String> aList) {
		return createCell(createHelper, bodyStyle, row, colNo,
				convertList(aList));
	}
}