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
				Role role= new Role();
				role.setRole(1);
				User user = new User();
				user.setEmail("admin@cqs");
				user.setPassword(Utils.md5("vu2012cqs"));
				user.setCreateDate(new Date());
				user.setRole(role);
				role.setUser(user);
				
				projectService.addUser(user);
			}
		}
	}
}
