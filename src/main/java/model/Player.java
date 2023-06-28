package model;

import java.sql.Timestamp;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Player {
	private Integer id;
	private Integer teamId;
	private String name;
	private Position position;
	private Timestamp createdAt;
}
