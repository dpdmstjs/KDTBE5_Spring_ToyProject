package service;

import java.sql.Connection;
import java.util.List;

import dao.StadiumDAO;
import dao.TeamDAO;
import dto.TeamRespDTO;

public class TeamService {
	private StadiumDAO stadiumDAO;
	private TeamDAO teamDAO;
	private Connection connection;

	public String addTeam(int teamId, int stadiumId, String name) {
		int result = teamDAO.createTeam(teamId, stadiumId, name);

		if (result > 0)
			return "성공";

		return "실패";
	}

	public List<TeamRespDTO> getTeamList() {
		List<TeamRespDTO> teamList = teamDAO.selectTeamList();

		return teamList;
	}

}
