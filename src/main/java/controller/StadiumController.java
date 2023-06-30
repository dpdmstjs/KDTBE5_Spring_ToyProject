package controller;

import constant.ExceptionMessage;
import dao.StadiumDao;
import db.DBConnection;
import exception.ArgumentMismatchException;
import exception.ElementNotFoundException;
import service.StadiumService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class StadiumController {
	private StadiumService stadiumService;

	public StadiumController() {
		this.stadiumService = new StadiumService(new StadiumDao(DBConnection.getInstance()));
	}

	@RequestMapping(name = "야구장등록")
	public String createStadium(String name) {

		if (name.equals(null) || name.isEmpty()) {
			throw new ArgumentMismatchException();
		}

		return stadiumService.addStadium(name);
	}

	@RequestMapping(name = "야구장목록")
	public String getStadiums() {
		try {
			String formattedStadiums = stadiumService.getStadiums();

			return formattedStadiums;
		} catch (ElementNotFoundException e) {
			return ExceptionMessage.ERR_MSG_STADIUMS_NOT_FOUND.getMessage();
		}
	}


}