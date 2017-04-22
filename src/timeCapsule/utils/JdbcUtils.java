package timeCapsule.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public class JdbcUtils {
	
	private static DataSource ds;
	//ThreadLocal可以使得对象与当前线程绑定
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();
	
	static{
		try{
			ds = new ComboPooledDataSource();
		}catch(Exception e){
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static Connection getConnection(){
		
		try{
			//从当前线程得到绑定的连接
			Connection conn = tl.get();
			if(conn==null){//代表线程上没有绑定连接
				conn = ds.getConnection();
				tl.set(conn);
			}
			return conn;
		}catch(Exception e){
			throw new RuntimeException();
		}
		
	}
	
	public static DataSource getDataSource(){
		return ds;	
	}
	
	public static void startTransaction(){
		try{
			//得到当前线程上绑定连接开启事务
			Connection conn = tl.get();
			if(conn==null){
				conn = ds.getConnection();
				tl.set(conn);
			}
			conn.setAutoCommit(false);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void commitTransaction(){
		try{
			Connection conn = tl.get();
			if(conn!=null){
				conn.commit();		
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void closeConnection(){
		try{
			Connection conn = tl.get();
			if(conn!=null){
				conn.close();
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			tl.remove();//一定要移除掉
		}
	}
	public static void release(Connection conn,Statement st,ResultSet rs){
		
		if(rs!=null){
			try{
				rs.close();   //throw new 
			}catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(st!=null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if(conn!=null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}	
