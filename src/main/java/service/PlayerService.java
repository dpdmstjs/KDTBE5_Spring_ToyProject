package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import dto.PositionRespDto;
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

	public String addPlayer(Integer teamId, String name, Position position) {
		try {
			String result = playerDao.insertPlayer(teamId, name, position);

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

		return playersByTeamToString(playerList);
	}

	public String getPlayersByPosition() {
		PositionRespDto positionRespDto = playerDao.selectPlayersByPosition();
		Map<Position, List<String>> positionMap = positionRespDto.getPositionMap();
		List<String> teamList = positionRespDto.getTeamList();
		return PlayersByPositionToString(positionMap, teamList);
	}

	private String PlayersByPositionToString(Map<Position, List<String>> positionMap, List<String> teamList) {
		StringBuilder builder = new StringBuilder();
		System.out.printf("%-10s", "포지션");
		for (String team : teamList) {
			System.out.printf("%-10s", team);
		}
		System.out.println();

		for (Position position : positionMap.keySet()) {

			System.out.printf("%-10s", position.getName());
			List<String> teamPlayerList = positionMap.get(position);
			for (String team : teamList) {
				String playerName = teamPlayerList.get(teamList.indexOf(team));
				System.out.printf("%-10s", playerName);
			}
			System.out.println();
		}
		return builder.toString();
	}

	private String playersByTeamToString(List<Player> playerList) {
		StringBuilder builder = new StringBuilder();
		builder.append("==========================\n");
		builder.append("선수명\t포지션\t등록일\n");
		builder.append("==========================\n");

		for (Player player : playerList) {
			builder.append(
				player.getName() + "\t" + player.getPosition().getName() + "\t" + player.getCreatedAt() + "\n");
		}
		return builder.toString();
	}
}