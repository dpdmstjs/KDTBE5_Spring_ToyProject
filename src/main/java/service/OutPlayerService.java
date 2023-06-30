package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import constant.ExceptionMessage;
import dao.OutPlayerDao;
import dao.PlayerDao;
import db.DBConnection;
import dto.OutPlayerRespDto;
import exception.ElementNotFoundException;

public class OutPlayerService {
	private PlayerDao playerDao;
	private OutPlayerDao outPlayerDao;
	private Connection connection;

	public OutPlayerService() {
		this.playerDao = PlayerDao.getInstance();
		this.outPlayerDao = OutPlayerDao.getInstance();
		this.connection = DBConnection.getInstance();
	}

	public String addOutPlayer(int playerId, String reason) throws SQLException {
		if (playerDao.selectPlayerById(playerId) == null)
			throw new ElementNotFoundException(ExceptionMessage.ERR_MSG_PLAYER_NOT_FOUND.getMessage());

		connection.setAutoCommit(false);

		int outPlayerResult = outPlayerDao.insertOutPlayer(playerId, reason);
		int playerResult = playerDao.updatePlayerTeamId(playerId, 0);

		if (outPlayerResult < 1 || playerResult < 1) {
			connection.rollback();
			throw new SQLException();
		}

		connection.commit();
		return "성공";
	}

	public String getOutPlayers() throws SQLException {
		List<OutPlayerRespDto> outPlayers = outPlayerDao.selectOutPlayers();

		if (outPlayers == null || outPlayers.size() == 0)
			throw new ElementNotFoundException(ExceptionMessage.ERR_MSG_OUTPLAYERS_NOT_FOUND.getMessage());

		return outPlayersToString(outPlayers);
	}

	private String outPlayersToString(List<OutPlayerRespDto> outPlayers) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============================================\n");
		builder.append("ID\t선수명\t포지션\t이유\t퇴출일\n");
		builder.append("=============================================\n");

		for (OutPlayerRespDto outPlayer : outPlayers) {
			builder.append(outPlayer);
		}

		return builder.toString();
	}
}
