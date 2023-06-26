package dto;

import lombok.Builder;
import lombok.Getter;
import model.Stadium;
import model.Team;

@Getter
public class TeamRespDTO {
	private Integer teamId;
	private String stadiumName;
	private String teamName;

	private TeamRespDTO() {
	}

	@Builder
	public TeamRespDTO(Integer teamId, String stadiumName, String teamName) {
		this.teamId = teamId;
		this.stadiumName = stadiumName;
		this.teamName = teamName;
	}
}
