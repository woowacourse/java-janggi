package view;

public final class PieceFormatter {

    private static final String GENERAL_DISPLAY_NAME_CHO = "楚";
    private static final String GENERAL_DISPLAY_NAME_HAN = "漢";
    private static final String GUARD_DISPLAY_NAME = "士";
    private static final String HORSE_DISPLAY_NAME = "馬";
    private static final String ELEPHANT_DISPLAY_NAME = "象";
    private static final String SOLDIER_DISPLAY_NAME_CHO = "卒";
    private static final String SOLDIER_DISPLAY_NAME_HAN = "兵";
    private static final String CANNON_DISPLAY_NAME = "包";
    private static final String CHARIOT_DISPLAY_NAME = "車";

    private static final String TEAM_CHO = "CHO";
    private static final String TEAM_HAN = "HAN";

    private static final String PIECE_GENERAL = "GENERAL";
    private static final String PIECE_GUARD = "GUARD";
    private static final String PIECE_HORSE = "HORSE";
    private static final String PIECE_ELEPHANT = "ELEPHANT";
    private static final String PIECE_SOLDIER = "SOLDIER";
    private static final String PIECE_CANNON = "CANNON";
    private static final String PIECE_CHARIOT = "CHARIOT";

    private static final String BLUE_CODE = "\u001B[34m";
    private static final String RED_CODE = "\u001B[31m";
    private static final String COLOR_RESET_CODE = "\u001B[0m";

    private PieceFormatter() {
    }

    public static String format(final String pieceType, final String team) {
        String displayName = toDisplayName(pieceType, team);
        return colorize(team, displayName);
    }

    private static String toDisplayName(final String pieceType, final String team) {
        if (PIECE_GENERAL.equals(pieceType)) {
            return getGeneralDisplayName(team);
        }
        if (PIECE_GUARD.equals(pieceType)) {
            return GUARD_DISPLAY_NAME;
        }
        if (PIECE_HORSE.equals(pieceType)) {
            return HORSE_DISPLAY_NAME;
        }
        if (PIECE_ELEPHANT.equals(pieceType)) {
            return ELEPHANT_DISPLAY_NAME;
        }
        if (PIECE_CANNON.equals(pieceType)) {
            return CANNON_DISPLAY_NAME;
        }
        if (PIECE_CHARIOT.equals(pieceType)) {
            return CHARIOT_DISPLAY_NAME;
        }
        if (PIECE_SOLDIER.equals(pieceType)) {
            return getSoldierDisplayName(team);
        }

        throw new IllegalArgumentException("정의되지 않은 기물 종류입니다. pieceType: " + pieceType);
    }

    private static String getGeneralDisplayName(final String team) {
        if (TEAM_CHO.equals(team)) {
            return GENERAL_DISPLAY_NAME_CHO;
        }
        if (TEAM_HAN.equals(team)) {
            return GENERAL_DISPLAY_NAME_HAN;
        }
        throw new IllegalArgumentException("정의되지 않은 팀입니다. team: " + team);
    }

    private static String getSoldierDisplayName(final String team) {
        if (TEAM_CHO.equals(team)) {
            return SOLDIER_DISPLAY_NAME_CHO;
        }
        if (TEAM_HAN.equals(team)) {
            return SOLDIER_DISPLAY_NAME_HAN;
        }
        throw new IllegalArgumentException("정의되지 않은 팀입니다. team: " + team);
    }

    private static String colorize(final String team, final String displayName) {
        if (TEAM_CHO.equals(team)) {
            return BLUE_CODE + displayName + COLOR_RESET_CODE;
        }
        if (TEAM_HAN.equals(team)) {
            return RED_CODE + displayName + COLOR_RESET_CODE;
        }
        throw new IllegalArgumentException("정의되지 않은 팀입니다. team: " + team);
    }
}
