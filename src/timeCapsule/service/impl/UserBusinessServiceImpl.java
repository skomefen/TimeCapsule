package timeCapsule.service.impl;


import java.util.List;
import java.util.Map;

import timeCapsule.dao.UserDao;
import timeCapsule.dao.impl.UserDaoImpl;
import timeCapsule.domain.PageBean;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.User;
import timeCapsule.exception.EmailExistException;
import timeCapsule.exception.UserExistException;
import timeCapsule.factory.DaoFactory;
import timeCapsule.service.UserBusinessService;
import timeCapsule.utils.Permission;
import timeCapsule.utils.ServiceUtils;

public class UserBusinessServiceImpl implements UserBusinessService {
	
	private UserDao dao = DaoFactory.getInstance().createDao(UserDao.class);
	
	//对web层提供注册服务
	public void register(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	
	//对web层提供登陆服务
	public User login(String username,String password){
		
		password=ServiceUtils.md5(password);
		return dao.find(username, password);
				
	}
	


	public User findUser(String username, String password){
		return dao.find(username, password);
	}
	
	public User find(String id){
		return dao.find(id);
	}
	
	
	public Map<String,User> getUserMapAll(){
		return dao.getMapAll();
	}
	
	
	
	
	//校验用户名和电子邮件是否存在
	private boolean validate(User user) throws UserExistException, EmailExistException{		
		boolean flag=false;
		boolean b = dao.findUser(user.getUsername(),user.getId());
		
		if(b){
			flag=true;
			throw new UserExistException();
		}
		b = dao.findEmail(user.getEmail(),user.getId());
		if(b){
			flag=true;
			throw new EmailExistException();
		}
		return flag;
		
	}

	public User findbyUsername(String username) {
		return dao.findbyUsername(username);
	}

	@Override
	public List<Privilege> getUserAllPrivilege(User user) {
		return dao.getUserAllPrivilege(user);
	}

	/****************************************
	 * 
	 * 管理页面
	 * 
	 * *************************************/
	
	@Permission("管理员权限")
	public void addUser(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	@Permission("管理员权限")
	public void deleteUser(String id){
		dao.delete(id);
	}

	@Permission("管理员权限")
	public void updateUser(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.update(user);
		}
	}
	@Permission("管理员权限")
	public PageBean pageQuery(QueryInfo queryinfo){
		//从数据库拿到页面数据
		QueryResult	qr = dao.pageQuery(queryinfo.getStartindex(),queryinfo.getPagesize());
		PageBean bean = new PageBean();
		bean.setCurrentpage(queryinfo.getCurrentpage());
		bean.setList(qr.getList());
		bean.setPagesize(queryinfo.getPagesize());
		bean.setTotalrecord(qr.getTotalrecord());
		return bean;
	}
	
}
