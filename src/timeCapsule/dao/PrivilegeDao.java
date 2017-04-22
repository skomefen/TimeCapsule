package timeCapsule.dao;

import timeCapsule.domain.Privilege;

public interface PrivilegeDao {

	void add(Privilege privilege);

	void delete(String id);

	void update(Privilege privilege);

	Privilege findbyId(String id);

}