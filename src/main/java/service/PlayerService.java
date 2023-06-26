package service;

import java.sql.Connection;

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

	public Player createPlayer(int teamId, String name, Position positon) {
		Player player = playerDao.createPlayer(teamId, name, positon);

		return player;
	}

	public Player getPlayer(int id) {
		Player player = playerDao.getPlayerById(id);

		return player;
	}
}
