package timeCapsule.dao.impl;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import timeCapsule.dao.PrivilegeDao;
import timeCapsule.domain.Privilege;
import timeCapsule.exception.DaoException;
import timeCapsule.utils.JdbcUtils;

/**
 * 			create table privilege(
				id  varchar(40) primary key,
				name varchar(40) not null,
				description varchar(255)
			);
 * */

public class PrivilegeDaoImpl implements PrivilegeDao  {
	public void add(Privilege privilege){
		Connection conn = JdbcUtils.getConnection();
		try{
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			String sql = "insert into privilege (id,name,description) value (?,?,?)";
			Object[] params = {privilege.getId(),privilege.getName(),privilege.getDescription()};
			runner.update(conn, sql, params);
			JdbcUtils.commitTransaction();
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}
	public void delete(String id){
		Connection conn = JdbcUtils.getConnection();
		try{
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			
			String sql = "delete from privilege where id=?";
			
			runner.update(conn, sql, id);
			JdbcUtils.commitTransaction();

		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}
	public void update(Privilege privilege){
		Connection conn = JdbcUtils.getConnection();
		try{
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			
			String sql = "update privilege set name=?,description=? where id=?";
			Object[] param = {privilege.getName(),privilege.getDescription(),privilege.getId()};

			
			runner.update(conn, sql, param);
			JdbcUtils.commitTransaction();

		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}
	public Privilege findbyId(String id){
		Connection conn = JdbcUtils.getConnection();
		try{
			JdbcUtils.startTransaction();
			QueryRunner runner = new QueryRunner();
			
			String sql = "select * from privilege where id=?";
			
			Privilege p =(Privilege) runner.query(conn, sql, id, new BeanHandler(Privilege.class));
			JdbcUtils.commitTransaction();
			return p;
		}catch(Exception e){
			throw new DaoException();
		}finally{
			JdbcUtils.closeConnection();
		}
	}
}
