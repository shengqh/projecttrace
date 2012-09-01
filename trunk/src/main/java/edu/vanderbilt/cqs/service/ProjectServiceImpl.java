package edu.vanderbilt.cqs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.dao.PipelineDAO;
import edu.vanderbilt.cqs.dao.PipelineTaskDAO;
import edu.vanderbilt.cqs.dao.ProjectDAO;
import edu.vanderbilt.cqs.dao.ProjectTaskDAO;
import edu.vanderbilt.cqs.dao.ProjectTaskStatusDAO;
import edu.vanderbilt.cqs.dao.UserDAO;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PipelineDAO pipelineDAO;

	@Autowired
	private PipelineTaskDAO pipelineTaskDAO;

	@Autowired
	private ProjectDAO projectDAO;

	@Autowired
	private ProjectTaskDAO projectTaskDAO;

	@Autowired
	private ProjectTaskStatusDAO projectTaskStatusDAO;

	@Transactional
	@Override
	public void addUser(User user) {
		userDAO.save(user);
	}

	@Transactional
	@Override
	public List<User> listUser() {
		return userDAO.findAll();
	}

	@Transactional
	@Override
	public User validateUser(User user) {
		return userDAO.validateUser(user);
	}

	@Transactional
	@Override
	public void removeUser(Integer id) {
		userDAO.deleteById(id);
	}

	@Transactional
	@Override
	public void addProject(Project project) {
		projectDAO.save(project);
	}

	@Transactional
	@Override
	public List<Project> listProject(User currentUser) {
		return projectDAO.findAll();
	}

	@Transactional
	@Override
	public void removeProject(Integer id) {
		projectDAO.deleteById(id);
	}

	@Transactional
	@Override
	public Pipeline findPipeline(Integer id) {
		return pipelineDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void addPipeline(Pipeline pipeline) {
		pipelineDAO.save(pipeline);
	}

	@Transactional
	@Override
	public List<Pipeline> listPipeline(User currentUser) {
		return pipelineDAO.findAll();
	}

	@Transactional
	@Override
	public void removePipeline(Integer id) {
		pipelineDAO.deleteById(id);
	}

	@Transactional
	@Override
	public void applyPipeline(Project project, Pipeline pipeline, int count) {
		projectDAO.clearTask(project);
		for (PipelineTask task : pipeline.getTasks()) {
			project.getTasks().add(new ProjectTask(task, count));
		}
		projectDAO.save(project);
	}

	@Transactional
	@Override
	public PipelineTask findPipelineTask(Integer id) {
		return pipelineTaskDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void addPipelineTask(PipelineTask task) {
		pipelineTaskDAO.save(task);
	}

	@Transactional
	@Override
	public void removePipelineTask(Integer id) {
		pipelineTaskDAO.deleteById(id);
	}

	@Transactional
	@Override
	public Integer findPipelineByTask(Integer taskid) {
		return pipelineTaskDAO.findPipelineIdByTaskId(taskid);
	}

	@Transactional
	@Override
	public void persist(Object obj) {
		userDAO.persist(obj);
	}

	@Transactional
	@Override
	public Project findProject(Integer id) {
		return projectDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void addProjectTask(ProjectTask task) {
		projectTaskDAO.save(task);
	}

	@Transactional
	@Override
	public void removeProjectTask(Integer id) {
		projectTaskDAO.deleteById(id);
	}

	@Transactional
	@Override
	public void updatePipelineTask(PipelineTask task) {
		pipelineTaskDAO.update(task);
	}

	@Transactional
	@Override
	public void updateUser(User user) {
		userDAO.update(user);
	}

	@Transactional
	@Override
	public void updateProject(Project project) {
		projectDAO.update(project);
	}

	@Transactional
	@Override
	public void updatePipeline(Pipeline pipeline) {
		pipelineDAO.update(pipeline);
	}

	@Transactional
	@Override
	public ProjectTask findProjectTask(Integer id) {
		return projectTaskDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void updateProjectTask(ProjectTask task) {
		projectTaskDAO.update(task);
	}

	@Transactional
	@Override
	public Integer findProjectByTask(Integer taskid) {
		return projectTaskDAO.findProjecIdByTaskId(taskid);
	}

	@Transactional
	@Override
	public boolean hasUser() {
		return !userDAO.isEmpty();
	}
}
