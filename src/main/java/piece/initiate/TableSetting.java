package piece.initiate;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import move.MaMoveBehavior;
import move.SangMoveBehavior;
import piece.Piece;
import piece.Team;
import piece.position.Position;

public enum TableSetting {

    MA_SANG_MA_SANG("마상_마상",
            List.of(
                    new Piece(new Position(9, 1), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 2), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 6), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 7), new SangMoveBehavior(), Team.BLUE)
            ),
            List.of(
                    new Piece(new Position(0, 1), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 2), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 6), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 7), new SangMoveBehavior(), Team.RED)
            )
    ),
    MA_SANG_SANG_MA("마상_상마",
            List.of(
                    new Piece(new Position(9, 1), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 2), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 6), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 7), new MaMoveBehavior(), Team.BLUE)
            ),
            List.of(
                    new Piece(new Position(0, 1), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 2), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 6), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 7), new MaMoveBehavior(), Team.RED)
            )
    ),
    SANG_MA_MA_SANG("상마_마상",
            List.of(
                    new Piece(new Position(9, 1), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 2), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 6), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 7), new SangMoveBehavior(), Team.BLUE)
            ),
            List.of(
                    new Piece(new Position(0, 1), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 2), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 6), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 7), new SangMoveBehavior(), Team.RED)
            )
    ),
    SANG_MA_SANG_MA("상마_상마",
            List.of(
                    new Piece(new Position(9, 1), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 2), new MaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 6), new SangMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 7), new MaMoveBehavior(), Team.BLUE)
            ),
            List.of(
                    new Piece(new Position(0, 1), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 2), new MaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 6), new SangMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 7), new MaMoveBehavior(), Team.RED)
            )
    );

    private final String description;
    private final Map<Team, List<Piece>> piecesByTeam;

    TableSetting(String description, List<Piece> bluePieces, List<Piece> redPieces) {
        this.description = description;
        this.piecesByTeam = new EnumMap<>(Team.class);
        this.piecesByTeam.put(Team.BLUE, bluePieces);
        this.piecesByTeam.put(Team.RED, redPieces);
    }

    public List<Piece> getTableSettingPieces(Team team) {
        return piecesByTeam.get(team).stream().toList();
    }

    public static Map<Team, TableSetting> defaultOption() {
        return Map.of(Team.BLUE, TableSetting.SANG_MA_SANG_MA, Team.RED, TableSetting.SANG_MA_SANG_MA);
    }
}
