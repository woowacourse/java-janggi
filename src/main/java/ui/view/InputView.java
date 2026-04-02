package ui.view;

import domain.piece.Team;
import domain.settingType.SettingType;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import ui.dto.ActionType;
import ui.dto.MovePositionDto;
import ui.dto.PositionDto;

public class InputView {
    private static final String DELIMITER = ",";
    private static final String SETTING_DESCRIPTION = "초나라와 한나라의 차림을 각각 선택하세요(콤마로 구분, 예시 => 1,3)\n"
            + "① 왼상차림 (상마상마), ② 오른상차림 (마상마상), ③ 안상차림 (마상상마), ④ 바깥상차림 (상마마상)";
    private static final String MOVE_COMMAND_DESCRIPTION = "%s 이동할 기물의 위치와 이동할 위치를 입력하세요. (예: a7 a5)";
    private static final String ACTION_COMMAND_DESCRIPTION = "%s 차례입니다. 행동을 선택하세요.\n" + "1. 이동 2. 턴 넘기기";
    private static final String CHO_KOREAN_DESCRIPTION = "초나라";
    private static final String HAN_KOREAN_DESCRIPTION = "한나라";

    private static final String INVALID_SETTING_INPUT = "1~4 사이의 숫자만 입력해주세요.";
    private static final String INVALID_ACTION_INPUT = "1~2 사이의 숫자만 입력해주세요.";

    private final Scanner sc = new Scanner(System.in);

    public List<SettingType> readSettings() {
        System.out.println(SETTING_DESCRIPTION);
        return Arrays.stream(sc.nextLine().split(DELIMITER))
                .map(String::strip)
                .map(InputView::selectSettingType)
                .toList();
    }

    private static SettingType selectSettingType(String info) {
        if (info.equals("1")) {
            return SettingType.LEFT;
        }
        if (info.equals("2")) {
            return SettingType.RIGHT;
        }
        if (info.equals("3")) {
            return SettingType.INNER;
        }
        if (info.equals("4")) {
            return SettingType.OUTER;
        }
        throw new IllegalArgumentException(INVALID_SETTING_INPUT);
    }

    public MovePositionDto readMovePositions(Team team) {
        String commandDescription = String.format(MOVE_COMMAND_DESCRIPTION, convertTeamTypeToKorean(team));
        System.out.println(commandDescription);

        String input = sc.nextLine();

        String[] positions = input.split(" ");

        PositionDto startPosition = PositionDto.toDto(positions[0]);
        PositionDto destinationPosition = PositionDto.toDto(positions[1]);

        return new MovePositionDto(startPosition, destinationPosition);
    }

    public ActionType readAction(Team team) {
        String commandDescription = String.format(ACTION_COMMAND_DESCRIPTION, convertTeamTypeToKorean(team));
        System.out.println(commandDescription);
        String input = sc.nextLine();

        int action = 0;
        try {
            action = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ACTION_INPUT);
        }
        return ActionType.toValue(action);
    }

    private String convertTeamTypeToKorean(Team team) {
        if (team == Team.CHO) {
            return CHO_KOREAN_DESCRIPTION;
        }
        return HAN_KOREAN_DESCRIPTION;
    }
}
