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

	@Override
	public String toString() {
		return playerId + "\t" + name + "\t" + position.getName() + "\t" + reason + "\t" + formattedTimestamp(
			outCreatedAt) + "\n";
	}

	private String formattedTimestamp(Timestamp timestamp) {
		if (timestamp == null)
			return "";

		return timestamp.toString().split(" ")[0];
	}
}
