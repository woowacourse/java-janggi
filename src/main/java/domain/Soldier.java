package domain;

import java.util.List;

public class Soldier extends Piece {
    private static final List<Integer> deltaColumn = List.of(-1, 0, 1);
    private static final List<Integer> deltaRow = List.of(0, 1, 0);

    public Soldier(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        boolean isThereMyTeam = pieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(expectedPosition) && piece.isSameTeam(this));
        if (isThereMyTeam) {
            return false;
        }

        for (int i = 0; i < deltaColumn.size(); i++) {
            if (position.checkPositionAfterDeltaMove(deltaRow.get(i), deltaColumn.get(i), expectedPosition)) {
                return true;
            }
        }

        return false;
    }
}
