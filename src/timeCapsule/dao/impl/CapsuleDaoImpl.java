package timeCapsule.dao.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import timeCapsule.dao.CapsuleDao;
import timeCapsule.domain.Capsule;
import timeCapsule.domain.QueryResult;
import timeCapsule.domain.User;
import timeCapsule.exception.DaoException;
import timeCapsule.utils.IntHandler;
import timeCapsule.utils.JdbcUtils;


public class CapsuleDaoImpl implements CapsuleDao{

	public void add(Capsule capsule) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "insert into capsules(id,capsulename,savedate,readdate,description,email,isreaded,user_id )value(?,?,?,?,?,?,?,?)";
			Object[] params={capsule.getId(),capsule.getCapsulename(),capsule.getSavedate(),capsule.getReaddate(),capsule.getDescription(),capsule.getEmail(),capsule.isIsreaded(),capsule.getUsernameid()};
			runner.update(conn, sql, params);
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public List<Capsule> getAll() {
		Connection conn = null;
		List<Capsule> capsules = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select * from capsules ";
			capsules=(List<Capsule>) runner.query(conn, sql, new BeanListHandler(Capsule.class));
			JdbcUtils.commitTransaction();
			return capsules;
		} catch (Exception e) {
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public Capsule find(String id) {
		Connection conn = null;
		Capsule capsule = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "select id,capsulename,savedate,readdate,description,email,isreaded,sendnum,user_id from capsules where id=?";
			capsule = (Capsule) runner.query(conn, sql, id, new BeanHandler(Capsule.class));
			JdbcUtils.commitTransaction();
			return capsule;
		} catch (Exception e) {
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
	}

	public void delete(String id) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "delete from capsules where id=?";
			runner.update(conn, sql, id);
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
		
	}

	public void update(Capsule capsule) {
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "update capsules set capsulename=?,savedate=?,readdate=?,description=?,email=?,isreaded=?,user_id=?,sendnum=? where id=?";
			Object[] params={capsule.getCapsulename(),capsule.getSavedate(),capsule.getReaddate(),capsule.getDescription(),capsule.getEmail(),capsule.isIsreaded(),capsule.getUsernameid(),capsule.getSendnum(),capsule.getId()};
			runner.update(conn, sql, params);
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}
		
	}
	
	public QueryResult pageQuery(int startindex,int pagesize) {
		
		QueryResult qr = new QueryResult();
		Connection conn = null;			
		QueryRunner runner = new QueryRunner();

		try{
			conn = JdbcUtils.getConnection();
			
			JdbcUtils.startTransaction();
			
			String sql = "select * from capsules limit ?,?";
			Object[] params = {startindex,pagesize};

			List <Capsule> Capsule = (List<Capsule>) runner.query(conn, sql, params, new BeanListHandler(Capsule.class));
			
			sql = "select count(*) from capsules";
			int count = (int) runner.query(conn, sql, new IntHandler());
			
			JdbcUtils.commitTransaction();
			
			qr.setList(Capsule);
			qr.setTotalrecord(count);
			
			return qr;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();
		}

	}

	@Override
	public QueryResult capsuleCanRead(int startindex, int pagesize) {
		QueryResult qr = new QueryResult();
		Connection conn = null;
		QueryRunner runner = new QueryRunner();
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			Date nowdate = new Date();
			String sql = "select * from capsules where sendnum<3 && isreaded=0 && readdate<? limit ?,? ";
			Object params[] ={nowdate,startindex,pagesize };
			List<Capsule> list =  (List<Capsule>) runner.query(conn, sql, params, new BeanListHandler(Capsule.class));
			
			sql = "select count(*) from capsules where sendnum<3 && isreaded=0 && readdate<?";
			int count = (int) runner.query(conn, sql, nowdate, new IntHandler());
			
			qr.setList(list);
			qr.setTotalrecord(count);

			JdbcUtils.commitTransaction();
			return qr;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();

		}
	}

	@Override
	public int capsuleCanReadCount() {
		Connection conn = null;
		QueryRunner runner = new QueryRunner();
		try{
			conn = JdbcUtils.getConnection();
			JdbcUtils.startTransaction();
			Date nowdate = new Date();
			
			String sql = "select count(*) from capsules where sendnum<3 && isreaded=0 && readdate<?";
			int count = (int) runner.query(conn, sql, nowdate, new IntHandler());
		
			JdbcUtils.commitTransaction();
			return count;
		}catch(Exception e){
			throw new DaoException(e);
		}finally{
			JdbcUtils.closeConnection();

		}
	}
	
}
