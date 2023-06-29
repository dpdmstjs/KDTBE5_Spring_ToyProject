package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

	public String createOutPlayer(int playerId, String reason) {
		try {
			if (playerDao.selectPlayerById(playerId) == null)
				throw new ElementNotFoundException("입력하신 ID에 해당하는 선수가 존재하지 않습니다.");

			connection.setAutoCommit(false);

			int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
			int playerResult = playerDao.updatePlayerTeamId(playerId, 0);

			if (outPlayerResult < 1 || playerResult < 1) {
				connection.rollback();
				throw new SQLException("퇴출등록 중 오류가 발생했습니다. 다시 시도해주세요.");
			}

			connection.commit();
			return "성공";

		} catch (SQLException e) {
			return "퇴출등록 중 오류가 발생했습니다. 다시 시도해주세요.";
		}
	}

	public String getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.selectOutPlayers();

		if (outPlayerList == null || outPlayerList.size() == 0)
			throw new ElementNotFoundException("등록된 퇴출선수가 없습니다.");

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
