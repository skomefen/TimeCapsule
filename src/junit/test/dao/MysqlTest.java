package junit.test.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;





import java.util.UUID;

import org.junit.Test;

import com.sun.xml.internal.org.jvnet.staxex.Base64EncoderStream;

import sun.misc.BASE64Encoder;
import timeCapsule.domain.User;
import timeCapsule.utils.JdbcUtils;
import timeCapsule.utils.WebUtils;

public class MysqlTest {
	
	@Test
	public void insert(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "insert into users(id,username,password,email,nickname) values('4','eee','ICy5YqxZB1uWSwcVLSNLcA==','ee@sina.com','小王')";
			int num = st.executeUpdate(sql);
			if(num>0){
				System.out.println("插入成功！！！");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.release(conn, st, rs);
		}
		
	}
	
	@Test
	public void update() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "update users set username='aaa' where id='4'";
			int num = st.executeUpdate(sql);
			if(num>0){
				System.out.println("更新成功！！！");
			}
			
		}
		finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	@Test
	public void delete(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "delete from users where id='4'";
			int num = st.executeUpdate(sql);
			if(num>0){
				System.out.println("删除成功！！！");
			}
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	@Test
	public void find(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "select id,username,password,email,nickname from users where id='1'";
			rs = st.executeQuery(sql);
			User user = null;
			if(rs.next()){
				user = new User();
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setNickname(rs.getString("nickname"));
			}
			System.out.println(user);
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	@Test
	public void getAll(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "select id,username,password,email,nickname from users";
			rs = st.executeQuery(sql);
			List list = new ArrayList();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setNickname(rs.getString("nickname"));
				list.add(user);
			}
			System.out.println(list);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	@Test
	public void batch() throws SQLException, NoSuchAlgorithmException{
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils.getConnection();
			String sql = "insert into users(id,username,password,email,nickname)value (?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			
			for(int i=1;i<=1000;i++){
				st.setString(1,UUID.randomUUID().toString());
				st.setString(2,"z"+i );
				st.setString(3,new BASE64Encoder().encode(MessageDigest.getInstance("md5").digest("123".getBytes())));
				st.setString(4, "z"+i+"@sina.com");
				st.setString(5, "张"+i);
				st.addBatch();
			}
			st.executeBatch();
		}finally{
			JdbcUtils.release(conn, st, rs);
		}
	}
}
