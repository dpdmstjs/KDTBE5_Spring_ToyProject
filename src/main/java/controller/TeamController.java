package controller;

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

	@RequestMapping(name="팀등록")
	public String createTeam(int stadiumId, String name) {
		if (stadiumId <= 0 || name.equals(null) || name.isEmpty()) {
			throw new ArgumentMismatchException("입력 값을 확인해주세요.");
		}
		return teamService.addTeam(stadiumId, name);
	}

	@RequestMapping(name="팀목록")
	public String teams() {
		String formattedTeams = teamService.getTeams();

		if (formattedTeams == null)
			throw new ElementNotFoundException("등록된 야구장이 없습니다.");

		return formattedTeams ;
	}
}