package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.TeamRespDTO;
import model.Stadium;
import model.Team;

public class TeamDAO {
	private Connection connection;

	public TeamDAO(Connection connection) {
		this.connection = connection;
	}

	public Stadium createTeam(int id, int stadiumId, String name) throws SQLException {
		String query = "INSERT INTO team(id, stadium_id, name, created_at) VALUES (?, ?, ?, now())";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, stadiumId);
			statement.setString(3, name);
			statement.executeUpdate();
		}
		return null;
	}

	public List<TeamRespDTO> getTeamList() throws SQLException {
		List<TeamRespDTO> teamList = new ArrayList<>();
		String query = "SELECT team.id, stadium.name AS stadium_name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(query)) {
				while (resultSet.next()) {
					TeamRespDTO teamRespDTO = buildTeamRespDTOFromResultSet(resultSet);
					teamList.add(teamRespDTO);
				}
			}
		}
		return teamList;
	}

	public void printTeamList(List<TeamRespDTO> teamList) {
		for (TeamRespDTO teamRespDTO : teamList) {
			System.out.println("Team ID: " + teamRespDTO.getTeamId());
			System.out.println("Team Name: " + teamRespDTO.getTeamName());
			System.out.println("Stadium Name: " + teamRespDTO.getStadiumName());
		}
	}

	private TeamRespDTO buildTeamRespDTOFromResultSet(ResultSet resultSet) throws SQLException {
		int teamId = resultSet.getInt("id");
		String teamName = resultSet.getString("name");
		String stadiumName = resultSet.getString("name");

		return TeamRespDTO.builder()
			.teamId(teamId)
			.stadiumName(stadiumName)
			.teamName(teamName)
			.build();
	}
}
