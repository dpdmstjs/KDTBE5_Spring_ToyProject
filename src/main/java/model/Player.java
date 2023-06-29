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

	@Override
	public String toString() {
		return name + "\t" + position.getName() + "\t" + formattedTimestamp(createdAt) + "\n";
	}

	private String formattedTimestamp(Timestamp timestamp) {
		return timestamp.toString().split(" ")[0];
	}
}
