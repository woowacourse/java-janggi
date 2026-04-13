package view.formatter;

import java.util.Map;

public final class PieceFormatter {

    private static final String TEAM_CHO = "CHO";
    private static final String TEAM_HAN = "HAN";

    private static final String PIECE_GENERAL = "GENERAL";
    private static final String PIECE_GUARD = "GUARD";
    private static final String PIECE_HORSE = "HORSE";
    private static final String PIECE_ELEPHANT = "ELEPHANT";
    private static final String PIECE_SOLDIER = "SOLDIER";
    private static final String PIECE_CANNON = "CANNON";
    private static final String PIECE_CHARIOT = "CHARIOT";

    private static final String CHO_GENERAL_DISPLAY_NAME = "楚";
    private static final String HAN_GENERAL_DISPLAY_NAME = "漢";
    private static final String GUARD_DISPLAY_NAME = "士";
    private static final String HORSE_DISPLAY_NAME = "馬";
    private static final String ELEPHANT_DISPLAY_NAME = "象";
    private static final String CHO_SOLDIER_DISPLAY_NAME = "卒";
    private static final String HAN_SOLDIER_DISPLAY_NAME = "兵";
    private static final String CANNON_DISPLAY_NAME = "包";
    private static final String CHARIOT_DISPLAY_NAME = "車";

    private static final String CHO_COLOR_CODE = "\u001B[34m";
    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String COLOR_RESET_CODE = "\u001B[0m";

    private static final Map<String, String> CHO_PIECE_DISPLAY_NAMES = Map.of(
            PIECE_GENERAL, CHO_GENERAL_DISPLAY_NAME,
            PIECE_GUARD, GUARD_DISPLAY_NAME,
            PIECE_HORSE, HORSE_DISPLAY_NAME,
            PIECE_ELEPHANT, ELEPHANT_DISPLAY_NAME,
            PIECE_SOLDIER, CHO_SOLDIER_DISPLAY_NAME,
            PIECE_CANNON, CANNON_DISPLAY_NAME,
            PIECE_CHARIOT, CHARIOT_DISPLAY_NAME
    );

    private static final Map<String, String> HAN_PIECE_DISPLAY_NAMES = Map.of(
            PIECE_GENERAL, HAN_GENERAL_DISPLAY_NAME,
            PIECE_GUARD, GUARD_DISPLAY_NAME,
            PIECE_HORSE, HORSE_DISPLAY_NAME,
            PIECE_ELEPHANT, ELEPHANT_DISPLAY_NAME,
            PIECE_SOLDIER, HAN_SOLDIER_DISPLAY_NAME,
            PIECE_CANNON, CANNON_DISPLAY_NAME,
            PIECE_CHARIOT, CHARIOT_DISPLAY_NAME
    );

    private static final Map<String, String> TEAM_COLORS = Map.of(
            TEAM_CHO, CHO_COLOR_CODE,
            TEAM_HAN, HAN_COLOR_CODE
    );

    private PieceFormatter() {
    }

    public static String format(final String pieceType, final String team) {
        String displayName = toDisplayName(pieceType, team);
        String colorCode = getColorCode(team);
        return colorCode + displayName + COLOR_RESET_CODE;
    }

    private static String toDisplayName(final String pieceType, final String team) {
        Map<String, String> pieceDisplayNames = getPieceDisplayNames(team);
        if (!pieceDisplayNames.containsKey(pieceType)) {
            throw new IllegalArgumentException("정의되지 않은 기물 종류입니다. pieceType: " + pieceType);
        }
        return pieceDisplayNames.get(pieceType);
    }

    private static Map<String, String> getPieceDisplayNames(final String team) {
        if (TEAM_CHO.equals(team)) {
            return CHO_PIECE_DISPLAY_NAMES;
        }
        if (TEAM_HAN.equals(team)) {
            return HAN_PIECE_DISPLAY_NAMES;
        }
        throw new IllegalArgumentException("정의되지 않은 팀입니다. team: " + team);
    }

    private static String getColorCode(final String team) {
        if (!TEAM_COLORS.containsKey(team)) {
            throw new IllegalArgumentException("정의되지 않은 팀입니다. team: " + team);
        }
        return TEAM_COLORS.get(team);
    }
}
