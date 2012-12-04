package edu.vanderbilt.cqs.dao;

import org.springframework.stereotype.Repository;

import edu.vanderbilt.cqs.bean.Platform;

@Repository
public class PlatformDAOImpl extends GenericDAOImpl<Platform, Long>
		implements PlatformDAO {
}
