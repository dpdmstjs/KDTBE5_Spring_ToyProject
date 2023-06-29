package dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamRespDto {
	private Integer teamId;
	private String stadiumName;
	private String teamName;
}
