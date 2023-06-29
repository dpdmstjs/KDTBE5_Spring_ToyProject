package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.TeamRespDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamDAO {
	private final Connection connection;

	public int createTeam(int stadiumId, String name) {
		String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, stadiumId);
			statement.setString(2, name);

			int rowCount = statement.executeUpdate();

			return rowCount;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<TeamRespDTO> selectTeamList() {
		List<TeamRespDTO> teamList = new ArrayList<>();
		String query = "SELECT team.id, stadium.name AS stadium_name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(query)) {
				while (resultSet.next()) {
					TeamRespDTO teamRespDTO = TeamRespDTO.builder()
						.teamId(resultSet.getInt("id"))
						.stadiumName(resultSet.getString("name"))
						.teamName(resultSet.getString("name"))
						.build();

					teamList.add(teamRespDTO);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teamList;
	}
}
