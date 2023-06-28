package controller;
import service.StadiumService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class StadiumController {
	private StadiumService stadiumService;

	public StadiumController() {
		this.stadiumService = new StadiumService();
	}

	@RequestMapping(name= "야구장등록")
	public String createStadium(String name) {
		return stadiumService.addStadium(name);
	}

	@RequestMapping(name="아구장목록")
	public String stadiumList() {
		return stadiumService.getStadiumList().toString();
	}
}
