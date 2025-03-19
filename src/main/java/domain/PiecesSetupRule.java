package domain;


import java.util.List;
import java.util.Map;

public enum PiecesSetupRule {
    MA_SANG_MA_SANG(
        Map.of(
            PieceType.HORSE, List.of(1, 7),
            PieceType.ELEPHANT, List.of(2, 8)
        )
    ),
    MA_SANG_SANG_MA(
        Map.of(
            PieceType.HORSE, List.of(1, 8),
            PieceType.ELEPHANT, List.of(2, 7)
        )
    ),
    SANG_MA_MA_SANG(
        Map.of(
            PieceType.HORSE, List.of(2, 7),
            PieceType.ELEPHANT, List.of(1, 8)
        )
    ),
    SANG_MA_SANG_MA(
        Map.of(
            PieceType.HORSE, List.of(2, 8),
            PieceType.ELEPHANT, List.of(1, 7)
        )
    );

    private final Map<PieceType, List<Integer>> xPositionByPiece;

    PiecesSetupRule(final Map<PieceType, List<Integer>> xPositionByPiece) {
        this.xPositionByPiece = xPositionByPiece;
    }

    public Map<PieceType, List<Position>> getPiecePosition(final Team team) {
        int yIndex = getInitialYIndexByTeam(team);

        return Map.of(
            PieceType.HORSE, this.xPositionByPiece.get(PieceType.HORSE)
                .stream()
                .map(xIndex -> new Position(xIndex, yIndex))
                .toList(),
            PieceType.ELEPHANT, this.xPositionByPiece.get(PieceType.ELEPHANT)
                .stream()
                .map(xIndex -> new Position(xIndex, yIndex))
                .toList()
        );
    }

    private int getInitialYIndexByTeam(final Team team) {
        if (team == Team.GREEN) {
            return 0;
        }

        return 9;
    }
}
