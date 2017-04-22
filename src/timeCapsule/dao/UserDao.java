package timeCapsule.dao;

import java.util.List;
import java.util.Map;

import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.User;



public interface UserDao {

	void add(User user);

	User find(String username, String password);
	
	User findbyUsername(String username);

	User find(String id);
	//查找注册的用户是否在数据库中是否存在
	boolean findUser(String username);
	
	//查找注册的用户邮箱数据库中是否存在
	boolean findEmail(String email);
	
	void delete(String id);
	
	Map<String,User> getMapAll();
	
	List<User> getListAll();
	
	QueryResult pageQuery(int startindex,int pagesize);

	void update(User user);

	boolean findUser(String username, String id);

	boolean findEmail(String email, String id);

	List<Privilege> getUserAllPrivilege(User user);
	
}