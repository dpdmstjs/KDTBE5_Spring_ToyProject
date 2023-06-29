package service;

import java.sql.Connection;
import java.util.List;
import dao.StadiumDao;
import dao.TeamDao;
import db.DBConnection;
import dto.TeamRespDto;

public class TeamService {
	private TeamDao teamDAO;
	private StadiumDao stadiumDAO;
	private Connection connection;

	public TeamService() {
		this.teamDAO = TeamDao.getInstance();
		this.stadiumDAO = StadiumDao.getInstance();
		this.connection = DBConnection.getInstance();
	}

	public TeamService(StadiumDao stadiumDAO, TeamDao teamDAO) {
		this.stadiumDAO = stadiumDAO;
		this.teamDAO = teamDAO;
		this.connection = DBConnection.getInstance();
	}

	public String addTeam(int stadiumId, String name) {
		int result = teamDAO.createTeam(stadiumId, name);
		if (result < 0) {
			return "실패";
		}
		return "성공";
	}

	public String getTeamList() {
		List<TeamRespDto> teamList = teamDAO.selectTeamList();
		if (teamList == null || teamList.size() < 0) {
			return null;
		}
		return listToString(teamList);
	}

	private String listToString(List<TeamRespDto> teamList) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============\n");
		builder.append("   팀 목록  \n");
		builder.append("=============\n");

		for (TeamRespDto teamRespDTO : teamList) {
			builder.append(teamRespDTO.getTeamId() + "\t" +
				teamRespDTO.getStadiumName() + "\t" +
				teamRespDTO.getTeamName() + "\n");
		}

		return builder.toString();
	}


}