package timeCapsule.dao;

import timeCapsule.domain.Email;

public interface EmailDao {

	void add(Email email);

	void delete(String id);

	Email find(String id);

	void update(Email email);

}