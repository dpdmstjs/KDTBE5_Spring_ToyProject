package controller;

import java.sql.SQLException;

import constant.ExceptionMessage;
import constant.Position;
import exception.ArgumentMismatchException;
import exception.DuplicateKeyException;
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

		try {
			return teamService.addTeam(stadiumId, name);
		} catch (ElementNotFoundException | DuplicateKeyException e) {
			return e.getMessage();
		} catch (SQLException e) {
			return ExceptionMessage.ERR_MSG_SQL.getMessage();
		}
	}

	@RequestMapping(name = "팀목록")
	public String teams() {
		try {
			String formattedTeams = teamService.getTeams();
			return formattedTeams;
		} catch (ElementNotFoundException e) {
			return ExceptionMessage.ERR_MSG_TEAM_NOT_FOUND.getMessage();
		}

	}
}