package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Pipeline;

@Repository
public class PipelineDAOImpl extends GenericDAOImpl<Pipeline, Long>
		implements PipelineDAO {
}
