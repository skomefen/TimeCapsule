package timeCapsule.service;

import java.util.List;
import java.util.Map;

import timeCapsule.domain.PageBean;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.User;
import timeCapsule.exception.EmailExistException;
import timeCapsule.exception.UserExistException;

public interface UserBusinessService {

	//��web���ṩע�����
	void register(User user) throws UserExistException, EmailExistException;

	//��web���ṩ��½����
	User login(String username, String password);
	
	User findbyUsername(String username);

	void addUser(User user) throws UserExistException, EmailExistException;

	User findUser(String username, String password);
	
	User find(String id);

	void deleteUser(String id);

	void updateUser(User user) throws UserExistException, EmailExistException;

	Map<String, User> getUserMapAll();

	PageBean pageQuery(QueryInfo queryinfo);

	List<Privilege> getUserAllPrivilege(User user);

}