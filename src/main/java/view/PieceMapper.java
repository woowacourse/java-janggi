package view;

import domain.game.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Map;

public class PieceMapper {
    private static final String RESET = "\u001B[0m";
    private static final Map<Team, String> COLORS = Map.of(
            Team.CHO, "\u001B[34m",
            Team.HAN, "\u001B[31m"
    );

    private static final Map<Class<? extends Piece>, String> DISPLAY_NAMES = Map.of(
            Chariot.class, "차",
            Horse.class, "마",
            Elephant.class, "상",
            Guard.class, "사",
            General.class, "궁",
            Cannon.class, "포",
            Soldier.class, "병"
    );

    public static String toDisplayName(Piece piece) {
        if (!piece.isNotEmpty()) {
            return "ㅡ";
        }
        String name = DISPLAY_NAMES.get(piece.getClass());
        return COLORS.get(piece.getTeam()) + name + RESET;
    }
}
