package com.project.model;

import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<UserDO>{

	@Override
	public UserDO mapRow(ResultSet rs,int rowNum) throws SQLException {
        return new UserDO(
				rs.getString("user_id"),
				rs.getString("name"),
				rs.getString("nickname"),
				rs.getString("email"),
				rs.getString("password"),
				rs.getString("created_date"));
	}
}