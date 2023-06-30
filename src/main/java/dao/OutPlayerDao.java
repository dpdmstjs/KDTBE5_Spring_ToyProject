package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constant.Position;
import db.DBConnection;
import dto.OutPlayerRespDto;

public class OutPlayerDao {
	private static OutPlayerDao outPlayerDao;
	private final Connection connection;

	private OutPlayerDao() {
		connection = DBConnection.getInstance();
	}

	public static OutPlayerDao getInstance() {
		if (outPlayerDao == null) {
			outPlayerDao = new OutPlayerDao();
		}

		return outPlayerDao;
	}

	public int insertOutPlayer(int playerId, String reason) throws SQLException {
		String sql = "insert into out_player(player_id, reason) values (?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, playerId);
		statement.setString(2, reason);

		int rowCount = statement.executeUpdate();

		return rowCount;
	}

	public List<OutPlayerRespDto> selectOutPlayers() throws SQLException {
		List<OutPlayerRespDto> outPlayers = new ArrayList<>();

		String sql = "SELECT p.id, p.name, p.position, IFNULL(o.reason, '') AS reason, o.created_at " +
			"FROM player p LEFT OUTER JOIN out_player o ON p.id = o.player_id";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			OutPlayerRespDto outPlayerRespDto = OutPlayerRespDto.builder()
				.playerId(resultSet.getInt("id"))
				.name(resultSet.getString("name"))
				.position(Position.findByName(resultSet.getString("position")))
				.reason(resultSet.getString("reason"))
				.outCreatedAt(resultSet.getTimestamp("created_at"))
				.build();

			outPlayers.add(outPlayerRespDto);
		}

		return outPlayers;
	}
}
