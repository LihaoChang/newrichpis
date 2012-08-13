package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.model.MenuItem;

public class MenuItemDao extends BaseDao{
	
	private static MenuItemDao daoInstance = new MenuItemDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;
	
	public static MenuItemDao getInstance() {
        return daoInstance;
    }
	
	//取得欲連線的資料庫來源
    protected String getDsName() {
        return DATA_SOURCE_DEFAULT;
    }

    protected Log getLog() {
        return log;
    }
    
    public MenuItemDao() {
    	
        super();
        //System.out.println("666");
        this.log = LogFactory.getLog(this.getClass());
        this.jdbcTemplate = new JdbcTemplate(getDataSource());
        //System.out.println("777");
    }
    
	/**
     * 讀取所有資料
     * @return List 
     */
	private static final String SELECT_SQL = 
		    "SELECT * "
			+ "FROM menu_item ";
    
	public List<MenuItem> findAll() {
    	return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new MenuItemMapper()));
    }
	
	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
    class MenuItemMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int index) throws SQLException {
        	MenuItem vo = new MenuItem();
        	vo.setId(rs.getInt("id"));
        	vo.setParent_name(rs.getString("parent_name"));
        	vo.setName(rs.getString("name"));
        	vo.setTitle(rs.getString("title"));
        	vo.setDescription(rs.getString("description"));
        	vo.setLocation(rs.getString("location"));
        	vo.setTarget(rs.getString("target"));
        	vo.setOnclick(rs.getString("onclick"));
        	vo.setOnmouseover(rs.getString("onmouseover"));
        	vo.setOnmouseout(rs.getString("onmouseout"));
        	vo.setImage(rs.getString("image"));
        	vo.setAltImage(rs.getString("altImage"));
        	vo.setTooltip(rs.getString("tooltip"));
        	vo.setRoles(rs.getString("roles"));
        	vo.setPage(rs.getString("page"));
        	vo.setWidth(rs.getString("width"));
        	vo.setHeight(rs.getString("height"));
        	vo.setForward(rs.getString("forward"));
        	vo.setAction(rs.getString("action"));
        	
            return vo;
        }
    }
}
