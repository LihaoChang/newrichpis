package com.newRich.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.newRich.backRun.vo.SelectVO;


public class SystemUtil implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8706229590393992105L;
	
	public static String QUARTZ_PACKAGE = "com.newRich.quartz";
	public static String QUARTZ_CLASS_NAME[] = new String[] { "JobDemo",
			"StockCode2DB", "StockFindGood2DB", "StockIchart2DB",
			"StockSector2DB", "StockValue2DB" };
	public static String QUARTZ_CLASS_NAME_JobDemo = "JobDemo";
	public static String QUARTZ_CLASS_NAME_StockCode2DB = "StockCode2DB";
	public static String QUARTZ_CLASS_NAME_StockFindGood2DB = "StockFindGood2DB";
	public static String QUARTZ_CLASS_NAME_StockIchart2DB = "StockIchart2DB";
	public static String QUARTZ_CLASS_NAME_StockSector2DB = "StockSector2DB";
	public static String QUARTZ_CLASS_NAME_StockValue2DB = "StockValue2DB";
	
	public String moneyFormat(String str) {
		String check_str = str.substring(0, 1);
		String add_str = "";
		if (check_str.equals("-")) {
			str = str.substring(1);
			add_str = "-";
		}

		int size = str.length();
		if (str.length() <= 3) {
			return add_str + str;
		} else {
			return moneyFormat(add_str + str.substring(0, size - 3)) + ","
					+ (str.substring(size - 3));
		}
	}

	// string is null to ""
	public String StrUtilNull(String str) {
		String out_str = "";
		if (str.trim().equals("null")) {
			out_str = "";
		} else {
			out_str = str.trim();
		}
		return out_str;
	}
	
	//將map裡的值進行排序，由小到大
	public static List sortMap(Map map){
		//排序
		List data = new ArrayList(map.values());
		Collections.sort(data);
		return data;
	}
	
	public static List<SelectVO> getSelectClassName(){
		List<SelectVO> quartzTypeList = new ArrayList<SelectVO>();
		
		Set<Class<?>> set = SystemUtil.getClasses(SystemUtil.QUARTZ_PACKAGE);
		Iterator<Class<?>> it = set.iterator();
		String reClassName = "", lastName = "";
		int count = 0;
		while (it.hasNext()) {
			SelectVO select = new SelectVO();
			Class<?> classs = (Class<?>) it.next();
			String className = classs.getName();
			if(null != className)
				className = className.replace(SystemUtil.QUARTZ_PACKAGE+".", "");
			//System.out.println("lastName:"+lastName+"  className:"+className);
			//System.out.println("className.indexOf('$'):"+className.indexOf('$'));
			if(className.indexOf('$') == -1 && !className.equals(lastName)){
				count++;
				reClassName = className;
				select.setString(reClassName);
				select.setValue(reClassName);
				quartzTypeList.add(select);
			}
			lastName = className;
		}
		return quartzTypeList;
	}
	
	public static List<String> getClassName(){
		List<String> list = new ArrayList<String>();
		
		Set<Class<?>> set = SystemUtil.getClasses(SystemUtil.QUARTZ_PACKAGE);
		Iterator<Class<?>> it = set.iterator();
		String reClassName = "", lastName = "";
		int count = 0;
		while (it.hasNext()) {
			SelectVO select = new SelectVO();
			Class<?> classs = (Class<?>) it.next();
			String className = classs.getName();
			if(null != className)
				className = className.replace(SystemUtil.QUARTZ_PACKAGE+".", "");
			//System.out.println("lastName:"+lastName+"  className:"+className);
			//System.out.println("className.indexOf('$'):"+className.indexOf('$'));
			if(className.indexOf('$') == -1 && !className.equals(lastName)){
				count++;
				reClassName = className;
				list.add(className);
			}
			lastName = className;
		}
		return list;
	}
	
	/**
	 * 取得指定package下的所有類別名稱
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// class類的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循環
		boolean recursive = true;
		// 獲取pack的名字並進行替換
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定義一個Enumeration的集合
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(
					packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				// 得到協議的名稱
				String protocol = url.getProtocol();
				// 找出檔案形式
				if ("file".equals(protocol)) {
					//System.err.println("File檔案的掃描==");
					// 取得包的路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以檔案形式的方式掃描整個包下的文件並新增到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					//System.err.println("jar類型的掃描==");
					JarFile jar;
					try {
						// 取得jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一個package
								if (idx != -1) {
									// 取得package 把"/"轉換成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以再選下去就是一個package
								if ((idx != -1) || recursive) {
									// 如果是一個.class文件 而且不是目錄
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉後面的".class" 獲得真正的類別名稱
										String className = name.substring(
												packageName.length() + 1, name
														.length() - 6);
										try {
											// 加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式來取得package下的所有Class
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "."
						+ file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
}
