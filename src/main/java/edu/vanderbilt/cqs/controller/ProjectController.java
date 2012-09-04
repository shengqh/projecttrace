package edu.vanderbilt.cqs.controller;

import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@RequestMapping("/project")
	@Secured("ROLE_OBSERVER")
	public String listProject(@ModelAttribute("currentuser") User currentUser,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " projectList.");

		model.addAttribute("projectList",
				projectService.listProject(currentUser));

		return "project";
	}

	@RequestMapping("/addproject")
	@Secured("ROLE_ADMIN")
	public String addProject(@ModelAttribute("currentuser") User currentUser,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " addProject.");

		ProjectForm form = new ProjectForm();
		form.setProject(new Project());
		form.setAllUsers(projectService.listUser());

		model.addAttribute("projectForm", form);

		return "projectedit";
	}

	@RequestMapping("/editproject")
	@Secured("ROLE_MANAGER")
	public String editProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProject.");

		Project project = projectService.findProject(projectId);
		if (project != null) {
			ProjectForm form = new ProjectForm();
			form.setProject(project);
			form.setAllUsers(projectService.getActiveUsers());

			model.addAttribute("projectForm", form);

			return "projectedit";
		} else {
			return "redirect:/project";
		}
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	@Secured("ROLE_MANAGER")
	public String saveProject(@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("projectForm") ProjectForm projectForm) {
		Project project = projectForm.getProject();
		if (project.getId() != null) {
			Project old = projectService.findProject(project.getId());
			old.setName(project.getName());
			old.setDescription(project.getDescription());
			projectService.updateProject(old);
			logger.info(currentUser.getEmail() + " saveProject - update.");
		} else {
			project.setCreateDate(new Date());
			project.setCreator(currentUser);
			projectService.addProject(project);
			logger.info(currentUser.getEmail() + " saveProject - new.");
		}

		return "redirect:/project";
	}

	@RequestMapping("/deleteproject")
	@Secured("ROLE_ADMIN")
	public String deleteProject(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId) {
		projectService.removeProject(projectId);

		logger.info(currentUser.getEmail() + " deleteProject.");

		return "redirect:/project";
	}

	@RequestMapping("/showproject")
	@Secured("ROLE_OBSERVER")
	public String showProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId, ModelMap model) {
		logger.info(currentUser.getEmail() + " showProject.");

		Project project = projectService.findProject(projectId);
		if (project != null) {
			Integer permission = projectService.getPermission(currentUser,
					project);
			if (permission >= Role.OBSERVER) {
				ProjectDetailForm form = new ProjectDetailForm();
				form.setProject(project);
				form.setCanManage(permission >= Role.MANAGER);
				form.setCanEdit(permission >= Role.USER);
				model.addAttribute("projectDetailForm", form);
				return "projectshow";
			} else {
				return "project";
			}
		} else {
			return "project";
		}
	}

	@RequestMapping("/addprojecttask")
	public String addProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectid, ModelMap model) {
		logger.info(currentUser.getEmail() + " addProjectTask.");

		Project project = projectService.findProject(projectid);
		if (project == null) {
			return "projectshow";
		} else {
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
			model.addAttribute("projectTaskForm", form);
			return "projecttaskedit";
		}
	}

	@RequestMapping("/editprojecttask")
	public String editProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long taskid, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProjectTask.");

		ProjectTask task = projectService.findProjectTask(taskid);

		ProjectTaskForm form = new ProjectTaskForm();
		form.setTask(task);
		form.setProjectId(task.getProject().getId());
		model.addAttribute("projectTaskForm", form);
		return "projecttaskedit";
	}

	@RequestMapping(value = "/saveprojecttask", method = RequestMethod.POST)
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
	public String deleteProjectTask(@RequestParam("id") Long taskid) {
		Long projectId = projectService.findProjectByTask(taskid);

		projectService.removeProjectTask(taskid);

		return getProjectDetailRedirect(projectId);
	}

	private String getProjectDetailRedirect(Long projectId) {
		return "redirect:/showproject?id=" + projectId.toString();
	}

}