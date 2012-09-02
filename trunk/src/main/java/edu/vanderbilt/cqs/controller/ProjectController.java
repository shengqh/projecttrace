package edu.vanderbilt.cqs.controller;

import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.vanderbilt.cqs.bean.BeanUtils;
import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.form.PipelineForm;
import edu.vanderbilt.cqs.form.ProjectForm;
import edu.vanderbilt.cqs.service.ProjectService;

@Controller
@SessionAttributes({ "currentuser" })
@RequestMapping("/")
public class ProjectController {
	private static final Logger logger = Logger
			.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;

	@RequestMapping
	public String gohome() {
		return "list";
	}

	// @RequestMapping("/index")
	// public String home(ModelMap model) {
	// if (model.containsAttribute("currentuser")) {
	// return "list";
	// } else {
	// model.put("user", new User());
	// return "login";
	// }
	// }
	//
	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public String login(@ModelAttribute("user") User user, ModelMap model) {
	// User currentUser = projectService.validateUser(user);
	// model.put("currentuser", currentUser);
	// if (currentUser != null) {
	// logger.info(user.getEmail() + "login succeed.");
	// return "list";
	// } else {
	// logger.info(user.getEmail() + "login failed.");
	// return "redirect:/index";
	// }
	// }
	//
	@RequestMapping("/user")
	public String listUsers(ModelMap model) {

		model.addAttribute("user", new User());
		model.addAttribute("userList", projectService.listUser());

		return "user";
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user) {

		user.setCreateDate(new Date());
		projectService.addUser(user);

		return "redirect:/user";
	}

	@RequestMapping("/deleteuser/{userId}")
	public String deleteUser(@PathVariable Long userId) {

		projectService.removeUser(userId);

		return "redirect:/user";
	}

	@RequestMapping("/pipeline")
	public String listPipeline(@ModelAttribute("currentuser") User currentUser,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " listPipeline.");

		PipelineForm form = new PipelineForm();
		form.setPipeline(new Pipeline());
		form.setPipelineList(projectService.listPipeline(currentUser));
		form.setCanEdit(true);

		model.addAttribute("pipelineForm", form);

		return "pipeline";
	}

	@RequestMapping("/editpipeline")
	public String editPipeline(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long pipelineId, ModelMap model) {
		logger.info(currentUser.getEmail() + " editPipeline.");

		PipelineForm form = new PipelineForm();
		form.setPipeline(projectService.findPipeline(pipelineId));
		form.setPipelineList(projectService.listPipeline(currentUser));
		form.setCanEdit(true);

		model.addAttribute("pipelineForm", form);

		return "pipeline";
	}

	@RequestMapping(value = "/savepipeline", method = RequestMethod.POST)
	public String savePipeline(@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("pipelineForm") PipelineForm pipelineForm) {
		Pipeline pipeline = pipelineForm.getPipeline();
		if (pipeline.getId() != null) {
			Pipeline old = projectService.findPipeline(pipeline.getId());
			old.setName(pipeline.getName());
			logger.info(currentUser.getEmail() + " savePipeline - update.");
			projectService.updatePipeline(old);
		} else {
			logger.info(currentUser.getEmail() + " savePipeline - new.");
			pipeline.setCreateDate(new Date());
			pipeline.setCreator(currentUser);
			projectService.addPipeline(pipeline);
		}

		return "redirect:/pipeline";
	}

	@RequestMapping("/deletepipeline")
	public String deletePipeline(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long pipelineId) {
		logger.info(currentUser.getEmail() + " deletePipeline - update.");

		projectService.removePipeline(pipelineId);

		return "redirect:/pipeline";
	}

	@RequestMapping("/pipelinedetail")
	public String listPipelineDetail(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long pipelineId, ModelMap model) {
		logger.info(currentUser.getEmail() + " listPipelineDetail.");

		Pipeline pl = projectService.findPipeline(pipelineId);

		PipelineTask task = new PipelineTask();
		task.setName("Task");
		task.setPeopleTime(1.0);
		task.setMachineTime(1.0);
		task.setTaskIndex(BeanUtils.getNextTaskIndex(pl.getTasks()));

		model.addAttribute("pipelinetask", task);
		model.addAttribute("pipeline", pl);

		return "pipelinedetail";
	}

