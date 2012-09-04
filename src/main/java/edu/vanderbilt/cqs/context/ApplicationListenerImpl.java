package edu.vanderbilt.cqs.context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.Role;
import edu.vanderbilt.cqs.Status;
import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTask;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.service.ProjectService;

@Repository
public class ApplicationListenerImpl implements
		ApplicationListener<ContextRefreshedEvent> {
	@Resource
	private ProjectService projectService;
	private boolean bFirst = true;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (bFirst) {
			bFirst = false;
			initializeDatabase();
		}
	}

	private void initializeDatabase() {
		if (!projectService.hasUser()) {
			User admin = addUser("yu.shyr@vanderbilt.edu", "cqs", Role.ADMIN);
			User manager = addUser("yan.guo@vanderbilt.edu", "cqs", Role.MANAGER);
			User user = addUser("quanhu.sheng@vanderbilt.edu", "cqs", Role.USER);
			User observer = addUser("test@vanderbilt.edu", "cqs", Role.OBSERVER);
			
			Project rnaseq = addProject(admin, "2144", manager, user, observer);
			addTask(user, rnaseq, 1, "download dataset from VU gtc web", 1, 2, Status.FINISHED);
			addTask(user, rnaseq, 2, "move dataset to ACCRE /scratch/", 1, 5, Status.FINISHED);
			addTask(user, rnaseq, 3, "unzip files", 0.5, 1.5, Status.FINISHED);
			addTask(user, rnaseq, 4, "QC processing and report", 1.5, 2.5, Status.FINISHED);
			addTask(user, rnaseq, 5, "tophat processing for testing", 4, 78, Status.PROCESSING);
			addTask(user, rnaseq, 6, "tophat processing", 6, 84, Status.PENDING);
			addTask(user, rnaseq, 7, "cuffdiff", 4, 4, Status.PENDING);
			addTask(user, rnaseq, 8, "cufflink and cluster", 4, 4, Status.PENDING);
			addTask(user, rnaseq, 9, "report", 4, 2, Status.PENDING);

			Project dnaseq = addProject(admin, "Microarray", user, user, null);
			addTask(user, dnaseq, 1, "task 1", 1, 2, Status.FINISHED);
			addTask(user, dnaseq, 2, "task 2", 1, 5, Status.PROCESSING);
			addTask(user, dnaseq, 3, "task 3", 0.5, 1.5, Status.PENDING);

			Project other = addProject(admin, "Other", null, null, user);
			addTask(user, other, 1, "task 1", 1, 2, Status.PENDING);
			addTask(user, other, 2, "task 2", 1, 5, Status.PENDING);
			addTask(user, other, 3, "task 3", 0.5, 1.5, Status.PENDING);
		}
	}

	private void addTask(User user, Project rnaseq, int index, String name,
			double peopletime, double machinetime, String status) {
		ProjectTask task = new ProjectTask();
		task.setTaskIndex(index);
		task.setMachineTime(machinetime);
		task.setPeopleTime(peopletime);
		task.setName(name);
		task.setProject(rnaseq);
		task.setStatus(status);
		task.setUpdateDate(new Date());
		task.setUpdateUser(user.getEmail());
		projectService.addProjectTask(task);
	}

	private Project addProject(User creator, String name, User manager, User user, User observer) {
		Project project = new Project();
		project.setCreator(creator.getEmail());
		project.setName(name);
		project.setCreateDate(new Date());
		project.setDescription("Description of " + name);
		project.setManagers(getList(manager));
		project.setUsers(getList(user));
		project.setObservers(getList(observer));
		
		projectService.addProject(project);
		return project;
	}

	private List<User> getList(User manager) {
		List<User> result = new ArrayList<User>();
		if(manager != null){
			result.add(manager);
		}
		return result;
	}

	private User addUser(String email, String password, Integer permission) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(Utils.md5(password));
		user.setCreateDate(new Date());
		user.setRole(permission);

		projectService.addUser(user);
		
		return user;
	}
}
