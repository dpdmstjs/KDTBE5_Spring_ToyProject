package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import dto.TeamRespDTO;
import lombok.RequiredArgsConstructor;
import model.Team;

@RequiredArgsConstructor
public class TeamDAO {
	private static TeamDAO teamDAO;
	private final Connection connection;

<<<<<<< HEAD
<<<<<<< HEAD
	private TeamDAO() {
		connection = DBConnection.getInstance();
	}

	public static TeamDAO getInstance() {
		if (teamDAO == null) {
			teamDAO = new TeamDAO();
		}
		return teamDAO;
	}

=======
>>>>>>> b41663cb676eedfad657182463b5d0878a615165
	public int createTeam(int stadiumId, String name) {
		if (!isStadiumId(stadiumId)) {
			throw new IllegalArgumentException("없는 경기장 입니다.");
		}

		String query = "INSERT INTO team(name, created_at) VALUES (?, ?, now())";
<<<<<<< HEAD
=======
	public int createTeam(int stadiumId, String name) {
		String query = "INSERT INTO team(stadium_id, name, created_at) VALUES (?, ?, now())";
>>>>>>> main
=======
>>>>>>> b41663cb676eedfad657182463b5d0878a615165
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
<<<<<<< HEAD
<<<<<<< HEAD
		String query = "SELECT team.id, stadium.name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = statement.executeQuery()) {
=======
		String query = "SELECT team.id, stadium.name AS stadium_name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(query)) {
>>>>>>> main
=======
		String query = "SELECT team.id, stadium.name AS stadium_name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = statement.executeQuery()) {
>>>>>>> b41663cb676eedfad657182463b5d0878a615165
				while (resultSet.next()) {
					TeamRespDTO teamRespDTO = TeamRespDTO.builder()
						.teamId(resultSet.getInt("id"))
						.stadiumName(resultSet.getString("stadium.name"))
						.teamName(resultSet.getString("team.name"))
						.build();
					teamList.add(teamRespDTO);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teamList;
	}

	private boolean isStadiumId(int stadiumId) {
		String query = "SELECT COUNT(id) FROM stadium WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, stadiumId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt(1);
					if (count > 0) {
						return true;
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return false;
	}

	public boolean isExistTeam(int teamId) {
		String query = "SELECT count(id) FROM team WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, teamId);

			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();

				if (resultSet.getInt(1) > 0) {
					return true;
				}

				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}