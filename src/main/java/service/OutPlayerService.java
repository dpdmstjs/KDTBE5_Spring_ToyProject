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
		this.playerDao = PlayerDao.getInstance();
		this.outPlayerDao = OutPlayerDao.getInstance();
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

		if (outPlayerResult < 1 || playerResult < 1) {
			connection.rollback();
			throw new RuntimeException("퇴출등록 중 오류가 발생했습니다. 다시 시도해주세요.");
		}

		connection.commit();
		return "성공";
	}

	public String getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.selectOutPlayers();

		if (outPlayerList == null || outPlayerList.size() == 0)
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
