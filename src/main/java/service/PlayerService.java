package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import dto.PositionRespDto;
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
			int result = playerDao.insertPlayer(teamId, name, position);

			return "성공";

		} catch (SQLException e) {
			return e.getMessage();
		}
	}

	public void getPlayer(int id) {
		Player player = playerDao.selectPlayerById(id);

		System.out.println(player);
	}

	public String getPlayersByTeam(int teamId) {
		List<Player> players = playerDao.selectPlayersByTeam(teamId);

		if (players == null || players.size() == 0)
			return null;

		return playersByTeamToString(players);
	}

	public String getPlayersByPosition() {
		PositionRespDto positionRespDto = playerDao.selectPlayersByPosition();
		Map<Position, List<String>> positionMap = positionRespDto.getPositions();
		List<String> teamList = positionRespDto.getTeams();
		return PlayersByPositionToString(positionMap, teamList);
	}

	private String PlayersByPositionToString(Map<Position, List<String>> positions, List<String> teams) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%-10s", "포지션"));
		for (String team : teams) {
			builder.append(String.format("%-10s", team));
		}
		builder.append("\n");

		for (Position position : positions.keySet()) {

			builder.append(String.format("%-10s", position.getName()));
			List<String> teamPlayers = positions.get(position);
			for (String team : teams) {
				String playerName = teamPlayers.get(teams.indexOf(team));
				builder.append(String.format("%-10s", playerName));
			}
			builder.append("\n");
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
				player);
		}
		return builder.toString();
	}
}