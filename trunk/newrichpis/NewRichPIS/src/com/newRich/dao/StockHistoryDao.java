package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.model.StockHistory;

public class StockHistoryDao extends BaseDao{
	
	private static StockHistoryDao daoInstance = new StockHistoryDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;
	
	public static StockHistoryDao getInstance() {
        return daoInstance;
    }
	
	//取得欲連線的資料庫來源
    protected String getDsName() {
        return DATA_SOURCE_DEFAULT;
    }

    protected Log getLog() {
        return log;
    }
    
    public StockHistoryDao() {
        super();
        //System.out.println("666");
        this.log = LogFactory.getLog(this.getClass());
        this.jdbcTemplate = new JdbcTemplate(getDataSource());
        //System.out.println("777");
    }
    //limit n,m => 從第n筆開始，取出m筆的資料
    /**
     * 讀取所有資料
     * @return List 
     */
	private static final String SELECT_SQL = 
		    "SELECT * "
			+ "FROM stock_history ";
    
	public List<StockHistory> findAll() {
    	return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockHistoryMapper()));
    }
	
	/**
     * 新增資料
     * @return int
     */
    private static final String INSERT_stockHistory_SQL =
    		" INSERT INTO stock_history "
			+ " (id, stockCode, createDate, open, high, "
			+ "	low, close, volume, adjClose) "
			+ " VALUES "
			+ " (?, ?, ?, ?, ?,"
			+ "  ?, ?, ?, ?) ";
    public int insert(StockHistory vo) {
        final Object[] params = new Object[] {
          vo.getId(), vo.getStockCode(), vo.getCreateDate(), vo.getOpen(), vo.getHigh(),
          vo.getLow(), vo.getClose(), vo.getVolume(), vo.getAdjClose()
        };
        return jdbcTemplate.update(INSERT_stockHistory_SQL, params);
    }
	
	/**
     * 刪除資料
     * @return int
     */
    private static final String DELETE_SQL = 
    	" DELETE FROM stock_history " 
    	+ " WHERE STOCKCODE = ? AND CREATEDATE < ? ";
    	
	public int delete(StockHistory vo) {
		final Object[] params = new Object[] { vo.getStockCode(), vo.getCreateDate() };
		return jdbcTemplate.update(DELETE_SQL, params);
	}
	
	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
    class StockHistoryMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int index) throws SQLException {
        	StockHistory vo = new StockHistory();
        	vo.setId(rs.getString("id"));
        	vo.setStockCode(rs.getString("stockCode"));
        	vo.setCreateDate(rs.getString("createDate"));
        	vo.setOpen(rs.getString("OPEN"));
        	vo.setHigh(rs.getString("high"));
            vo.setLow(rs.getString("low"));
            vo.setClose(rs.getString("CLOSE"));
            vo.setVolume(rs.getString("volume"));
            vo.setAdjClose(rs.getString("adjClose"));
            return vo;
        }
    }
}
