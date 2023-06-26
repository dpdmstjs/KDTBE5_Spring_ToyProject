package service;

import java.sql.Connection;
import java.util.List;

import dao.OutPlayerDao;
import dao.PlayerDao;
import dto.OutPlayerRespDto;

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

	public List<OutPlayerRespDto> getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.getOutPlayers();

		return outPlayerList;
	}
}
