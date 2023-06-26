package service;

import java.sql.Connection;

import dao.OutPlayerDao;
import dao.PlayerDao;

public class OutPlayerService {
	private PlayerDao playerDao;
	private OutPlayerDao outPlayerDao;
	private Connection connection;

	public String createOutPlayer(int playerId, String reason) {
		int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
		int playerResult = playerDao.updateTeamId(playerId, 0);

		if (outPlayerResult == 1 && playerResult == 1)
			return "성공";

		return "실패";
	}
}
