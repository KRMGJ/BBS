package egovframework.let.bbs.dta.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import egovframework.let.bbs.dta.service.DataService;

@Controller
public class DataController {

	@Resource(name = "dataService")
	private DataService dataService;
}
