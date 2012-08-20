package com.newRich.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import com.newRich.backRun.vo.QuartzTriggersForm;
import com.newRich.backRun.vo.QuartzTriggersVO;
import com.newRich.util.SpringQuartzConstant;

public class QuartzTriggersDao extends BaseDao{
	
	private static QuartzTriggersDao daoInstance = new QuartzTriggersDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;
	
	public static QuartzTriggersDao getInstance() {
        return daoInstance;
    }
	
	//取得欲連線的資料庫來源
    protected String getDsName() {
        return DATA_SOURCE_DEFAULT;
    }

    protected Log getLog() {
        return log;
    }
    
    public QuartzTriggersDao() {
        super();
        //System.out.println("666");
        this.log = LogFactory.getLog(this.getClass());
        this.jdbcTemplate = new JdbcTemplate(getDataSource());
        //System.out.println("777");
    }
    
    /**
	 * 讀取資料BY user、pwd
	 * 
	 * @return List
	 */
	public List<QuartzTriggersVO> findAllByForm(QuartzTriggersForm formVO) {
		String SELECT_formVO_SQL = "SELECT * FROM QRTZ_TRIGGERS where 1=1 ";
		System.out.println("findAllByForm formVO.getTriggerGroup():"+formVO.getTriggerGroup());
		if (StringUtils.isNotBlank(formVO.getTriggerGroup())) {
			SELECT_formVO_SQL += "and TRIGGER_GROUP like '%" + formVO.getTriggerGroup() + "%' ";
		}
		SELECT_formVO_SQL += " order by start_time ";
		System.out.println("findAllByForm SELECT_formVO_SQL:"+SELECT_formVO_SQL);

		return (List) jdbcTemplate.query(SELECT_formVO_SQL, new RowMapperResultSetExtractor(new QuartzTriggersMapper()));
	}
    
    /**
	 * 讀取資料BY user、pwd
	 * 
	 * @return List
	 */
	public List<QuartzTriggersVO> findByFromEnd(QuartzTriggersForm formVO, int from, int pageShowSize) {
		String SELECT_formVO_SQL = "SELECT * FROM QRTZ_TRIGGERS where 1=1 ";
		System.out.println("findByFromEnd formVO.getTriggerGroup():"+formVO.getTriggerGroup());
		if (StringUtils.isNotBlank(formVO.getTriggerGroup())) {
			SELECT_formVO_SQL += "and TRIGGER_GROUP like '%" + formVO.getTriggerGroup() + "%' ";
		}
		SELECT_formVO_SQL += " order by start_time ";
		
		SELECT_formVO_SQL += " limit ?,? ";
		final Object[] params = new Object[] { from, pageShowSize };
		System.out.println("findByFromEnd SELECT_formVO_SQL:"+SELECT_formVO_SQL);
		return (List) jdbcTemplate.query(SELECT_formVO_SQL, params, new RowMapperResultSetExtractor(new QuartzTriggersMapper()));
	}
    
    public List<Map<String, Object>> getQrtzTriggers() {
		List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from QRTZ_TRIGGERS order by start_time");
		long val = 0;
		String temp = null;
		for (Map<String, Object> map : results) {
			temp = MapUtils.getString(map, "trigger_name");
			if(StringUtils.indexOf(temp, "&") != -1){
				map.put("display_name", StringUtils.substringBefore(temp, "&"));
			}else{
				map.put("display_name", temp);
			}
			
			val = MapUtils.getLongValue(map, "next_fire_time");
			if (val > 0) {
				map.put("next_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, "prev_fire_time");
			if (val > 0) {
				map.put("prev_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, "start_time");
			if (val > 0) {
				map.put("start_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}
			
			val = MapUtils.getLongValue(map, "end_time");
			if (val > 0) {
				map.put("end_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
			}
			
			map.put("statu",SpringQuartzConstant.status.get(MapUtils.getString(map, "trigger_state")));
		}

		return results;
	}
    
    /**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class QuartzTriggersMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			QuartzTriggersVO vo = new QuartzTriggersVO();
			vo.setTriggerName(parserTriggerName(rs.getString("TRIGGER_NAME")));
			vo.setTriggerGroup(rs.getString("TRIGGER_GROUP"));
			vo.setJobName(rs.getString("JOB_NAME"));
			vo.setJobGroup(rs.getString("JOB_GROUP"));
			vo.setIsVolatile(rs.getString("IS_VOLATILE"));
			vo.setDescription(rs.getString("DESCRIPTION"));
			vo.setNextFireTime(longToDateString(rs.getLong("NEXT_FIRE_TIME")));
			vo.setPrevFireTime(longToDateString(rs.getLong("PREV_FIRE_TIME")));
			vo.setPriority(rs.getInt("PRIORITY"));
			vo.setTriggerState(rs.getString("TRIGGER_STATE"));
			vo.setTriggerType(rs.getString("TRIGGER_TYPE"));
			vo.setStartTime(longToDateString(rs.getLong("START_TIME")));
			vo.setEndTime(longToDateString(rs.getLong("END_TIME")));
			vo.setCalendarName(rs.getString("CALENDAR_NAME"));
			vo.setMisfireInstr(rs.getInt("MISFIRE_INSTR"));
			vo.setJobData(rs.getBlob("JOB_DATA"));

			return vo;
		}
	}
	
	
	public String longToDateString(long dateLong){
		String date = "";
		if (dateLong > 0) {
			date = DateFormatUtils.format(dateLong, "yyyy-MM-dd HH:mm:ss");
		}
		return date;
	}
	
	public String parserTriggerName(String triggerName){
		String name = "";
		if(StringUtils.indexOf(triggerName, "&") != -1){
			name = StringUtils.substringBefore(triggerName, "&");
		}else{
			name = triggerName;
		}
		return name;
	}
}
