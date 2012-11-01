package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.backRun.vo.StockVO;

public class SpringStockDao extends BaseDao {

	private static SpringStockDao daoInstance = new SpringStockDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static SpringStockDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public SpringStockDao() {
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
	private static final String SELECT_SQL = "SELECT * " + "FROM stock ";

	public List<StockVO> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockMapper()));
	}

	/**
	 * 讀取資料BY StockCode
	 * 
	 * @return List
	 */
	public List<StockVO> findByStockCode(String StockCode) {
		String SELECT_StockCode_SQL = "SELECT * FROM stock " + "where StockCode = ? ";
		final Object[] params = new Object[] { StockCode };
		return (List) jdbcTemplate.query(SELECT_StockCode_SQL, params, new RowMapperResultSetExtractor(new StockMapper()));
	}

	/**
	 * 新增資料
	 * 
	 * @return int
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO stock " + " (stockCode, title, nowPrice, url, sharesTraded, "
			+ "prefixedTicker, netIncome, netIncomeGrowth, netMargin, debtEquity, " + "bookValuePerShare, cashPerShare, roe, roa, dividend, "
			+ "reScheduleDate, createDate, updateDate, sector, exDividendDate, options, weeklyoptions) " + " VALUES " + " (?, ?, ?, ?, ?,"
			+ "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?, ?, ?) ";

	public int insert(StockVO vo) {
		final Object[] params = new Object[] { vo.getStockCode(), vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(),
				vo.getPrefixedTicker(), vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(),
				vo.getCashPerShare(), vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(),
				vo.getSector(), vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}

	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE stock SET " + "title = ?, nowPrice = ?, url = ?, sharesTraded = ?, "
			+ "prefixedTicker = ?, netIncome = ?, netIncomeGrowth = ?, netMargin = ?, debtEquity = ?, "
			+ "bookValuePerShare = ?, cashPerShare = ?, roe = ?, roa = ?, dividend = ?, "
			+ "reScheduleDate = ?, createDate = ?, updateDate = ?, sector = ?, exDividendDate = ? , options = ?, weeklyoptions = ? "
			+ " where stockCode = ?";

	public int update(StockVO vo) {
		final Object[] params = new Object[] { vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(), vo.getPrefixedTicker(),
				vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(), vo.getCashPerShare(),
				vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(), vo.getSector(),
				vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class StockMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			StockVO vo = new StockVO();
			vo.setStockCode(rs.getString("stockCode"));
			vo.setTitle(rs.getString("title"));
			vo.setNowPrice(rs.getDouble("nowPrice"));
			vo.setUrl(rs.getString("url"));
			vo.setSharesTraded(rs.getInt("sharesTraded"));
			vo.setPrefixedTicker(rs.getString("prefixedTicker"));
			vo.setNetIncome(rs.getDouble("netIncome"));
			vo.setNetIncomeGrowth(rs.getDouble("netIncomeGrowth"));
			vo.setNetMargin(rs.getDouble("netMargin"));
			vo.setDebtEquity(rs.getDouble("debtEquity"));
			vo.setBookValuePerShare(rs.getDouble("bookValuePerShare"));
			vo.setCashPerShare(rs.getDouble("cashPerShare"));
			vo.setRoe(rs.getDouble("roe"));
			vo.setRoa(rs.getDouble("roa"));
			vo.setDividend(rs.getDouble("dividend"));
			vo.setReScheduleDate(rs.getString("reScheduleDate"));
			vo.setCreateDate(rs.getString("createDate"));
			vo.setUpdateDate(rs.getString("updateDate"));
			vo.setSector(rs.getString("sector"));
			vo.setStrategy(rs.getString("strategy"));
			vo.setExDividendDate(rs.getString("exDividendDate"));
			vo.setOptions(rs.getString("options"));
			vo.setWeeklyoptions(rs.getString("weeklyoptions"));

			return vo;
		}
	}
}
