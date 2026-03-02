/* eslint-disable @typescript-eslint/no-explicit-any */
import { useState } from 'react'
import { apis, endpoints } from '../../apis/api'
import { useNavigate } from 'react-router-dom'
import { useLoginUserStore } from '../../hooks/useLoginUser';

interface LoginResponse {
    result: string;
    loginVO: any;
}

export default function Login() {
    const { setLoginUser } = useLoginUserStore();
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
            setLoginUser(res.loginVO);
            navigate('/bbs/notice/list.do')
        } else {
            setError(true)
            setMsg('로그인 실패')
        }
    }
    return (
        <div
            style={{
                width: '360px',
                margin: '80px auto',
                padding: '20px',
                border: '1px solid #ddd'
            }}
        >
            <div style={{ marginBottom: '12px' }}>
                <label htmlFor="userId" style={{ display: 'block', marginBottom: '6px' }}>
                    아이디
                </label>
                <input
                    type="text"
                    id="userId"
                    autoComplete="username"
                    value={userId}
                    onChange={(e) => setUserId(e.target.value)}
                    style={{
                        width: '100%',
                        padding: '8px',
                        boxSizing: 'border-box'
                    }}
                />
            </div>

            <div style={{ marginBottom: '12px' }}>
                <label htmlFor="password" style={{ display: 'block', marginBottom: '6px' }}>
                    비밀번호
                </label>
                <input
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{
                        width: '100%',
                        padding: '8px',
                        boxSizing: 'border-box'
                    }}
                />
            </div>

            <button
                style={{
                    width: '100%',
                    padding: '10px',
                    marginBottom: '12px'
                }}
                type="button"
                id="btnLogin"
                onClick={handleLogin}
            >
                로그인
            </button>

            <button
                style={{
                    width: '100%',
                    padding: '10px',
                    marginBottom: '12px'
                }}
                type="button"
                id="btnJoin"
                onClick={() => navigate('/bbs/user/joinView.do')}
            >
                회원가입
            </button>

            <p
                style={{
                    marginTop: '12px',
                    color: error ? '#c00' : 'inherit'
                }}
            >
                {msg}
            </p>
        </div>
    )
}
