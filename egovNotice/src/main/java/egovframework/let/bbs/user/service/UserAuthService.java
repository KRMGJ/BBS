package egovframework.let.bbs.user.service;

import egovframework.com.cmm.vo.LoginVO;

public interface UserAuthService {

	/**
	 * 로그인 처리
	 * 
	 * @param userId      - 아이디
	 * @param rawPassword - 비밀번호(평문)
	 * @return LoginVO
	 */
	LoginVO login(String userId, String rawPassword);

	/**
	 * 로그아웃 처리
	 */
	void logout();

	/**
	 * 현재 로그인 사용자 정보 조회(로그인하지 않은 경우 null 반환)
	 * 
	 * @return LoginVO or null
	 */
	LoginVO meOrNull();
}
