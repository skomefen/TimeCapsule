package timeCapsule.service.manage;

import java.util.List;

import timeCapsule.domain.Capsule;
import timeCapsule.domain.Email;
import timeCapsule.domain.QueryResult;

public interface EmailBusinessService {

	/******************************
	 * 
	 * Ê±¼ä½ºÄÒemail
	 * 
	 * ***************************/

	void addCapsuleAutoEmail();

	void deleteCapsuleAutoEmail();

	void sendEmail(Email email);

	QueryResult getCanReadCapsuleEmailAdressPage(int startindex,int pagesize);

	QueryResult getCanReadCapsule(int startindex, int pagesize);

	int getCanReadCapsuleCount();
	



}