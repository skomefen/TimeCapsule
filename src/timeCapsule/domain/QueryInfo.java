package timeCapsule.domain;

//页面查询信息
public class QueryInfo {
	int currentpage = 1; //用户当前（在看的）页
	int pagesize = 10; //每页多少条信息
	int startindex; //算出当前页数据在数据库的起始位置
	
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
