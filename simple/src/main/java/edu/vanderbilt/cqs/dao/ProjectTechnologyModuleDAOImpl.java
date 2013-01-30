package edu.vanderbilt.cqs.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Module;
import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;

@Repository
public class ProjectTechnologyModuleDAOImpl extends
		GenericDAOImpl<ProjectTechnologyModule, Long> implements
		ProjectTechnologyModuleDAO {
	@Autowired
	private ModuleDAO mDAO;

	private boolean ObjectEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		return obj1.equals(obj2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assignModulePrice(Long projectId) {
		String hql = String
				.format("From ProjectTechnologyModule as ptm where ptm.technology.project.id=%d",
						projectId);
		List<ProjectTechnologyModule> modules = getSession().createQuery(hql)
				.list();

		List<ProjectTechnologyModule> updated = new ArrayList<ProjectTechnologyModule>();
		for (ProjectTechnologyModule ptm : modules) {
			Module m = mDAO.findById(ptm.getModuleId());
			if (m == null) {
				continue;
			}
			if (ObjectEquals(m.getPricePerProject(), ptm.getPricePerProject())
					&& ObjectEquals(m.getPricePerUnit(), ptm.getPricePerUnit())
					&& ObjectEquals(m.getModuleType(), ptm.getModuleType())) {
				continue;
			}
			ptm.setPricePerProject(m.getPricePerProject());
			ptm.setPricePerUnit(m.getPricePerUnit());
			ptm.setModuleType(m.getModuleType());
			updated.add(ptm);
		}

		if (updated.size() > 0) {
			for (ProjectTechnologyModule ptm : updated) {
				update(ptm);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectTechnologyModule> getModuleInProject(Long projectId) {
		String hql = String
				.format("From ProjectTechnologyModule as ptm where ptm.technology.project.id=%d",
						projectId);
		return getSession().createQuery(hql).list();
	}
}
