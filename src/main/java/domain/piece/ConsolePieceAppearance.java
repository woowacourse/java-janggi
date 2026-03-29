package domain.piece;

import domain.game.Team;
import java.util.Map;

public class ConsolePieceAppearance implements PieceAppearance {
    private static final String RESET = "\u001B[0m";
    private static final Map<Team, String> COLORS = Map.of(
            Team.CHO, "\u001B[34m",
            Team.HAN, "\u001B[31m"
    );

    private static final Map<PieceType, String> DISPLAY_NAMES = Map.of(
            PieceType.CHA, "차",
            PieceType.MA, "마",
            PieceType.SANG, "상",
            PieceType.SA, "사",
            PieceType.GENERAL, "궁",
            PieceType.PHO, "포",
            PieceType.BYEONG, "병"
    );

    @Override
    public String colorize(Team team, PieceType type) {
        return COLORS.get(team) + DISPLAY_NAMES.get(type) + RESET;
    }

    @Override
    public String colorizeEmpty() {
        return "ㅡ";
    }
}
