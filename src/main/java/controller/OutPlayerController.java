package controller;

import java.sql.SQLException;

import constant.ExceptionMessage;
import exception.ElementNotFoundException;
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
	public String getOutPlayers() {
		try {
			String formattedOutPlayers = outPlayerService.getOutPlayers();

			return formattedOutPlayers;
		} catch (ElementNotFoundException e) {
			return e.getMessage();
		} catch (SQLException e) {
			return ExceptionMessage.ERR_MSG_SQL.getMessage();
		}
	}

	@RequestMapping(name = "퇴출등록")
	public String createOutPlayer(int playerId, String reason) {
		try {
			return outPlayerService.addOutPlayer(playerId, reason);
		} catch (ElementNotFoundException e) {
			return e.getMessage();
		} catch (SQLException e) {
			return ExceptionMessage.ERR_MSG_SQL.getMessage();
		}
	}
}
