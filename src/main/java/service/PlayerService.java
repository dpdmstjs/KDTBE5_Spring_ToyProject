package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import exception.DuplicateKeyException;
import exception.ElementNotFoundException;
import model.Player;

public class PlayerService {
	private PlayerDao playerDao;
	private Connection connection;

	public PlayerService() {
		this.playerDao = PlayerDao.getInstance();
		this.connection = DBConnection.getInstance();
	}

	public String createPlayer(Integer teamId, String name, Position position) {
		try {
			String result = playerDao.createPlayer(teamId, name, position);

			return result;
		} catch (ElementNotFoundException | DuplicateKeyException | SQLException e) {
			return e.getMessage();
		}
	}

	public void getPlayer(int id) {
		Player player = playerDao.selectPlayerById(id);

		System.out.println(player);
	}

	public String getPlayersByTeam(int teamId) {
		List<Player> playerList = playerDao.selectPlayersByTeam(teamId);

		if (playerList == null || playerList.size() == 0)
			throw new ElementNotFoundException("해당 팀에 선수가 존재하지 않습니다.");

		return listToString(playerList);
	}

	private String listToString(List<Player> playerList) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============================================\n");
		builder.append("선수명\t포지션\t등록일\n");
		builder.append("=============================================\n");

		for (Player player : playerList) {
			builder.append(player);
		}

		return builder.toString();
	}
}
