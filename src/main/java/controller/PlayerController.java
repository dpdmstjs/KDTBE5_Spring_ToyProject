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

		String playerListtoString = playerService.getPlayersByTeam(teamId);

		if (playerListtoString == null)
			throw new ElementNotFoundException("등록된 선수가 없습니다.");

		return playerListtoString;
	}

	@RequestMapping(name = "선수등록")
	public String createPlayer(int teamId, String name, String position) {
		if (teamId <= 0 || name.equals(null) || name.isEmpty() || position.equals(null) || position.isEmpty())
			throw new ArgumentMismatchException("입력 값을 확인해주세요.");

		return playerService.createPlayer(teamId, name, Position.findByName(position));
	}
}
