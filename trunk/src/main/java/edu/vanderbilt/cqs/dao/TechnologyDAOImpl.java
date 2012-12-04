package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Technology;

@Repository
public class TechnologyDAOImpl extends GenericDAOImpl<Technology, Long>
		implements TechnologyDAO {
}
