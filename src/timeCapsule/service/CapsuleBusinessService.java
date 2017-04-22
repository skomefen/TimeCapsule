package timeCapsule.service;

import java.util.List;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;

public interface CapsuleBusinessService {
	
	void add(Capsule capsule,List<UpCapsulefile> upfiles);
	
	Capsule fineCapsule(String id);
	
	UpCapsulefile fineUpCapsulefile(String id);
	
	void updateCapsule(Capsule capsule);
	
	void updateUpCapsulefile(UpCapsulefile upfile);
	
	void deleleCapsule (String id);
	
	void deleleUpCapsulefile (String id);

	List<UpCapsulefile> findCapulefile(String capsuleid);
	List<Privilege> getUserAllPrivilege(User user);

	QueryResult getCanReadCapsule(int startindex, int pagesize);

	int getCanReadCapsuleCount();

}
