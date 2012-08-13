package com.newRich.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
