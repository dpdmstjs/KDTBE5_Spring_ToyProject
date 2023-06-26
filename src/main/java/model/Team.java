package model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Team {
	private Integer id;
	private Integer stadiumId;
	private String name;
	private Timestamp createdAt;

	public Team() {
	}

	@Builder
	public Team(Integer id, Integer stadiumId, String name, Timestamp createdAt) {
		this.id = id;
		this.stadiumId = stadiumId;
		this.name = name;
		this.createdAt = createdAt;
	}
}
