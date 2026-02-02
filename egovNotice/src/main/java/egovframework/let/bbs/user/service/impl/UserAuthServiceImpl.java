package egovframework.let.bbs.user.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cryptography.EgovPasswordEncoder;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.bbs.user.dao.ComtnUserDAO;
import egovframework.let.bbs.user.service.UserAuthService;
import egovframework.let.bbs.user.vo.ComtnUserVO;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

	@Resource(name = "comtnUserDAO")
	private ComtnUserDAO comtnUserDAO;

	@Resource(name = "egovPasswordEncoder")
	private EgovPasswordEncoder passwordEncoder;

	/**
	 * 로그인 처리
	 */
	@Override
	public LoginVO login(String userId, String rawPassword) {
		ComtnUserVO user = comtnUserDAO.selectUserForLogin(userId);
		if (user == null) {
			throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		if (!"Y".equals(user.getUseAt())) {
			throw new IllegalStateException("사용 중지된 계정입니다.");
		}
		if ("Y".equals(user.getLockAt())) {
			throw new IllegalStateException("잠금 처리된 계정입니다.");
		}
		if (user.getPassword() == null || !passwordEncoder.checkPassword(rawPassword, user.getPassword())) {
			throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
		}

		LoginVO loginVO = new LoginVO();
		loginVO.setUniqId(user.getUserId()); // USER_ID를 uniqId로 사용
		loginVO.setName(user.getUserNm());
		loginVO.setEmail(user.getEmail());

		EgovUserDetailsHelper.setAuthenticatedUser(loginVO);
		return loginVO;
	}

	/**
	 * 로그아웃 처리
	 */
	@Override
	public void logout() {
		EgovUserDetailsHelper.clearAuthenticatedUser();
	}

	/**
	 * 현재 로그인 사용자 정보 조회(로그인하지 않은 경우 null 반환)
	 */
	@Override
	public LoginVO meOrNull() {
		Object obj = EgovUserDetailsHelper.getAuthenticatedUser();
		if (obj instanceof LoginVO) {
			return (LoginVO) obj;
		}
		return null;
	}

}
