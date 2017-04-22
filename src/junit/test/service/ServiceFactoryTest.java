package junit.test.service;

import org.junit.Assert;
import org.junit.Test;

import timeCapsule.dao.impl.UserDaoImpl;
import timeCapsule.domain.User;
import timeCapsule.factory.ServiceFactory;
import timeCapsule.service.CapsuleBusinessService;
import timeCapsule.service.impl.CapsuleBusinessServiceImpl;

public class ServiceFactoryTest {
	@Test
	public void testCreateService(){
		ServiceFactory st = ServiceFactory.getInstance();
		UserDaoImpl dao = new UserDaoImpl();
		User user = dao.find("manage");
		CapsuleBusinessService cbs = st.createService(CapsuleBusinessService.class, user);
		
		System.out.println(cbs);
	}
}
