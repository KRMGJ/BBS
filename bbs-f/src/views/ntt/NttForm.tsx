/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState, type ChangeEvent } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { apis, endpoints } from "../../apis/api"
import style from "./ntt.module.css"

export default function NttForm() {

    const navigate = useNavigate()
    const { nttId } = useParams()

    const isEdit = !!nttId

    const [form, setForm] = useState<any>({
        nttId: "",
        bbsId: "",
        subject: "",
        content: "",
        pinnedAt: "N",
        startDate: "",
        endDate: ""
    })

    const [fileList, setFileList] = useState<any[]>([])
    const [files, setFiles] = useState<FileList | null>(null)
    const [delFileSn, setDelFileSn] = useState<string[]>([])

    useEffect(() => {
        if (isEdit) {
            apis.get(endpoints.nttDetail(nttId)).then((res: any) => {
                setForm(res.notice)
                setFileList(res.fileList || [])
            })
        }
    }, [nttId])

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = async () => {

        const title = form.subject?.trim()
        const body = form.content?.trim()

        if (!title) {
            alert("제목을 입력하세요.")
            return
        }
        if (!body) {
            alert("내용을 입력하세요.")
            return
        }

        const b = form.startDate?.trim()
        const e = form.endDate?.trim()

        if ((b && b.length !== 8) || (e && e.length !== 8)) {
            alert("게시기간은 YYYYMMDD 형식으로 입력하세요.")
            return
        }

        if (b && e && b > e) {
            alert("시작일은 종료일보다 클 수 없습니다.")
            return
        }

        const formData = new FormData()

        Object.keys(form).forEach(key => {
            if (form[key] !== null && form[key] !== undefined) {
                formData.append(key, form[key])
            }
        })

        if (files) {
            Array.from(files).forEach(file => {
                formData.append("files", file)
            })
        }

        delFileSn.forEach(sn => {
            formData.append("delFileSn", sn)
        })

        try {
            if (isEdit) {
                await apis.post(endpoints.updateNtt, formData)
            } else {
                await apis.post(endpoints.insertNtt, formData)
            }

            alert(isEdit ? "수정되었습니다." : "등록되었습니다.")
            navigate("/bbs/notice/list.do")
        } catch {
            alert("처리 중 오류가 발생했습니다.")
        }
    }

    const handleFileDeleteCheck = (sn: string) => {
        setDelFileSn(prev =>
            prev.includes(sn)
                ? prev.filter(v => v !== sn)
                : [...prev, sn]
        )
    }

    return (
        <div className={style.ntWrap}>
            <div className={style.ntBox}>

                <div className={style.ntRow}>
                    <label>제목</label>
                    <input
                        type="text"
                        name="subject"
                        value={form.subject}
                        onChange={handleChange}
                        maxLength={500}
                    />
                </div>

                <div className={style.ntRow}>
                    <label>내용</label>
                    <textarea
                        name="content"
                        value={form.content}
                        onChange={handleChange}
                    />
                </div>

                <div className={`${style.ntRow} ${style.ntGrid2}`}>
                    <div>
                        <label>공지 설정</label>
                        <select
                            name="pinnedAt"
                            value={form.pinnedAt}
                            onChange={handleChange}
                        >
                            <option value="N">일반</option>
                            <option value="Y">공지(상단)</option>
                        </select>
                    </div>

                    <div>
                        <label>게시기간</label>
                        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "8px" }}>
                            <input
                                type="text"
                                name="startDate"
                                placeholder="시작(YYYYMMDD)"
                                value={form.startDate}
                                onChange={handleChange}
                            />
                            <input
                                type="text"
                                name="endDate"
                                placeholder="종료(YYYYMMDD)"
                                value={form.endDate}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                </div>

                <div className={style.ntRow}>
                    <label>첨부파일</label>
                    <input
                        type="file"
                        multiple
                        onChange={(e) => setFiles(e.target.files)}
                    />

                    {isEdit && (
                        <div style={{ marginTop: 10 }}>
                            <div style={{ fontWeight: 700 }}>기존 첨부</div>

                            {fileList.length > 0 ? (
                                <ul style={{ marginTop: 6 }}>
                                    {fileList.map(f => (
                                        <li key={f.fileSn}>
                                            {f.orignlFileNm}
                                            <span className={style.ntMuted}>
                                                ({f.fileSize} bytes)
                                            </span>

                                            <label style={{ marginLeft: 8 }}>
                                                <input
                                                    type="checkbox"
                                                    onChange={() => handleFileDeleteCheck(String(f.fileSn))}
                                                />
                                                삭제
                                            </label>
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <div className={style.ntMuted}>
                                    기존 첨부파일이 없습니다.
                                </div>
                            )}
                        </div>
                    )}
                </div>

                <div className={style.ntActions}>
                    <button
                        type="button"
                        onClick={() => navigate("/bbs/notice/list.do")}
                    >
                        목록
                    </button>

                    <button
                        type="button"
                        onClick={handleSubmit}
                    >
                        {isEdit ? "수정" : "등록"}
                    </button>
                </div>

            </div>
        </div>
    )
}