package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import dto.TeamRespDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamDao {
	private static TeamDao teamDAO;
	private final Connection connection;

	private TeamDao() {
		connection = DBConnection.getInstance();
	}

	public static TeamDao getInstance() {
		if (teamDAO == null) {
			teamDAO = new TeamDao();
		}
		return teamDAO;
	}

	public int createTeam(int stadiumId, String name) {
		if (!isStadiumId(stadiumId)) {
			throw new IllegalArgumentException("없는 경기장 입니다.");
		}

		String query = "INSERT INTO team(name, created_at) VALUES (?, ?, now())";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, stadiumId);
			statement.setString(2, name);

			int rowCount = statement.executeUpdate();

			return rowCount;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<TeamRespDto> selectTeamList() {
		List<TeamRespDto> teamList = new ArrayList<>();

		String query = "SELECT team.id, stadium.name, team.name " +
			"FROM team " +
			"JOIN stadium ON team.stadium_id = stadium.id";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					TeamRespDto teamRespDTO = TeamRespDto.builder()
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