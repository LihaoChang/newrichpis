package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.backRun.vo.PersonForm;
import com.newRich.model.Person;

public class PersonDao extends BaseDao {

	private static PersonDao daoInstance = new PersonDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static PersonDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public PersonDao() {
		super();
		// System.out.println("666");
		this.log = LogFactory.getLog(this.getClass());
		this.jdbcTemplate = new JdbcTemplate(getDataSource());
		// System.out.println("777");
	}

	/**
	 * 讀取所有資料
	 * 
	 * @return List
	 */
	private static final String SELECT_SQL = "SELECT * " + "FROM Person ";

	public List<Person> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new PersonMapper()));
	}

	/**
	 * 讀取資料By Query Form
	 * 
	 * @return List
	 */
	public List<Person> findAllByForm(PersonForm formVO) {
		String SELECT_UserPwd_SQL = "SELECT * FROM Person where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_UserPwd_SQL += "and name like '%" + formVO.getName() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getPassword())) {
			SELECT_UserPwd_SQL += "and password = '" + formVO.getPassword() + "' ";
		}

		return (List) jdbcTemplate.query(SELECT_UserPwd_SQL, new RowMapperResultSetExtractor(new PersonMapper()));
	}

	/**
	 * 讀取資料BY user、pwd
	 * 
	 * @return List
	 */
	public List<Person> findByUserPwd(PersonForm formVO, int from, int pageShowSize) {
		String SELECT_UserPwd_SQL = "SELECT * FROM Person " + "where 1=1 ";
		// + "where name = ? and password = ? ";
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_UserPwd_SQL += "and name like '%" + formVO.getName() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getPassword())) {
			SELECT_UserPwd_SQL += "and password = '" + formVO.getPassword() + "' ";
		}
		SELECT_UserPwd_SQL += " limit ?,? ";
		final Object[] params = new Object[] { from, pageShowSize };

		return (List) jdbcTemplate.query(SELECT_UserPwd_SQL, params, new RowMapperResultSetExtractor(new PersonMapper()));
	}

	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public List<Person> findByUserPwd(String user, String pwd) {
		String SELECT_UserPwd_SQL = "SELECT * FROM Person " + "where name = ? and password = ? ";
		final Object[] params = new Object[] { user, pwd };
		return (List) jdbcTemplate.query(SELECT_UserPwd_SQL, params, new RowMapperResultSetExtractor(new PersonMapper()));
	}

	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public Person findById(String id) {
		Person vo = null;
		String SELECT_BY_ID_SQL = "SELECT * FROM Person " + "where id = ? ";
		final Object[] params = new Object[] { id };
		List list = (List) jdbcTemplate.query(SELECT_BY_ID_SQL, params, new RowMapperResultSetExtractor(new PersonMapper()));
		if (null != list && list.size() > 0) {
			vo = (Person) list.get(0);
		} else {
			return null;
		}
		return vo;
	}

	/**
	 * 新增資料
	 * 
	 * @return int
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO Person " + " (id, name, password) " + " VALUES " + " (?, ?, ?) ";

	public int insert(Person vo) {
		final Object[] params = new Object[] { vo.getId(), vo.getName(), vo.getPassword() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}

	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE Person SET " + "name = ?, password = ? where id = ? ";

	public int update(Person vo) {
		final Object[] params = new Object[] { vo.getName(), vo.getPassword(), vo.getId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	/**
	 * 刪除資料
	 * 
	 * @return int
	 */
	private static final String DELETE_SQL = " DELETE FROM Person " + " WHERE id = ? ";

	public int delete(String id) {
		final Object[] params = new Object[] { id };
		return jdbcTemplate.update(DELETE_SQL, params);
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class PersonMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Person vo = new Person();
			vo.setId(rs.getString("id"));
			vo.setName(rs.getString("name"));
			vo.setPassword(rs.getString("password"));
			vo.setRole(rs.getString("role"));

			return vo;
		}
	}

}