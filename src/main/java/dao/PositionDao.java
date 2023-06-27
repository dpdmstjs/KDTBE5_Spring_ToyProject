package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constant.Position;
import dto.PositionRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionDao {

	private final Connection connection;

	public List<PositionRespDto> getPositionInfo() throws SQLException {
		List<PositionRespDto> positionList = new ArrayList<>();

		String query1 =
			"SELECT GROUP_CONCAT(DISTINCT CONCAT('GROUP_CONCAT(CASE WHEN t.name = ''', t.name, ''' THEN p.name ELSE NULL END) AS `', t.name, '`') SEPARATOR ',') "
				+
				"FROM player p JOIN team t ON p.team_id = t.id";

		try (PreparedStatement statement1 = connection.prepareStatement(
			query1); ResultSet resultSet1 = statement1.executeQuery()) {
			resultSet1.next();

			String teams = resultSet1.getString(1);

			String query2 = "SELECT p.position," + teams +
				"FROM player p JOIN team t ON p.team_id = t.id " +
				"GROUP BY p.position";

			try (PreparedStatement statement2 = connection.prepareStatement(
				query2); ResultSet resultSet2 = statement2.executeQuery()) {
				while (resultSet2.next()) {
					String teamPlayers = resultSet2.getString(2);
					List<String> teamPlayerList = Arrays.asList(teamPlayers.split(","));
					PositionRespDto positionDto = PositionRespDto.builder()
						.position(Position.findByName(resultSet2.getString("position")))
						.teamPlayers(teamPlayerList)
						.build();
					positionList.add(positionDto);
				}
			}
			return positionList;
		}
	}
}