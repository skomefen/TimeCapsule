package timeCapsule.domain;

import java.util.List;
import java.util.Map;

public class Email {
	private String id;
	private String fromEmailAdress;
	private String recipientEmailAdress;
	private String subject;
	private String content;
	private Map<String,String> imageAdress;
	private List<String> attachmentAdress;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFromEmailAdress() {
		return fromEmailAdress;
	}
	public void setFromEmailAdress(String fromEmailAdress) {
		this.fromEmailAdress = fromEmailAdress;
	}
	public String getRecipientEmailAdress() {
		return recipientEmailAdress;
	}
	public void setRecipientEmailAdress(String recipientEmailAdress) {
		this.recipientEmailAdress = recipientEmailAdress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getImageAdress() {
		return imageAdress;
	}
	/**
	 * String cid;
	 * String imageAdress
	 * Map.put(cid,imageAdress)
	 * */
	public void setImageAdress(Map<String, String> imageAdress) {
		this.imageAdress = imageAdress;
	}
	public List<String> getAttachmentAdress() {
		return attachmentAdress;
	}
	public void setAttachmentAdress(List<String> attachmentAdress) {
		this.attachmentAdress = attachmentAdress;
	}


	
}
