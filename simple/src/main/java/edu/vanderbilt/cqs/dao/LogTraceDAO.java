package edu.vanderbilt.cqs.dao;

import java.util.List;

import edu.vanderbilt.cqs.bean.LogTrace;

public interface LogTraceDAO extends GenericDAO<LogTrace, Long> {
	List<LogTrace> listAll();
}
