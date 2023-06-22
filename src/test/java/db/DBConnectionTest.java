package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DBConnectionTest {

	@DisplayName("DB 연결 테스트")
	@Test
	public void ConnectionTest() throws SQLException {
		//given
		Connection connection = DBConnection.getInstance();
		PreparedStatement statement = connection.prepareStatement("select now() from dual");

		//when
		ResultSet rs = statement.executeQuery();

		//then
		Assertions.assertNotNull(rs.next());
	}
}
