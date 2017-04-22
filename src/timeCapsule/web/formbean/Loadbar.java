package timeCapsule.web.formbean;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;


public class Loadbar implements ProgressListener {
	private long pBytesRead;
	private String percent;
	private long pContentLength;
	private int pItems;
	private HttpServletRequest request;
	public String getPercent() {
		if(pContentLength==0)
			return "";
		percent=((double)pBytesRead/(double)pContentLength)+"";
		return percent;
	}
	
	public long getpBytesRead() {
		return pBytesRead;
	}
	public void setpBytesRead(long pBytesRead) {
		this.pBytesRead = pBytesRead;
	}
	public long getpContentLength() {
		return pContentLength;
	}
	public void setpContentLength(long pContentLength) {
		this.pContentLength = pContentLength;
	}
	public int getpItems() {
		return pItems;
	}
	public void setpItems(int pItems) {
		this.pItems = pItems;
	}

	private static Loadbar loadbar = new Loadbar();
	private long megaBytes = -1;
	public void update(long pBytesRead, long pContentLength, int pItems) {
	   long mBytes = pBytesRead / 1000000;
	   if (megaBytes == mBytes) {
	       return;
	   }
	   megaBytes = mBytes;
	   loadbar.setpItems(pItems);
	   
	   if (pContentLength == -1) {
		   request.getSession().removeAttribute("loadbar");
	   } else {
		   loadbar.setpBytesRead(pBytesRead);
		   loadbar.setpContentLength(pContentLength);
	       
	       request.getSession().setAttribute("loadbar", loadbar);
	   }
	   
	}
	
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}

	
}
