package timeCapsule.domain;

import java.util.List;

//封装页面显示需要信息，看页面设计来设计

public class PageBean {
	List list; // 页面显示数据
	int totalrecord; // 总记录数
	int pagesize; // 每页多少条
	int totalpage; // 页面总数
	int currentpage; // 当前页
	int previouspage; // 上一页
	int nextpage; // 下一页
	int pagebarsize;//页码条长度
	int[] pagebar; // 页码条

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {
		if (this.totalrecord % this.pagesize == 0) {
			this.totalpage = this.totalrecord / this.pagesize;
		} else {
			this.totalpage = this.totalrecord / this.pagesize + 1;
		}
		return totalpage;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPreviouspage() {
		if (this.currentpage - 1 < 1) {
			this.previouspage = 1;
		} else {
			this.previouspage = this.currentpage - 1;
		}
		return previouspage;
	}

	public int getNextpage() {
		if (this.currentpage + 1 > this.totalpage) {
			this.nextpage = totalpage;
		} else {
			this.nextpage = this.currentpage + 1;
		}
		return nextpage;
	}

	public int getPagebarsize() {
		return pagebarsize;
	}

	public void setPagebarsize(int pagebarsize) {
		this.pagebarsize = pagebarsize;
	}

	// 5
	public int[] getPagebar() {
		int startpage;
		int endpage;
		int pagebar[] = null;
		if (this.totalpage <= 10) {
			pagebar = new int[this.totalpage];
			startpage = 1;
			endpage = this.totalpage;
		} else {
			pagebar = new int[10];
			startpage = this.currentpage - 4;
			endpage = this.currentpage + 5;
			if (startpage < 1) {
				
				startpage = 1;
				endpage = 10;
			} 
			if (endpage > this.totalpage) {
				startpage = this.totalpage - 9;
				endpage = this.totalpage;
			}
			
		}

		int index = 0;
		for(int i=startpage;i<=endpage;i++){
			pagebar[index++] = i;
		}
		this.pagebar = pagebar;
		return this.pagebar;
	}

}
