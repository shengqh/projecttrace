package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.jboss.logging.Logger;
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

import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;
import edu.vanderbilt.cqs.bean.ProjectUser;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.ProjectDetailForm;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTaskStatusForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
public class ProjectController extends RootController {
	private static final Logger logger = Logger
			.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@Secured({ Permission.ROLE_PROJECT_VIEW })
	@RequestMapping("/project")
	public String listProject(ModelMap model) {
		logger.info(currentUser().getUsername() + " projectList.");

		if (isCurrentPowerUser()) {
			model.put("projectList", projectService.listProject());
		} else {
			model.put("projectList",
					projectService.listProject(currentUser().getId()));
		}

		return "/project/list";
	}

	@RequestMapping("/addproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String addProject(ModelMap model) {
		logger.info(currentUser().getUsername() + " addProject.");

		ProjectForm form = new ProjectForm();
		form.setProject(new Project());
		if (isCurrentPowerUser()) {
			form.setUserType(UserType.ADMIN);
		} else {
			form.setUserType(UserType.VANGARD_FACULTY);
		}

		initializeProjectForm(form);

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
				staffSet.add(user);
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
	}

	@RequestMapping("/editproject")
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String editProject(@RequestParam("projectid") Long projectId,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " editProject.");
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
			List<Long> ids, Integer role) {
		if (ids == null) {
			return new ArrayList<ProjectUser>();
		}

		List<ProjectUser> result = new ArrayList<ProjectUser>();
		for (Long id : ids) {
			User user = projectService.findUser(id);
			if (user != null) {
				ProjectUser pu = new ProjectUser(project, user, role);
				result.add(pu);
			}
		}
		return result;
	}

	private void initializeUsers(Project project, ProjectForm form) {
		project.getUsers().clear();
		project.getUsers().addAll(
				getUserListFromIdList(project, form.getContact(),
						UserType.CONTACT));
		project.getUsers().addAll(
				getUserListFromIdList(project, form.getStudyPI(),
						UserType.STUDYPI));
		project.getUsers().addAll(
				getUserListFromIdList(project, form.getStaff(),
						UserType.VANGARD_STAFF));
		project.getUsers().addAll(
				getUserListFromIdList(project, form.getFaculty(),
						UserType.VANGARD_FACULTY));
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	@Secured({ Permission.ROLE_PROJECT_EDIT })
	public String saveProject(
			@Valid @ModelAttribute("projectForm") ProjectForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			initializeProjectForm(form);
			return "/project/edit";
		}

		if (form.getProject().getId() != null) {
			Project project = projectService.findProject(form.getProject()
					.getId());
			BeanUtils.copyProperties(form.getProject(), project, new String[] {
					"technologies", "users", "comments" });

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				initializeProjectForm(form);
				return "/project/edit";
			}

			projectService.updateProject(project);
			logger.info(currentUser().getUsername() + " saveProject - update.");
		} else {
			Project project = new Project();
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
			logger.info(currentUser().getUsername() + " saveProject - new.");
		}

		return "redirect:/project";
	}

	@RequestMapping("/deleteproject/{projectid}")
	@Secured(Permission.ROLE_PROJECT_EDIT)
	public String deleteProject(@PathVariable Long projectid) {
		projectService.removeProject(projectid);

		logger.info(currentUser().getUsername() + " deleteProject "
				+ projectid.toString());

		return "redirect:/project";
	}

	@RequestMapping("/showproject")
	@Secured({ Permission.ROLE_PROJECT_VIEW })
	public String showProject(@RequestParam("projectid") Long projectId,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " showProject "
				+ projectId.toString());
		Integer userType = getUserType(projectId);
		if (userType == UserType.NONE) {
			return "/access/denied";
		}

		Project project = projectService.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setProject(project);
			form.setUserType(userType);
			model.addAttribute("projectForm", form);
			return "/project/show";
		} else {
			return "redirect:/project";
		}
	}

	private Integer getUserType(Long projectid) {
		Integer userType = isCurrentPowerUser() ? UserType.ADMIN
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

	private String getProjectDetailRedirect(Long projectid) {
		return "redirect:/showproject?projectid=" + projectid.toString();
	}
}