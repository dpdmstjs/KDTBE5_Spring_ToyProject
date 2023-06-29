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
		this.stadiumDAO = StadiumDAO.getInstance();
		this.teamDAO = TeamDAO.getInstance();
		this.connection = DBConnection.getInstance();
	}

	public TeamService(StadiumDAO stadiumDAO, TeamDAO teamDAO, Connection connection) {
		this.stadiumDAO = stadiumDAO;
		this.teamDAO = teamDAO;
		this.connection = DBConnection.getInstance();
	}

	public String addTeam(int stadiumId, String name) {
		int result = teamDAO.createTeam(stadiumId, name);

		if (result > 0)
			return "성공";

		return "실패";
	}

	public String getTeamList() {
		List<TeamRespDTO> teamList = teamDAO.selectTeamList();

		if (teamList == null || teamList.size() < 0) {
			return null;
		}

		return listToString(teamList);
	}

	private String listToString(List<TeamRespDTO> teamList) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============\n");
		builder.append("  팀 목록\n");
		builder.append("=============\n");

		for (TeamRespDTO teamRespDTO : teamList) {
			builder.append(teamRespDTO.getTeamId() + "\t" +
				teamRespDTO.getStadiumName() + "\t" +
				teamRespDTO.getTeamName() + "\n");
		}

		return builder.toString();
	}


}