package junit.test.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.dao.UpCapsulefileDao;
import timeCapsule.dao.impl.CapsuleDaoImpl;
import timeCapsule.dao.impl.UserDaoImpl;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.factory.DaoFactory;

public class CapsuleDaoTest {
	
	@Test
	public void add(){
		List<Capsule> list = new CapsuleDaoImpl().pageQuery(5, 10).getList();

		UpCapsulefileDao ucd = DaoFactory.getInstance().createDao(UpCapsulefileDao.class);
		
		UpCapsulefile capsulefile = new UpCapsulefile();
		for(int i = 0;i<10;i++){
			capsulefile.setId(UUID.randomUUID().toString());
			capsulefile.setFilename("abc"+i);
			capsulefile.setUuidname("abc"+i);
			capsulefile.setDescription("abc"+i);
			capsulefile.setUptime(new Date());
			capsulefile.setSavepath("abc"+i);
			capsulefile.setCapsuleid("19a8a29a-bb79-45cc-a4b6-80efbda156dc");
			ucd.add(capsulefile);
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
		capsule = csd.find("fe874f84-4b0b-45c4-a368-3e32278fc8cf");
		System.out.println(capsule);
	}
	
	@Test
	public void update(){
		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		Capsule capsule = new Capsule();
		capsule.setId("fe874f84-4b0b-45c4-a368-3e32278fc8cf");
		capsule.setCapsulename("时间胶囊拉拉");
		capsule.setReaddate(new Date());
		capsule.setSavedate(new Date());
		capsule.setEmail("aaaa@sina.com");

		capsule.setUsernameid("ff6ae3e7-141e-4879-bdec-5114236130ca");
		capsule.setDescription("时间胶囊lalalallala");
		csd.update(capsule);
		System.out.println("更新成功");
	}
	
	@Test
	public void testcapsuleCanRead(){
		CapsuleDao csd = DaoFactory.getInstance().createDao(CapsuleDao.class);
		QueryResult qr = csd.capsuleCanRead(0, 5);
		List<Capsule> list = qr.getList();
		for(Capsule capsule : list){
			System.out.println(capsule.getCapsulename());
		}
		System.out.println(qr.getTotalrecord());
	}
	
	public static void main(String[] args){
		new CapsuleDaoTest().update();
	}
	
	
	
}
