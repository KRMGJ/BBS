package egovframework.let.bbs.cmt.service;

import java.util.List;

import egovframework.let.bbs.cmt.vo.CommentVO;

public interface CommentService {

	/**
	 * 댓글 목록 조회
	 * 
	 * @param nttId - 댓글이 등록된 게시물ID
	 * @return 댓글 목록
	 * @throws Exception
	 */
	List<CommentVO> selectCommentList(String nttId) throws Exception;

	/**
	 * 댓글 등록
	 * 
	 * @param vo - 등록할 댓글 정보가 담긴 CommentVO
	 * @return 등록된 댓글ID
	 * @throws Exception
	 */
	String insertComment(CommentVO vo) throws Exception;

	/**
	 * 댓글 삭제
	 * 
	 * @param vo - 삭제할 댓글 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	void deleteComment(CommentVO vo) throws Exception;

	/**
	 * 댓글 수정
	 * 
	 * @param vo - 수정할 댓글 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	void updateComment(CommentVO vo) throws Exception;
}
