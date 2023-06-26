package model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Stadium {
	private Integer id;
	private String name;
	private Timestamp createdAt;

	@Builder
	public Stadium(Integer id, String name, Timestamp createdAt) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
	}
}
