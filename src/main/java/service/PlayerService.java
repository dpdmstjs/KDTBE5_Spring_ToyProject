package service;

import java.sql.Connection;
import java.util.List;

import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import model.Player;
import util.annotation.RequestMapping;
import util.annotation.Service;

@Service
public class PlayerService {
	private PlayerDao playerDao;
	private Connection connection;

	public PlayerService() {
		this.playerDao = new PlayerDao(DBConnection.getInstance());
		this.connection = DBConnection.getInstance();
	}

	public PlayerService(PlayerDao playerDao) {
		this.playerDao = playerDao;
		this.connection = DBConnection.getInstance();
	}

	@RequestMapping(name = "선수등록")
	public void createPlayer(Integer teamId, String name, String position) {
		int result = playerDao.createPlayer(teamId, name, Position.findByName(position));

		if (result > 0) {
			System.out.println("성공");
			return;
		}

		System.out.println("실패");
	}

	@RequestMapping(name = "선수조회")
	public void getPlayer(int id) {
		Player player = playerDao.selectPlayerById(id);

		System.out.println(player);
	}

	@RequestMapping(name = "선수목록")
	public void getPlayersByTeam(int teamId) {
		List<Player> playerList = playerDao.selectPlayersByTeam(teamId);

		System.out.println(playerList.toString());
	}
}
