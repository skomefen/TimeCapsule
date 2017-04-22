package timeCapsule.dao;

import java.util.List;

import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;


public interface UpCapsulefileDao {

	/*
	 * id varchar(40) primary key,
			uuidname varchar(100) not null unique,
			filename varchar(100) not null,
			savepath varchar(255) not null,
			uptime datetime not null,
			description varchar(255),
			username varchar(40) not null
	 */
	void add(UpCapsulefile upfile);

	List<UpCapsulefile> getAll();

	UpCapsulefile find(String id);

	void delete(String id);

	void update(UpCapsulefile upfile);
	
	QueryResult pageQuery(int startindex,int pagesize);

	List<UpCapsulefile> findCapulefile(String capsuleid);
}