package controller;

import service.TeamService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class TeamController {
	private TeamService teamService;

	public TeamController() {
		this.teamService = new TeamService();
	}

	@RequestMapping(name="팀등록")
	public String createTeam(int stadiumId, String name) {
		return teamService.addTeam(stadiumId, name);
	}

	@RequestMapping(name="팀목록")
	public String teamList() {
		String teamListtoString = teamService.getTeamList();
		return teamListtoString;
	}
}