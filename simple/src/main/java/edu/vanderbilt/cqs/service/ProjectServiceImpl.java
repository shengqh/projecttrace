package edu.vanderbilt.cqs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.bean.LogTrace;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.ProjectTaskStatus;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.dao.LogTraceDAO;
import edu.vanderbilt.cqs.dao.ModuleDAO;
import edu.vanderbilt.cqs.dao.PermissionDAO;
import edu.vanderbilt.cqs.dao.PipelineDAO;
import edu.vanderbilt.cqs.dao.PipelineTaskDAO;
import edu.vanderbilt.cqs.dao.PlatformDAO;
import edu.vanderbilt.cqs.dao.ProjectCommentDAO;
import edu.vanderbilt.cqs.dao.ProjectDAO;
import edu.vanderbilt.cqs.dao.ProjectTaskDAO;
import edu.vanderbilt.cqs.dao.ProjectTaskStatusDAO;
import edu.vanderbilt.cqs.dao.ProjectTechnologyDAO;
import edu.vanderbilt.cqs.dao.ProjectTechnologyModuleDAO;
import edu.vanderbilt.cqs.dao.RoleDAO;
import edu.vanderbilt.cqs.dao.TechnologyDAO;
import edu.vanderbilt.cqs.dao.UserDAO;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private PermissionDAO permissionDAO;

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

	@Autowired
	private TechnologyDAO technologyDAO;

	@Autowired
	private PlatformDAO platformDAO;

	@Autowired
	private ModuleDAO moduleDAO;

	@Autowired
	private ProjectTechnologyDAO projectTechnologyDAO;

	@Autowired
	private ProjectTechnologyModuleDAO ptmDAO;

	@Autowired
	private ProjectCommentDAO projectCommentDAO;
	
	@Autowired
	private LogTraceDAO logTraceDAO;

	@Transactional
	@Override
	public void addUser(User user) {
		userDAO.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		User user = userDAO.findById(id);
		if (user != null) {
			userDAO.delete(user);
		}
	}

	@Transactional
	@Override
	public boolean hasUser() {
		return !userDAO.isEmpty();
	}

	@Transactional
	@Override
	public User findUser(Long id) {
		return userDAO.findById(id);
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
	public List<Project> listProject(Long userid) {
		return projectDAO.getProjectByUser(userid);
	}

	@Transactional
	@Override
	public void removeProject(Long id) {
		Project project = projectDAO.findById(id);
		if (project != null) {
			projectDAO.delete(project);
		}
	}

	@Transactional
	@Override
	public Pipeline findPipeline(Long id) {
		return pipelineDAO.findById(id);
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
		return pipelineTaskDAO.findById(id);
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
		return projectDAO.findById(id);
	}

	@Transactional
	@Override
	public void addProjectTask(ProjectTask task, ProjectTaskStatus status) {
		projectTaskDAO.save(task);

		assignTaskToStatus(task, status);

		projectTaskStatusDAO.save(status);
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
		return projectTaskDAO.findById(id);
	}

	@Transactional
	@Override
	public void updateProjectTask(ProjectTask task, ProjectTaskStatus status) {
		projectTaskDAO.update(task);

		assignTaskToStatus(task, status);

		projectTaskStatusDAO.save(status);
	}

	private void assignTaskToStatus(ProjectTask task, ProjectTaskStatus status) {
		status.setStatus(task.getStatus());
		status.setUpdateUser(task.getUpdateUser());
		status.setUpdateDate(task.getUpdateDate());
		status.setTask(task);
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
	public Integer getUserType(Long userid, Long projectId) {
		return projectDAO.getUserType(userid, projectId);
	}

	@Transactional
	@Override
	public List<User> listUser() {
		return userDAO.findAll();
	}

	@Transactional
	@Override
	public List<User> listValidUser() {
		return userDAO.listValidUser();
	}

	@Transactional
	@Override
	public List<User> listInvalidUser() {
		return userDAO.listInvalidUser();
	}

	@Transactional
	@Override
	public List<Technology> listTechnology() {
		return technologyDAO.findAll();
	}

	@Transactional
	@Override
	public Technology findTechnology(Long id) {
		return technologyDAO.findById(id);
	}

	@Transactional
	@Override
	public void updateTechnology(Technology entity) {
		technologyDAO.update(entity);
	}

	@Transactional
	@Override
	public void addTechnology(Technology entity) {
		technologyDAO.save(entity);
	}

	@Transactional
	@Override
	public void removeTechnology(Long id) {
		technologyDAO.deleteById(id);
	}

	@Transactional
	@Override
	public Platform findPlatform(Long id) {
		return platformDAO.findById(id);
	}

	@Transactional
	@Override
	public void updatePlatform(Platform entity) {
		platformDAO.update(entity);
	}

	@Transactional
	@Override
	public void addPlatform(Platform entity) {
		platformDAO.save(entity);
	}

	@Transactional
	@Override
	public void removePlatform(Platform entity) {
		platformDAO.delete(entity);
	}

	@Transactional
	@Override
	public Module findModule(Long id) {
		return moduleDAO.findById(id);
	}

	@Transactional
	@Override
	public void updateModule(Module entity) {
		moduleDAO.update(entity);
	}

	@Transactional
	@Override
	public void addModule(Module entity) {
		moduleDAO.save(entity);
	}

	@Transactional
	@Override
	public void removeModule(Module entity) {
		moduleDAO.delete(entity);
	}

	@Transactional
	@Override
	public List<Technology> listValidTechnology() {
		return technologyDAO.listValidTechnology();
	}

	@Transactional
	@Override
	public Technology findTechnologyByName(String name) {
		return technologyDAO.findByName(name);
	}

	@Transactional
	@Override
	public List<Role> listRole() {
		return roleDAO.listRoles();
	}

	@Transactional
	@Override
	public void addRole(Role entity) {
		roleDAO.save(entity);
	}

	@Transactional
	@Override
	public Role findRoleByName(String name) {
		return roleDAO.findByName(name);
	}

	@Transactional
	@Override
	public List<Project> listProject() {
		return projectDAO.findAll();
	}

	@Transactional
	@Override
	public Role findRole(Long id) {
		return roleDAO.findById(id);
	}

	@Transactional
	@Override
	public List<Permission> listPermission() {
		return permissionDAO.listPermission();
	}

	@Transactional
	@Override
	public void addPermission(Permission entity) {
		permissionDAO.save(entity);
	}

	@Transactional
	@Override
	public Permission findPermissionByName(String name) {
		return permissionDAO.findByName(name);
	}

	@Transactional
	@Override
	public Permission findPermission(Long id) {
		return permissionDAO.findById(id);
	}

	@Transactional
	@Override
	public List<Module> listModule(Long technologyId) {
		return moduleDAO.findByTechnology(technologyId);
	}

	@Transactional
	@Override
	public void updateProjectTechnology(ProjectTechnology entity) {
		projectTechnologyDAO.update(entity);
	}

	@Transactional
	@Override
	public void updateRole(Role role) {
		roleDAO.update(role);
	}

	@Transactional
	@Override
	public void addProjectComment(ProjectComment entity) {
		projectCommentDAO.save(entity);
	}

	@Transactional
	@Override
	public ProjectComment findProjectComment(Long id) {
		return projectCommentDAO.findById(id);
	}

	@Transactional
	@Override
	public void removeProjectComment(ProjectComment entity) {
		projectCommentDAO.delete(entity);
	}

	@Transactional
	@Override
	public ProjectTechnology findProjectTechnology(Long id) {
		return projectTechnologyDAO.findById(id);
	}

	@Transactional
	@Override
	public void addProjectTechnology(ProjectTechnology entity) {
		projectTechnologyDAO.save(entity);
	}

	@Transactional
	@Override
	public void removeProjectTechnology(ProjectTechnology entity) {
		projectTechnologyDAO.delete(entity);
	}

	@Transactional
	@Override
	public ProjectTechnologyModule findProjectTechnologyModule(Long id) {
		return ptmDAO.findById(id);
	}

	@Transactional
	@Override
	public void updateProjectTechnologyModule(ProjectTechnologyModule entity) {
		ptmDAO.update(entity);
	}
	
	@Transactional
	@Override
	public void addLogTrace(LogTrace log) {
		logTraceDAO.save(log);
	}

	@Transactional
	@Override
	public List<LogTrace> listLog() {
		return logTraceDAO.listAll();
	}
}
