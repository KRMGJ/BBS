import { endpoints, postData } from '../apis/api';
import { useLoginUserStore } from '../hooks/useLoginUser'
import style from './Header.module.css'

export default function Header() {
    const {user} = useLoginUserStore();

    const path = location.pathname;

    document.querySelectorAll(`.${style.gnb} a`).forEach((a) => {
        if (a.getAttribute('href') === path) {
            a.classList.add(style.active);
        } else {
            a.classList.remove(style.active);
        }
    })

    const logoutBtn = async () => {
        const res = await postData(endpoints.logout, {});
        if(res.result != 'OK') {
            alert('로그아웃 실패')
            return
        }
        useLoginUserStore.getState().resetLoginUser();
        location.href = '/bbs/notice/list.do';
    };

    return (
        <header id={style.egovHeader}>
            <div className={style.headerInner}>
                <h1 className={style.logo}>
                    <a href="/">전자정부 공지시스템</a>
                </h1>

                <nav className={style.gnb}>
                    <ul>
                        <li><a href="/bbs/notice/list.do">공지사항</a></li>
                        <li><a href="/bbs/dta/list.do">자료실</a></li>
                        <li><a href="#">민원안내</a></li>
                    </ul>
                </nav>

                <div className={style.userArea}>
                    {user ? (
                        <>
                            <span className={style.userName}>{user.name}님</span>
                            <a href="" className={style.btn} onClick={logoutBtn}>로그아웃</a>
                        </>
                    ) : (
                        <a href="/bbs/user/loginView.do" className={style.btn}>로그인</a>
                    )}
                </div>
            </div>
        </header>
    )
}
