package service;

import java.sql.Connection;
import java.util.List;

import dao.OutPlayerDao;
import dao.PlayerDao;
import db.DBConnection;
import dto.OutPlayerRespDto;
import util.annotation.RequestMapping;
import util.annotation.Service;

@Service
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

	@RequestMapping(name = "퇴출등록")
	public void createOutPlayer(int playerId, String reason) {
		int outPlayerResult = outPlayerDao.createOutPlayer(playerId, reason);
		int playerResult = playerDao.updateTeamId(playerId, 0);

		if (outPlayerResult == 1 && playerResult == 1) {
			System.out.println("성공");
			return;
		}

		System.out.println("실패");
	}

	@RequestMapping(name = "퇴출목록")
	public void getOutPlayerList() {
		List<OutPlayerRespDto> outPlayerList = outPlayerDao.selectOutPlayers();

		System.out.println(outPlayerList.toString());
	}
}
