package timeCapsule.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class IntHandler implements ResultSetHandler {

	public Object handle(ResultSet rs) throws SQLException {
		try{
			if(rs!=null){
				if(rs.next()){
					return rs.getInt(1);
				}		
			}
		}catch(Exception e){
			throw new RuntimeException();
		}
		return 0;
	}

}
