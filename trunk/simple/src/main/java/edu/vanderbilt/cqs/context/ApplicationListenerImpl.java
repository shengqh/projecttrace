package edu.vanderbilt.cqs.context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.ModuleType;
import edu.vanderbilt.cqs.UserType;
import edu.vanderbilt.cqs.Utils;
import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.Permission;
import edu.vanderbilt.cqs.bean.Platform;
import edu.vanderbilt.cqs.bean.Project;
import edu.vanderbilt.cqs.bean.ProjectComment;
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
			try {
				initializeDatabase();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializeDatabase() throws ParseException {
		if (projectService.listPermission().size() == 0) {
			addPermission(Permission.ROLE_USER_VIEW);
			addPermission(Permission.ROLE_USER_EDIT);
			addPermission(Permission.ROLE_PROJECT_VIEW);
			addPermission(Permission.ROLE_PROJECT_EDIT);
			addPermission(Permission.ROLE_MODULE_VIEW);
			addPermission(Permission.ROLE_MODULE_EDIT);
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
			Permission mv = projectService
					.findPermissionByName(Permission.ROLE_MODULE_VIEW);
			Permission me = projectService
					.findPermissionByName(Permission.ROLE_MODULE_EDIT);

			addRole(Role.ROLE_USER, new Permission[] {});
			addRole(Role.ROLE_VANGARD_STAFF,
					new Permission[] { uv, pv, pe, mv });
			addRole(Role.ROLE_VANGARD_ADSTAFF, new Permission[] { uv, pv, pe,
					mv, me });
			addRole(Role.ROLE_VANGARD_FACULTY, new Permission[] { uv, ue, pv,
					pe, mv, me });
			addRole(Role.ROLE_ADMIN,
					new Permission[] { uv, ue, pv, pe, mv, me });
		}

		if (projectService.listUser().size() == 0) {
			Role user = projectService.findRoleByName(Role.ROLE_USER);
			Role staff = projectService.findRoleByName(Role.ROLE_VANGARD_STAFF);
			Role faculty = projectService
					.findRoleByName(Role.ROLE_VANGARD_FACULTY);
			Role admin = projectService.findRoleByName(Role.ROLE_ADMIN);
			Role adstaff = projectService
					.findRoleByName(Role.ROLE_VANGARD_ADSTAFF);

			// Faculty
			addUser("yu.shyr@vanderbilt.edu", "Yu", "Shyr", "cqs", new Role[] {
					admin, faculty, adstaff });
			addUser("lynne.d.berry@vanderbilt.edu", "Lynne", "Berry", "cqs",
					new Role[] { admin, faculty });
			addUser("yan.guo@vanderbilt.edu", "Yan", "Guo", "cqs",
					new Role[] { faculty });
			addUser("steven.chen@vanderbilt.edu", "Xi", "Chen", "cqs",
					new Role[] { faculty });
			addUser("fei.ye@vanderbilt.edu", "Fei", "Ye", "cqs",
					new Role[] { faculty });
			addUser("qi.liu@vanderbilt.edu", "Qi", "Liu", "cqs",
					new Role[] { faculty });

			// Staff&Postdoc
			addUser("quanhu.sheng@vanderbilt.edu", "Quanhu", "Sheng", "cqs",
					new Role[] { staff });
			addUser("jiang.river.li@vanderbilt.edu", "Jiang", "Li", "cqs",
					new Role[] { staff });
			addUser("xue.zhong@vanderbilt.edu", "Xue", "Zhong", "cqs",
					new Role[] { staff });
			addUser("chung-i.li@vanderbilt.edu", "Chung-i", "Li", "cqs",
					new Role[] { staff });
			addUser("mingsheng.guo@vanderbilt.edu", "Mingsheng", "Guo", "cqs",
					new Role[] { staff });
			addUser("pengcheng.lu@vanderbilt.edu", "Pengcheng", "Lu", "cqs",
					new Role[] { staff });

			// AdStaff
			addUser("sandra.hewston@vanderbilt.edu", "Sandra", "Hewston",
					"cqs", new Role[] { adstaff });
			addUser("lauren.alexander@vanderbilt.edu", "Lauren", "Alexander",
					"cqs", new Role[] { adstaff });

			// artificial user
			addUser("test_contact@vanderbilt.edu", "", "", "cqs",
					new Role[] { user });
			addUser("test_pi@vanderbilt.edu", "", "", "cqs",
					new Role[] { user });
		}

		if (projectService.listTechnology().size() == 0) {
			addTechnology("ChIP-seq", new String[] {}, new Module[] {
					new Module("Data storage", 1.62, 0.54,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0.0),
					new Module("Alignment", 0, 11.94),
					new Module("QC - raw data", 0, 11.53),
					new Module("QC - alignment", 0, 22.64),
					new Module("QC - SNP/somatic mutation", 0, 22.64),
					new Module("Analysis (<20 samples)", 2667.64, 0),
					new Module("Analysis (20-80 samples)", 0, 266.76) });
			addTechnology("DNA-seq (exome)", new String[] {}, new Module[] {
					new Module("Data storage", 1.62, 0.54,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0.0),
					new Module("Alignment", 0, 11.94),
					new Module("QC - raw data", 0, 11.53),
					new Module("QC - alignment", 0, 22.64),
					new Module("QC - SNP/somatic mutation", 0, 22.64),
					new Module("Refinement", 0, 12.66),
					new Module("SNP calls", 269.23, 0),
					new Module("Somatic mutation analysis 1 (standard)",
							133.38, 0.41),
					new Module("Somatic mutation analysis 2 (advanced)",
							133.38, 0.41),
					new Module("Structural variant analysis", 266.76, 0.21),
					new Module("Gene-level analysis", 2134.11, 0) });
			addTechnology("DNA-seq (whole genome)", new String[] {},
					new Module[] {
							new Module("Data storage", 10.8, 3.6,
									"Unit: per sample per month", ModuleType.PerSamplePerUnit),
							new Module("Communications", 200.07, 0.0),
							new Module("Alignment", 0, 14.2),
							new Module("QC - raw data", 0, 35.4),
							new Module("QC - alignment", 0, 46.52),
							new Module("QC - SNP/somatic mutation", 0, 46.52),
							new Module("Refinement", 0, 15.22),
							new Module("SNP calls", 274.16, 0),
							new Module(
									"Somatic mutation analysis 1 (standard)",
									133.38, 0.82),
							new Module(
									"Somatic mutation analysis 2 (advanced)",
									133.38, 0.82),
							new Module("Structural variant analysis", 266.76,
									0.62),
							new Module("Gene-level analysis", 2134.11, 0) });
			addTechnology("Genotyping", new String[] {}, new Module[] {
					new Module("Data storage", 0, 0,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0),
					new Module("Pre-QC SNP call", 266.76, 0),
					new Module("Analysis (QC & SNP call) (<1,000 samples)",
							2667.64, 0),
					new Module("Analysis (QC & SNP call) (>1,000 samples)", 0,
							6.67),
					new Module("Association analysis", 933.68, 133.38,
							"Unit: per model") });
			addTechnology("Methylation array", new String[] {}, new Module[] {
					new Module("Data storage", 1.62, 0.54,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0.0),
					new Module("Analysis (<20 samples)", 266.76, 0),
					new Module("Analysis (20-80 samples)", 0, 26.68) });
			addTechnology("Microarray", new String[] {}, new Module[] {
					new Module("Data storage", 0, 0,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0.0),
					new Module(
							"Differential expression analysis (<100 samples)",
							266.76, 0),
					new Module(
							"Differential expression analysis (>=100 samples)",
							533.53, 0),
					new Module("Pathway/functional analysis", 200.07, 0) });
			addTechnology("miRNA", new String[] {}, new Module[] {
					new Module("Data storage", 0.27, 0.09,
							"Unit: per sample per month", ModuleType.PerSamplePerUnit),
					new Module("Communications", 200.07, 0.0),
					new Module("Flicker (alignment & QC)", 0, 11.32),
					new Module("Analysis", 533.53, 0),
					new Module("Pathway/functional analysis", 533.53, 0) });
			addTechnology(
					"RNA-seq",
					new String[] {},
					new Module[] {
							new Module("Data storage", 1.62, 0.54,
									"Unit: per sample per month", ModuleType.PerSamplePerUnit),
							new Module("Communications", 200.07, 0.0),
							new Module("Alignment", 0, 11.94),
							new Module("QC - raw data", 0, 11.53),
							new Module("QC - alignment", 0, 22.64),
							new Module("QC - SNP/somatic mutation", 0, 22.64),
							new Module("Refinement", 0, 12.66),
							new Module("SNP calls", 402.61, 0),
							new Module(
									"Somatic mutation analysis 1 (standard)",
									200.07, 0.41),
							new Module(
									"Somatic mutation analysis 2 (advanced)",
									200.07, 0.41),
							new Module("Gene quantification analysis", 133.38,
									0.21),
							new Module("Gene fusion analysis (deFuse)", 0,
									74.09),
							new Module(
									"Gene fusion analysis (fusion hunter) (<10 samples)",
									266.76, 2.47),
							new Module(
									"Gene fusion analysis (fusion hunter) (>=10 samples)",
									0, 55.82),
							new Module("Comparison 1", 0.41, 66.69,
									"Unit: per comparison"),
							new Module("Comparison 2 (de novo)", 0.62, 133.38,
									"Unit: per comparison"),
							new Module(
									"Comparison 3 (additional models) (<10 samples)",
									2.47, 133.38, "Unit: per comparison"),
							new Module(
									"Comparison 3 (additional models) (>=10 samples)",
									4.93, 200.07, "Unit: per comparison"),
							new Module(
									"Functional/pathway analysis (<10 samples)",
									266.76, 0),
							new Module(
									"Functional/pathway analysis (>=10 samples)",
									0, 53.35) });
		}

		if (projectService.listProject().size() == 0) {
			User admin = projectService
					.findUserByEmail("yu.shyr@vanderbilt.edu");
			User vfaculty = projectService
					.findUserByEmail("yan.guo@vanderbilt.edu");
			User vstaff = projectService
					.findUserByEmail("quanhu.sheng@vanderbilt.edu");
			// User contact = projectService
			// .findUserByEmail("test_contact@vanderbilt.edu");
			// User studypi = projectService
			// .findUserByEmail("test_pi@vanderbilt.edu");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Project project = new Project();
			project.setCreator(vfaculty.getEmail());
			project.setCreateDate(new Date());

			project.setName("2144");
			project.setIsBioVUSampleRequest(true);
			project.setContractDate(sdf.parse("2012-04-02"));
			project.setContact("Test User");
			project.setStudyPI("Test PI");
			project.setQuoteAmount(11000.0);
			project.setWorkStarted(sdf.parse("2012-04-04"));
			project.setWorkCompleted(sdf.parse("2012-05-04"));

			project.setBilledAmount(10000.0);
			project.setBilledBy("Sandra");
			project.setRequestCostCenterSetupInCORES(sdf.parse("2012-05-07"));
			project.setBilledInCORES(sdf.parse("2012-05-08"));
			project.setCostCenterToBill("9000.0");
			project.setRequestedBy("Jill Shell");

			ProjectComment pc = new ProjectComment();
			pc.setComment("Demo project, any comments are welcome!");
			pc.setCommentDate(new Date());
			pc.setCommentUser(admin.getEmail());
			pc.setProject(project);
			project.getComments().add(pc);

			// addProjectUser(project, contact, UserType.CONTACT);
			// addProjectUser(project, studypi, UserType.STUDYPI);
			addProjectUser(project, vfaculty, UserType.VANGARD_FACULTY);
			addProjectUser(project, vstaff, UserType.VANGARD_STAFF);

			addProjectTechnology(project, "RNA-seq", "Illumina", 6);
			addProjectTechnology(project, "Microarray", "agilentg4502a_07_3", 5);
			// ProjectTechnology pt =
			addProjectTechnology(project, "Genotyping", "", 4);

			projectService.addProject(project);
			// pt.getModules().clear();
			// projectService.updateProjectTechnology(pt);
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

	private ProjectTechnology addProjectTechnology(Project project,
			String name, String platform, int sampleNumber) {
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
			Module[] modules) {
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
		for (Module obj : modules) {
			index++;
			obj.setModuleIndex(index);
			obj.setTechnology(tec);
			projectService.addModule(obj);
		}
	}

	private void addProjectUser(Project project, User user, Integer userType) {
		if (user != null) {
			ProjectUser pu = new ProjectUser(project, user, userType);
			project.getUsers().add(pu);
		}
	}

	private User addUser(String email, String firstname, String lastname,
			String password, Role[] roles) {
		User user = new User();
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPassword(Utils.md5(password));
		user.setCreateDate(new Date());
		for (Role role : roles) {
			user.getRoles().add(new UserRole(user, role));
		}
		projectService.addUser(user);

		return user;
	}
}
