package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Position;
import dto.PositionRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionDao {
	private final Connection connection;

	public PositionRespDto positionList() {
		List<String> teamList = getTeamNameList();
		Map<Position, List<String>> positionMap = new HashMap<>();

		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT a.position, ");

			// 팀이름으로 각각 대체
			for (String team : teamList) {
				builder.append("MAX(IF(a.team_name = ?, a.player_name, '-')) as ");
				builder.append(team);
				builder.append(", ");
			}

			builder.delete(builder.length() - 2, builder.length());
			builder.append(" FROM ( ");
			builder.append("SELECT p.name as player_name, p.position, t.name as team_name ");
			builder.append("FROM team t ");
			builder.append("JOIN player p ON p.team_id = t.id ");
			builder.append(") a ");
			builder.append("GROUP BY a.position");

			PreparedStatement statement = connection.prepareStatement(builder.toString());

			int index = 1;
			for (String team : teamList) {
				statement.setString(index++, team);
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Position position = Position.findByName(resultSet.getString("position"));
					List<String> teamPlayerList = new ArrayList<>();

					for (String team : teamList) {
						String teamName = resultSet.getString(team); // Update this line
						teamPlayerList.add(teamName);
					}
					positionMap.put(position, teamPlayerList);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return PositionRespDto.builder()
			.positionMap(positionMap)
			.teamList(teamList)
			.build();
	}

	public List<String> getTeamNameList() {
		List<String> teamList = new ArrayList<>();
		try {
			String query = "SELECT name FROM team";
			PreparedStatement statement = connection.prepareStatement(query);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					teamList.add(resultSet.getString("name"));
				}
			} catch (SQLException e) {

			}
		} catch (Exception e) {

		}
		return teamList;
	}

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
					// PositionRespDto positionDto = PositionRespDto.builder()
					// 	.position(Position.findByName(resultSet2.getString("position")))
					// 	.teamPlayers(teamPlayerList)
					// 	.build();
					// positionList.add(positionDto);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return positionList;
	}
}