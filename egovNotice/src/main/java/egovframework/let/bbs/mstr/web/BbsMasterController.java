package egovframework.let.bbs.mstr.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.let.bbs.mstr.service.BbsMasterService;
import egovframework.let.bbs.mstr.vo.BbsMasterVO;

@Controller
public class BbsMasterController {

	@Resource(name = "bbsMasterService")
	private BbsMasterService bbsMasterService;

	/**
	 * 게시판 마스터 목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @param model    - 모델
	 * @return mstr/bbsMasterList
	 * @throws Exception
	 */
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

		return "mstr/bbsMasterList";
	}

	/**
	 * 등록 화면
	 * 
	 * @param vo    - 등록할 정보가 담긴 VO
	 * @param model - 모델
	 * @return mstr/bbsMasterRegist
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/bbsMaster/insertBbsMasterView.do")
	public String insertBbsMasterView(@ModelAttribute("bbsMasterVO") BbsMasterVO vo, ModelMap model) throws Exception {
		// 기본값(화면 기본값과 맞춤)
		if (vo.getAtchPosblFileNumber() == 0) {
			vo.setAtchPosblFileNumber(3);
		}
		if (vo.getAtchPosblFileSize() == 0) {
			vo.setAtchPosblFileSize(50);
		}
		if (vo.getFileAtchPosblAt() == null) {
			vo.setFileAtchPosblAt("Y");
		}
		if (vo.getUseAt() == null) {
			vo.setUseAt("Y");
		}

		return "mstr/bbsMasterRegist";
	}

	/**
	 * 게시판 마스터 정보를 등록한다.
	 * 
	 * @param vo - 등록할 정보가 담긴 VO
	 * @return "redirect:/admin/bbsMaster/selectBbsMasterList.do"
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/bbsMaster/insertBbsMaster.do")
	public String insertBbsMaster(@ModelAttribute("bbsMasterVO") BbsMasterVO vo) throws Exception {

		// 서버 측 필수값 검증(최소)
		if (vo.getBbsNm() == null || vo.getBbsNm().trim().isEmpty()) {
			// 간단 처리: 등록 화면으로 복귀(실무는 validator+message 처리)
			return "mstr/bbsMasterRegist";
		}

		String bbsId = bbsMasterService.insertBbsMaster(vo);

		// 등록 후 상세로 이동
		return "redirect:/admin/bbsMaster/selectBbsMasterDetail.do?bbsId=" + bbsId;
		// 목록으로 보내려면 아래 주석 해제
		// return "redirect:/admin/bbsMaster/selectBbsMasterList.do";
	}

	/**
	 * 게시판 마스터 상세정보를 조회한다.
	 * 
	 * @param vo    - 조회할 정보가 담긴 VO
	 * @param model - 모델
	 * @return mstr/bbsMasterDetail
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/bbsMaster/selectBbsMasterDetail.do")
	public String selectBbsMasterDetail(@ModelAttribute("searchVO") BbsMasterVO searchVO, Model model)
			throws Exception {
		if (searchVO.getBbsId() == null || searchVO.getBbsId().trim().isEmpty()) {
			// bbsId 없으면 목록으로(필요하면 에러 메시지 처리)
			return "redirect:/admin/bbsMaster/selectBbsMasterList.do";
		}

		BbsMasterVO bbsMasterVO = bbsMasterService.selectBbsMasterDetail(searchVO);

		model.addAttribute("bbsMasterVO", bbsMasterVO);
		return "mstr/bbsMasterDetail";
	}

	/**
	 * 게시판 마스터 수정화면으로 이동한다.
	 * 
	 * @param vo    - 수정할 정보가 담긴 VO
	 * @param model - 모델
	 * @return mstr/bbsMasterModify
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/bbsMaster/updateBbsMasterView.do")
	public String updateBbsMasterView(@ModelAttribute("searchVO") BbsMasterVO searchVO, Model model) throws Exception {

		if (searchVO.getBbsId() == null || searchVO.getBbsId().trim().isEmpty()) {
			return "redirect:/admin/bbsMaster/selectBbsMasterList.do";
		}

		BbsMasterVO detail = bbsMasterService.selectBbsMasterDetail(searchVO);
		model.addAttribute("bbsMasterVO", detail);

		return "mstr/bbsMasterModify";
	}

	/**
	 * 게시판 마스터 정보를 수정한다.
	 * 
	 * @param bbsMasterVO - 수정할 정보가 담긴 VO
	 * @param searchVO    - 검색정보가 담긴 VO
	 * @param model       - 모델
	 * @return "redirect:/admin/bbsMaster/selectBbsMasterDetail.do?bbsId=..."
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/bbsMaster/updateBbsMaster.do")
	public String updateBbsMaster(@ModelAttribute("bbsMasterVO") BbsMasterVO bbsMasterVO,
			@ModelAttribute("searchVO") BbsMasterVO searchVO, Model model) throws Exception {

		// 필수값 최소 검증
		if (bbsMasterVO.getBbsId() == null || bbsMasterVO.getBbsId().trim().isEmpty()) {
			return "redirect:/admin/bbsMaster/selectBbsMasterList.do";
		}
		if (bbsMasterVO.getBbsNm() == null || bbsMasterVO.getBbsNm().trim().isEmpty()) {
			// 수정 화면으로 복귀(상세 다시 로드해서 뿌려도 됨)
			model.addAttribute("bbsMasterVO", bbsMasterService.selectBbsMasterDetail(bbsMasterVO));
			return "mstr/bbsMasterModify";
		}

		bbsMasterService.updateBbsMaster(bbsMasterVO);

		return "redirect:/admin/bbsMaster/selectBbsMasterDetail.do?bbsId=" + bbsMasterVO.getBbsId();
	}

}
