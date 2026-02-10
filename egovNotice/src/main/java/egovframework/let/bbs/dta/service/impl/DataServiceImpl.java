package egovframework.let.bbs.dta.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.bbs.dta.dao.DataDao;
import egovframework.let.bbs.dta.service.DataService;

@Service("dataService")
public class DataServiceImpl implements DataService {

	@Resource(name = "dataDao")
	private DataDao dataDao;

}
