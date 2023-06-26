package service;

import java.sql.Connection;
import java.util.List;

import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import model.Player;

public class PlayerService {
	private PlayerDao playerDao;
	private Connection connection;

	public PlayerService(PlayerDao playerDao) {
		this.playerDao = playerDao;
		this.connection = DBConnection.getInstance();
	}

	public String createPlayer(int teamId, String name, Position positon) {
		int result = playerDao.createPlayer(teamId, name, positon);

		if (result > 0)
			return "성공";

		return "실패";
	}

	public Player getPlayer(int id) {
		Player player = playerDao.getPlayerById(id);

		return player;
	}

	public List<Player> getPlayersByTeam(int teamId) {
		List<Player> playerList = playerDao.getPlayersByTeam(teamId);

		return playerList;
	}
}
