package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.ProjectUser;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTaskStatusForm;
import edu.vanderbilt.cqs.form.ProjectTechnologyForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ProjectController extends RootController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@Secured({ Permission.ROLE_PROJECT_VIEW })
	@RequestMapping("/project")
	public String listProject(ModelMap model) {
		addUserLogInfo(currentUser().getUsername() + " list project.", false);

		model.put("projectList", projectService.listProject());

		return "/project/list";
	}

	@RequestMapping("/addproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String addProject(ModelMap model) {
		addUserLogInfo(currentUser().getUsername() + " try to add project.",
				false);

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
		form.setTechnologyList(projectService.listValidTechnology());

		List<User> users = projectService.listValidUser();

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
		addUserLogInfo(currentUser().getUsername() + " try to edit project.");
		Integer userType = getUserType(projectId);
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

		Project project = projectService.findProject(projectId);
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

	private List<Long> getIdListFromUserList(Set<ProjectUser> users,
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
			User user = projectService.findUser(id);
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
			project = projectService.findProject(form.getProject().getId());
			BeanUtils.copyProperties(form.getProject(), project, new String[] {
					"technologies", "users", "comments" });

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				initializeProjectForm(form);
				return "/project/edit";
			}

			projectService.updateProject(project);
			addUserLogInfo(String.format("%s updated project %s [%d]",
					currentUser().getUsername(), project.getName(),
					project.getId()));
		} else {
			project = new Project();
			BeanUtils.copyProperties(form.getProject(), project, new String[] {
					"technologies", "users" });
			project.setCreateDate(new Date());
			project.setCreator(currentUser().getUsername());

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				initializeProjectForm(form);
				return "/project/edit";
			}

			projectService.addProject(project);
			addUserLogInfo(String.format("%s created project %s [%d}]",
					currentUser().getUsername(), project.getName(),
					project.getId()));
		}

		return toProject(project.getId());
	}

	@RequestMapping("/deleteproject/{projectid}")
	@Secured(Permission.ROLE_PROJECT_EDIT)
	public String deleteProject(@PathVariable Long projectid) {
		Project project = projectService.findProject(projectid);
		if (project != null) {
			projectService.removeProject(projectid);

			addUserLogInfo(String.format("%s deleted project %s [%d]",
					currentUser().getUsername(), project.getName(),
					project.getId()));
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

		Project project = projectService.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setTechnologyList(projectService.listTechnology());
			form.setProject(project);
			form.setUserType(userType);
			model.addAttribute("projectForm", form);

			addUserLogInfo(String.format("%s viewed project %s [%d]",
					currentUser().getUsername(), project.getName(),
					project.getId()), false);

			return "/project/show";
		} else {
			return "redirect:/project";
		}
	}

	private Integer getUserType(Long projectid) {
		Integer userType = isCurrentPowerUser() ? UserType.VANGARD_FACULTY
				: projectService.getUserType(currentUser().getId(), projectid);
		return userType;
	}

	@RequestMapping(value = "/getStatusList", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_PROJECT_VIEW })
	public @ResponseBody
	List<ProjectTaskStatusForm> getStatusList(
			@RequestParam("taskid") Long taskid) {
		return doGetStatusList(taskid);
	}

	private List<ProjectTaskStatusForm> doGetStatusList(Long taskid) {
		List<ProjectTaskStatusForm> result = new ArrayList<ProjectTaskStatusForm>();
		ProjectTask task = projectService.findProjectTask(taskid);
		if (task != null) {
			for (ProjectTaskStatus pts : task.getStatuses()) {
				ProjectTaskStatusForm form = new ProjectTaskStatusForm();
				BeanUtils.copyProperties(pts, form);
				form.setStatusString(pts.getStatus());
				form.setUpdateDateString(pts.getUpdateDate().toString());
				result.add(form);
			}
		}
		return result;
	}

	@RequestMapping("/addprojecttechnology")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String addProjectTechnology(
			@ModelAttribute("projectForm") ProjectForm form, ModelMap model) {
		Project project = projectService.findProject(form.getProject().getId());
		if (project != null) {
			Technology tec = projectService.findTechnology(form
					.getNewTechnology());
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
						"%s try to add technology to project %s [%d]",
						currentUser().getUsername(), project.getName(),
						project.getId()), false);

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
		ProjectTechnology pt = projectService.findProjectTechnology(id);
		if (pt == null) {
			return "redirect:/project";
		}

		Technology tec = projectService.findTechnology(pt.getTechnologyId());
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

		addUserLogInfo(String.format(
				"%s try to edit technology %s of project %s [%d]",
				currentUser().getUsername(), pt.getTechnology(), pt
						.getProject().getName(), pt.getProject().getId()),
				false);

		return "/project/edittechnology";
	}

	@RequestMapping("/saveprojecttechnology")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProjectTechnology(
			@ModelAttribute("technologyForm") ProjectTechnologyForm form,
			ModelMap model) {
		Project project = projectService.findProject(form.getProjectId());
		if (project == null) {
			return "redirect:/project";
		}

		ProjectTechnology pt;
		if (form.getTechnology().getId() == null) {
			pt = form.getTechnology();
		} else {
			pt = projectService.findProjectTechnology(form.getTechnology()
					.getId());
			BeanUtils.copyProperties(form.getTechnology(), pt,
					new String[] { "modules" });
		}

		pt.setProject(project);

		List<ProjectTechnologyModule> newModules = new ArrayList<ProjectTechnologyModule>();
		int mindex = 0;
		for (Long moduleid : form.getModules()) {
			Module mod = projectService.findModule(moduleid);
			mindex++;
			boolean bfound = false;
			for (ProjectTechnologyModule ptm : pt.getModules()) {
				if (ptm.getModuleId().equals(moduleid)) {
					ptm.setModuleIndex(mindex);
					ptm.setName(mod.getName());
					newModules.add(ptm);
					bfound = true;
					break;
				}
			}

			if (!bfound) {
				ProjectTechnologyModule ptm = new ProjectTechnologyModule();
				ptm.setTechnology(pt);
				ptm.setModuleIndex(mindex);
				ptm.setModuleId(mod.getId());
				ptm.setName(mod.getName());
				newModules.add(ptm);
			}
		}

		pt.getModules().clear();
		pt.getModules().addAll(newModules);

		if (pt.getPlatformId() != null) {
			Platform pf = projectService.findPlatform(pt.getPlatformId());
			if (pf != null) {
				pt.setPlatform(pf.getName());
			}
		}

		if (form.getTechnology().getId() == null) {
			projectService.addProjectTechnology(pt);
			addUserLogInfo(String.format(
					"%s added technology %s of project %s [%d]",
					currentUser().getUsername(), pt.getTechnology(), pt
							.getProject().getName(), pt.getProject().getId()));
		} else {
			projectService.updateProjectTechnology(pt);
			addUserLogInfo(String.format(
					"%s updated technology %s of project %s [%d]",
					currentUser().getUsername(), pt.getTechnology(), pt
							.getProject().getName(), pt.getProject().getId()));
		}

		return toProject(form.getProjectId());
	}

	@RequestMapping("/deleteprojecttechnology/{ptid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String deleteProjectTechnology(@PathVariable Long ptid,
			ModelMap model) {
		ProjectTechnology pt = projectService.findProjectTechnology(ptid);
		if (ptid == null) {
			return "redirect:/project";
		}

		Long projectId = pt.getProject().getId();
		Integer userType = getUserType(projectId);
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.ADMIN)) {
			projectService.removeProjectTechnology(pt);
			addUserLogInfo(String.format(
					"%s deleted technology %s of project %s [%d]",
					currentUser().getUsername(), pt.getTechnology(), pt
							.getProject().getName(), pt.getProject().getId()));
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

		Project project = projectService.findProject(form.getProject().getId());
		if (project == null) {
			return "redirect:/project";
		}

		ProjectComment comment = new ProjectComment();
		comment.setProject(project);
		comment.setComment(form.getComment().trim());
		comment.setCommentDate(new Date());
		comment.setCommentUser(currentUser().getEmail());

		projectService.addProjectComment(comment);

		addUserLogInfo(String
				.format("%s added comment to project %s [%d]", currentUser()
						.getUsername(), project.getName(), project.getId()));

		return toProject(project.getId());
	}

	private String toProject(Long projectId) {
		return "redirect:/showproject?id=" + projectId.toString();
	}

	@RequestMapping("/deleteprojectcomment/{commentid}")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String deleteProjectComment(@PathVariable Long commentid,
			ModelMap model) {
		ProjectComment comment = projectService.findProjectComment(commentid);
		if (comment == null) {
			return "redirect:/project";
		}

		Project project = comment.getProject();
		Integer userType = getUserType(project.getId());
		if (userType.equals(UserType.VANGARD_FACULTY)
				|| userType.equals(UserType.ADMIN)) {
			projectService.removeProjectComment(comment);
			addUserLogInfo(String.format(
					"%s deleted comment from project %s [%d]", currentUser()
							.getUsername(), project.getName(), project.getId()));
		}

		return toProject(project.getId());
	}

	@RequestMapping("/editptm")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProjectTechnologyModule(@RequestParam("id") Long id,
			ModelMap model) {
		ProjectTechnologyModule ptm = projectService
				.findProjectTechnologyModule(id);
		Project project = ptm.getTechnology().getProject();
		Integer userType = getUserType(project.getId());
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

		model.put("ptmForm", ptm);

		addUserLogInfo(String
				.format("%s try to edit module %s of technology %s of project %s [%d]",
						currentUser().getUsername(), ptm.getName(), ptm
								.getTechnology().getTechnology(), project
								.getName(), project.getId()));

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

		ProjectTechnologyModule oldptm = projectService
				.findProjectTechnologyModule(ptm.getId());
		if (oldptm == null) {
			return "/access/error?message=cannot find module "
					+ ptm.getId().toString();
		}

		oldptm.setSampleNumber(ptm.getSampleNumber());
		oldptm.setOtherUnit(ptm.getOtherUnit());
		projectService.updateProjectTechnologyModule(oldptm);
		addUserLogInfo(String.format(
				"%s update module %s of technology %s of project %s [%d]",
				currentUser().getUsername(), oldptm.getName(), oldptm
						.getTechnology().getTechnology(), oldptm
						.getTechnology().getProject().getName(), oldptm
						.getTechnology().getProject().getId()));

		return toProject(oldptm.getTechnology().getProject().getId());
	}

	@RequestMapping("/showlog")
	@Secured({ Role.ROLE_ADMIN })
	public String listLog(ModelMap model) {
		model.put("logs", projectService.listLog());
		return "/log/list";
	}
}