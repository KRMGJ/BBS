package egovframework.let.bbs.cmt.vo;

import java.util.Date;

public class CommentLikeVO {
	private String commentLikeId; // COMMENT_LIKE_ID
	private String commentId; // COMMENT_ID
	private String userId; // USER_ID
	private Date likeDt;
	private String useAt; // USE_AT

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getCommentLikeId() {
		return commentLikeId;
	}

	public void setCommentLikeId(String commentLikeId) {
		this.commentLikeId = commentLikeId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLikeDt() {
		return likeDt;
	}

	public void setLikeDt(Date likeDt) {
		this.likeDt = likeDt;
	}
}
