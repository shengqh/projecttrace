package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Module;

@Repository
public class ModuleDAOImpl extends GenericDAOImpl<Module, Long> implements
		ModuleDAO {
}
