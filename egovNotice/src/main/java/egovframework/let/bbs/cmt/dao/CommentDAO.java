package egovframework.let.bbs.cmt.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.cmt.vo.CommentVO;

@Repository("commentDAO")
public class CommentDAO extends EgovAbstractMapper {

	/**
	 * 댓글 목록 조회
	 * 
	 * @param nttId - 댓글이 등록된 게시물ID
	 * @return 댓글 목록
	 */
	public List<CommentVO> selectCommentList(String nttId) {
		return selectList("CommentDAO.selectCommentList", nttId);
	}

	/**
	 * 댓글 등록
	 * 
	 * @param vo - 등록할 댓글 정보가 담긴 CommentVO
	 */
	public void insertComment(CommentVO vo) {
		insert("CommentDAO.insertComment", vo);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param vo - 삭제할 댓글 정보가 담긴 CommentVO
	 */
	public void deleteComment(CommentVO vo) {
		update("CommentDAO.deleteComment", vo);
	}

	/**
	 * 댓글 수정
	 * 
	 * @param vo - 수정할 댓글 정보가 담긴 CommentVO
	 */
	public void updateComment(CommentVO vo) {
		update("CommentDAO.updateComment", vo);
	}
}
