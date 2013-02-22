package edu.vanderbilt.cqs.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.vanderbilt.cqs.bean.LogTrace;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
import edu.vanderbilt.cqs.bean.ProjectCostCenter;
import edu.vanderbilt.cqs.bean.ProjectFile;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.RolePermission;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.dao.LogTraceDAO;
import edu.vanderbilt.cqs.dao.ModuleDAO;
import edu.vanderbilt.cqs.dao.PermissionDAO;
import edu.vanderbilt.cqs.dao.PlatformDAO;
import edu.vanderbilt.cqs.dao.ProjectCommentDAO;
import edu.vanderbilt.cqs.dao.ProjectCostCenterDAO;
import edu.vanderbilt.cqs.dao.ProjectDAO;
import edu.vanderbilt.cqs.dao.ProjectFileDAO;
import edu.vanderbilt.cqs.dao.ProjectTechnologyDAO;
import edu.vanderbilt.cqs.dao.ProjectTechnologyModuleDAO;
import edu.vanderbilt.cqs.dao.RoleDAO;
import edu.vanderbilt.cqs.dao.RolePermissionDAO;
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
	private ProjectDAO projectDAO;

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

	@Autowired
	private ProjectFileDAO fileDAO;

	@Autowired
	private ProjectCostCenterDAO costCenterDAO;

	@Autowired
	private RolePermissionDAO rpDAO;

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

	private class MyProjectComparable implements Comparator<Project> {
		@Override
		public int compare(Project o1, Project o2) {
			if (o1.getId() == null) {
				return -1;
			}
			return o1.getId().compareTo(o2.getId());
		}
	}

	@Transactional
	@Override
	public List<Project> listProject() {
		List<Project> result = projectDAO.findAll();
		Collections.sort(result, new MyProjectComparable());
		return result;
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

	@Transactional
	@Override
	public void updateProjectTechnologyModules(
			List<ProjectTechnologyModule> entities) {
		for (ProjectTechnologyModule module : entities) {
			ptmDAO.update(module);
		}
	}

	@Transactional
	@Override
	public void addProjectFile(ProjectFile file) {
		fileDAO.save(file);
	}

	@Transactional
	@Override
	public void updateProjectFile(ProjectFile file) {
		fileDAO.update(file);
	}

	@Transactional
	@Override
	public void removeProjectFile(ProjectFile file) {
		fileDAO.delete(file);
	}

	@Transactional
	@Override
	public ProjectFile findProjectFile(Long fileid) {
		return fileDAO.findById(fileid);
	}

	@Transactional
	@Override
	public void addProjectFiles(List<ProjectFile> pfiles) {
		for (ProjectFile file : pfiles) {
			fileDAO.save(file);
		}
	}

	@Transactional
	@Override
	public void assignModulePrice(Long projectId) {
		ptmDAO.assignModulePrice(projectId);
	}

	@Transactional
	@Override
	public List<ProjectTechnologyModule> getModuleInProject(Long projectId) {
		return ptmDAO.getModuleInProject(projectId);
	}

	@Transactional
	@Override
	public ProjectCostCenter findProjectCostCenter(Long id) {
		return costCenterDAO.findById(id);
	}

	@Transactional
	@Override
	public void addRolePermission(RolePermission rp) {
		rpDAO.save(rp);
	}

	@Transactional
	@Override
	public void addProjectCostCenter(ProjectCostCenter pcc) {
		costCenterDAO.save(pcc);
	}
}
