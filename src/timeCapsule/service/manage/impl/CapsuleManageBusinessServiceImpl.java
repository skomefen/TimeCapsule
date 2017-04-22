package timeCapsule.service.manage.impl;


import java.util.List;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.dao.UpCapsulefileDao;
import timeCapsule.dao.UserDao;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.PageBean;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.factory.DaoFactory;
import timeCapsule.service.manage.CapsuleManageBusinessService;
import timeCapsule.utils.Permission;

public class CapsuleManageBusinessServiceImpl implements CapsuleManageBusinessService {

	DaoFactory df = DaoFactory.getInstance();
	CapsuleDao dao = df.createDao(CapsuleDao.class);
	private UpCapsulefileDao upfiledao = df.createDao(UpCapsulefileDao.class); 

	
	@Permission("����ԱȨ��")
	public PageBean pageQueryCapsule(QueryInfo queryinfo) {
		//�����ݿ��õ�ҳ������
		QueryResult	qr = dao.pageQuery(queryinfo.getStartindex(),queryinfo.getPagesize());
		PageBean bean = new PageBean();
		bean.setCurrentpage(queryinfo.getCurrentpage());
		bean.setList(qr.getList());
		bean.setPagesize(queryinfo.getPagesize());
		bean.setTotalrecord(qr.getTotalrecord());
		return bean;
	}

	@Permission("����ԱȨ��")
	public void deleteCapsule(String id) {
		dao.delete(id);
	}

	@Permission("����ԱȨ��")
	public void updateCapsule(Capsule capsule) {
		dao.update(capsule);
	}

	@Permission("����ԱȨ��")
	public List<Privilege> getUserAllPrivilege(User user) {
		DaoFactory df = DaoFactory.getInstance();
		UserDao dao = df.createDao(UserDao.class);
		return dao.getUserAllPrivilege(user);
	}

	@Permission("����ԱȨ��")
	public void addCapsule(Capsule capsule, List<UpCapsulefile> upfiles) {
		dao.add(capsule);
		for (UpCapsulefile upfile : upfiles){
			upfiledao.add(upfile);
		}
		
	}

	@Permission("����ԱȨ��")
	public Capsule findCapsule(String id) {
		
		return dao.find(id);
	}

	@Permission("����ԱȨ��")
	public List<UpCapsulefile> findCapulefile(String capsuleid) {
		return upfiledao.findCapulefile(capsuleid);
	}


}
