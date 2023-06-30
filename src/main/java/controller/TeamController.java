package controller;

import constant.ExceptionMessage;
import exception.ArgumentMismatchException;
import exception.ElementNotFoundException;
import service.TeamService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class TeamController {
	private TeamService teamService;

	public TeamController() {
		this.teamService = new TeamService();
	}

	@RequestMapping(name = "팀등록")
	public String createTeam(int stadiumId, String name) {
		if (stadiumId <= 0 || name.equals(null) || name.isEmpty()) {
			throw new ArgumentMismatchException();
		}
		return teamService.addTeam(stadiumId, name);
	}

	@RequestMapping(name = "팀목록")
	public String teams() {
		try {
			String formattedTeams = teamService.getTeams();
			return formattedTeams;
		} catch (ElementNotFoundException e) {
			return e.getMessage(ExceptionMessage.ERR_MSG_TEAM_NOT_FOUND);
		}

	}
}