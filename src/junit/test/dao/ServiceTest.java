package junit.test.dao;

import org.junit.Test;

import timeCapsule.utils.ServiceUtils;

public class ServiceTest {
	
	@Test
	public void Md5test(){
	System.out.println(ServiceUtils.md5("123"));
	}
}
