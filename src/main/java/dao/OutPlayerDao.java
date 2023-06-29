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

	public int createOutPlayer(int playerId, String reason) {
		String sql = "insert into out_player(player_id, reason) values (?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, playerId);
			statement.setString(2, reason);

			int rowCount = statement.executeUpdate();

			return rowCount;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<OutPlayerRespDto> selectOutPlayers() {
		List<OutPlayerRespDto> outPlayerList = new ArrayList<>();

		String sql = "select p.id, p.name, p.position, o.reason, o.created_at" +
			" from out_player o left outer join player p on o.player_id = p.id";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					OutPlayerRespDto outPlayerRespDto = OutPlayerRespDto.builder()
						.playerId(resultSet.getInt("id"))
						.name(resultSet.getString("name"))
						.position(Position.findByName(resultSet.getString("position")))
						.reason(resultSet.getString("reason"))
						.outCreatedAt(resultSet.getTimestamp("created_at"))
						.build();

					outPlayerList.add(outPlayerRespDto);
				}
			}
		} catch (SQLException e) {
			System.out.println("퇴출선수 목록 조회 중 오류가 발생했습니다.");
		}

		return outPlayerList;
	}
}
