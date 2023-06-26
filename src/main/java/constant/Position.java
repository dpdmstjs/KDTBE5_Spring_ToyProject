package constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
	FIRST_BASEMAN("1루수"), SECOND_BASEMAN("2루수"), THIRD_BASEMAN("3루수"), CATCHER("포수"),
	PITHCER("투수"), SHORT_FIELDER("유격수"), LEFT_FIELDER("좌익수"),
	CENTER_FIELDER("중견수"), RIGHT_FIELDER("우익수");

	private String name;

}
