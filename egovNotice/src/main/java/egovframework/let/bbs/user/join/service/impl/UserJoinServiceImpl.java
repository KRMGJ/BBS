package egovframework.let.bbs.user.join.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cryptography.EgovPasswordEncoder;
import org.springframework.stereotype.Service;

import egovframework.let.bbs.user.dao.ComtnUserDAO;
import egovframework.let.bbs.user.join.service.UserJoinService;
import egovframework.let.bbs.user.vo.ComtnUserVO;

@Service("userJoinService")
public class UserJoinServiceImpl implements UserJoinService {

	@Resource(name = "comtnUserDAO")
	private ComtnUserDAO comtnUserDAO;

	@Resource(name = "egovPasswordEncoder")
	private EgovPasswordEncoder passwordEncoder;

	@Override
	public void join(ComtnUserVO user) {

		if (user.getUserId() == null || user.getUserId().isEmpty()) {
			throw new IllegalArgumentException("아이디를 입력하세요.");
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new IllegalArgumentException("비밀번호를 입력하세요.");
		}
		if (isDuplicatedUserId(user.getUserId())) {
			throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
		}

		// 비밀번호 암호화
		user.setPassword(passwordEncoder.encryptPassword(user.getPassword()));

		// 최초 등록자 = 본인
		user.setFrstRegisterId(user.getUserId());

		comtnUserDAO.insertUser(user);
	}

	@Override
	public boolean isDuplicatedUserId(String userId) {
		return comtnUserDAO.selectUserIdCount(userId) > 0;
	}

}
