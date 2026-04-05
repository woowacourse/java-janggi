package view;

import domain.game.Team;
import domain.piece.PieceDefinition;
import java.util.Map;

public class ConsolePieceAppearance implements PieceAppearance {
    private static final String RESET = "\u001B[0m";
    private static final Map<Team, String> COLORS = Map.of(
            Team.CHO, "\u001B[34m",
            Team.HAN, "\u001B[31m"
    );

    private static final Map<PieceDefinition, String> DISPLAY_NAMES = Map.of(
            PieceDefinition.CHA, "차",
            PieceDefinition.MA, "마",
            PieceDefinition.SANG, "상",
            PieceDefinition.SA, "사",
            PieceDefinition.GENERAL, "궁",
            PieceDefinition.PHO, "포",
            PieceDefinition.BYEONG, "병"
    );

    @Override
    public String colorize(Team team, PieceDefinition type) {
        return COLORS.get(team) + DISPLAY_NAMES.get(type) + RESET;
    }

    @Override
    public String colorizeEmpty() {
        return "ㅡ";
    }
}
