package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.backRun.vo.SelectVO;
import com.newRich.backRun.vo.Stock;
import com.newRich.backRun.vo.StockForm;
import com.newRich.backRun.vo.StockVO;

public class StockDao extends BaseDao {

	private static StockDao daoInstance = new StockDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static StockDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public StockDao() {
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
	private static final String SELECT_SQL = "SELECT * " + "FROM stock ";

	public List<StockVO> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockMapper()));
	}

	/**
	 * 讀取所有資料By Query Form
	 * 
	 * @return List
	 */
	public List<StockVO> findAllByForm(StockForm formVO) {
		String SELECT_SQL = "SELECT * FROM stock where 1=1 ";
		if (null != formVO) {
			if (StringUtils.isNotEmpty(formVO.getStockCode())) {
				SELECT_SQL += " and stockCode like '%" + formVO.getStockCode() + "%' ";
			}

			if (StringUtils.isNotEmpty(formVO.getSector())) {
				SELECT_SQL += " and sector like '%" + formVO.getSector() + "%' ";
			}

			if (StringUtils.isNotEmpty(formVO.getOptions())) {
				SELECT_SQL += " and options = '" + formVO.getOptions() + "' ";
			}

			if (StringUtils.isNotEmpty(formVO.getWeeklyoptions())) {
				SELECT_SQL += " and weeklyoptions = '" + formVO.getWeeklyoptions() + "' ";
			}

			if (StringUtils.isNotEmpty(formVO.getStrategy())) {
				SELECT_SQL += " and strategy like '%" + formVO.getStrategy() + "%' ";
			}

			if (null != formVO.getNetIncome()) {
				SELECT_SQL += " and netIncome " + formVO.getNetIncomeType() + formVO.getNetIncome() + " ";
			}

			if (null != formVO.getNetIncomeGrowth()) {
				SELECT_SQL += " and netIncomeGrowth " + formVO.getNetIncomeGrowthType() + formVO.getNetIncomeGrowth() + " ";
			}

			if (null != formVO.getNetMargin()) {
				SELECT_SQL += " and netMargin " + formVO.getNetMarginType() + formVO.getNetMargin() + " ";
			}

			if (null != formVO.getDebtEquity()) {
				SELECT_SQL += " and debtEquity " + formVO.getDebtEquityType() + formVO.getDebtEquity() + " ";
			}

			if (null != formVO.getBookValuePerShare()) {
				SELECT_SQL += " and bookValuePerShare " + formVO.getBookValuePerShareType() + formVO.getBookValuePerShare() + " ";
			}

			if (null != formVO.getCashPerShare()) {
				SELECT_SQL += " and cashPerShare " + formVO.getCashPerShareType() + formVO.getCashPerShare() + " ";
			}

			if (null != formVO.getRoe()) {
				SELECT_SQL += " and roe " + formVO.getRoeType() + formVO.getRoe() + " ";
			}

			if (null != formVO.getRoa()) {
				SELECT_SQL += " and roa " + formVO.getRoaType() + formVO.getRoa() + " ";
			}

			if (null != formVO.getDividend()) {
				SELECT_SQL += " and dividend " + formVO.getDividendType() + formVO.getDividend() + " ";
			}

			if (formVO.getSharesTraded() != 0) {
				SELECT_SQL += " and sharesTraded " + formVO.getSharesTradedType() + formVO.getSharesTraded() + " ";
			}

			if (null != formVO.getExDividendDateStart() && null != formVO.getExDividendDateEnd()) {
				SELECT_SQL += " and exDividendDate >= '" + formVO.getExDividendDateStart() + "' ";
				SELECT_SQL += " and exDividendDate <= '" + formVO.getExDividendDateEnd() + "' ";
				SELECT_SQL += " and exDividendDate is not null ";
			}
			
			if (null != formVO.getIsSp500() && "Y".equals(formVO.getIsSp500())) {
				SELECT_SQL += " and isSp500 = '" + formVO.getIsSp500() + "' ";
			}
		}
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockMapper()));
	}

	/**
	 * 讀取所有資料，分頁 from ex: 使用者點選第二頁，傳入的參數就是10 pageShowSize 每頁要秀的筆數
	 * 
	 * @return List
	 */
	public List<StockVO> findAllByPage(StockForm formVO, int from, int pageShowSize) {
		String SELECT_SQL = "SELECT * FROM stock where 1=1 ";
		Object[] params = new Object[] {};

		if (null != formVO) {
			if (StringUtils.isNotEmpty(formVO.getStockCode())) {
				SELECT_SQL += " and stockCode like '%" + formVO.getStockCode() + "%' ";
			}

			if (StringUtils.isNotEmpty(formVO.getSector())) {
				SELECT_SQL += " and sector like '%" + formVO.getSector() + "%' ";
			}

			if (StringUtils.isNotEmpty(formVO.getStrategy())) {
				SELECT_SQL += " and strategy like '%" + formVO.getStrategy() + "%' ";
			}

			if (StringUtils.isNotEmpty(formVO.getOptions())) {
				SELECT_SQL += " and options = '" + formVO.getOptions() + "' ";
			}

			if (StringUtils.isNotEmpty(formVO.getWeeklyoptions())) {
				SELECT_SQL += " and weeklyoptions = '" + formVO.getWeeklyoptions() + "' ";
			}

			if (null != formVO.getNetIncome()) {
				SELECT_SQL += " and netIncome " + formVO.getNetIncomeType() + formVO.getNetIncome() + " ";
			}

			if (null != formVO.getNetIncomeGrowth()) {
				SELECT_SQL += " and netIncomeGrowth " + formVO.getNetIncomeGrowthType() + formVO.getNetIncomeGrowth() + " ";
			}

			if (null != formVO.getNetMargin()) {
				SELECT_SQL += " and netMargin " + formVO.getNetMarginType() + formVO.getNetMargin() + " ";
			}

			if (null != formVO.getDebtEquity()) {
				SELECT_SQL += " and debtEquity " + formVO.getDebtEquityType() + formVO.getDebtEquity() + " ";
			}

			if (null != formVO.getBookValuePerShare()) {
				SELECT_SQL += " and bookValuePerShare " + formVO.getBookValuePerShareType() + formVO.getBookValuePerShare() + " ";
			}

			if (null != formVO.getCashPerShare()) {
				SELECT_SQL += " and cashPerShare " + formVO.getCashPerShareType() + formVO.getCashPerShare() + " ";
			}

			if (null != formVO.getRoe()) {
				SELECT_SQL += " and roe " + formVO.getRoeType() + formVO.getRoe() + " ";
			}

			if (null != formVO.getRoa()) {
				SELECT_SQL += " and roa " + formVO.getRoaType() + formVO.getRoa() + " ";
			}

			if (null != formVO.getDividend()) {
				SELECT_SQL += " and dividend " + formVO.getDividendType() + formVO.getDividend() + " ";
			}

			if (formVO.getSharesTraded() != 0) {
				SELECT_SQL += " and sharesTraded " + formVO.getSharesTradedType() + formVO.getSharesTraded() + " ";
			}

			if (null != formVO.getExDividendDateStart() && null != formVO.getExDividendDateEnd()) {
				SELECT_SQL += " and exDividendDate >= '" + formVO.getExDividendDateStart() + "' ";
				SELECT_SQL += " and exDividendDate <= '" + formVO.getExDividendDateEnd() + "' ";
				SELECT_SQL += " and exDividendDate is not null ";
			}

			if (null != formVO.getOrderBy() && null != formVO.getOrderByType()) {
				SELECT_SQL += " order by " + formVO.getOrderBy() + " " + formVO.getOrderByType();
			}
			if (null != formVO.getIsSp500() && "Y".equals(formVO.getIsSp500())) {
				SELECT_SQL += " and isSp500 = '" + formVO.getIsSp500() + "' ";
			}

			SELECT_SQL += " limit ?,? ";
			params = new Object[] { from, pageShowSize };
		}

		return (List) jdbcTemplate.query(SELECT_SQL, params, new RowMapperResultSetExtractor(new StockMapper()));
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
	 */
	private static final String INSERT_SQL = " INSERT INTO stock " + " (stockCode, title, nowPrice, url, sharesTraded, "
			+ "prefixedTicker, netIncome, netIncomeGrowth, netMargin, debtEquity, " + "bookValuePerShare, cashPerShare, roe, roa, dividend, "
			+ "reScheduleDate, createDate, updateDate, sector, strategy, exDividendDate, options, weeklyoptions ) " + " VALUES " + " (?, ?, ?, ?, ?,"
			+ "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?,?,?,?,? ) ";

	public int insert(StockVO vo) {
		final Object[] params = new Object[] { vo.getStockCode(), vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(),
				vo.getPrefixedTicker(), vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(),
				vo.getCashPerShare(), vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(),
				vo.getSector(), vo.getStrategy(), vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions() };
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
			+ "reScheduleDate = ?, createDate = ?, updateDate = ?, sector = ? , strategy = ? , exDividendDate = ? , options = ?, weeklyoptions = ? "
			+ "where stockCode = ?";

	public int update(StockVO vo) {
		final Object[] params = new Object[] { vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(), vo.getPrefixedTicker(),
				vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(), vo.getCashPerShare(),
				vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(), vo.getSector(),
				vo.getStrategy(), vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	/**
	 * 查詢Sector資料
	 * 
	 * @return int
	 * 
	 */
	private static final String SELECT_SECTOR_SQL = " SELECT DISTINCT(sector) FROM stock order by 1";

	public List<SelectVO> querySectorList() {
		List<SelectVO> selects = new ArrayList<SelectVO>();
		List<StockVO> sectorList = (List) jdbcTemplate.query(SELECT_SECTOR_SQL, new RowMapperResultSetExtractor(new SectorMapper()));
		for (int i = 0; i < sectorList.size(); i++) {
			StockVO vo = sectorList.get(i);
			if (null != vo.getSector()) {
				SelectVO svo = new SelectVO();
				svo.setString(vo.getSector());
				svo.setValue(vo.getSector());
				selects.add(svo);
			}
		}
		return selects;
	}

	/**
	 * 讀取所有資料
	 * 
	 * @return List
	 */
	private static final String SELECT_stock_SQL = "SELECT * FROM stock ";

	public List<Stock> findAllToStock() {
		return (List) jdbcTemplate.query(SELECT_stock_SQL, new RowMapperResultSetExtractor(new StockMapper2()));
	}

	/**
	 * 讀取資料BY StockCode
	 * 
	 * @return List
	 */
	public Stock findByStockCodeToStock(String StockCode) {
		try {
			String SELECT_StockCode_SQL = "SELECT * FROM stock " + " where StockCode = ? ";
			final Object[] params = new Object[] { StockCode };
			ArrayList list = (ArrayList) jdbcTemplate.query(SELECT_StockCode_SQL, params, new RowMapperResultSetExtractor(new StockMapper2()));
			if (null != list && list.size() > 0) {
				return (Stock) list.get(0);
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
	private static final String INSERT_stock_SQL = " INSERT INTO stock " + " (stockCode, title, nowPrice, url, sharesTraded, "
			+ "prefixedTicker, netIncome, netIncomeGrowth, netMargin, debtEquity, " + "bookValuePerShare, cashPerShare, roe, roa, dividend, "
			+ "reScheduleDate, createDate, updateDate, sector, strategy, exDividendDate, options, weeklyoptions, isSp500) " + " VALUES " + " (?, ?, ?, ?, ?,"
			+ "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

	public int insert(Stock vo) {
		final Object[] params = new Object[] { vo.getStockCode(), vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(),
				vo.getPrefixedTicker(), vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(),
				vo.getCashPerShare(), vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(),
				vo.getSector(), vo.getStrategy(), vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions(), vo.getIsSp500() };
		return jdbcTemplate.update(INSERT_stock_SQL, params);
	}

	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_stock_SQL = " UPDATE stock SET " + "title = ?, nowPrice = ?, url = ?, sharesTraded = ?, "
			+ "prefixedTicker = ?, netIncome = ?, netIncomeGrowth = ?, netMargin = ?, debtEquity = ?, "
			+ "bookValuePerShare = ?, cashPerShare = ?, roe = ?, roa = ?, dividend = ?, "
			+ "reScheduleDate = ?, createDate = ?, updateDate = ?, sector = ? , strategy = ? , exDividendDate = ?, options = ?, weeklyoptions = ? "
			+ "where stockCode = ?";

	public int update(Stock vo) {
		final Object[] params = new Object[] { vo.getTitle(), vo.getNowPrice(), vo.getUrl(), vo.getSharesTraded(), vo.getPrefixedTicker(),
				vo.getNetIncome(), vo.getNetIncomeGrowth(), vo.getNetMargin(), vo.getDebtEquity(), vo.getBookValuePerShare(), vo.getCashPerShare(),
				vo.getRoe(), vo.getRoa(), vo.getDividend(), vo.getReScheduleDate(), vo.getCreateDate(), vo.getUpdateDate(), vo.getSector(),
				vo.getStrategy(), vo.getExDividendDate(), vo.getOptions(), vo.getWeeklyoptions(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_stock_SQL, params);
	}

	/**
	 * 讀取資料StockFindGood2DB
	 * 
	 * @return List
	 */

	public List<Stock> FindStockFindGood2DBAll(String where) {
		String SELECT_StockFindGood2DB_SQL = "SELECT * FROM STOCK WHERE (  ";
		SELECT_StockFindGood2DB_SQL += where + " ) ";
		log.info("StockFindGood2DBFindAll SQL:" + SELECT_StockFindGood2DB_SQL);
		return (List) jdbcTemplate.query(SELECT_StockFindGood2DB_SQL, new RowMapperResultSetExtractor(new StockMapper2()));
	}

	/**
	 * 更新資料StockFindGood2DB
	 * 
	 * @return int
	 * 
	 */
	public int updateStockFindGood2DB(String statement) {
		return jdbcTemplate.update(statement);
	}

	/**
	 * 更新資料StockSector2DB
	 * 
	 * @return int
	 * 
	 */
	public int updateSector(Stock vo) {
		String UPDATE_StockSector2DB_SQL = " UPDATE stock SET " + "updateDate = ?, sector = ? " + "where stockCode = ?";
		final Object[] params = new Object[] { vo.getUpdateDate(), vo.getSector(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_StockSector2DB_SQL, params);
	}

	public int updateIchart(Stock vo) {
		String UPDATE_StockIchart2DB_SQL = " UPDATE stock SET " + "updateDate = ?, strategy = ? " + "where stockCode = ?";
		final Object[] params = new Object[] { vo.getUpdateDate(), vo.getStrategy(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_StockIchart2DB_SQL, params);
	}

	public int updateAllOptionsEmpty() {
		String UPDATE_SQL = " UPDATE stock SET " + "options = ?";
		final Object[] params = new Object[] { "" };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	public int updateOptions(Stock vo) {
		String UPDATE_SQL = " UPDATE stock SET " + " options = ?,updateDate = ? " + " where stockCode = ?";
		final Object[] params = new Object[] { vo.getOptions(), vo.getUpdateDate(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	public int updateAllWeeklyoptionsEmpty() {
		String UPDATE_SQL = " UPDATE stock SET " + "weeklyoptions = ?";
		final Object[] params = new Object[] { "" };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	public int updateWeeklyoptions(Stock vo) {
		String UPDATE_SQL = " UPDATE stock SET " + " weeklyoptions = ?,updateDate = ? " + " where stockCode = ?";
		final Object[] params = new Object[] { vo.getWeeklyoptions(), vo.getUpdateDate(), vo.getStockCode() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	public int updateIsSP500(String sp500String) {
		String UPDATE_StockIsSP5002DB_SQL = " UPDATE stock SET isSp500 = 'Y' where stockCode in ('"+sp500String+"') ";
		return jdbcTemplate.update(UPDATE_StockIsSP5002DB_SQL);
	}

	/**
	 * 將strategy欄位的值清除
	 */
	private static final String UPDATE_CLEAR_STRATEGY_SQL = " UPDATE stock SET strategy = '' ";

	public int clearStrategy() {
		return jdbcTemplate.update(UPDATE_CLEAR_STRATEGY_SQL);
	}
	
	/**
	 * 將ISSP500欄位的值清除
	 */
	private static final String UPDATE_CLEAR_ISSP500_SQL = " UPDATE stock SET isSp500 = '' ";

	public int clearIsSP500() {
		return jdbcTemplate.update(UPDATE_CLEAR_ISSP500_SQL);
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
			vo.setIsSp500(rs.getString("isSp500"));
			
			return vo;
		}
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class SectorMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			StockVO vo = new StockVO();
			vo.setSector(rs.getString("sector"));

			return vo;
		}
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class StockMapper2 implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Stock vo = new Stock();
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
			vo.setIsSp500(rs.getString("isSp500"));
			
			return vo;
		}
	}

	public List<Stock> queryStockBySql(String sql) {
		return (List) jdbcTemplate.query(sql, new RowMapperResultSetExtractor(new StockMapper2()));
	}
}
