/* eslint-disable @typescript-eslint/no-explicit-any */
import { useState } from 'react'
import { apis, endpoints } from '../../apis/api'
import { useNavigate } from 'react-router-dom'

export default function Join() {
    const [userId, setUserId] = useState('')
    const [password, setPassword] = useState('')
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [phone, setPhone] = useState('')
    const [msg, setMsg] = useState('')
    const [error, setError] = useState(true)

    const navigate = useNavigate();

    const handleCheckDuplicate = async () => {
        if (!userId) {
            setError(true)
            setMsg('아이디를 입력해주세요.')
            return
        }
        const res = await apis.get<any>(`${endpoints.checkDuplicate}?userId=${userId}`)
        console.log(res)
        if (res.duplicated == false) {
            setMsg('사용 가능한 아이디입니다.')
            setError(false)
        } else {
            setError(true)
            setMsg('이미 사용 중인 아이디입니다.')
        }
    }


    const handleJoin = async () => {
        if (!userId || !password || !name || !email || !phone) {
            setError(true)
            setMsg('모든 필드를 입력해주세요.')
            return
        }
        const res = await apis.post<any>(endpoints.join, { userId, password, userNm: name, email, mobile: phone })
        if (res.result == 'OK') {
            setMsg('회원가입 성공!')
            setError(false)
            navigate('/bbs/user/loginView.do');
        } else {
            setError(true)
            setMsg('회원가입 실패')
        }
    }
    return (
        <div
            style={{
                width: '380px',
                margin: '80px auto',
                padding: '20px',
                border: '1px solid #ddd'
            }}
        >
            <input
                placeholder="아이디"
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                style={{
                    width: '100%',
                    marginBottom: '8px',
                    padding: '8px',
                    boxSizing: 'border-box'
                }}
            />

            <button
                type="button"
                onClick={handleCheckDuplicate}
                style={{
                    width: '100%',
                    padding: '10px',
                    marginBottom: '6px'
                }}
            >
                중복확인
            </button>

            <input
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                style={{
                    width: '100%',
                    marginBottom: '8px',
                    padding: '8px',
                    boxSizing: 'border-box'
                }}
            />

            <input
                placeholder="이름"
                value={name}
                onChange={(e) => setName(e.target.value)}
                style={{
                    width: '100%',
                    marginBottom: '8px',
                    padding: '8px',
                    boxSizing: 'border-box'
                }}
            />

            <input
                type="email"
                placeholder="이메일"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                style={{
                    width: '100%',
                    marginBottom: '8px',
                    padding: '8px',
                    boxSizing: 'border-box'
                }}
            />

            <input
                placeholder="휴대폰"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                style={{
                    width: '100%',
                    marginBottom: '8px',
                    padding: '8px',
                    boxSizing: 'border-box'
                }}
            />

            <button
                type="button"
                onClick={handleJoin}
                style={{
                    width: '100%',
                    padding: '10px',
                    marginBottom: '6px'
                }}
            >
                가입하기
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
