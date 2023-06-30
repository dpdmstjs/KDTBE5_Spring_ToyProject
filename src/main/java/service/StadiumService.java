package service;

import java.sql.Connection;
import java.util.List;

import dao.StadiumDao;
import db.DBConnection;
import model.Stadium;

public class StadiumService {
	private StadiumDao stadiumDAO;
	private Connection connection;

	public StadiumService(StadiumDao stadiumDAO) {
		this.stadiumDAO = stadiumDAO;
		this.connection = DBConnection.getInstance();
	}

	public String addStadium(String name) {
		int result = stadiumDAO.insertStadium(name);

		if (result < 0) {
			return "실패";
		}
		return "성공";
	}

	public Stadium getStadiumById(int id) {
		Stadium stadium = stadiumDAO.selectStadiumById(id);
		return stadium;
	}

	public String getStadiums() {
		List<Stadium> stadiums = stadiumDAO.selectStadiums();

		if (stadiums == null) {
			return null;
		}
		return buildStadiumListString(stadiums);
	}

	private String buildStadiumListString(List<Stadium> stadiums) {
		StringBuilder builder = new StringBuilder();
		builder.append("============\n");
		builder.append("  야구장명\n");
		builder.append("============\n");

		for (Stadium stadium : stadiums) {
			builder.append(
				stadium.getId() + "." + "\t" +
				stadium.getName() + "\n");
		}

		return builder.toString();
	}

}