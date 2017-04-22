package timeCapsule.service.impl;

import java.util.List;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.dao.PrivilegeDao;
import timeCapsule.dao.UpCapsulefileDao;
import timeCapsule.dao.UserDao;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.factory.DaoFactory;
import timeCapsule.service.CapsuleBusinessService;

public class CapsuleBusinessServiceImpl implements CapsuleBusinessService{
	
	private DaoFactory df = DaoFactory.getInstance();
	private CapsuleDao capsuledao = df.createDao(CapsuleDao.class);
	private UpCapsulefileDao upfiledao = df.createDao(UpCapsulefileDao.class); 
	private UserDao userdao = df.createDao(UserDao.class); 

	public void add(Capsule capsule, List<UpCapsulefile> upfiles) {
		capsuledao.add(capsule);
		for (UpCapsulefile upfile : upfiles){
			upfiledao.add(upfile);
		}
	}

	public Capsule fineCapsule(String id) {
		
		return capsuledao.find(id);
	}

	public UpCapsulefile fineUpCapsulefile(String id) {
		return upfiledao.find(id);
	}

	public void updateCapsule(Capsule capsule) {
		capsuledao.update(capsule);
	}

	public void updateUpCapsulefile(UpCapsulefile upfile) {
		upfiledao.update(upfile);
	}

	public void deleleCapsule(String id) {
		capsuledao.delete(id);
	}

	public void deleleUpCapsulefile(String id) {
		upfiledao.delete(id);
	}

	@Override
	public List<UpCapsulefile> findCapulefile(String capsuleid) {
		
		return upfiledao.findCapulefile(capsuleid);
		
	}

	@Override
	public List<Privilege> getUserAllPrivilege(User user) {
		return userdao.getUserAllPrivilege(user);
	}
	@Override
	public QueryResult getCanReadCapsule(int startindex, int pagesize) {
		DaoFactory df = DaoFactory.getInstance();
		CapsuleDao dao = df.createDao(CapsuleDao.class);
		QueryResult qr =dao.capsuleCanRead(startindex, pagesize);
		List<Capsule> capsulelist = qr.getList();
		for(Capsule capsule :capsulelist){
			capsule.setSendnum(capsule.getSendnum()+1);
			dao.update(capsule);
		}
		return qr;

	}

	@Override
	public int getCanReadCapsuleCount() {
		DaoFactory df = DaoFactory.getInstance();
		CapsuleDao dao = df.createDao(CapsuleDao.class);
		int count =dao.capsuleCanReadCount();
		return count;
	}

	
}
