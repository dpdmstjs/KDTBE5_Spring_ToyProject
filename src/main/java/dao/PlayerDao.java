package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Position;
import db.DBConnection;
import dto.PositionRespDto;
import exception.DuplicateKeyException;
import exception.ElementNotFoundException;
import model.Player;

public class PlayerDao {
	private static PlayerDao playerDao;
	private final Connection connection;

	private PlayerDao() {
		this.connection = DBConnection.getInstance();
	}

	public static PlayerDao getInstance() {
		if (playerDao == null) {
			playerDao = new PlayerDao();
		}
		return playerDao;
	}

	private TeamDao teamDao = new TeamDao(DBConnection.getInstance());

	public int insertPlayer(int teamId, String name, Position position) throws SQLException {
		if (!teamDao.isExistTeam(teamId))
			throw new ElementNotFoundException("해당 팀은 존재하지 않습니다.");

		if (isExistTeamPosition(teamId, position)) {
			throw new DuplicateKeyException("해당 팀에 동일한 포지션의 선수가 존재합니다.");
		}

		String sql = "insert into player(team_id, name, position) values (?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, teamId);
		statement.setString(2, name);
		statement.setString(3, position.getName());

		int rowCount = statement.executeUpdate();

		if (rowCount <= 0)
			throw new SQLException("선수등록 중 오류가 발생했습니다.");

		return rowCount;
	}

	public List<Player> selectPlayersByTeam(int teamId) {
		List<Player> players = new ArrayList<>();

		String sql = "select * from player where team_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, teamId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Player player = buildPlayerFromResultSet(resultSet);
					players.add(player);
				}
			}
		} catch (SQLException e) {
			System.out.println("선수 목록 조회 중 오류가 발생했습니다.");
		}

		return players;
	}

	public int updatePlayerTeamId(int id, int teamId) {
		String sql = "update player set team_id = ? where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			if (teamId == 0) {
				statement.setNull(1, Types.INTEGER);
			} else {
				statement.setInt(1, teamId);
			}

			statement.setInt(2, id);

			int rowCount = statement.executeUpdate();

			return rowCount;
		} catch (SQLException e) {
			System.out.println("선수의 소속팀 수정 중 오류가 발생했습니다.");
		}

		return -1;
	}

	public Player selectPlayerById(int id) {
		String sql = "select * from player where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return buildPlayerFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	public PositionRespDto selectPositions() {
		List<String> teams = teamDao.getTeamNames();
		Map<Position, List<String>> positions = new HashMap<>();

		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT team_players.position, ");

			for (String team : teams) {
				builder.append("MAX(IF(team_players.team_name = ?, team_players.player_name, ' ')) AS ");
				builder.append(team);
				builder.append(", ");
			}

			builder.delete(builder.length() - 2, builder.length());
			builder.append(" FROM ( ");
			builder.append("SELECT p.name AS player_name, p.position, t.name AS team_name ");
			builder.append("FROM team t ");
			builder.append("JOIN player p ON p.team_id = t.id ");
			builder.append(") team_players ");
			builder.append("GROUP BY team_players.position");

			PreparedStatement statement = connection.prepareStatement(builder.toString());

			int index = 1;
			for (String team : teams) {
				statement.setString(index++, team);
			}

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Position position = Position.findByName(resultSet.getString("position"));
					List<String> teamPlayers = new ArrayList<>();

					for (String team : teams) {
						String teamName = resultSet.getString(team);
						teamPlayers.add(teamName);
					}
					positions.put(position, teamPlayers);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return PositionRespDto.builder()
			.positions(positions)
			.teams(teams)
			.build();
	}

	public List<String> selectTeams() {
		List<String> teams = new ArrayList<>();
		try {
			String query = "SELECT name FROM team";
			PreparedStatement statement = connection.prepareStatement(query);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					teams.add(resultSet.getString("name"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return teams;
	}

	private boolean isExistTeamPosition(int teamId, Position position) throws SQLException {
		String query = "select count(*) from player where team_id = ? and position = ?";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, teamId);
		statement.setString(2, position.getName());

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			if (resultSet.getInt(1) > 0)
				return true;
		}

		return false;
	}

	private Player buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		int teamId = resultSet.getInt("team_id");
		String name = resultSet.getString("name");
		Position position = Position.findByName(resultSet.getString("position"));
		Timestamp createdAt = resultSet.getTimestamp("created_at");

		return Player.builder()
			.id(id)
			.teamId(teamId)
			.name(name)
			.position(position)
			.createdAt(createdAt)
			.build();

	}
}