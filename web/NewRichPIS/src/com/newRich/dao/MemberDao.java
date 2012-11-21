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

import com.newRich.model.Person;
import com.newRich.util.RtMember;

public class MemberDao extends BaseDao {

	private static MemberDao daoInstance = new MemberDao();
	private JdbcTemplate jdbcTemplate = null;
	private Log log = null;

	public static MemberDao getInstance() {
		return daoInstance;
	}

	// 取得欲連線的資料庫來源
	protected String getDsName() {
		return DATA_SOURCE_DEFAULT;
	}

	protected Log getLog() {
		return log;
	}

	public MemberDao() {
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
	private static final String SELECT_SQL = "SELECT * FROM rt_member ";

	public List<RtMember> findAll() {
		return (List) jdbcTemplate.query(SELECT_SQL, new RowMapperResultSetExtractor(new Rtmember()));
	}
	
	/**
	 * 讀取資料By Query Form
	 * 
	 * @return List
	 */
	public List<Person> findAllByForm(RtMember formVO) {
		String SELECT_MEMBER_SQL = "SELECT * FROM rt_member where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getMemberId())) {
			SELECT_MEMBER_SQL += "and memberId like '%" + formVO.getMemberId() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getNickname())) {
			SELECT_MEMBER_SQL += "and nickname = '" + formVO.getNickname() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getRealname())) {
			SELECT_MEMBER_SQL += "and realname = '" + formVO.getRealname() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getEmail())) {
			SELECT_MEMBER_SQL += "and email = '" + formVO.getEmail() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getScale())) {
			SELECT_MEMBER_SQL += "and scale = '" + formVO.getScale() + "' ";
		}

		return (List) jdbcTemplate.query(SELECT_MEMBER_SQL, new RowMapperResultSetExtractor(new Rtmember()));
	}
	
	/**
	 * 查詢固定筆數的資料
	 * @param formVO
	 * @param from
	 * @param pageShowSize
	 * @return
	 */
	public List<RtMember> findAllByFormForPage(RtMember formVO, int from, int pageShowSize) {
		String SELECT_MEMBER_SQL = "SELECT * FROM rt_member where 1=1 ";
		if (StringUtils.isNotBlank(formVO.getMemberId())) {
			SELECT_MEMBER_SQL += "and memberId like '%" + formVO.getMemberId() + "%' ";
		}
		if (StringUtils.isNotBlank(formVO.getNickname())) {
			SELECT_MEMBER_SQL += "and nickname = '" + formVO.getNickname() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getRealname())) {
			SELECT_MEMBER_SQL += "and realname = '" + formVO.getRealname() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getEmail())) {
			SELECT_MEMBER_SQL += "and email = '" + formVO.getEmail() + "' ";
		}
		if (StringUtils.isNotBlank(formVO.getScale())) {
			SELECT_MEMBER_SQL += "and scale = '" + formVO.getScale() + "' ";
		}
		SELECT_MEMBER_SQL += " limit ?,? ";
		
		final Object[] params = new Object[] { from, pageShowSize };

		return (List) jdbcTemplate.query(SELECT_MEMBER_SQL, params, new RowMapperResultSetExtractor(new Rtmember()));
	}
	
	/**
	 * 新增資料
	 * 
	 * @return int
	 * 
	 */
	private static final String INSERT_SQL = " INSERT INTO rt_member (memberId, nickname, realname, email, scale, updateDate)  VALUES  (?, ?, ?, ?, ?, ?) ";

	public int insert(RtMember vo) {
		final Object[] params = new Object[] { vo.getMemberId(), vo.getNickname(), vo.getRealname(), vo.getEmail(), vo.getScale(), vo.getUpdateDate() };
		return jdbcTemplate.update(INSERT_SQL, params);
	}
	
	/**
	 * 更新資料
	 * 
	 * @return int
	 * 
	 */
	private static final String UPDATE_SQL = " UPDATE rt_member SET nickname = ?, realname = ?, email = ?, scale = ?, updateDate = ? where memberId = ? ";

	public int update(RtMember vo) {
		final Object[] params = new Object[] { vo.getNickname(), vo.getRealname(), vo.getEmail(), vo.getScale(), vo.getUpdateDate(), vo.getMemberId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	
	/**
	 * 刪除資料
	 * 
	 * @return int
	 */
	private static final String DELETE_SQL = " DELETE FROM rt_member " + " WHERE memberId = ? ";

	public int delete(String memberId) {
		final Object[] params = new Object[] { memberId };
		return jdbcTemplate.update(DELETE_SQL, params);
	}
	
	/**
	 * 讀取資料BY user or pwd
	 * 
	 * @return List
	 */
	public RtMember findById(String id) {
		RtMember vo = null;
		String SELECT_BY_ID_SQL = "SELECT * FROM rt_member " + "where memberId = ? ";
		final Object[] params = new Object[] { id };
		List list = (List) jdbcTemplate.query(SELECT_BY_ID_SQL, params, new RowMapperResultSetExtractor(new Rtmember()));
		if (null != list && list.size() > 0) {
			vo = (RtMember) list.get(0);
		} else {
			return null;
		}
		return vo;
	}
	
	/**
	 * 更新資料-UpdateDate
	 * 
	 * @return int
	 * 
	 */
	public int memberUpdateDate(RtMember vo) {
		String UPDATE_SQL = " UPDATE rt_member SET updateDate = ? where memberId = ? ";
		final Object[] params = new Object[] { vo.getUpdateDate(), vo.getMemberId() };
		return jdbcTemplate.update(UPDATE_SQL, params);
	}
	
	/**
	 * Friendly Class 讓Spring把讀取的資料一個一個塞到VO裡Call Back用.
	 */
	class Rtmember implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			RtMember vo = new RtMember();
			vo.setMemberId(rs.getString("memberId"));
			vo.setNickname(rs.getString("nickname"));
			vo.setRealname(rs.getString("realname"));
			vo.setEmail(rs.getString("email"));
			vo.setScale(rs.getString("scale"));
			vo.setUpdateDate(rs.getString("updateDate"));

			return vo;
		}
	}
}
