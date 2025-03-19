package domain;

import java.util.List;

public class Piece {

    private final PieceType pieceType;
    private final Team team;

    Piece(
        final PieceType pieceType,
        final Team team
    ) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public List<Offset> findMovementRule(
        final Position before,
        final Position after
    ) {
        final Offset offset = after.calculateOffset(before);

        return pieceType.findMovementRule(offset, team)
            .orElseThrow(
                () -> new IllegalArgumentException("해당 말은 이동할 수 없습니다."));
    }
}
