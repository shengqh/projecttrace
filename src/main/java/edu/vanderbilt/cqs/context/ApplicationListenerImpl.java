package edu.vanderbilt.cqs.context;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.Role;
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
			if (!projectService.hasUser()) {
				User user = addUser("yu.shyr@vanderbilt.edu", "cqs", Role.ADMIN);
				addUser("yan.guo@vanderbilt.edu", "cqs", Role.MANAGER);
				addUser("quanhu.sheng@vanderbilt.edu", "cqs", Role.USER);
				addUser("test@vanderbilt.edu", "cqs", Role.OBSERVER);
				
				Project project = new Project();
				project.setCreator(user);
				project.setName("RNASeq");
				project.setCreateDate(new Date());
				project.setDescription("RNA Seq Project");
				projectService.addProject(project);
				
				ProjectTask task = new ProjectTask();
				task.setTaskIndex(1);
				task.setMachineTime(1.0);
				task.setPeopleTime(1.0);
				task.setName("Task1");
				task.setProject(project);
				projectService.addProjectTask(task);
				
				task = new ProjectTask();
				task.setTaskIndex(2);
				task.setMachineTime(1.0);
				task.setPeopleTime(1.0);
				task.setName("Task2");
				task.setProject(project);
				projectService.addProjectTask(task);
			}
		}
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
