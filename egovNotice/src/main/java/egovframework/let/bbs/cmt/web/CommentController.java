package egovframework.let.bbs.cmt.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.vo.ApiVO;
import egovframework.let.bbs.cmm.util.EgovUtil;
import egovframework.let.bbs.cmt.service.CommentService;
import egovframework.let.bbs.cmt.vo.CommentVO;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/bbs/cmt")
public class CommentController {

	@Resource(name = "commentService")
	private CommentService commentService;

	/**
	 * 댓글 목록 조회
	 */
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ResponseEntity<?> selectCommentList(@RequestParam("nttId") String nttId) throws Exception {

		List<CommentVO> comment = commentService.selectCommentList(nttId);
		return ResponseEntity.ok(ApiVO.success("댓글 조회 성공", comment));
	}

	/**
	 * 댓글 등록
	 */
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public ResponseEntity<?> insertComment(@RequestBody CommentVO vo) throws Exception {

		vo.setCommentCn(EgovUtil.clearXSS(vo.getCommentCn()));

		vo.setFrstRegisterId(vo.getUserId());

		commentService.insertComment(vo);
		return ResponseEntity.ok(ApiVO.success("댓글 등록 성공", "OK"));
	}

	/**
	 * 댓글 삭제 (논리삭제)
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public ResponseEntity<?> deleteComment(@RequestBody CommentVO vo) throws Exception {

		if (vo.getUserId().equals(vo.getFrstRegisterId())) {
			return ResponseEntity.status(403).body(ApiVO.error("권한이 없습니다."));
		}
		commentService.deleteComment(vo);
		return ResponseEntity.ok(ApiVO.success("댓글 삭제 성공", "OK"));
	}

	/**
	 * 댓글 수정
	 */
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public ResponseEntity<?> updateComment(@RequestBody CommentVO vo) throws Exception {

		vo.setCommentCn(EgovUtil.clearXSS(vo.getCommentCn()));

		if (vo.getUserId().equals(vo.getFrstRegisterId())) {
			return ResponseEntity.status(403).body(ApiVO.error("권한이 없습니다."));
		}
		commentService.updateComment(vo);
		return ResponseEntity.ok(ApiVO.success("댓글 수정 성공", "OK"));
	}

	@RequestMapping(value = "/like.do", method = RequestMethod.POST)
	public ResponseEntity<?> likeComment(@RequestBody CommentVO vo) throws Exception {

		String res = commentService.likeComment(vo);
		return ResponseEntity.ok(ApiVO.success("좋아요 처리 완료", res));
	}
}
