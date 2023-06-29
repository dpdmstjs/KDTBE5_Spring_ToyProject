package controller;

import dao.StadiumDAO;
import db.DBConnection;
<<<<<<< HEAD
import exception.ArgumentMismatchException;
import exception.ElementNotFoundException;
=======
>>>>>>> main
import service.StadiumService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class StadiumController {
	private StadiumService stadiumService;

	public StadiumController() {
		this.stadiumService = new StadiumService(new StadiumDAO(DBConnection.getInstance()));
	}

	@RequestMapping(name = "야구장등록")
	public String createStadium(String name) {

		if (name.equals(null) || name.isEmpty())
			throw new ArgumentMismatchException("입력 값을 확인해주세요.");

		return stadiumService.addStadium(name);
	}

<<<<<<< HEAD
	@RequestMapping(name = "야구장목록")
=======
	@RequestMapping(name = "아구장목록")
>>>>>>> main
	public String stadiumList() {
		String stadiumListtoString = stadiumService.getStadiumList();

		if (stadiumListtoString == null)
			throw new ElementNotFoundException("등록된 야구장이 없습니다.");

		return stadiumListtoString;
	}


}