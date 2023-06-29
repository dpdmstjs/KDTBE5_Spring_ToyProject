package dto;

import java.util.List;
import java.util.Map;

import constant.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionRespDto {
	private Map<Position, List<String>> positionMap;
	private List<String> teamList;

	public void getList() {
		// Map<Position, List<String>> positionMap = positionRespDto.getPositionMap();
		// List<String> teamList = positionRespDto.getTeamList();
		//
		// System.out.printf("%-10s", "포지션");
		// for (String team : teamList) {
		// 	System.out.printf("%-10s", team);
		// }
		// System.out.println();
		//
		// for (Position position : positionMap.keySet()) {
		// 	System.out.printf("%-10s", position.getName());
		// 	List<String> teamPlayerList = positionMap.get(position);
		// 	for (String team : teamList) {
		// 		String playerName = teamPlayerList.get(teamList.indexOf(team));
		// 		System.out.printf("%-10s", playerName);
		// 	}
		// 	System.out.println();
		// }
	}
}