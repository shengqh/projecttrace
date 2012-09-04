package edu.vanderbilt.cqs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.Role;
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
	public void removeUser(Long id) {
		userDAO.deleteById(id);
	}

	@Transactional
	@Override
	public boolean hasUser() {
		return !userDAO.isEmpty();
	}

	@Transactional
	@Override
	public User findUser(Long id) {
		return userDAO.findById(id, false);
	}

	@Transactional
	@Override
	public User findUserByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Transactional
	@Override
	public boolean hasUser(Long id) {
		return userDAO.hasUser(id);
	}

	@Transactional
	@Override
	public void addProject(Project project) {
		projectDAO.save(project);
	}

	@Transactional
	@Override
	public List<Project> listProject(User currentUser) {
		if (currentUser.getRole() >= Role.MANAGER) {
			return projectDAO.findAll();
		} else {
			return projectDAO.getProjectByUser(currentUser.getId());
		}
	}

	@Transactional
	@Override
	public void removeProject(Long id) {
		projectDAO.deleteById(id);
	}

	@Transactional
	@Override
	public Pipeline findPipeline(Long id) {
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
	public void removePipeline(Long id) {
		pipelineDAO.deleteById(id);
	}

	@Transactional
	@Override
	public PipelineTask findPipelineTask(Long id) {
		return pipelineTaskDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void addPipelineTask(PipelineTask task) {
		pipelineTaskDAO.save(task);
	}

	@Transactional
	@Override
	public void removePipelineTask(Long id) {
		pipelineTaskDAO.deleteById(id);
	}

	@Transactional
	@Override
	public Long findPipelineByTask(Long taskid) {
		return pipelineTaskDAO.findPipelineIdByTaskId(taskid);
	}

	@Transactional
	@Override
	public void persist(Object obj) {
		userDAO.persist(obj);
	}

	@Transactional
	@Override
	public Project findProject(Long id) {
		return projectDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void addProjectTask(ProjectTask task) {
		projectTaskDAO.save(task);
	}

	@Transactional
	@Override
	public void removeProjectTask(Long id) {
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
	public ProjectTask findProjectTask(Long id) {
		return projectTaskDAO.findById(id, false);
	}

	@Transactional
	@Override
	public void updateProjectTask(ProjectTask task) {
		projectTaskDAO.update(task);
	}

	@Transactional
	@Override
	public Long findProjectByTask(Long taskid) {
		return projectTaskDAO.findProjecIdByTaskId(taskid);
	}

	@Transactional
	@Override
	public void updatePassword(Long id, String newPassword) {
		userDAO.updatePassword(id, newPassword);
	}

	@Transactional
	@Override
	public List<User> getActiveUsers() {
		return userDAO.getActiveUsers();
	}

	@Transactional
	@Override
	public Integer getPermission(User user, Long projectId) {
		if (user.getRole() >= Role.MANAGER) {
			return user.getRole();
		}

		return projectDAO.getPermission(user.getId(), projectId);
	}

	@Transactional
	@Override
	public List<User> getActiveUsers(Integer role) {
		return userDAO.getActiveUsers(role);
	}
}
