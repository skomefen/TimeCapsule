package timeCapsule.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import timeCapsule.dao.UpCapsulefileDao;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.UpCapsulefile;
import timeCapsule.domain.User;
import timeCapsule.exception.DaoException;
import timeCapsule.utils.IntHandler;
import timeCapsule.utils.JdbcUtils;
import timeCapsule.utils.WebUtils;

public class UpCapsulefileDaoImpl implements UpCapsulefileDao {

	/*
	 * id varchar(40) primary key,
			uuidname varchar(100) not null unique,
			filename varchar(100) not null,
			savepath varchar(255) not null,
			uptime datetime not null,
			description varchar(255),
			capsulename varchar(40) not null
			capsuleid varchar(40) not null
	 */
	public void add(UpCapsulefile upfile) {
		Connection conn = null;
		
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner qr = new QueryRunner();
			String sql = "insert into upcapsulefiles (id,uuidname,filename,savepath,uptime,description,capsule_id)values(?,?,?,?,?,?,?)";
			Object params[] = {upfile.getId(),upfile.getUuidname(),upfile.getFilename(),upfile.getSavepath(),upfile.getUptime(),upfile.getDescription(),upfile.getCapsuleid()};
			qr.update(conn, sql, params);
			JdbcUtils.commitTransaction();
		
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public List<UpCapsulefile> getAll() {
		Connection conn = null;
		UpCapsulefile upfile = null;
		List<UpCapsulefile> list = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,uuidname,filename,savepath,uptime,description,capsule_id from users ";
			list = (List<UpCapsulefile>) runner.query(conn, sql,  new BeanListHandler(UpCapsulefile.class));
			JdbcUtils.commitTransaction();
			return list;
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public UpCapsulefile find(String id) {
		Connection conn = null;
		UpCapsulefile upfile = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,uuidname,filename,savepath,uptime,description,capsule_id from users where id=?";
			upfile = (UpCapsulefile) runner.query(conn, sql, id, new BeanHandler(UpCapsulefile.class));
			JdbcUtils.commitTransaction();
			return upfile;
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
			QueryRunner qr = new QueryRunner();
			String sql = "delete from upcapsulefiles where id=?";
			qr.update(conn, sql, id);
			JdbcUtils.commitTransaction();
		
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public void update(UpCapsulefile upfile) {
		Connection conn = null;
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner qr = new QueryRunner();
			String sql = "update  set upcapsulefiles uuidname=?,filename=?,savepath=?,uptime=?,description=?,capsule_id=? where id=?";
			Object params[] = {upfile.getUuidname(),upfile.getFilename(),upfile.getSavepath(),upfile.getUptime(),upfile.getDescription(),upfile.getCapsuleid(),upfile.getId()};
			qr.update(conn, sql, params);
			JdbcUtils.commitTransaction();
		
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public QueryResult pageQuery(int startindex, int pagesize) {
		QueryResult qr = new QueryResult();
		Connection conn = null;			
		QueryRunner runner = new QueryRunner();

		try{
			conn = JdbcUtils.getConnection();
			
			JdbcUtils.startTransaction();
			
			String sql = "select * from upcapsulefiles limit ?,?";
			Object[] params = {startindex,pagesize};

			List <UpCapsulefile> users = (List<UpCapsulefile>) runner.query(conn, sql, params, new BeanListHandler(UpCapsulefile.class));
			
			sql = "select count(*) from upcapsulefiles";
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

	public List<UpCapsulefile> findCapulefile(String capsuleid) {
		
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		List<UpCapsulefile> list = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			String sql = "select * from upcapsulefiles where capsule_id = ?";
			list = (List<UpCapsulefile>) runner.query(conn, sql, capsuleid, new BeanListHandler(UpCapsulefile.class));
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
		return list;
	}
	


}
