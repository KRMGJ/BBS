package egovframework.let.bbs.dta.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.let.bbs.dta.service.DataService;
import egovframework.let.bbs.dta.vo.DataVO;

@Controller
@RequestMapping("/bbs/dta")
public class DataController {

	@Resource(name = "dataService")
	private DataService dataService;

	private static final String DATA_BBS_ID = "BBSMSTR_000000000002"; // 자료실 BBS_ID

	/** 자료실 목록을 조회한다. */
	@RequestMapping("/list.do")
	public String selectDataList(DataVO searchVO, Model model) throws Exception {

		searchVO.setBbsId(DATA_BBS_ID);

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<DataVO> resultList = dataService.selectDataList(searchVO);
		int totalCnt = dataService.selectDataListCnt(searchVO);

		paginationInfo.setTotalRecordCount(totalCnt);

		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);

		return "dta/dataList";
	}
}
