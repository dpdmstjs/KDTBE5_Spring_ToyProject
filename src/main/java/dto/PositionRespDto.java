package dto;
import java.util.List;
import java.util.Map;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionRespDto {
	private Map<String, List<String>> positionMap;
}