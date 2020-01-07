package com.discordapp.JarvisBot.utils.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SelectCall {
	void call(ResultSet resultSet) throws SQLException;
}
