package timeCapsule.dao;

import java.util.List;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.QueryResult;


public interface CapsuleDao {
	void add(Capsule capsule);

	List<Capsule> getAll();

	Capsule find(String id);

	void delete(String id);

	void update(Capsule capsule);
	
	QueryResult pageQuery(int startindex,int pagesize);

	QueryResult capsuleCanRead(int startindex,int pagesize);

	int capsuleCanReadCount();
	
}
