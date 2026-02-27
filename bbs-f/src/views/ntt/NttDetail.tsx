/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState } from "react";
import { apis, endpoints } from "../../apis/api";
import { useLoginUserStore } from "../../hooks/useLoginUser";
import style from "./ntt.module.css";

interface Props {
    nttId: string;
}

const NoticeDetail = ({ nttId }: Props) => {
    const { user } = useLoginUserStore();
    const [notice, setNotice] = useState<any | null>(null);
    const [liked, setLiked] = useState(false);
    const [comments, setComments] = useState<any[]>([]);
    const [commentText, setCommentText] = useState("");
    const [canEdit, setCanEdit] = useState(false);

    const [replyParentId, setReplyParentId] = useState<string | null>(null);
    const [replyText, setReplyText] = useState("");
    const [editingId, setEditingId] = useState<string | null>(null);
    const [editText, setEditText] = useState("");

    const loadNotice = async () => {
        const res = await apis.get<any>(endpoints.nttDetail(nttId));
        if (res) {
            setNotice(res.notice);
            setLiked(res.liked);
            setCanEdit(res.canEdit);
        }
    };

    const loadComments = async () => {
        const res = await apis.get<any>(endpoints.comments(nttId));
        if (res) {
            setComments(res);
        }
    };

    useEffect(() => {
        loadNotice();
        loadComments();
    }, [nttId]);

    const handleNoticeLike = async () => {
        if (!user) {
            alert("로그인 후 이용하세요.");
            return;
        }

        const res = await apis.post<any>(endpoints.nttLike, { nttId, userId: user.uniqId });

        if (res.status === "LIKED") {
            setNotice((prev: any) =>
                prev ? { ...prev, likeCnt: prev.likeCnt + 1 } : prev
            );
            setLiked(true);
        } else if (res.status === "UNLIKED") {
            setNotice((prev: any) =>
                prev ? { ...prev, likeCnt: prev.likeCnt - 1 } : prev
            );
            setLiked(false);
        }
    };

    const handleCommentLike = async (commentId: string) => {
        if (!user) {
            alert("로그인 후 이용하세요.");
            return;
        }

        const res = await apis.post<any>(endpoints.commentLike, { commentId, userId: user.uniqId });

        setComments((prev) =>
            prev.map((c) =>
                c.commentId === commentId
                    ? {
                        ...c,
                        likeCnt:
                            res === "LIKED"
                                ? c.likeCnt + 1
                                : res === "UNLIKED"
                                    ? Math.max(c.likeCnt - 1, 0)
                                    : c.likeCnt,
                    }
                    : c
            )
        );
    };

    const handleCommentSubmit = async () => {
        if (!commentText.trim()) {
            alert("내용을 입력하세요.");
            return;
        }
        console.log(user);

        await apis.post(endpoints.insertComment, {
            nttId,
            commentCn: commentText,
            parentId: replyParentId,
            userId: user?.uniqId,
        });

        setCommentText("");
        loadComments();
    };

    const handleReplySubmit = async () => {
        if (!replyText.trim()) {
            alert("내용을 입력하세요.");
            return;
        }

        await apis.post(endpoints.insertComment, {
            nttId,
            commentCn: replyText,
            parentId: replyParentId,
            userId: user?.uniqId,
        });

        setReplyParentId(null);
        setReplyText("");
        loadComments();
    };

    const startEdit = (commentId: string, content: string) => {
        setEditingId(commentId);
        setEditText(content);
    };

    const handleEditSave = async (commentId: string) => {
        if (!editText.trim()) {
            alert("내용을 입력하세요.");
            return;
        }

        await apis.post(endpoints.updateComment, {
            commentId,
            commentCn: editText,
            frstRegisterId: user?.uniqId,
            userId: user?.uniqId,
        });

        setEditingId(null);
        setEditText("");
        loadComments();
    };

    const handleDelete = async (commentId: string) => {
        if (!window.confirm("삭제하시겠습니까?")) return;

        await apis.post(endpoints.deleteComment, {
            commentId,
            frstRegisterId: user?.uniqId,
            userId: user?.uniqId,
        });

        loadComments();
    };

    if (!notice) return <div>Loading...</div>;

    return (
        <div className={style.ntBox}>
            <h2 className={style.ntTitle}>
                <span className={style.ntBadgeNotice}>
                    {notice.pinnedAt === "Y" && "[공지] "}
                </span>
                {notice.subject}
            </h2>

            <div className={style.ntMeta}>
                작성자: {notice.frstRegisterId} |
                작성일: {notice.frstRegistPnttm} |
                조회수: {notice.viewCnt}
                <span className={style.ntLikeArea}>
                    <button onClick={handleNoticeLike} className={liked ? "active" : ""}>
                        👍 {notice.likeCnt}
                    </button>
                </span>
            </div>

            {notice.pinnedAt === "Y" && (notice.startDate || notice.endDate) && (
                <div className={`${style.ntNoticePeriod} ${style.ntMeta}`}>
                    게시기간 :
                    {notice.startDate}
                    ~
                    {notice.endDate}
                </div>
            )}
            <div className={style.ntContent}>
                {notice.content}
            </div>

            <div className={style.ntFiles}>
                <strong>첨부파일</strong>
                {notice.fileList && notice.fileList.length > 0 ? (
                    <ul>
                        {notice.fileList.map((f: any) => (
                            <li key={f.fileSn}>
                                <a href="#" className="js-file-download"
                                    data-atch={f.atchFileId} data-sn={f.fileSn}>
                                    {f.orignlFileNm}
                                </a>
                                <span className={style.ntMuted}> ({f.fileSize} byte)</span>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <div className={style.ntMuted}>첨부파일이 없습니다.</div>
                )}
                <form id="downloadForm" method="post" action='/bbs/notice/downloadNoticeFile.do'>
                    <input type="hidden" name="nttId" value={notice.nttId} />
                    <input type="hidden" name="atchFileId" />
                    <input type="hidden" name="fileSn" />
                </form>
            </div>

            <div className={style.ntActions}>
                <a className={style.ntBtn} href="/bbs/notice/list.do">목록 </a>

                {canEdit && (
                    <a className={style.ntBtn}
                        href={`/bbs/notice/form.do/${notice.nttId}`}>
                        수정 </a>
                )}

                <button type="button" className={`${style.ntBtn} js-delete`}>삭제</button>

                <form id="deleteForm" method="post" action="/bbs/notice/deleteNotice.do">
                    <input type="hidden" name="nttId" id="nttId" value={notice.nttId} />
                    <input type="hidden" name="bbsId" value={notice.bbsId} />
                </form>
            </div>

            <hr />

            <div>
                <textarea
                    value={commentText}
                    onChange={(e) => setCommentText(e.target.value)}
                    placeholder="댓글을 입력하세요"
                />
                <button onClick={handleCommentSubmit}>등록</button>
            </div>

            <ul style={{ marginTop: 20 }}>
                {comments && comments.length === 0 && (
                    <li className={style.commentEmpty}>댓글이 없습니다.</li>
                )}

                {comments.map((c) => (
                    <li
                        key={c.commentId}
                        style={{ marginLeft: c.commentDepth * 20 }}
                        className={style.commentItem}
                    >
                        <div className={style.commentInfo}>
                            <strong className={style.writer}>{c.frstRegisterId}</strong>
                            <span className={style.date}> {c.frstRegistPnttm}</span>
                        </div>

                        {/* 수정 모드 */}
                        {editingId === c.commentId ? (
                            <div>
                                <textarea
                                    value={editText}
                                    onChange={(e) => setEditText(e.target.value)}
                                />
                                <button onClick={() => handleEditSave(c.commentId)}>저장</button>
                                <button onClick={() => setEditingId(null)}>취소</button>
                            </div>
                        ) : (
                            <div className={style.commentCn}>{c.commentCn}</div>
                        )}

                        <div className={style.commentActions}>
                            <button
                                onClick={() => setReplyParentId(c.commentId)}
                                className={style.btnReply}
                            >
                                답글
                            </button>

                            <button
                                onClick={() => handleCommentLike(c.commentId)}
                                className={style.btnLike}
                            >
                                👍 {c.likeCnt}
                            </button>

                            {user?.uniqId === c.frstRegisterId && (
                                <>
                                    <button
                                        onClick={() => startEdit(c.commentId, c.commentCn)}
                                        className={style.btnEdit}
                                    >
                                        수정
                                    </button>
                                    <button
                                        onClick={() => handleDelete(c.commentId)}
                                        className={style.btnDelete}
                                    >
                                        삭제
                                    </button>
                                </>
                            )}
                        </div>

                        {/* 답글 입력창 */}
                        {replyParentId === c.commentId && (
                            <div style={{ marginLeft: 20 }}>
                                <textarea
                                    value={replyText}
                                    onChange={(e) => setReplyText(e.target.value)}
                                    placeholder="답글을 입력하세요"
                                />
                                <button onClick={handleReplySubmit}>등록</button>
                                <button onClick={() => setReplyParentId(null)}>취소</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>
        </div >
    );
};

export default NoticeDetail;