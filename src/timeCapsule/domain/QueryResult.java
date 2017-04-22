package timeCapsule.domain;

import java.util.List;

//查询结果
public class QueryResult {
	List list;//页面数据
	int totalrecord;//总记录数
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
	
}
