package junit.test.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

import timeCapsule.dao.UserDao;
import timeCapsule.dao.impl.UserDaoImpl;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.User;
import timeCapsule.utils.ServiceUtils;
import timeCapsule.utils.WebUtils;



public class UserDaoTest {

	@Test
	public void testAdd(){
		User user = new User();
		user.setEmail("aaaa@sina.com");
		user.setId("131311331");
		user.setNickname("Ð¢×Ó");
		user.setPassword("123");
		user.setUsername("abcabc");
		
		UserDao dao = new UserDaoImpl();
		dao.add(user);
	}

	@Test
	public void testFind(){
		UserDao dao = new UserDaoImpl();
		
		dao.find("abcabc","123");
	}
	
	@Test
	public void testFindByUsername(){
		UserDao dao = new UserDaoImpl();
		
		System.out.println(dao.findUser("abcabc"));
	}
	
	@Test
	public void testDelete(){
		UserDao dao = new UserDaoImpl();
		
		dao.delete("aaaaaa");
	}
	
	@Test
	public void testFindbyUsername(){
		UserDao dao = new UserDaoImpl();
		
		User user = dao.findbyUsername("ÕÅÈý");
		System.out.println(user.getUsername());
	}
	
	@Test
	public void test(){
		System.out.println(ServiceUtils.md5("herobybigborther1234"));
	}
	
	@Test
	public void testUserAllPrivilege(){
		UserDao dao = new UserDaoImpl();
		User user = dao.findbyUsername("big_ming_brother");
		List<Privilege> list = dao.getUserAllPrivilege(user);
		System.out.println(list);
	}
}