	@RequestMapping("/editpipelinetask")
	public String editPipelineTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long taskid, ModelMap model) {
		logger.info(currentUser.getEmail() + " editPipelineTask.");

		PipelineTask task = projectService.findPipelineTask(taskid);

		Pipeline pl = task.getPipeline();

		model.addAttribute("pipelinetask", task);
		model.addAttribute("pipeline", pl);

		return "pipelinedetail";
	}

	@RequestMapping(value = "/savepipelinetask", method = RequestMethod.POST)
	public String savePipelineTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("pipelineid") Long pipelineId,
			@ModelAttribute("pipelinetask") PipelineTask task) {
		Pipeline pl = projectService.findPipeline(pipelineId);
		task.setPipeline(pl);

		if (task.getId() != null) {
			logger.info(currentUser.getEmail() + " updating pipeline task.");
			projectService.updatePipelineTask(task);
		} else {
			logger.info(currentUser.getEmail() + " adding pipeline task.");
			projectService.addPipelineTask(task);
		}

		return getPipelineDetailRedirect(pipelineId);
	}

	@RequestMapping("/deletepipelinetask/{pipelinetaskId}")
	public String deletePipelineTask(@PathVariable Long pipelinetaskId) {
		Long pipelineId = projectService.findPipelineByTask(pipelinetaskId);

		projectService.removePipelineTask(pipelinetaskId);

		return getPipelineDetailRedirect(pipelineId);
	}

	private String getPipelineDetailRedirect(Long pipelineId) {
		return "redirect:/pipelinedetail?id=" + pipelineId.toString();
	}

	@RequestMapping("/project")
	public String listProject(@ModelAttribute("currentuser") User currentUser,
			ModelMap model) {
		logger.info(currentUser.getEmail() + " listProject.");

		ProjectForm form = new ProjectForm();
		form.setProject(new Project());
		form.setProjectList(projectService.listProject(currentUser));
		form.setAllPipelines(projectService.listPipeline(currentUser));
		form.setAllUsers(projectService.listUser());
		form.setCanEdit(true);

		model.addAttribute("projectForm", form);

		return "project";
	}

	@RequestMapping("/editproject")
	public String editProject(@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProject.");

		ProjectForm form = new ProjectForm();
		form.setProject(projectService.findProject(projectId));
		form.setProjectList(projectService.listProject(currentUser));
		if (form.getProject().getTasks().size() == 0) {
			form.setAllPipelines(projectService.listPipeline(currentUser));
		}
		form.setAllUsers(projectService.listUser());
		form.setCanEdit(true);

		model.addAttribute("projectForm", form);

		return "project";
	}

	@RequestMapping(value = "/saveproject", method = RequestMethod.POST)
	public String saveProject(@ModelAttribute("currentuser") User currentUser,
			@ModelAttribute("projectForm") ProjectForm projectForm) {
		Project project = projectForm.getProject();
		if (project.getId() != null) {
			Project old = projectService.findProject(project.getId());
			old.setName(project.getName());
			logger.info(currentUser.getEmail() + " savePipeline - update.");
			projectService.updateProject(old);
		} else {
			logger.info(currentUser.getEmail() + " savePipeline - new.");
			project.setCreateDate(new Date());
			project.setCreator(currentUser);
			projectService.addProject(project);
		}

		return "redirect:/project";
	}

	@RequestMapping("/deleteproject")
	public String deleteProject(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId) {
		logger.info(currentUser.getEmail() + " deleteProject - update.");

		projectService.removeProject(projectId);

		return "redirect:/project";
	}

	@RequestMapping("/projectdetail")
	public String listProjectDetail(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long projectId, ModelMap model) {
		logger.info(currentUser.getEmail() + " listProjectDetail.");

		Project pl = projectService.findProject(projectId);

		ProjectTask task = new ProjectTask();
		task.setName("Task");
		task.setPeopleTime(1.0);
		task.setMachineTime(1.0);
		task.setTaskIndex(BeanUtils.getNextTaskIndex(pl.getTasks()));

		model.addAttribute("projecttask", task);
		model.addAttribute("project", pl);

		return "projectdetail";
	}

	@RequestMapping("/editprojecttask")
	public String editProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("id") Long taskid, ModelMap model) {
		logger.info(currentUser.getEmail() + " editProjectTask.");

		ProjectTask task = projectService.findProjectTask(taskid);

		Project pl = task.getProject();

		model.addAttribute("projecttask", task);
		model.addAttribute("project", pl);

		return "projectdetail";
	}

	@RequestMapping(value = "/saveprojecttask", method = RequestMethod.POST)
	public String saveProjectTask(
			@ModelAttribute("currentuser") User currentUser,
			@RequestParam("projectid") Long projectId,
			@ModelAttribute("projecttask") ProjectTask task) {
		Project pl = projectService.findProject(projectId);
		task.setProject(pl);

		if (task.getId() != null) {
			logger.info(currentUser.getEmail() + " updating project task.");
			projectService.updateProjectTask(task);
		} else {
			logger.info(currentUser.getEmail() + " adding project task.");
			projectService.addProjectTask(task);
		}

		return getProjectDetailRedirect(projectId);
	}

	@RequestMapping("/deleteprojecttask/{projecttaskId}")
	public String deleteProjectTask(@PathVariable Long projecttaskId) {
		Long projectId = projectService.findProjectByTask(projecttaskId);

		projectService.removeProjectTask(projecttaskId);

		return getProjectDetailRedirect(projectId);
	}

	private String getProjectDetailRedirect(Long projectId) {
		return "redirect:/projectdetail?id=" + projectId.toString();
	}

}
