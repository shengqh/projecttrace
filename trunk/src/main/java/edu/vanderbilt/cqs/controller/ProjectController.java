package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.ProjectDetailForm;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTaskForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
@SessionAttributes({ "currentuser" })
public class ProjectController {
	private static final Logger logger = Logger
			.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private Validator validator;

	@RequestMapping("/project")
	@Secured("ROLE_OBSERVER")
	public String listProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam(value = "message", required = false) String message,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " projectList.");

		model.put("message", message);
		model.put("projectList", projectService.listProject(currentUser));

		return "project";
	}

	@RequestMapping("/addproject")
	@Secured("ROLE_ADMIN")
	public String addProject(@ModelAttribute("currentuser") User currentUser,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " addProject.");

		ProjectForm form = new ProjectForm();

		initializeValidUsers(form);

		model.put("projectForm", form);

		return "projectedit";
	}

	@RequestMapping("/editproject")
	@Secured("ROLE_MANAGER")
	public String editProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("projectid") Long projectId, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProject.");

		Project project = projectService.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			BeanUtils.copyProperties(project, form);

			initializeValidUsers(form);

			form.setManagerIds(getIdListFromUserList(project.getManagers()));
			form.setUserIds(getIdListFromUserList(project.getUsers()));
			form.setObserverIds(getIdListFromUserList(project.getObservers()));

			model.put("projectForm", form);

			return "projectedit";
		} else {
			return "redirect:/project";
		}
	}

	private void initializeValidUsers(ProjectForm form) {
		form.setValidManagers(projectService.listValidUser(Role.MANAGER));
		form.setValidUsers(projectService.listValidUser(Role.USER));
		form.setValidObservers(projectService.listValidUser(Role.OBSERVER));
	}

	private List<Long> getIdListFromUserList(List<User> users) {
		List<Long> result = new ArrayList<Long>();
		for (User u : users) {
			result.add(u.getId());
		}
		return result;
	}

	private List<User> getUserListFromIdList(List<Long> ids) {
		if (ids == null) {
			return new ArrayList<User>();
		}

		List<User> result = new ArrayList<User>();
		for (Long id : ids) {
			User user = projectService.findUser(id);
			if (user != null) {
				result.add(user);
			}
		}
		return result;
	}

	private void initializeUsers(Project project, ProjectForm form) {
		project.setManagers(getUserListFromIdList(form.getManagerIds()));
		project.setUsers(getUserListFromIdList(form.getUserIds()));
		project.setObservers(getUserListFromIdList(form.getObserverIds()));
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	@Secured("ROLE_MANAGER")
	public String saveProject(@ModelAttribute("currentuser") User currentUser,
			@Valid @ModelAttribute("projectForm") ProjectForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			initializeValidUsers(form);
			return "projectedit";
		}

		if (form.getId() != null) {
			Project project = projectService.findProject(form.getId());
			BeanUtils.copyProperties(form, project);

			initializeUsers(project, form);

			projectService.updateProject(project);
			logger.info(currentUser.getEmail() + " saveProject - update.");
		} else {
			Project project = new Project();
			BeanUtils.copyProperties(form, project);
			project.setCreateDate(new Date());
			project.setCreator(currentUser.getEmail());

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				return "projectedit";
			}

			projectService.addProject(project);
			logger.info(currentUser.getEmail() + " saveProject - new.");
		}

		return "redirect:/project";
	}

	@RequestMapping("/deleteproject/{projectid}")
	@Secured("ROLE_ADMIN")
	public String deleteProject(
			@ModelAttribute("currentuser") User currentUser,
			@PathVariable Long projectid) {
		projectService.removeProject(projectid);

		logger.info(currentUser.getEmail() + " deleteProject "
				+ projectid.toString());

		return "redirect:/project";
	}

	@RequestMapping("/showproject")
	@Secured("ROLE_OBSERVER")
	public String showProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("projectid") Long projectid, ModelMap model) {
		logger.info(currentUser.getEmail() + " showProject "
				+ projectid.toString());

		Project project = projectService.findProject(projectid);
		if (project != null) {
			Integer permission = projectService.getPermission(currentUser,
					projectid);
			if (permission >= Role.OBSERVER) {
				ProjectDetailForm form = new ProjectDetailForm();
				form.setProject(project);
				form.setCanManage(permission >= Role.MANAGER);
				form.setCanEdit(permission >= Role.USER);
				model.put("projectDetailForm", form);
				return "projectshow";
			} else {
				return "/access/denied";
			}
		} else {
			model.put("message", "Project with id " + projectid.toString()
					+ " not exists");
			return "redirect:/project";
		}
	}

	@RequestMapping("/addprojecttask")
	@Secured("ROLE_USER")
	public String addProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("projectid") Long projectid, ModelMap model) {
		logger.info(currentUser.getEmail() + " addProjectTask.");

		Project project = projectService.findProject(projectid);
		if (project == null) {
			model.put("message", "Cannot find project!");
			return "projectshow";
		} else {
			Integer permission = projectService.getPermission(currentUser,
					projectid);

			if (permission <= Role.MANAGER) {
				return "/access/denied";
			}

			ProjectTaskForm form = new ProjectTaskForm();
			ProjectTask task = new ProjectTask();
			task.setProject(project);
			task.setMachineTime(1.0);
			task.setPeopleTime(1.0);
			task.setName("Task");
			task.setStatus(Status.PENDING);
			task.setTaskIndex(Utils.getNextTaskIndex(project.getTasks()));
			form.setTask(task);
			form.setProjectId(project.getId());
			form.setStatusList(Status.getStatusList());

			model.put("projectTaskForm", form);

			return "projecttaskedit";
		}
	}

	@RequestMapping("/editprojecttask")
	@Secured("ROLE_USER")
	public String editProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("taskid") Long taskid, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProjectTask.");

		ProjectTask task = projectService.findProjectTask(taskid);

		ProjectTaskForm form = new ProjectTaskForm();
		form.setTask(task);
		form.setProjectId(task.getProject().getId());
		form.setStatusList(Status.getStatusList());

		model.put("projectTaskForm", form);

		return "projecttaskedit";
	}

	@RequestMapping(value = "/saveprojecttask", method = RequestMethod.POST)
	@Secured("ROLE_USER")
	public String saveProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("projectTaskForm") ProjectTaskForm form) {
		Project project = projectService.findProject(form.getProjectId());
		ProjectTask task = form.getTask();
		task.setProject(project);
		task.setUpdateDate(new Date());
		task.setUpdateUser(currentUser.getEmail());

		if (task.getId() != null) {
			logger.info(currentUser.getEmail() + " updating project task.");
			projectService.updateProjectTask(task);
		} else {
			logger.info(currentUser.getEmail() + " adding project task.");
			projectService.addProjectTask(task);
		}

		return getProjectDetailRedirect(project.getId());
	}

	@RequestMapping("/deleteprojecttask")
	@Secured("ROLE_USER")
	public String deleteProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("taskid") Long taskid) {
		Long projectid = projectService.findProjectByTask(taskid);

		Integer permission = projectService.getPermission(currentUser,
				projectid);

		if (permission <= Role.MANAGER) {
			return "/denied";
		}

		projectService.removeProjectTask(taskid);

		return getProjectDetailRedirect(projectid);
	}

	private String getProjectDetailRedirect(Long projectid) {
		return "redirect:/showproject?projectid=" + projectid.toString();
	}

}