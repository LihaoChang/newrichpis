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

import com.newRich.backRun.vo.FinvizStockVO;


public class Sp500Dao extends BaseDao {

	private static Sp500Dao daoInstance = new Sp500Dao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static Sp500Dao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public Sp500Dao() {
		super();
		this.log = LogFactory.getLog(this.getClass());
		this.jdbcTemplate = new JdbcTemplate(getDataSource());
	}
	
	/**
	 * 讀取所有資料
	 * 
	 * @return List
	 */
	private static final String SELECT_SQL = "SELECT * FROM sp500 ";

	public List<FinvizStockVO> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * 讀取資料By Query Form
	 * 
	 * @return List
	 */
	public List<FinvizStockVO> findAllByForm(FinvizStockVO formVO) {
		String SELECT_SP500_SQL = "SELECT * FROM sp500 where 1=1 ";
		
		if (StringUtils.isNotBlank(formVO.getTicker())) {
			SELECT_SP500_SQL += "and ticker like '%" + formVO.getTicker() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getCompany())) {
			SELECT_SP500_SQL += "and company like '%" + formVO.getCompany() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getSector())) {
			SELECT_SP500_SQL += "and sector = '" + formVO.getSector() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getIndustry())) {
			SELECT_SP500_SQL += "and industry = '" + formVO.getIndustry() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getCountry())) {
			SELECT_SP500_SQL += "and country = '" + formVO.getCountry() + "' ";
		}

		return (List) jdbcTemplate.query(SELECT_SP500_SQL, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * 查詢固定筆數的資料
	 * @param formVO
	 * @param from
	 * @param pageShowSize
	 * @return
	 */
	public List<FinvizStockVO> findAllByFormForPage(FinvizStockVO formVO, int from, int pageShowSize) {
		String SELECT_SP500_SQL = "SELECT * FROM sp500 where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getTicker())) {
			SELECT_SP500_SQL += "and ticker like '%" + formVO.getTicker() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getCompany())) {
			SELECT_SP500_SQL += "and company like '%" + formVO.getCompany() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getSector())) {
			SELECT_SP500_SQL += "and sector = '" + formVO.getSector() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getIndustry())) {
			SELECT_SP500_SQL += "and industry = '" + formVO.getIndustry() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getCountry())) {
			SELECT_SP500_SQL += "and country = '" + formVO.getCountry() + "' ";
		}
		SELECT_SP500_SQL += " limit ?,? ";
		
		final Object[] params = new Object[] { from, pageShowSize };

		return (List) jdbcTemplate.query(SELECT_SP500_SQL, params, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * 新增資料
	 * 
	 * @return int
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO sp500 (ticker, company, sector, industry, country, marketCap, pe, price, changePer, volume, updateDate)  VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	public int insert(FinvizStockVO vo) {
		final Object[] params = new Object[] { vo.getTicker(), vo.getCompany(),
				vo.getSector(), vo.getIndustry(), vo.getCountry(),
				vo.getMarketCap(), vo.getPe(), vo.getPrice(),
				vo.getChangePer(), vo.getVolume(), vo.getUpdateDate() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}
	
	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE sp500 SET ticker = ?, company = ?, sector = ?, industry = ?, country = ?, marketCap = ?, pe = ?, price = ?, volume = ?, updateDate = ? where memberId = ? ";
	
	public int update(FinvizStockVO vo) {
		final Object[] params = new Object[] { vo.getCompany(),
				vo.getSector(), vo.getIndustry(), vo.getCountry(),
				vo.getMarketCap(), vo.getPe(), vo.getPrice(),
				vo.getChangePer(), vo.getVolume(), vo.getUpdateDate(), 
				vo.getTicker() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	
	/**
	 * 刪除資料
	 * 
	 * @return int
	 */
	private static final String DELETE_SQL = " DELETE FROM sp500 WHERE ticker = ? ";

	public int delete(String ticker) {
		final Object[] params = new Object[] { ticker };
		return jdbcTemplate.update(DELETE_SQL, params);
	}
	
	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public FinvizStockVO findById(String ticker) {
		FinvizStockVO vo = null;
		String SELECT_BY_ID_SQL = "SELECT * FROM sp500 where ticker = ? ";
		final Object[] params = new Object[] { ticker };
		List list = (List) jdbcTemplate.query(SELECT_BY_ID_SQL, params, new RowMapperResultSetExtractor(new FinvizStockMap()));
		if (null != list && list.size() > 0) {
			vo = (FinvizStockVO) list.get(0);
		} else {
			return null;
		}
		return vo;
	}
	
	/**
	 * 查詢distinct(sector)
	 * @return
	 */
	public List<FinvizStockVO> findSector() {
		String SELECT_SP500_SQL = "select distinct(sector) from sp500 ";
		return (List) jdbcTemplate.query(SELECT_SP500_SQL, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * 查詢distinct(industry)
	 * @return
	 */
	public List<FinvizStockVO> findIndustry() {
		String SELECT_SP500_SQL = "select distinct(industry) from sp500 ";
		return (List) jdbcTemplate.query(SELECT_SP500_SQL, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * 查詢distinct(country)
	 * @return
	 */
	public List<FinvizStockVO> findCountry() {
		String SELECT_SP500_SQL = "select distinct(country) from sp500 ";
		return (List) jdbcTemplate.query(SELECT_SP500_SQL, new RowMapperResultSetExtractor(new FinvizStockMap()));
	}
	
	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class FinvizStockMap implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			
			FinvizStockVO vo = new FinvizStockVO();
			
			vo.setTicker(rs.getString("ticker"));
			vo.setCompany(rs.getString("company"));
			vo.setSector(rs.getString("sector"));
			vo.setIndustry(rs.getString("industry"));
			vo.setCountry(rs.getString("country"));
			vo.setMarketCap(rs.getString("marketCap"));
			vo.setPe(rs.getString("pe"));
			vo.setPrice(rs.getDouble("price"));
			vo.setChangePer(rs.getDouble("changePer"));
			vo.setVolume(rs.getDouble("volume"));
			vo.setUpdateDate(rs.getString("updateDate"));

			return vo;
		}
	}
}
