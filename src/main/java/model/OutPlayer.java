package model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutPlayer {
	private Integer id;
	private Integer playerId;
	private String reason;
	private Timestamp createdAt;
}
