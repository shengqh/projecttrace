package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Module;

public interface ModuleDAO extends GenericDAO<Module, Long> {
	List<Module> findByTechnology(Long technologyId);
}
