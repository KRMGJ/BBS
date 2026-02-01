package egovframework.let.bbs.mstr.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.let.bbs.mstr.service.BbsMasterService;
import egovframework.let.bbs.mstr.vo.BbsMasterVO;

@Controller
public class BbsMasterController {

	@Resource(name = "bbsMasterService")
	private BbsMasterService bbsMasterService;

	@RequestMapping(value = "/admin/bbsMaster/selectBbsMasterList.do")
	public String selectBbsMasterList(@ModelAttribute("searchVO") BbsMasterVO searchVO, Model model) throws Exception {

		if (searchVO.getPageIndex() < 1) {
			searchVO.setPageIndex(1);
		}
		if (searchVO.getPageUnit() < 1) {
			searchVO.setPageUnit(10);
		}
		if (searchVO.getPageSize() < 1) {
			searchVO.setPageSize(10);
		}

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		searchVO.setLastIndex(searchVO.getFirstIndex() + searchVO.getRecordCountPerPage());

		int totalCnt = bbsMasterService.selectBbsMasterListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalCnt);

		List<BbsMasterVO> resultList = bbsMasterService.selectBbsMasterList(searchVO);

		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);

		// JSP 경로는 네 프로젝트 ViewResolver에 맞춰 조정
		// 예) /WEB-INF/jsp/egovframework/let/bbs/mstr/bbsMasterList.jsp
		return "mstr/bbsMasterList";
	}

}
