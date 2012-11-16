package edu.vanderbilt.cqs.controller;

import java.util.ArrayList;
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

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;
import edu.vanderbilt.cqs.bean.ProjectUser;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.ProjectDetailForm;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.form.ProjectTaskForm;
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

	@Secured("ROLE_OBSERVER")
	@RequestMapping("/project")
	public String listProject(ModelMap model) {
		logger.info(currentUser().getUsername() + " projectList.");

		model.put("projectList", projectService.listProject(currentUser()
				.getId(), currentUser().getRole()));

		return "/project/list";
	}

	@RequestMapping("/addproject")
	@Secured("ROLE_VANGARD_USER")
	public String addProject(ModelMap model) {
		logger.info(currentUser().getUsername() + " addProject.");

		ProjectForm form = new ProjectForm();

		initializeValidUsers(form);

		model.put("projectForm", form);

		return "/project/edit";
	}

	@RequestMapping("/editproject")
	@Secured("ROLE_VANGARD_USER")
	public String editProject(@RequestParam("projectid") Long projectId,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " editProject.");

		Project project = projectService.findProject(projectId);
		if (project != null) {
			if (!CanEditProject(project.getId())) {
				return "redirect:/denied";
			}

			ProjectForm form = new ProjectForm();
			BeanUtils.copyProperties(project, form);

			initializeValidUsers(form);

			form.setUserIds(getIdListFromUserList(project.getUsers(),
					Role.VANGARD_USER));
			form.setObserverIds(getIdListFromUserList(project.getUsers(),
					Role.OBSERVER));

			model.put("projectForm", form);

			return "/project/edit";
		} else {
			return "redirect:/project";
		}
	}

	private void initializeValidUsers(ProjectForm form) {
		form.setValidUsers(projectService.listValidUser(Role.VANGARD_USER));
		form.setValidObservers(projectService.listValidUser(Role.OBSERVER));
	}

	private List<Long> getIdListFromUserList(Set<ProjectUser> users,
			Integer role) {
		List<Long> result = new ArrayList<Long>();
		for (ProjectUser u : users) {
			if (u.getPermission() == role)
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
		Set<ProjectUser> pus = new HashSet<ProjectUser>();
		pus.addAll(getUserListFromIdList(project, form.getUserIds(),
				Role.VANGARD_USER));
		pus.addAll(getUserListFromIdList(project, form.getObserverIds(),
				Role.OBSERVER));
		project.setUsers(pus);
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	@Secured("ROLE_VANGARD_USER")
	public String saveProject(
			@Valid @ModelAttribute("projectForm") ProjectForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			initializeValidUsers(form);
			return "/project/edit";
		}

		if (form.getId() != null) {
			Project project = projectService.findProject(form.getId());
			BeanUtils.copyProperties(form, project);
			
			if (!CanEditProject(form.getId())) {
				return "redirect:/denied";
			}

			initializeUsers(project, form);

			projectService.updateProject(project);
			logger.info(currentUser().getUsername() + " saveProject - update.");
		} else {
			Project project = new Project();
			BeanUtils.copyProperties(form, project);
			project.setCreateDate(new Date());
			project.setCreator(currentUser().getUsername());

			initializeUsers(project, form);

			validator.validate(project, result);
			if (result.hasErrors()) {
				return "/project/edit";
			}

			projectService.addProject(project);
			logger.info(currentUser().getUsername() + " saveProject - new.");
		}

		return "redirect:/project";
	}

	@RequestMapping("/deleteproject/{projectid}")
	@Secured("ROLE_ADMIN")
	public String deleteProject(@PathVariable Long projectid) {
		projectService.removeProject(projectid);

		logger.info(currentUser().getUsername() + " deleteProject "
				+ projectid.toString());

		return "redirect:/project";
	}

	@RequestMapping("/showproject")
	@Secured("ROLE_OBSERVER")
	public String showProject(
			@RequestParam("projectid") Long projectid,
			@RequestParam(value = "taskid", required = false, defaultValue = "0") Long taskid,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " showProject "
				+ projectid.toString());

		Project project = projectService.findProject(projectid);
		if (project != null) {
			Integer permission = projectService.getPermission(currentUser()
					.getId(), currentUser().getRole(), projectid);
			if (permission >= Role.OBSERVER) {
				ProjectDetailForm form = new ProjectDetailForm();
				form.setProject(project);
				form.setCanEdit(permission >= Role.VANGARD_USER);
				form.setStatusMap(Status.getStatusMap());
				model.addAttribute("projectDetailForm", form);
				model.addAttribute("taskid", taskid);
				return "/project/show";
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
	@Secured("ROLE_VANGARD_USER")
	public String addProjectTask(@RequestParam("projectid") Long projectid,
			ModelMap model) {
		logger.info(currentUser().getUsername() + " addProjectTask.");

		Project project = projectService.findProject(projectid);
		if (project == null) {
			model.put("message", "Cannot find project!");
			return "/project/show";
		} else {
			if (!CanEditProject(projectid)) {
				return "redirect:/denied";
			}

			ProjectTaskForm form = new ProjectTaskForm();

			form.setMachineTime(1.0);
			form.setPeopleTime(1.0);
			form.setName("Task");
			form.setStatus(Status.PENDING);
			form.setTaskIndex(Utils.getNextTaskIndex(project.getTasks()));
			form.setProjectId(project.getId());
			form.setStatusMap(Status.getStatusMap());

			model.put("projectTaskForm", form);

			return "/project/taskedit";
		}
	}

	@RequestMapping("/editprojecttask")
	@Secured("ROLE_VANGARD_USER")
	public String editProjectTask(

	@RequestParam("taskid") Long taskid, ModelMap model) {
		logger.info(currentUser().getUsername() + " editProjectTask.");

		ProjectTask task = projectService.findProjectTask(taskid);

		if (!CanEditProject(task.getProject().getId())) {
			return "redirect:/denied";
		}

		ProjectTaskForm form = new ProjectTaskForm();

		BeanUtils.copyProperties(task, form);
		form.setProjectId(task.getProject().getId());
		form.setStatusMap(Status.getStatusMap());

		model.put("projectTaskForm", form);

		return "/project/taskedit";
	}

	private boolean CanEditProject(long projectId) {
		Integer permission = projectService.getPermission(
				currentUser().getId(), currentUser().getRole(), projectId);

		return (permission == Role.VANGARD_USER) || (permission == Role.ADMIN);
	}

	@RequestMapping(value = "/saveprojecttask", method = RequestMethod.POST)
	@Secured("ROLE_VANGARD_USER")
	public String saveProjectTask(
			@ModelAttribute("projectTaskForm") ProjectTaskForm form) {
		Project project = projectService.findProject(form.getProjectId());
		if (form.getId() != null) {
			if (!CanEditProject(project.getId())) {
				return "redirect:/denied";
			}

			ProjectTask task = projectService.findProjectTask(form.getId());
			BeanUtils.copyProperties(form, task);
			task.setUpdateDate(new Date());
			task.setUpdateUser(currentUser().getUsername());

			ProjectTaskStatus status = newProjectTaskStatus(form, task);

			projectService.updateProjectTask(task, status);
			logger.info(currentUser().getUsername() + " updating project task.");
		} else {
			ProjectTask task = new ProjectTask();
			BeanUtils.copyProperties(form, task);
			task.setProject(project);
			task.setUpdateDate(new Date());
			task.setUpdateUser(currentUser().getUsername());

			ProjectTaskStatus status = newProjectTaskStatus(form, task);

			projectService.addProjectTask(task, status);
			logger.info(currentUser().getUsername() + " added project task.");
		}

		return getProjectDetailRedirect(project.getId());
	}

	private ProjectTaskStatus newProjectTaskStatus(ProjectTaskForm form,
			ProjectTask task) {
		ProjectTaskStatus result = new ProjectTaskStatus();
		result.setComment(form.getComment());
		result.setStatus(task.getStatus());
		result.setUpdateDate(task.getUpdateDate());
		result.setUpdateUser(task.getUpdateUser());
		return result;
	}

	@RequestMapping("/deleteprojecttask/{taskid}")
	@Secured("ROLE_VANGARD_USER")
	public String deleteProjectTask(@PathVariable("taskid") Long taskid) {
		Long projectid = projectService.findProjectByTask(taskid);

		if (!CanEditProject(projectid)) {
			return "redirect:/denied";
		}

		projectService.removeProjectTask(taskid);

		return getProjectDetailRedirect(projectid);
	}

	@RequestMapping(value = "/getStatusList", method = RequestMethod.POST)
	@Secured("ROLE_OBSERVER")
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
				form.setStatusString(pts.getStatusString());
				form.setUpdateDateString(pts.getUpdateDate().toString());
				result.add(form);
			}
		}
		return result;
	}

	@RequestMapping("/getStatusList/{taskid}")
	@Secured("ROLE_OBSERVER")
	public @ResponseBody
	List<ProjectTaskStatusForm> getStatusList4(
			@PathVariable("taskid") Long taskid) {
		return doGetStatusList(taskid);
	}

	private String getProjectDetailRedirect(Long projectid) {
		return "redirect:/showproject?projectid=" + projectid.toString();
	}

}