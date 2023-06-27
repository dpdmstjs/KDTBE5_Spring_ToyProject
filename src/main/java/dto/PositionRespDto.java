package dto;

import java.util.List;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionRespDto {
	Position position;
	private List<String> teamPlayers;
}
