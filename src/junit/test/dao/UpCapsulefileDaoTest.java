package junit.test.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.dao.UpCapsulefileDao;
import timeCapsule.dao.impl.UserDaoImpl;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.factory.DaoFactory;

public class UpCapsulefileDaoTest {
	
	@Test
	public void add(){
		List<User> list = new UserDaoImpl().pageQuery(5, 10).getList();

		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		
		Capsule capsule = new Capsule();
		for(int i = 0;i<10;i++){
			capsule.setId(UUID.randomUUID().toString());
			capsule.setCapsulename("时间胶囊"+i);
			capsule.setReaddate(new Date());
			capsule.setSavedate(new Date());
			capsule.setUsernameid(list.get(i).getId());
			capsule.setDescription("时间胶囊for"+list.get(i).getId());
			capsule.setEmail("a"+i+"@sina");
			csd.add(capsule);
		}
		System.out.println("添加成功");
	}
	
	@Test
	public void detele(){
		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		
		csd.delete("0484b5c3-0d1c-4ead-b48b-9589a3a5fa0b");
		
		System.out.println("删除成功");
	}
	
	@Test
	public void find(){
		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		Capsule capsule = null;
		capsule = csd.find("1cb44c40-0496-4e20-b232-13247513d770");
		System.out.println(capsule);
	}
	
	@Test
	public void update(){
		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		Capsule capsule = new Capsule();
		capsule.setId("1cb44c40-0496-4e20-b232-13247513d770");
		capsule.setCapsulename("时间胶囊拉拉");
		capsule.setReaddate(new Date());
		capsule.setSavedate(new Date());
		capsule.setUsernameid("06130015-06c4-4191-a81c-bc882231b05f");
		capsule.setDescription("时间胶囊lalalallala");
		csd.update(capsule);
		System.out.println("更新成功");
	}
	
	@Test
	public void findCapulefile(){
		UpCapsulefileDao csd = DaoFactory.getInstance().createDao(UpCapsulefileDao.class);
		List<UpCapsulefile> list = null;
		list = csd.findCapulefile("080091a6-4c70-4d49-b00a-8e27b6355517");
		System.out.println(list);
	}
	
}
