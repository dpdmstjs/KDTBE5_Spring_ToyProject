package model;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Team {
	private Integer id;
	private Integer stadiumId;
	private String name;
	private Timestamp createdAt;
}
