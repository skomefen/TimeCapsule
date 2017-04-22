package timeCapsule.service.manage;

import java.util.List;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.PageBean;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryInfo;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;

public interface CapsuleManageBusinessService {		
	
	void deleteCapsule(String id);
	
	void updateCapsule(Capsule capsule);

	PageBean pageQueryCapsule(QueryInfo queryinfo);


	List<Privilege> getUserAllPrivilege(User user);


	void addCapsule(Capsule capsule, List<UpCapsulefile> upfiles);

	Capsule findCapsule(String id);
	
	List<UpCapsulefile> findCapulefile(String capsuleid);

	
}
