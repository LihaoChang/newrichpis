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

import com.newRich.backRun.vo.CountLogin;

public class CountLoginDao extends BaseDao {

	private static CountLoginDao daoInstance = new CountLoginDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static CountLoginDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public CountLoginDao() {
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
	private static final String SELECT_SQL = "SELECT * FROM countLogin ";

	public List<CountLogin> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new CountLoginMap()));
	}
	
	/**
	 * 讀取資料By Query Form
	 * 
	 * @return List
	 */
	public List<CountLogin> findAllByForm(CountLogin formVO) {
		String SELECT_countLogin_SQL = "SELECT * FROM countLogin where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getMemberId())) {
			SELECT_countLogin_SQL += "and memberId like '%" + formVO.getMemberId() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getRealName())) {
			SELECT_countLogin_SQL += "and realname = '" + formVO.getRealName() + "' ";
		}
		if (null != formVO.getDateStart() && null != formVO.getDateEnd()) {
			SELECT_countLogin_SQL += " and updateDate >= '" + formVO.getDateStart() + "' ";
			SELECT_countLogin_SQL += " and updateDate <= '" + formVO.getDateEnd() + "' ";
			SELECT_countLogin_SQL += " and updateDate is not null ";
		}

		return (List) jdbcTemplate.query(SELECT_countLogin_SQL, new RowMapperResultSetExtractor(new CountLoginMap()));
	}
	
	/**
	 * 查詢固定筆數的資料
	 * @param formVO
	 * @param from
	 * @param pageShowSize
	 * @return
	 */
	public List<CountLogin> findAllByFormForPage(CountLogin formVO, int from, int pageShowSize) {
		String SELECT_countLogin_SQL = "SELECT * FROM countLogin where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getMemberId())) {
			SELECT_countLogin_SQL += "and memberId like '%" + formVO.getMemberId() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getRealName())) {
			SELECT_countLogin_SQL += "and realname = '" + formVO.getRealName() + "' ";
		}
		if (null != formVO.getDateStart() && null != formVO.getDateEnd()) {
			SELECT_countLogin_SQL += " and updateDate >= '" + formVO.getDateStart() + "' ";
			SELECT_countLogin_SQL += " and updateDate <= '" + formVO.getDateEnd() + "' ";
			SELECT_countLogin_SQL += " and updateDate is not null ";
		}
		SELECT_countLogin_SQL += " limit ?,? ";
		
		final Object[] params = new Object[] { from, pageShowSize };

		return (List) jdbcTemplate.query(SELECT_countLogin_SQL, params, new RowMapperResultSetExtractor(new CountLoginMap()));
	}
	
	/**
	 * 新增資料
	 * 
	 * @return int
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO countLogin (memberId, realName, updateDate)  VALUES  (?, ?, ?) ";

	public int insert(CountLogin vo) {
		final Object[] params = new Object[] { vo.getMemberId(), vo.getRealName(), vo.getUpdateDate() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}
	
	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE countLogin SET realname = ?, updateDate = ? where memberId = ? ";

	public int update(CountLogin vo) {
		final Object[] params = new Object[] { vo.getRealName(), vo.getUpdateDate(), vo.getMemberId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	
	/**
	 * 刪除資料
	 * 
	 * @return int
	 */
	private static final String DELETE_SQL = " DELETE FROM countLogin " + " WHERE memberId = ? ";

	public int delete(String memberId) {
		final Object[] params = new Object[] { memberId };
		return jdbcTemplate.update(DELETE_SQL, params);
	}
	
	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public CountLogin findById(String id) {
		CountLogin vo = null;
		String SELECT_BY_ID_SQL = "SELECT * FROM countLogin " + "where memberId = ? ";
		final Object[] params = new Object[] { id };
		List list = (List) jdbcTemplate.query(SELECT_BY_ID_SQL, params, new RowMapperResultSetExtractor(new CountLoginMap()));
		if (null != list && list.size() > 0) {
			vo = (CountLogin) list.get(0);
		} else {
			return null;
		}
		return vo;
	}
	
	/**
	 * 更新資料-UpdateDate
	 * 
	 * @return int
	 * 
	 */
	public int memberUpdateDate(CountLogin vo) {
		String UPDATE_SQL = " UPDATE countLogin SET updateDate = ? where memberId = ? ";
		final Object[] params = new Object[] { vo.getUpdateDate(), vo.getMemberId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	
	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class CountLoginMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CountLogin vo = new CountLogin();
			vo.setMemberId(rs.getString("memberId"));
			vo.setRealName(rs.getString("realName"));
			vo.setUpdateDate(rs.getString("updateDate"));

			return vo;
		}
	}
}