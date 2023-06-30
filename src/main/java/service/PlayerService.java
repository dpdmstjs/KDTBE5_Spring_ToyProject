package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import constant.ExceptionMessage;
import constant.Position;
import dao.PlayerDao;
import db.DBConnection;
import dto.PositionRespDto;
import exception.ElementNotFoundException;
import model.Player;

public class PlayerService {
	private PlayerDao playerDao;
	private Connection connection;

	public PlayerService() {
		this.playerDao = PlayerDao.getInstance();
		this.connection = DBConnection.getInstance();
	}

	public String addPlayer(Integer teamId, String name, Position position) throws SQLException {
		int result = playerDao.insertPlayer(teamId, name, position);

		if (result <= 0)
			throw new SQLException();

		return "성공";
	}

	public String getPlayersByTeam(int teamId) throws SQLException {
		List<Player> players = playerDao.selectPlayersByTeam(teamId);

		if (players == null || players.size() == 0)
			throw new ElementNotFoundException(ExceptionMessage.ERR_MSG_PLAYERS_NOT_FOUND.getMessage());

		return playersByTeamToString(players);
	}

	public String getPlayersByPosition() {
		PositionRespDto positionRespDto = playerDao.selectPositions();
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