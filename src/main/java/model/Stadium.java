package model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Stadium {
	private Integer id;
	private String name;
	private Timestamp createdAt;
}
