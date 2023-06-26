package service;

import java.sql.Connection;

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

	public Player getPlayer(int id) {
		Player player = playerDao.getPlayerById(id);

		return player;
	}
}
