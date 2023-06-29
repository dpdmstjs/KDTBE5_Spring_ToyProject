package service;

import java.sql.Connection;
import java.util.List;

import dao.StadiumDAO;
import db.DBConnection;
import model.Player;
import model.Stadium;

public class StadiumService {
	private StadiumDAO stadiumDAO;
	private Connection connection;

	public StadiumService(StadiumDAO stadiumDAO) {
		this.stadiumDAO = stadiumDAO;
		this.connection = DBConnection.getInstance();
	}

	public String addStadium(String name) {
		int result = stadiumDAO.createStadium(name);

		if (result > 0)
			return "성공";

		return "실패";
	}

	public Stadium getStadiumById(int id) {
		Stadium stadium = stadiumDAO.selectStadiumById(id);

		return stadium;
	}

	public String getStadiumList() {
		List<Stadium> stadiumList = stadiumDAO.selectStadiumList();

		if (stadiumList == null) {
			return null;
		}
		return listToString(stadiumList);
	}

	private String listToString(List<Stadium> stadiumList) {
		StringBuilder builder = new StringBuilder();
		builder.append("=============\n");
		builder.append("야구장명\n");
		builder.append("============\n");

		for (Stadium stadium : stadiumList) {
			builder.append(
				stadium.getName() + "\n");
		}

		return builder.toString();
	}

}