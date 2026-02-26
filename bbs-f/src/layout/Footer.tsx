import styles from './Footer.module.css';

export default function Footer() {
    return (
        <footer id="egov-footer" className={styles.egovFooter}>
            <div className={styles.footerInner}>
                <div className={styles.footerInfo}>
                    <p className={styles.siteName}>전자정부 공지시스템</p>
                    <p>
                        (12345) 서울특별시 ○○구 ○○로 123<br /> TEL : 02-1234-5678 | FAX :
                            02-1234-5679
                    </p>
                </div>

                <div className={styles.footerLinks}>
                    <a href="#">이용약관</a> <span className={styles.divider}>|</span> <a href="#">개인정보처리방침</a>
                    <span className={styles.divider}>|</span> <a href="#">저작권정책</a>
                </div>

                <div className={styles.footerCopy}>
                    <p>Copyright © <span className={styles.year}></span> 전자정부표준프레임워크.</p> All Rights Reserved.
                </div>
            </div>
        </footer>
    )
}
