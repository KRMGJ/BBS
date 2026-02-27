/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from 'react'
import { apis, endpoints } from '../../apis/api';
import style from './ntt.module.css'
import { useNavigate } from 'react-router-dom';
import { useLoginUserStore } from '../../hooks/useLoginUser';

export default function NttList() {
    const { user, checkUser } = useLoginUserStore();
    const [searchVO, setSearchVO] = useState<any>({})
    const [noticeList, setNoticeList] = useState<any[]>([])
    const [pinnedList, setPinnedList] = useState<any[]>([])
    const [paginationInfo, setPaginationInfo] = useState<any>(null)
    const [totalCount, setTotalCount] = useState<number>(0)
    const [totalPageCount, setTotalPageCount] = useState<number>(0)
    const [startPage, setStartPage] = useState<number>(0)
    const [endPage, setEndPage] = useState<number>(0)

    const [replyTitle, setReplyTitle] = useState<string>('')
    const [replyText, setReplyText] = useState<string>('')

    const [replyParentId, setReplyParentId] = useState<string | null>(null)

    const navigate = useNavigate();

    const fetchData = async () => {
        const res = await apis.get<any>(endpoints.nttList(searchVO))
        if (res) {
            setSearchVO(res.searchVO)
            setNoticeList(res.noticeList)
            setPinnedList(res.pinnedList)
            setPaginationInfo(res.paginationInfo)
            setTotalCount(res.totalCount)
            setTotalPageCount(res.totalPageCount)
            setStartPage(res.startPage)
            setEndPage(res.endPage)
        }
    }

    const handleDelete = async () => {
        checkUser();
        const checked = Array.from(
            document.querySelectorAll("input[name='nttId']:checked")
        ) as HTMLInputElement[]

        if (checked.length === 0) {
            alert("삭제할 게시글을 선택하세요.")
            return
        }

        if (!confirm("선택한 게시글을 삭제하시겠습니까?")) {
            return
        }

        const nttIdList = checked.map(el => el.value)

        try {
            await apis.post(endpoints.deleteNttList(nttIdList), {})
            alert("삭제되었습니다.")
            fetchData();
        } catch {
            alert("삭제 중 오류가 발생했습니다.")
        }
    }

    const handleDeleteReply = async (nttId: string) => {
        checkUser();
        if (!confirm("댓글을 삭제하시겠습니까?")) {
            return
        }

        try {
            await apis.post(endpoints.deleteNttList([nttId]), {})
            alert("댓글이 삭제되었습니다.")
            fetchData();
        } catch {
            alert("댓글 삭제 중 오류가 발생했습니다.")
        }
    }

    const handleReply = async () => {
        checkUser();
        if (!replyParentId) {
            alert("답글의 부모 게시글이 없습니다.")
            return
        }

        if (replyText.trim() === '') {
            alert("내용을 입력하세요.")
            return
        }

        try {
            await apis.post(endpoints.nttReply, {
                bbsId: searchVO.bbsId,
                parntNttId: replyParentId,
                subject: replyTitle,
                content: replyText,
                frstRegisterId: user?.uniqId
            })
            alert("답글이 등록되었습니다.")
            setReplyTitle('');
            setReplyText('');
            setReplyParentId(null);
            fetchData();
        } catch {
            alert("답글 등록 중 오류가 발생했습니다.")
        }
    }

    useEffect(() => {
        fetchData();
    }, [searchVO.pageIndex]);

    const handleReg = () => {
        navigate("/bbs/notice/form.do");
    }

    const linkPage = (pageNo: number) => {
        setSearchVO((prev: any) => ({ ...prev, pageIndex: pageNo }))
    }

    const handleSearch = () => {
        setSearchVO({ ...searchVO, pageIndex: 1 })
    }

    const openReply = (id: string) => {
        setReplyTitle('');
        setReplyText('');
        setReplyParentId(id);
    };

    return (
        <>
            <div className={style.ntWrap}>
                <form id="searchForm" method="get">
                    <input type="hidden" name="pageIndex" value={searchVO.pageIndex} />
                    <input type="hidden" name="bbsId" value={searchVO.bbsId} />

                    <div className={style.ntToolbar}>
                        <div className={style.ntSearch}>
                            <select name="searchCondition" value={searchVO.searchCondition} onChange={(e) => setSearchVO({ ...searchVO, searchCondition: e.target.value })}>
                                <option value="0">제목</option>
                                <option value="1">내용</option>
                                <option value="2">작성자</option>
                            </select>
                            <input type="text" name="searchKeyword" value={searchVO.searchKeyword} onChange={(e) => setSearchVO({ ...searchVO, searchKeyword: e.target.value })} placeholder="검색어 입력" />
                            <button type="button" id="btnSearch" onClick={handleSearch}>검색</button>
                        </div>

                        <div>
                            <button type="button" onClick={handleReg}>등록</button>
                        </div>
                    </div >
                </form >

                <div className={style.ntMuted}>
                    총 <b>{paginationInfo ? paginationInfo.totalRecordCount : (totalCount ? totalCount : 0)}</b>
                    건
                </div>

                <table>
                    <thead>
                        <tr>
                            <th style={{ width: '40px' }}><input type="checkbox" id="checkAll" /></th>
                            <th className={style.ntColNo}>번호</th>
                            <th>제목</th>
                            <th className={style.ntColDate}>등록일</th>
                            <th className={style.ntColView}>조회</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pinnedList && pinnedList.length > 0 && pinnedList.map((n) => (
                            <tr className={style.ntNotice} key={`pinned-${n.nttId}`}>
                                <td style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                                    <input type="checkbox" name="nttId" value={n.nttId} />
                                </td>
                                <td className={style.ntColNo}>공지</td>
                                <td>
                                    <a href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}>
                                        {n.subject}
                                    </a>
                                    {n.atchFileId && (
                                        <span className={style.ntMuted}>[첨부]</span>
                                    )}
                                </td>
                                <td className={style.ntColDate}>{n.frstRegistPnttm}</td>
                                <td className={style.ntColView}>{n.viewCnt}</td>
                            </tr>
                        ))}
                        {noticeList && noticeList.length > 0 ? (
                            noticeList.map((n) => (
                                <React.Fragment key={n.nttId}>
                                    {n.nttLevel === 1 ? (
                                        <tr
                                            className={style.ntRow}
                                            data-root={n.rootId}
                                            data-id={n.nttId}
                                        >
                                            <td>
                                                <input type="checkbox" name="nttId" value={n.nttId} />
                                            </td>
                                            <td className={style.ntColNo}>{n.nttId}</td>
                                            <td>
                                                {n.delAt === 'Y' ? (
                                                    <span className={style.deletedText}>
                                                        삭제된 게시글입니다.
                                                    </span>
                                                ) : (
                                                    <>
                                                        <a href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}>
                                                            {n.subject}
                                                        </a>
                                                        <button
                                                            type="button"
                                                            className={style.btnReply}
                                                            onClick={() => openReply(n.nttId)}
                                                        >
                                                            답글
                                                        </button>
                                                    </>
                                                )}
                                            </td>
                                            <td className={style.ntColDate}>{n.frstRegistPnttm}</td>
                                            <td className={style.ntColView}>{n.viewCnt}</td>
                                        </tr>
                                    ) : (
                                        <tr
                                            className={style.ntReplyRow}
                                            data-root={n.rootId}
                                            data-id={n.nttId}
                                        >
                                            <td></td>
                                            <td></td>
                                            <td colSpan={3}>
                                                <div
                                                    className={style.replyBox}
                                                    style={{ marginLeft: `${(n.nttLevel - 1) * 25}px` }}
                                                >
                                                    {n.rootDelAt === 'Y' ? (
                                                        <span className={style.deletedParentMsg}>
                                                            삭제된 게시물의 답글입니다.
                                                        </span>
                                                    ) : n.delAt === 'Y' ? (
                                                        <span className={style.deletedText}>
                                                            삭제된 게시글입니다.
                                                        </span>
                                                    ) : (
                                                        <>
                                                            <a
                                                                href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}
                                                                className={style.replyContent}
                                                            >
                                                                {n.subject}
                                                            </a>
                                                            <span className={style.replyDate}>
                                                                ({n.frstRegistPnttm})
                                                            </span>
                                                            <button
                                                                type="button"
                                                                className={style.btnReply}
                                                                onClick={() => openReply(n.nttId)}
                                                            >
                                                                답글
                                                            </button>
                                                            <button
                                                                type="button"
                                                                className={style.btnDeleteReply}
                                                                onClick={() => handleDeleteReply(n.nttId)}
                                                            >
                                                                삭제
                                                            </button>
                                                        </>
                                                    )}
                                                </div>
                                            </td>
                                        </tr>
                                    )}

                                    {replyParentId === n.nttId && (
                                        <tr>
                                            <td colSpan={5}>
                                                <div
                                                    style={{
                                                        marginLeft: `${(n.nttLevel - 1) * 25}px`,
                                                        padding: '10px 0'
                                                    }}
                                                >
                                                    <input
                                                        value={replyTitle}
                                                        onChange={(e) => setReplyTitle(e.target.value)}
                                                        placeholder="제목"
                                                    />

                                                    <textarea
                                                        value={replyText}
                                                        onChange={(e) => setReplyText(e.target.value)}
                                                        placeholder="내용"
                                                    />

                                                    <button onClick={handleReply}>등록</button>

                                                    <button
                                                        type="button"
                                                        onClick={() => setReplyParentId(null)}
                                                    >
                                                        취소
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    )}

                                </React.Fragment>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={5} style={{ textAlign: 'center', color: '#666' }}>
                                    조회된 데이터가 없습니다.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table >

                <div className={style.btnArea}>
                    <button type="button" onClick={handleDelete}>선택삭제</button>
                </div>

                <div className={style.pagination}>
                    {totalPageCount > 0 && startPage > 1 && (
                        <a onClick={() => linkPage(startPage - 1)}>이전</a>
                    )}
                    {Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i).map((i) => (
                        i === searchVO.pageIndex ? (
                            <strong key={i}>{i}</strong>
                        ) : (
                            <a key={i} onClick={() => linkPage(i)}>{i}</a>
                        )
                    ))}
                    {totalPageCount > 0 && endPage < totalPageCount && (
                        <a onClick={() => linkPage(endPage + 1)}>다음</a>
                    )}
                </div>
            </div >
        </>
    )
}