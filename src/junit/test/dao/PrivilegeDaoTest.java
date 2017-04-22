package junit.test.dao;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import sun.misc.BASE64Encoder;
import timeCapsule.dao.impl.PrivilegeDaoImpl;
import timeCapsule.domain.Privilege;

public class PrivilegeDaoTest {
	@Test
	public void testAdd(){
		
		PrivilegeDaoImpl p = new PrivilegeDaoImpl();
		Privilege privilege = new Privilege();
		privilege.setId("aaa");
		privilege.setName("aaa");
		privilege.setDescription("aaa");
		p.add(privilege);
		System.out.println(UUID.randomUUID().toString().length());
	}
	@Test
	public void testUpdate(){
		
		PrivilegeDaoImpl p = new PrivilegeDaoImpl();
		Privilege privilege = new Privilege();
		privilege.setId("aaa");
		privilege.setName("123");
		p.update(privilege);
		
	}
	@Test
	public void testFindbyId(){
		
		PrivilegeDaoImpl p = new PrivilegeDaoImpl();

		Privilege privilege= p.findbyId("aaa");
		Assert.assertEquals("aaa", privilege.getId());
		Assert.assertEquals("123", privilege.getName());

	}
	@Test
	public void testDelete(){
		
		PrivilegeDaoImpl p = new PrivilegeDaoImpl();

		p.delete("aaa");
		
	}
	

}
