package egovframework.let.bbs.cmt.dao;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.cmt.vo.CommentLikeVO;
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

	/**
	 * 부모 댓글 조회
	 * 
	 * @param parentId - 부모 댓글 ID
	 * @return 부모 댓글 정보가 담긴 CommentVO
	 */
	public CommentVO selectComment(String parentId) {
		return selectOne("CommentDAO.selectComment", parentId);
	}

	/**
	 * 댓글 순서 업데이트 (기존 댓글들 +1)
	 * 
	 * @param parent - 부모 댓글 정보가 담긴 CommentVO
	 */
	public void updateOrderPlus(CommentVO parent) {
		update("CommentDAO.updateOrderPlus", parent);
	}

	/**
	 * 최대 댓글 순서 조회
	 * 
	 * @param nttId - 댓글이 등록된 게시물ID
	 * @return 최대 댓글 순서
	 */
	public int selectMaxOrder(String nttId) {
		return selectOne("CommentDAO.selectMaxOrder", nttId);
	}

	/**
	 * 댓글 좋아요 조회
	 * 
	 * @param vo     - 좋아요할 댓글 정보가 담긴 CommentVO
	 * @param userId - 좋아요를 누른 사용자ID
	 * @return 댓글 좋아요 정보가 담긴 CommentLikeVO (좋아요가 없는 경우 null)
	 */
	public CommentLikeVO selectLike(CommentVO vo, String userId) {
		return selectOne("CommentDAO.selectLike", Map.of("commentId", vo.getCommentId(), "userId", userId));
	}

	/**
	 * 댓글 좋아요 수 업데이트
	 * 
	 * @param vo - 좋아요할 댓글 정보가 담긴 CommentVO
	 * @param i  - 업데이트할 좋아요 수 (증가 또는 감소)
	 */
	public void updateLikeCount(CommentVO vo, int i) {
		update("CommentDAO.updateLikeCount", Map.of("commentId", vo.getCommentId(), "likeCount", i));
	}

	/**
	 * 댓글 좋아요 등록
	 * 
	 * @param vo     - 좋아요할 댓글 정보가 담긴 CommentVO
	 * @param userId - 좋아요를 누른 사용자ID
	 */
	public void insertLike(CommentVO vo, String userId) {
		insert("CommentDAO.insertLike",
				Map.of("likeId", vo.getLikeId(), "commentId", vo.getCommentId(), "userId", userId));
	}

	/**
	 * 댓글 좋아요 사용 여부 업데이트
	 * 
	 * @param vo     - 좋아요할 댓글 정보가 담긴 CommentVO
	 * @param userId - 좋아요를 누른 사용자ID
	 * @param string - 사용 여부 ("Y" 또는 "N")
	 */
	public void updateLikeUseAt(CommentVO vo, String userId, String string) {
		update("CommentDAO.updateLikeUseAt", Map.of("commentId", vo.getCommentId(), "userId", userId, "useAt", string));

	}
}
