package controller;

import java.sql.SQLException;

import service.OutPlayerService;
import util.annotation.Controller;
import util.annotation.RequestMapping;

@Controller
public class OutPlayerController {
	private OutPlayerService outPlayerService;

	public OutPlayerController() {
		this.outPlayerService = new OutPlayerService();
	}

	@RequestMapping(name = "퇴출목록")
	public String getOutPlayerList() {
		return outPlayerService.getOutPlayerList();
	}

	@RequestMapping(name = "퇴출등록")
	public String createOutPlayer(int playerId, String reason) throws SQLException {
		return outPlayerService.createOutPlayer(playerId, reason);
	}
}