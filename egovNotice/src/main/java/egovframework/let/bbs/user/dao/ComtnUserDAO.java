package egovframework.let.bbs.user.dao;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import egovframework.let.bbs.user.vo.ComtnUserVO;

@Repository("comtnUserDAO")
public class ComtnUserDAO extends EgovAbstractMapper {

	/**
	 * userId로 사용자 정보 조회
	 * 
	 * @param userId - 조회할 userId
	 * @return ComtnUserVO - 조회된 사용자 정보
	 */
	public ComtnUserVO selectUserById(String userId) {
		return (ComtnUserVO) selectOne("ComtnUserDAO.selectUserById", userId);
	}

	/**
	 * 로그인 처리를 위한 사용자 정보 조회
	 * 
	 * @param userId - 조회할 userId
	 * @return ComtnUserVO - 조회된 사용자 정보
	 */
	public ComtnUserVO selectUserForLogin(String userId) {
		return (ComtnUserVO) selectOne("ComtnUserDAO.selectUserForLogin", userId);
	}

	/**
	 * 사용자 등록
	 * 
	 * @param user - 등록할 사용자 정보
	 * @return int - 등록 결과
	 */
	public int insertUser(ComtnUserVO user) {
		return insert("ComtnUserDAO.insertUser", user);
	}

	/**
	 * userId로 사용자 수 조회
	 * 
	 * @param userId - 조회할 userId
	 * @return int - 조회된 사용자 수
	 */
	public int selectUserIdCount(String userId) {
		return (Integer) selectOne("ComtnUserDAO.selectUserIdCount", userId);
	}
}
