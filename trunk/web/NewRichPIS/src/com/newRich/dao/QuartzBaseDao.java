package com.newRich.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class QuartzBaseDao {
	/**
     * 資料庫來源(預設DB連線).
     */
    public static final String DATA_SOURCE_DEFAULT = "default";
    /**
     * 存放DataSource，模擬連線池
     */
    private static Map<String, DataSource> map = new HashMap<String, DataSource>();

    public static String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}
    public static Log getLog() {
		return null;
	}
    
    public static DataSource dataSource;

    public static void setDataSource(DataSource ds) {
      dataSource = ds;
    }

    /**
     * 取得資料來源，根據每一個繼承此DAO的類別，所定義之預設資料來源名稱getDataSourceName()
     *
     * @return (DataSource)
     */
    public static DataSource getDataSource() {
    	//讀取db 連線相關資訊
    	//System.out.println("11111"+getDsName());
    	Properties prop = loadPreoperties();
    	dataSource = (javax.sql.DataSource) map.get(getDsName());
        if ( dataSource != null)
            return dataSource;
        //System.out.println("22222:");
        try {
        	//System.out.println("333333");
        	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        	//System.out.println(".jdbc.driverClass:"+prop.getProperty(getDsName()+".jdbc.driverClass"));
        	driverManagerDataSource.setDriverClassName(prop.getProperty(getDsName()+".jdbc.driverClass"));
        	driverManagerDataSource.setUrl(prop.getProperty(getDsName()+".jdbc.jdbcUrl"));
        	driverManagerDataSource.setUsername(prop.getProperty(getDsName()+".jdbc.user"));
        	driverManagerDataSource.setPassword(prop.getProperty(getDsName()+".jdbc.password"));
        	//System.out.println("444444");
            setDataSource(driverManagerDataSource);
            map.put(getDsName(), dataSource);//將建立好的連線放進map中，以供重覆使用。
            //System.out.println("555555");
        } catch (Exception e) {
        	e.printStackTrace();
            getLog().error("Get DataSource Error!", e);
            throw new RuntimeException("Get Connection Error!", e);
        }
        return dataSource;
    }
    
	public static Properties loadPreoperties() {
		Properties props = new Properties();
		InputStream in = QuartzBaseDao.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}
		return props;
	}
}
