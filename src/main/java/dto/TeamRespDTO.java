package dto;

import lombok.Builder;
import lombok.Getter;
import model.Stadium;
import model.Team;

@Getter
@Builder
public class TeamRespDTO {
	private Integer teamId;
	private String stadiumName;
	private String teamName;
}
