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

import com.newRich.backRun.vo.StockToolForm;
import com.newRich.model.StockTool;

public class StockToolDao extends BaseDao {

	private static StockToolDao daoInstance = new StockToolDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static StockToolDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public StockToolDao() {
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
	private static final String SELECT_SQL = "SELECT * " + "FROM Stock_Tool ";

	public List<StockTool> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new StockToolMapper()));
	}

	/**
	 * 讀取資料BY user、pwd
	 * 
	 * @return List
	 */
	public List<StockTool> findByFromEnd(StockToolForm formVO, int from, int pageShowSize) {
		String SELECT_formVO_SQL = "SELECT * FROM Stock_Tool " + "where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getId())) {
			SELECT_formVO_SQL += "and id = '" + formVO.getId() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_formVO_SQL += "and userid = '" + formVO.getUserid() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_formVO_SQL += "and name = '" + formVO.getName() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_formVO_SQL += "and url = '" + formVO.getUrl() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_formVO_SQL += "and remark = '" + formVO.getRemark() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getName())) {
			SELECT_formVO_SQL += "and updateDate = '" + formVO.getUpdateDate() + "' ";
		}
		
		SELECT_formVO_SQL += " order by name ";
		
		SELECT_formVO_SQL += " limit ?,? ";
		final Object[] params = new Object[] { from, pageShowSize };

		return (List) jdbcTemplate.query(SELECT_formVO_SQL, params, new RowMapperResultSetExtractor(new StockToolMapper()));
	}

	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public StockTool findById(String id) {
		try {
			String SELECT_BY_ID_SQL = "SELECT * FROM Stock_Tool " + "where id = ? ";
			final Object[] params = new Object[] { id };
			ArrayList list = (ArrayList) jdbcTemplate.query(SELECT_BY_ID_SQL, params, new RowMapperResultSetExtractor(new StockToolMapper()));
			if (null != list && list.size() >= 0) {
				return (StockTool) list.get(0);
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
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO Stock_Tool " + " (id, userid, name, url, remark, updateDate) " + " VALUES "
			+ " (?, ?, ?, ?, ?, ?) ";

	public int insert(StockTool vo) {
		final Object[] params = new Object[] { vo.getId(), vo.getUserid(), vo.getName(), vo.getUrl(), vo.getRemark(), vo.getUpdateDate() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}

	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE Stock_Tool SET " + "userid = ?, name = ?, url = ?, remark = ?, updateDate = ? where id = ? ";

	public int update(StockTool vo) {
		final Object[] params = new Object[] { vo.getUserid(), vo.getName(), vo.getUrl(), vo.getRemark(), vo.getUpdateDate(), vo.getId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}

	/**
	 * 刪除資料
	 * 
	 * @return int
	 */
	private static final String DELETE_SQL = " DELETE FROM Stock_Tool " + " WHERE id = ?";

	public int delete(String id) {
		final Object[] params = new Object[] { id };
		return jdbcTemplate.update(DELETE_SQL, params);
	}

	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class StockToolMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			StockTool vo = new StockTool();
			vo.setId(rs.getString("id"));
			vo.setUserid(rs.getString("userid"));
			vo.setName(rs.getString("name"));
			vo.setUrl(rs.getString("url"));
			vo.setRemark(rs.getString("remark"));
			vo.setUpdateDate(rs.getString("updateDate"));

			return vo;
		}
	}
}