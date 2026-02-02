package egovframework.let.bbs.mstr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.mstr.dao.BbsMasterDAO;
import egovframework.let.bbs.mstr.service.BbsMasterService;
import egovframework.let.bbs.mstr.vo.BbsMasterVO;

@Service("bbsMasterService")
public class BbsMasterServiceImpl implements BbsMasterService {

	@Resource(name = "bbsMasterDAO")
	private BbsMasterDAO bbsMasterDAO;

	@Resource(name = "bbsMasterIdGnrService")
	private EgovIdGnrService bbsMasterIdGnrService;

	/**
	 * 게시판 마스터 목록을 조회한다.
	 */
	@Override
	public List<BbsMasterVO> selectBbsMasterList(BbsMasterVO searchVO) throws Exception {
		return bbsMasterDAO.selectBbsMasterList(searchVO);
	}

	/**
	 * 게시판 마스터 총 갯수를 조회한다.
	 */
	@Override
	public int selectBbsMasterListCnt(BbsMasterVO searchVO) throws Exception {
		return bbsMasterDAO.selectBbsMasterListCnt(searchVO);
	}

	@Override
	public String insertBbsMaster(BbsMasterVO vo) throws Exception {
		// IDGEN으로 BBS_ID 생성
		String bbsId = bbsMasterIdGnrService.getNextStringId();
		vo.setBbsId(bbsId);

		// 기본값 보정
		if (!StringUtils.hasText(vo.getFileAtchPosblAt())) {
			vo.setFileAtchPosblAt("Y");
		}
		if (vo.getAtchPosblFileNumber() < 0) {
			vo.setAtchPosblFileNumber(0);
		}
		if (vo.getAtchPosblFileSize() < 0) {
			vo.setAtchPosblFileSize(0);
		}
		if (!StringUtils.hasText(vo.getUseAt())) {
			vo.setUseAt("Y");
		}

		// 등록자/수정자 세팅(로그인 연동 전이면 임시값 가능)
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String loginId = (loginVO != null) ? loginVO.getUniqId() : "system";

		vo.setFrstRegisterId(loginId);
		vo.setLastUpdusrId(loginId);

		bbsMasterDAO.insertBbsMaster(vo);

		return bbsId;
	}

	@Override
	public BbsMasterVO selectBbsMasterDetail(BbsMasterVO vo) throws Exception {
		return bbsMasterDAO.selectBbsMasterDetail(vo);
	}

	@Override
	public int updateBbsMaster(BbsMasterVO vo) throws Exception {
		// 최소 방어
		if (!StringUtils.hasText(vo.getFileAtchPosblAt())) {
			vo.setFileAtchPosblAt("Y");
		}
		if (!StringUtils.hasText(vo.getUseAt())) {
			vo.setUseAt("Y");
		}
		if (vo.getAtchPosblFileNumber() < 0) {
			vo.setAtchPosblFileNumber(0);
		}
		if (vo.getAtchPosblFileSize() < 0) {
			vo.setAtchPosblFileSize(0);
		}

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String loginId = loginVO != null ? loginVO.getUniqId() : "system";
		vo.setLastUpdusrId(loginId);

		return bbsMasterDAO.updateBbsMaster(vo);
	}

}
