package egovframework.let.bbs.cmt.vo;

import java.io.Serializable;

public class CommentVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String commentId;
	private String nttId;
	private String commentCn;
	private String useAt;
	private String delAt;
	private String frstRegisterId;
	private String frstRegistPnttm;
	private Integer likeCnt; // 좋아요 수
	private String likeId; // COMMENT_LIKE_ID
	private String parentId; // 부모 댓글 ID
	private String commentGroup; // 댓글 그룹
	private Integer commentDepth; // 댓글 깊이
	private Integer commentOrder; // 댓글 순서
	private String userId; // 로그인한 사용자 ID

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getNttId() {
		return nttId;
	}

	public void setNttId(String nttId) {
		this.nttId = nttId;
	}

	public String getCommentCn() {
		return commentCn;
	}

	public void setCommentCn(String commentCn) {
		this.commentCn = commentCn;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getDelAt() {
		return delAt;
	}

	public void setDelAt(String delAt) {
		this.delAt = delAt;
	}

	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCommentGroup() {
		return commentGroup;
	}

	public void setCommentGroup(String commentGroup) {
		this.commentGroup = commentGroup;
	}

	public Integer getCommentDepth() {
		return commentDepth;
	}

	public void setCommentDepth(Integer commentDepth) {
		this.commentDepth = commentDepth;
	}

	public Integer getCommentOrder() {
		return commentOrder;
	}

	public void setCommentOrder(Integer commentOrder) {
		this.commentOrder = commentOrder;
	}

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public Integer getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Integer likeCnt) {
		this.likeCnt = likeCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
