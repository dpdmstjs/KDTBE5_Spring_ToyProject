package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		Map<String, List<String>> positionMap = new HashMap<>();

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
					String position = resultSet.getString("position");
					List<String> teamPlayerList = new ArrayList<>();

					for (String team : teamList) {
						String teamName = resultSet.getString(team);
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
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teamList;
	}
}