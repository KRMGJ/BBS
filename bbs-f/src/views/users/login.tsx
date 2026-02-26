import React, { useState } from 'react'
import { apis, endpoints } from '../../apis/api'
import style from './login.module.css'
import { useNavigate } from 'react-router-dom'

interface LoginResponse {
    result: string;
}

export default function Login() {
    const [userId, setUserId] = useState<string>('')
    const [password, setPassword] = useState<string>('')
    const [msg, setMsg] = useState<string>('')
    const [error, setError] = useState<boolean>(true)

    const navigate = useNavigate();

    const handleLogin = async () => {
        if (!userId || !password) {
            setError(true)
            setMsg('아이디와 비밀번호를 입력해주세요.')
            return
        }
        const res = await apis.post<LoginResponse>(endpoints.login, { userId, password })
        console.log(res)
        if (res.result == 'OK') {
            setMsg('로그인 성공!')
            setError(false)
            navigate('/bbs/notice/list.do')
        } else {
            setError(true)
            setMsg('로그인 실패')
        }
    }
    return (
        <div className={style.loginWrapper}>
            <div className={style.row}>
                <label htmlFor="userId">아이디</label>
                <input type="text" id="userId" autoComplete="username" value={userId} onChange={(e) => setUserId(e.target.value)} />
            </div>

            <div className={style.row}>
                <label htmlFor="password">비밀번호</label>
                <input type="password" id="password" autoComplete="current-password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </div>

            <button type="button" id="btnLogin" onClick={handleLogin}>로그인</button>
            <button type="button" id="btnJoin" onClick={() => navigate('/bbs/user/joinView.do')}>회원가입</button>

            <p className={error ? 'err' : ''}>{msg}</p>
        </div>
    )
}
