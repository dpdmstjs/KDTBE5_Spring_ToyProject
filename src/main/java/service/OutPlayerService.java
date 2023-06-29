package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.OutPlayerDao;
import dao.PlayerDao;
import db.DBConnection;
import dto.OutPlayerRespDto;

public class OutPlayerService {
	private PlayerDao playerDao;
	private OutPlayerDao outPlayerDao;
	private Connection connection;

	public OutPlayerService() {
		this.playerDao = new PlayerDao(DBConnection.getInstance());
		this.outPlayerDao = new OutPlayerDao(DBConnection.getInstance());
		this.connection = DBConnection.getInstance();
	}

	public OutPlayerService(PlayerDao playerDao, OutPlayerDao outPlayerDao) {
		this.playerDao = playerDao;
		this.outPlayerDao = outPlayerDao;
		this.connection = DBConnection.getInstance();
	}

	public String createOutPlayer(int playerId, String reason) throws SQLException {
		connection.setAutoCommit(false);
		int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
		int playerResult = playerDao.updatePlayerTeamId(playerId, 0);

		if (outPlayerResult == 1 && playerResult == 1) {
			connection.commit();
			return "성공";
		}

		connection.rollback();
		return "실패";
	}

	public String getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.selectOutPlayers();

		if (outPlayerList == null)
			return null;

		return listTostring(outPlayerList);
	}

	private String listTostring(List<OutPlayerRespDto> outPlayerList) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============================================\n");
		builder.append("순번\t선수명\t포지션\t이유\t퇴출일\n");
		builder.append("=============================================\n");

		for (OutPlayerRespDto outPlayer : outPlayerList) {
			builder.append(outPlayer);
		}

		return builder.toString();
	}
}
