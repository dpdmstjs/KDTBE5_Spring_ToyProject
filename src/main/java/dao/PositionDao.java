package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constant.Position;
import dto.PositionRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionDao {

	private final Connection connection;
	public List<PositionRespDto> getPositionInfo() {
		List<PositionRespDto> positionList = new ArrayList<>();

		String query = "SET @sql = NULL; "
			+ "SELECT GROUP_CONCAT(DISTINCT "
			+ "           CONCAT("
			+ "             'GROUP_CONCAT(CASE WHEN t.name = ''', "
			+ "             t.name, "
			+ "             ''' THEN p.name ELSE NULL END) AS `', "
			+ "             t.name, "
			+ "             '`' "
			+ "           ) SEPARATOR ',') INTO @sql "
			+ "FROM player p "
			+ "JOIN team t ON p.team_id = t.id; "
			+ "SET @sql = CONCAT('SELECT p.position, ', @sql, ' FROM player p JOIN team t ON p.team_id = t.id GROUP BY p.position'); "
			+ "PREPARE stmt FROM @sql; "
			+ "EXECUTE stmt; "
			+ "DEALLOCATE PREPARE stmt";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			boolean hasResultSet = statement.execute();
			if (hasResultSet) {
				try (ResultSet resultSet = statement.getResultSet()) {
					while (resultSet.next()) {
						String teamPlayers = resultSet.getString(2);
						List<String> teamPlayerList = Arrays.asList(teamPlayers.split(","));
						PositionRespDto positionDto = PositionRespDto.builder()
							.position(Position.findByName(resultSet.getString("position")))
							.teamPlayers(teamPlayerList)
							.build();
						positionList.add(positionDto);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return positionList;
	}
}