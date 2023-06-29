package service;

import java.sql.Connection;
import java.util.List;

import dao.StadiumDAO;
import db.DBConnection;
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

	public List<Stadium> getStadiumList() {
		List<Stadium> stadiumList = stadiumDAO.selectStadiumList();

		return stadiumList;
	}
}
