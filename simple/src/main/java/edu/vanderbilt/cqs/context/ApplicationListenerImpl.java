package edu.vanderbilt.cqs.context;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectTechnology;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;
import edu.vanderbilt.cqs.bean.ProjectUser;
import edu.vanderbilt.cqs.bean.Role;
import edu.vanderbilt.cqs.bean.RolePermission;
import edu.vanderbilt.cqs.bean.Technology;
import edu.vanderbilt.cqs.bean.User;
import edu.vanderbilt.cqs.bean.UserRole;
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
		if (projectService.listPermission().size() == 0) {
			addPermission(Permission.ROLE_USER_VIEW);
			addPermission(Permission.ROLE_USER_EDIT);
			addPermission(Permission.ROLE_PROJECT_VIEW);
			addPermission(Permission.ROLE_PROJECT_EDIT);
		}

		if (projectService.listRole().size() == 0) {
			Permission uv = projectService
					.findPermissionByName(Permission.ROLE_USER_VIEW);
			Permission ue = projectService
					.findPermissionByName(Permission.ROLE_USER_EDIT);
			Permission pv = projectService
					.findPermissionByName(Permission.ROLE_PROJECT_VIEW);
			Permission pe = projectService
					.findPermissionByName(Permission.ROLE_PROJECT_EDIT);

			addRole(Role.ROLE_USER, new Permission[] { uv, pv });
			addRole(Role.ROLE_VANGARD_USER, new Permission[] { uv, pv, pe });
			addRole(Role.ROLE_ADMIN, new Permission[] { uv, pv, ue, pe });
		}

		if (projectService.listUser().size() == 0) {
			Role user = projectService.findRoleByName(Role.ROLE_USER);
			Role vuser = projectService.findRoleByName(Role.ROLE_VANGARD_USER);
			Role admin = projectService.findRoleByName(Role.ROLE_ADMIN);

			addUser("lynne.d.berry@vanderbilt.edu", "cqs", new Role[] { vuser,
					admin });
			addUser("quanhu.sheng@vanderbilt.edu", "cqs", new Role[] { vuser,
					admin });
			addUser("yu.shyr@vanderbilt.edu", "cqs", new Role[] { admin });

			addUser("david.biagi@vanderbilt.edu", "cqs", new Role[] { vuser });
			addUser("fei.ye@vanderbilt.edu", "cqs", new Role[] { vuser });
			addUser("yan.guo@vanderbilt.edu", "cqs", new Role[] { vuser });
			addUser("test_contact@vanderbilt.edu", "cqs", new Role[] { user });
			addUser("test_pi@vanderbilt.edu", "cqs", new Role[] { user });
		}

		if (projectService.listTechnology().size() == 0) {
			addTechnology("ChIP-seq", new String[] {}, new String[] {
					"Data storage", "Analysis (<20 samples)",
					"Analysis (20-80 samples)" });
			addTechnology("DNA-seq (exome)", new String[] {}, new String[] {
					"Data storage", "Refinement", "SNP calls",
					"Somatic mutation analysis 1 (standard)",
					"Somatic mutation analysis 2 (advanced)",
					"Structural variant analysis", "Gene-level analysis" });
			addTechnology("DNA-seq (whole genome)", new String[] {},
					new String[] { "Data storage", "Refinement", "SNP calls",
							"Somatic mutation analysis 1 (standard)",
							"Somatic mutation analysis 2 (advanced)",
							"Structural variant analysis",
							"Gene-level analysis" });
			addTechnology("Genotyping", new String[] {}, new String[] {
					"Data storage", "SNP calls (<1,000 samples)",
					"SNP calls (1,000-3,000 samples)", "Association analysis" });
			addTechnology("Methylation array", new String[] {}, new String[] {
					"Data storage", "Analysis (<20 samples)",
					"Analysis (>=20 samples)" });
			addTechnology("Microarray", new String[] {}, new String[] {
					"Data storage",
					"Differential expression analysis (<100 samples)",
					"Differential expression analysis (>=100 samples)",
					"Pathway/functional analysis" });
			addTechnology("miRNA", new String[] {}, new String[] {
					"Data storage", "Analysis", "Functional/pathway analysis" });
			addTechnology("RNA-seq", new String[] {}, new String[] {
					"Data storage", "Refinement", "SNP calls",
					"Somatic mutation analysis",
					"Gene quantification analysis",
					"Gene fusion analysis (deFuse)",
					"Gene fusion analysis (fusion hunter) (<10 samples)",
					"Gene fusion analysis (fusion hunter) (>=10 samples)",
					"Comparison 1", "Comparison 2 (de novo)",
					"Comparison 3 (additional models)",
					"Functional/pathway analysis (<10 samples)",
					"Functional/pathway analysis (>=10 samples)", });
		}

		if (projectService.listProject().size() == 0) {
			User admin = projectService
					.findUserByEmail("yu.shyr@vanderbilt.edu");
			User vfaculty = projectService
					.findUserByEmail("yan.guo@vanderbilt.edu");
			User vstaff = projectService
					.findUserByEmail("fei.ye@vanderbilt.edu");
			User contact = projectService
					.findUserByEmail("test_contact@vanderbilt.edu");
			User studypi = projectService
					.findUserByEmail("test_pi@vanderbilt.edu");

			Project project = new Project();
			project.setCreator(admin.getEmail());
			project.setName("2144");
			project.setCreateDate(new Date());
			project.setCreator(vfaculty.getEmail());
			project.setComments("Demo project, any comments are welcome!");

			addProjectUser(project, contact, UserType.CONTACT);
			addProjectUser(project, studypi, UserType.STUDYPI);
			addProjectUser(project, vfaculty, UserType.VANGARD_FACULTY);
			addProjectUser(project, vstaff, UserType.VANGARD_STAFF);

			addProjectTechnology(project, "RNA-seq", "Illumina", 6);
			addProjectTechnology(project, "Microarray", "agilentg4502a_07_3", 5);
			//ProjectTechnology pt = 
			addProjectTechnology(project, "Genotyping", "", 4);

			projectService.addProject(project);
			//pt.getModules().clear();
			//projectService.updateProjectTechnology(pt);
		}
	}

	private void addPermission(String name) {
		Permission permission = new Permission();
		permission.setName(name);
		projectService.addPermission(permission);
	}

	private void addRole(String roleName, Permission[] permissions) {
		Role role = new Role();
		role.setName(roleName);
		for (Permission permission : permissions) {
			role.getPermissions().add(new RolePermission(role, permission));
		}
		projectService.addRole(role);
	}

	private ProjectTechnology addProjectTechnology(Project project, String name,
			String platform, int sampleNumber) {
		Technology rnaseq = projectService.findTechnologyByName(name);
		ProjectTechnology pt = new ProjectTechnology();
		pt.setProject(project);
		pt.setTechnology(rnaseq.getName());
		pt.setTechnologyId(rnaseq.getId());
		pt.setPlatform(platform);
		pt.setSampleNumber(sampleNumber);
		List<Module> mods = projectService.listModule(rnaseq.getId());
		int mindex = 0;
		for (Module module : mods) {
			mindex++;
			ProjectTechnologyModule ptm = new ProjectTechnologyModule();
			ptm.setName(module.getName());
			ptm.setModuleId(module.getId());
			ptm.setTechnology(pt);
			ptm.setModuleIndex(mindex);
			pt.getModules().add(ptm);
		}
		project.getTechnologies().add(pt);
		return pt;
	}

	private void addTechnology(String technology, String[] platforms,
			String[] modules) {
		Technology tec = new Technology();
		tec.setName(technology);
		projectService.addTechnology(tec);

		for (String name : platforms) {
			Platform obj = new Platform();
			obj.setName(name);
			obj.setTechnology(tec);
			projectService.addPlatform(obj);
		}

		int index = 0;
		for (String name : modules) {
			index++;
			Module obj = new Module();
			obj.setName(name);
			obj.setTechnology(tec);
			obj.setModuleIndex(index);
			projectService.addModule(obj);
		}
	}

	private void addProjectUser(Project project, User user, Integer userType) {
		if (user != null) {
			ProjectUser pu = new ProjectUser(project, user, userType);
			project.getUsers().add(pu);
		}
	}

	private User addUser(String email, String password, Role[] roles) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(Utils.md5(password));
		user.setCreateDate(new Date());
		for (Role role : roles) {
			user.getRoles().add(new UserRole(user, role));
		}
		projectService.addUser(user);

		return user;
	}
}
