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
	
	//��web���ṩע�����
	public void register(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	
	//��web���ṩ��½����
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
	
	
	
	
	//У���û����͵����ʼ��Ƿ����
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
	 * ����ҳ��
	 * 
	 * *************************************/
	
	@Permission("����ԱȨ��")
	public void addUser(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	@Permission("����ԱȨ��")
	public void deleteUser(String id){
		dao.delete(id);
	}

	@Permission("����ԱȨ��")
	public void updateUser(User user) throws UserExistException, EmailExistException{
		if(!validate(user)){
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.update(user);
		}
	}
	@Permission("����ԱȨ��")
	public PageBean pageQuery(QueryInfo queryinfo){
		//�����ݿ��õ�ҳ������
		QueryResult	qr = dao.pageQuery(queryinfo.getStartindex(),queryinfo.getPagesize());
		PageBean bean = new PageBean();
		bean.setCurrentpage(queryinfo.getCurrentpage());
		bean.setList(qr.getList());
		bean.setPagesize(queryinfo.getPagesize());
		bean.setTotalrecord(qr.getTotalrecord());
		return bean;
	}
	
}
