package constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
	ERR_MSG_INPUT("입력 값을 확인해주세요."),
	ERR_MSG_SQL("DB 처리 중 오류가 발생했습니다."),
	ERR_MSG_STADIUM_NOT_FOUND("등록된 야구장이 없습니다."),
	ERR_MSG_TEAM_NOT_FOUND("등록된 야구장이 없습니다."),
	ERR_MSG_TEAM_DUPLICATED("해당 팀이 존재합니다."),
	ERR_MSG_POSITIONS_NOT_FOUND("포지션별 조회 목록이 없습니다.");

	private String message;
}
