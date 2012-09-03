package edu.vanderbilt.cqs.context;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Role;
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
				addUser("yu.shyr@vanderbilt.edu", "cqs", Role.ADMIN);
				addUser("yan.guo@vanderbilt.edu", "cqs", Role.MANAGER);
				addUser("quanhu.sheng@vanderbilt.edu", "cqs", Role.USER);
				addUser("test@vanderbilt.edu", "cqs", Role.OBSERVER);
			}
		}
	}

	private void addUser(String email, String password, Integer permission) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(Utils.md5(password));
		user.setCreateDate(new Date());
		user.setRole(permission);

		projectService.addUser(user);
	}
}
