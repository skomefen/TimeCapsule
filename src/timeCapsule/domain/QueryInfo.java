package timeCapsule.domain;

//ҳ���ѯ��Ϣ
public class QueryInfo {
	int currentpage = 1; //�û���ǰ���ڿ��ģ�ҳ
	int pagesize = 10; //ÿҳ��������Ϣ
	int startindex; //�����ǰҳ���������ݿ����ʼλ��
	
	public int getCurrentpage() {
		return currentpage;
	}


	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}


	public int getPagesize() {
		return pagesize;
	}


	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}


	public int getStartindex(){
		this.startindex = (this.currentpage-1)*this.pagesize;
		return startindex;
	}
	
}
