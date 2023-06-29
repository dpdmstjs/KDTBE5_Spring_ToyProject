package controller;

import constant.Position;
import exception.ArgumentMismatchException;
import exception.ElementNotFoundException;
import service.PlayerService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class PlayerController {
	private PlayerService playerService;

	public PlayerController() {
		this.playerService = new PlayerService();
	}

	@RequestMapping(name = "선수목록")
	public String getPlayersByTeam(int teamId) {
		try {
			return playerService.getPlayersByTeam(teamId);
		} catch (ElementNotFoundException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(name = "선수등록")
	public String createPlayer(int teamId, String name, String position) {
		if (teamId <= 0 || name.equals(null) || name.isEmpty() || position.equals(null) || position.isEmpty())
			throw new ArgumentMismatchException("입력 값을 확인해주세요.");

		try {
			return playerService.createPlayer(teamId, name, Position.findByName(position));
		} catch (ElementNotFoundException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(name = "포지션별목록")
	public String positions() {
		String formattedPositions = playerService.getPositions();

		if (formattedPositions == null) {
			throw new ElementNotFoundException("포지션별 조회 목록이 없습니다.");
		}
		return formattedPositions;
	}
}