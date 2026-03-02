/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from 'react'
import { apis, endpoints } from '../../apis/api';
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
        if (!checkUser()) return;
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
        if (!checkUser()) return;
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
        if (!checkUser()) return;
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
            <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
                <form id="searchForm" method="get">
                    <input type="hidden" name="pageIndex" value={searchVO.pageIndex} />
                    <input type="hidden" name="bbsId" value={searchVO.bbsId} />

                    <div
                        style={{
                            display: 'flex',
                            justifyContent: 'space-between',
                            alignItems: 'flex-end',
                            gap: '12px',
                            margin: '12px 0'
                        }}
                    >
                        <div style={{ display: 'flex', gap: '8px', alignItems: 'center' }}>
                            <select
                                name="searchCondition"
                                value={searchVO.searchCondition}
                                onChange={(e) => setSearchVO({ ...searchVO, searchCondition: e.target.value })}
                                style={{ height: '32px', padding: '0 8px', boxSizing: 'border-box' }}
                            >
                                <option value="0">제목</option>
                                <option value="1">내용</option>
                                <option value="2">작성자</option>
                            </select>

                            <input
                                type="text"
                                name="searchKeyword"
                                value={searchVO.searchKeyword}
                                onChange={(e) => setSearchVO({ ...searchVO, searchKeyword: e.target.value })}
                                placeholder="검색어 입력"
                                style={{ height: '32px', padding: '0 8px', boxSizing: 'border-box' }}
                            />

                            <button
                                type="button"
                                id="btnSearch"
                                onClick={handleSearch}
                                style={{ height: '32px', padding: '0 12px', cursor: 'pointer' }}
                            >
                                검색
                            </button>
                        </div>

                        <div>
                            <button
                                type="button"
                                onClick={handleReg}
                                style={{ height: '32px', padding: '0 12px', cursor: 'pointer' }}
                            >
                                등록
                            </button>
                        </div>
                    </div>
                </form>

                <div style={{ color: '#666', fontSize: '12px' }}>
                    총 <b>{paginationInfo ? paginationInfo.totalRecordCount : (totalCount ? totalCount : 0)}</b>건
                </div>

                <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '10px' }}>
                    <thead>
                        <tr>
                            <th style={{ width: '40px', border: '1px solid #ddd', padding: '10px', background: '#f7f7f7' }}>
                                <input type="checkbox" id="checkAll" />
                            </th>
                            <th style={{ width: '70px', textAlign: 'center', border: '1px solid #ddd', padding: '10px', background: '#f7f7f7' }}>
                                번호
                            </th>
                            <th style={{ border: '1px solid #ddd', padding: '10px', background: '#f7f7f7' }}>
                                제목
                            </th>
                            <th style={{ width: '170px', textAlign: 'center', border: '1px solid #ddd', padding: '10px', background: '#f7f7f7' }}>
                                등록일
                            </th>
                            <th style={{ width: '90px', textAlign: 'center', border: '1px solid #ddd', padding: '10px', background: '#f7f7f7' }}>
                                조회
                            </th>
                        </tr>
                    </thead>

                    <tbody>
                        {pinnedList && pinnedList.length > 0 && pinnedList.map((n) => (
                            <tr key={`pinned-${n.nttId}`} style={{ background: '#fff7e6' }}>
                                <td style={{ border: '1px solid #ddd', padding: '10px', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                                    <input type="checkbox" name="nttId" value={n.nttId} />
                                </td>
                                <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>공지</td>
                                <td style={{ border: '1px solid #ddd', padding: '10px' }}>
                                    <a
                                        href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}
                                        style={{ color: '#1a73e8', textDecoration: 'none' }}
                                    >
                                        {n.subject}
                                    </a>
                                    {n.atchFileId && (
                                        <span style={{ color: '#666', fontSize: '12px', marginLeft: '6px' }}>[첨부]</span>
                                    )}
                                </td>
                                <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                    {n.frstRegistPnttm}
                                </td>
                                <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                    {n.viewCnt}
                                </td>
                            </tr>
                        ))}

                        {noticeList && noticeList.length > 0 ? (
                            noticeList.map((n) => (
                                <React.Fragment key={n.nttId}>
                                    {n.nttLevel === 1 ? (
                                        <tr data-root={n.rootId} data-id={n.nttId}>
                                            <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                                <input type="checkbox" name="nttId" value={n.nttId} />
                                            </td>
                                            <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                                {n.nttId}
                                            </td>
                                            <td style={{ border: '1px solid #ddd', padding: '10px' }}>
                                                {n.delAt === 'Y' ? (
                                                    <span style={{ color: '#999', fontStyle: 'italic' }}>
                                                        삭제된 게시글입니다.
                                                    </span>
                                                ) : (
                                                    <>
                                                        <a
                                                            href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}
                                                            style={{ color: '#1a73e8', textDecoration: 'none' }}
                                                        >
                                                            {n.subject}
                                                        </a>
                                                        <button
                                                            type="button"
                                                            onClick={() => openReply(n.nttId)}
                                                            style={{ marginLeft: '8px', fontSize: '12px', cursor: 'pointer' }}
                                                        >
                                                            답글
                                                        </button>
                                                    </>
                                                )}
                                            </td>
                                            <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                                {n.frstRegistPnttm}
                                            </td>
                                            <td style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center' }}>
                                                {n.viewCnt}
                                            </td>
                                        </tr>
                                    ) : (
                                        <tr data-root={n.rootId} data-id={n.nttId} style={{ background: '#fafafa' }}>
                                            <td style={{ border: '1px solid #ddd', padding: '10px' }}></td>
                                            <td style={{ border: '1px solid #ddd', padding: '10px' }}></td>
                                            <td colSpan={3} style={{ border: '1px solid #ddd', padding: '10px' }}>
                                                <div style={{ padding: '6px 0', fontSize: '14px', marginLeft: `${(n.nttLevel - 1) * 25}px` }}>
                                                    {n.rootDelAt === 'Y' ? (
                                                        <span style={{ color: '#999', fontStyle: 'italic' }}>
                                                            삭제된 게시물의 답글입니다.
                                                        </span>
                                                    ) : n.delAt === 'Y' ? (
                                                        <span style={{ color: '#999', fontStyle: 'italic' }}>
                                                            삭제된 게시글입니다.
                                                        </span>
                                                    ) : (
                                                        <>
                                                            <a
                                                                href={`/bbs/notice/selectNoticeDetail.do?nttId=${n.nttId}`}
                                                                style={{ marginRight: '10px', color: '#1a73e8', textDecoration: 'none' }}
                                                            >
                                                                {n.subject}
                                                            </a>
                                                            <span style={{ color: '#aaa', fontSize: '12px' }}>
                                                                ({n.frstRegistPnttm})
                                                            </span>
                                                            <button
                                                                type="button"
                                                                onClick={() => openReply(n.nttId)}
                                                                style={{ marginLeft: '8px', fontSize: '12px', cursor: 'pointer' }}
                                                            >
                                                                답글
                                                            </button>
                                                            <button
                                                                type="button"
                                                                onClick={() => handleDeleteReply(n.nttId)}
                                                                style={{ marginLeft: '6px', fontSize: '12px', cursor: 'pointer' }}
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
                                            <td colSpan={5} style={{ border: '1px solid #ddd', padding: '10px' }}>
                                                <div style={{ marginLeft: `${(n.nttLevel - 1) * 25}px`, padding: '10px 0' }}>
                                                    <input
                                                        value={replyTitle}
                                                        onChange={(e) => setReplyTitle(e.target.value)}
                                                        placeholder="제목"
                                                        style={{ width: '100%', marginBottom: '8px', padding: '8px', boxSizing: 'border-box' }}
                                                    />

                                                    <textarea
                                                        value={replyText}
                                                        onChange={(e) => setReplyText(e.target.value)}
                                                        placeholder="내용"
                                                        style={{ width: '100%', minHeight: '240px', padding: '10px', boxSizing: 'border-box', resize: 'none' }}
                                                    />

                                                    <button
                                                        onClick={handleReply}
                                                        style={{ marginTop: '8px', height: '34px', padding: '0 12px', cursor: 'pointer' }}
                                                    >
                                                        등록
                                                    </button>

                                                    <button
                                                        type="button"
                                                        onClick={() => setReplyParentId(null)}
                                                        style={{ marginLeft: '8px', height: '34px', padding: '0 12px', cursor: 'pointer' }}
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
                                <td colSpan={5} style={{ border: '1px solid #ddd', padding: '10px', textAlign: 'center', color: '#666' }}>
                                    조회된 데이터가 없습니다.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>

                <div style={{ marginTop: '14px', display: 'flex', gap: '8px' }}>
                    <button
                        type="button"
                        onClick={handleDelete}
                        style={{ height: '32px', padding: '0 12px', cursor: 'pointer' }}
                    >
                        선택삭제
                    </button>
                </div>

                <div style={{ margin: '40px 0', textAlign: 'center', fontSize: 0 }}>
                    {totalPageCount > 0 && startPage > 1 && (
                        <a
                            onClick={() => linkPage(startPage - 1)}
                            style={{
                                display: 'inline-block',
                                minWidth: '36px',
                                height: '36px',
                                lineHeight: '34px',
                                margin: '0 3px',
                                padding: '0 14px',
                                fontSize: '14px',
                                border: '1px solid #c9c9c9',
                                background: '#fff',
                                color: '#333',
                                textDecoration: 'none',
                                cursor: 'pointer'
                            }}
                        >
                            이전
                        </a>
                    )}

                    {Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i).map((i) =>
                        i === searchVO.pageIndex ? (
                            <strong
                                key={i}
                                style={{
                                    display: 'inline-block',
                                    minWidth: '36px',
                                    height: '36px',
                                    lineHeight: '34px',
                                    margin: '0 3px',
                                    padding: '0 10px',
                                    fontSize: '14px',
                                    border: '1px solid #005ea6',
                                    background: '#005ea6',
                                    color: '#fff',
                                    fontWeight: 600
                                }}
                            >
                                {i}
                            </strong>
                        ) : (
                            <a
                                key={i}
                                onClick={() => linkPage(i)}
                                style={{
                                    display: 'inline-block',
                                    minWidth: '36px',
                                    height: '36px',
                                    lineHeight: '34px',
                                    margin: '0 3px',
                                    padding: '0 10px',
                                    fontSize: '14px',
                                    border: '1px solid #c9c9c9',
                                    background: '#fff',
                                    color: '#333',
                                    textDecoration: 'none',
                                    cursor: 'pointer'
                                }}
                            >
                                {i}
                            </a>
                        )
                    )}

                    {totalPageCount > 0 && endPage < totalPageCount && (
                        <a
                            onClick={() => linkPage(endPage + 1)}
                            style={{
                                display: 'inline-block',
                                minWidth: '36px',
                                height: '36px',
                                lineHeight: '34px',
                                margin: '0 3px',
                                padding: '0 14px',
                                fontSize: '14px',
                                border: '1px solid #c9c9c9',
                                background: '#fff',
                                color: '#333',
                                textDecoration: 'none',
                                cursor: 'pointer'
                            }}
                        >
                            다음
                        </a>
                    )}
                </div>
            </div>
        </>
    )
}