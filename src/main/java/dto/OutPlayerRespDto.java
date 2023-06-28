package dto;

import java.sql.Timestamp;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutPlayerRespDto {
	private int playerId;
	private String name;
	private Position position;
	private String reason;
	private Timestamp outCreatedAt;
}
