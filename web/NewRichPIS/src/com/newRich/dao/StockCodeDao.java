package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.backRun.vo.StockCode;

public class StockCodeDao extends BaseDao {

	private static StockCodeDao daoInstance = new StockCodeDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static StockCodeDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public StockCodeDao() {
		super();
		// System.out.println("666");
		this.log = LogFactory.getLog(this.getClass());
		this.jdbcTemplate = new JdbcTemplate(getDataSource());
		// System.out.println("777");
	}

	// limit n,m => 從第n筆開始，取出m筆的資料
	/**
	 * 讀取所有資料
	 * 
	 * @return List
	 */
	private static final String SELECT_SQL = "SELECT * " + "FROM StockCode ";

	public List<StockCode> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockCodeMapper()));
	}

	/**
	 * 讀取資料BY StockCode
	 * 
	 * @return List
	 */
	public StockCode findByStockCode(String StockCode) {
		try {
			String SELECT_StockCode_SQL = "SELECT * FROM StockCode " + "where StockCode = ? ";
			final Object[] params = new Object[] { StockCode };
			ArrayList list = (ArrayList) jdbcTemplate.query(SELECT_StockCode_SQL, params, new RowMapperResultSetExtractor(new StockCodeMapper()));
			if (null != list && list.size() > 0) {
				return (StockCode) list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 新增資料
	 * 
	 * @return int
	 */
	private static final String INSERT_SQL = " INSERT INTO StockCode " + " (stockCode) " + " VALUES " + " (?) ";

	public int insert(StockCode vo) {
		final Object[] params = new Object[] { vo.getStockCode() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}

	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE StockCode SET " + "stockCode = ? ";

	public int update(StockCode vo) {
		final Object[] params = new Object[] { vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class StockCodeMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			StockCode vo = new StockCode();
			vo.setStockCode(rs.getString("stockCode"));

			return vo;
		}
	}
}
