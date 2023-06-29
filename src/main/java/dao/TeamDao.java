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

	private StadiumDao stadiumDao = new StadiumDao(DBConnection.getInstance());

	public int createTeam(int stadiumId, String name) {
		if (!stadiumDao.isExistStadiumId(stadiumId)) {
			throw new IllegalArgumentException("없는 경기장 입니다.");
		}

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

	public List<TeamRespDto> selectTeams() {
		List<TeamRespDto> teams = new ArrayList<>();

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
					teams.add(teamRespDTO);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teams;
	}

	protected boolean isExistTeam(int teamId) {
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