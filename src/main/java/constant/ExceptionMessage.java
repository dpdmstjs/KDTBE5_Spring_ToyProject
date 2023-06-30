package constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
	ERR_MSG_INPUT("입력 값을 확인해주세요."),
	ERR_MSG_SQL("DB 처리 중 오류가 발생했습니다."),
	ERR_MSG_METHOD_NOT_FOUND("요청을 찾지 못했습니다."),
	ERR_MSG_REFLECTION("요청 실행 중 오류가 발생했습니다."),
	ERR_MSG_STADIUM_NOT_FOUND("등록된 야구장이 없습니다."),
	ERR_MSG_STADIUMS_NOT_FOUND("야구장 조회 목록이 없습니다."),
	ERR_MSG_TEAM_NOT_FOUND("팀 조회 목록이 없습니다."),
	ERR_MSG_TEAM_DUPLICATED("해당 팀이 존재합니다."),
	ERR_MSG_POSITIONS_NOT_FOUND("포지션별 조회 목록이 없습니다."),
	ERR_MSG_POSITION_NOT_FOUND("포지션을 찾을 수 없습니다."),
	ERR_MSG_OUTPLAYERS_NOT_FOUND("퇴출선수 목록이 없습니다."),
	ERR_MSG_PLAYER_NOT_FOUND("해당 선수가 없습니다."),
	ERR_MSG_PLAYERS_NOT_FOUND("선수 조회 목록이 없습니다."),
	ERR_MSG_POSTION_DUPLICATED("해당 팀에 동일한 포지션의 선수가 존재합니다.");

	private String message;
}
