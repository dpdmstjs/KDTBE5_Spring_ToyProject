package service;

import java.sql.Connection;
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

	public String createOutPlayer(int playerId, String reason) {
		int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
		int playerResult = playerDao.updateTeamId(playerId, 0);

		if (outPlayerResult == 1 && playerResult == 1) {
			return "성공";
		}

		return "실패";
	}

	public String getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.selectOutPlayers();

		return outPlayerList.toString();
	}
}
