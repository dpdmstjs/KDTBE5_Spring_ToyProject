package service;

import java.sql.Connection;
import java.util.List;

import dao.StadiumDAO;
import dao.TeamDAO;
import db.DBConnection;
import dto.TeamRespDTO;

public class TeamService {
	private StadiumDAO stadiumDAO;
	private TeamDAO teamDAO;
	private Connection connection;

	public TeamService() {
		this.stadiumDAO = new StadiumDAO(DBConnection.getInstance());
		this.teamDAO = new TeamDAO(DBConnection.getInstance());
		this.connection = DBConnection.getInstance();
	}

	public String addTeam(int stadiumId, String name) {
		int result = teamDAO.createTeam(stadiumId, name);

		if (result > 0)
			return "성공";

		return "실패";
	}

	public List<TeamRespDTO> getTeamList() {
		List<TeamRespDTO> teamList = teamDAO.selectTeamList();

		return teamList;
	}

}
