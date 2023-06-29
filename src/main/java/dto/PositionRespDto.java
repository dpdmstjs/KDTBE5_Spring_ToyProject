package dto;

import java.util.List;
import java.util.Map;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionRespDto {
	private Map<Position, List<String>> positions;
	private List<String> teams;
}