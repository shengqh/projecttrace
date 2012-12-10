package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.Technology;

public interface TechnologyDAO extends GenericDAO<Technology, Long> {
	List<Technology> listValidTechnology();

	Technology findByName(String name);
}
