package view.formatter;

import java.util.Map;

public final class TeamNameFormatter {

    private static final String HAN_TEAM_NAME = "HAN";
    private static final String CHO_TEAM_NAME = "CHO";

    private static final String HAN_TEAM_DISPLAY_NAME = "한";
    private static final String CHO_TEAM_DISPLAY_NAME = "초";

    private static final Map<String, String> DISPLAY_NAME_BY_TEAM = Map.of(
            CHO_TEAM_NAME, CHO_TEAM_DISPLAY_NAME,
            HAN_TEAM_NAME, HAN_TEAM_DISPLAY_NAME
    );

    private TeamNameFormatter() {
    }

    public static String format(final String teamName) {
        if (!DISPLAY_NAME_BY_TEAM.containsKey(teamName)) {
            throw new IllegalArgumentException("정의되지 않은 팀명 입니다.");
        }
        return DISPLAY_NAME_BY_TEAM.get(teamName);
    }
}
