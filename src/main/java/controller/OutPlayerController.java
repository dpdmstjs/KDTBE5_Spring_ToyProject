package controller;

import service.OutPlayerService;
import util.annotation.RequestMapping;

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
	public String createOutPlayer(int playerId, String reason) {
		return outPlayerService.createOutPlayer(playerId, reason);
	}
}
