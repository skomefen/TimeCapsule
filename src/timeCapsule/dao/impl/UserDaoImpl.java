package timeCapsule.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import timeCapsule.dao.UserDao;
import timeCapsule.domain.Privilege;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.User;
import timeCapsule.exception.DaoException;
import timeCapsule.utils.IntHandler;
import timeCapsule.utils.JdbcUtils;


public class UserDaoImpl implements UserDao {
	
	public void add(User user){
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "insert into users(id,username,password,email,nickname) values(?,?,?,?,?)";
			Object params[] = {user.getId(),user.getUsername(),user.getPassword(),user.getEmail(),user.getNickname()};
			runner.update(conn,sql,params);
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}		
	

	}
	
	public User find(String username,String password){
		
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users where username=? and password=?";
			Object params[] = {username,password};
			user = (User) runner.query(conn, sql, params, new BeanHandler(User.class));
			JdbcUtils.commitTransaction();
			return user;
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}	
		
	}
	
	public User find(String id){
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users where id=?";
			user = (User) runner.query(conn, sql, id, new BeanHandler(User.class));
			JdbcUtils.commitTransaction();
			return user;
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}

	}
	
	//查找注册的用户是否在数据库中存在
	public boolean findUser(String username){
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users where username=?";
			user = (User) runner.query(conn, sql, username, new BeanHandler(User.class) );
			JdbcUtils.commitTransaction();
			if(user!=null){
				return true;
			}
			return false;
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}

	}
	public void update(User user) {
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "update users set username=?,password=?,email=?,nickname=? where id=?";
			Object params[] = {user.getUsername(),user.getPassword(),user.getEmail(),user.getNickname(),user.getId()};
			runner.update(conn,sql,params);
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public void delete(String id) {
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "delete from users where id=?";
			runner.update(conn,sql,id);
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}

	}

	public Map<String, User> getMapAll() {
		Map<String,User> map = null;
		List<User> list = null;
		try{
			//调用此方法得到所有User给list集合
			list = this.getListAll();
			if(list==null){
				return null;
			}
			User user = null;
			for (int i = 0; i < list.size(); i++) {
				user = list.get(i);
				map.put(user.getId(), user);
			}
			return map;
		}catch(Exception e){
			throw new DaoException();
		}
		
	}

	public boolean findEmail(String email) {
		
			Connection conn = null;
			User user = null;
			try{
				conn = JdbcUtils.getConnection();
				JdbcUtils.startTransaction();
				QueryRunner runner = new QueryRunner();
				String sql = "select id,username,password,email,nickname from users where email=?";
				user = (User) runner.query(conn, sql, email, new BeanHandler(User.class) );
				JdbcUtils.commitTransaction();
				if(user!=null){
					return true;
				}
				return false;
			}catch(Exception e){
				throw new DaoException();
			}finally{
				JdbcUtils.closeConnection();
			}

	}

	//得到数据库起始位置和页面大小，返回页面信息（页面数据和总记录数）
	public QueryResult pageQuery(int startindex,int pagesize) {
		
		QueryResult qr = new QueryResult();
		Connection conn = null;			
		QueryRunner runner = new QueryRunner();

		try{
			conn = JdbcUtils.getConnection();
			
			JdbcUtils.startTransaction();
			
			String sql = "select * from users limit ?,?";
			Object[] params = {startindex,pagesize};

			List <User> users = (List<User>) runner.query(conn, sql, params, new BeanListHandler(User.class));
			
			sql = "select count(*) from users";
			int count = (int) runner.query(conn, sql, new IntHandler());
			
			JdbcUtils.commitTransaction();
			
			qr.setList(users);
			qr.setTotalrecord(count);
			
			return qr;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}

	}

	@Override
	public List<User> getListAll() {
		Connection conn = null;
		List<User> list = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users";
			list = (List<User>) runner.query(conn, sql, new BeanListHandler(User.class));
			JdbcUtils.commitTransaction();
			return list;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public boolean findUser(String username, String id) {
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users where username=? && id!=?";
			String[] parames = {username,id};
			user = (User) runner.query(conn, sql, parames, new BeanHandler(User.class) );
			JdbcUtils.commitTransaction();
			if(user!=null){
				return true;
			}
			return false;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public boolean findEmail(String email, String id) {
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,username,password,email,nickname from users where email=? && id!=?";
			String[] parames = {email,id};
			user = (User) runner.query(conn, sql,parames , new BeanHandler(User.class) );
			JdbcUtils.commitTransaction();
			if(user!=null){
				return true;
			}
			return false;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public User findbyUsername(String username) {
		
		Connection conn = null;
		User user = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select * from users where username=?";
			
			user = (User) runner.query(conn, sql,username , new BeanHandler(User.class) );
			JdbcUtils.commitTransaction();
			
		
		return user;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	@Override
	public List<Privilege> getUserAllPrivilege(User user) {
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select p.* from users_privilege up,privilege p where up.users_id=? and p.id=up.privilege_id";
			
			List<Privilege> list = (List<Privilege>) runner.query(conn, sql,user.getId() , new BeanListHandler(Privilege.class) );
			JdbcUtils.commitTransaction();
			return list;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	
	
	
}
