package edu.vanderbilt.cqs.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.ProjectTechnologyModule;

@Repository
public class ProjectTechnologyModuleDAOImpl extends
		GenericDAOImpl<ProjectTechnologyModule, Long> implements
		ProjectTechnologyModuleDAO {

	@Override
	public void assignModulePrice(Long projectId) {
		String sql = String
				.format("update module as m, projecttechnologymodule as ptm, projecttechnology as pt set ptm.pricePerProject=m.pricePerProject, ptm.pricePerUnit=m.pricePerUnit, ptm.description=m.description, ptm.moduleType=m.moduleType where pt.project_id=%d and ptm.technology_id=pt.id and ptm.module_id=m.id",
						projectId);
		Query qry = getSession().createSQLQuery(sql);
		qry.executeUpdate();
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
