package egovframework.let.bbs.cmt.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.vo.LoginVO;
import egovframework.let.bbs.cmm.util.EgovUtil;
import egovframework.let.bbs.cmt.service.CommentService;
import egovframework.let.bbs.cmt.vo.CommentVO;

@Controller
@RequestMapping("/bbs/cmt")
public class CommentController {

	@Resource(name = "commentService")
	private CommentService commentService;

	/**
	 * 댓글 목록 조회
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	@ResponseBody
	public List<CommentVO> selectCommentList(@RequestParam("nttId") String nttId) throws Exception {

		return commentService.selectCommentList(nttId);
	}

	/**
	 * 댓글 등록
	 */
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertComment(CommentVO vo) throws Exception {

		vo.setCommentCn(EgovUtil.clearXSS(vo.getCommentCn()));

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		vo.setFrstRegisterId(loginVO.getUniqId());

		commentService.insertComment(vo);
		return "OK";
	}

	/**
	 * 댓글 삭제 (논리삭제)
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteComment(CommentVO vo) throws Exception {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (!loginVO.getUniqId().equals(vo.getFrstRegisterId())) {
			return "NOT_AUTHORIZED";
		}
		commentService.deleteComment(vo);
		return "OK";
	}

	/**
	 * 댓글 수정
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateComment(CommentVO vo) throws Exception {

		vo.setCommentCn(EgovUtil.clearXSS(vo.getCommentCn()));

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		if (!loginVO.getUniqId().equals(vo.getFrstRegisterId())) {
			return "NOT_AUTHORIZED";
		}
		commentService.updateComment(vo);
		return "OK";
	}
}
