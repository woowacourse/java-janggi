package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.PieceOnPath;

public class Cannon extends PalacePiece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateJumpOnlyOnePiece(pieceOnPath);
        validateJumpCannon(pieceOnPath);
        validateSameTeam(endPiece);
        validateEndCannon(endPiece);
    }

    private void validateJumpOnlyOnePiece(PieceOnPath piecesOnPath) {
        if (piecesOnPath.countNonEmpty() != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
        }
    }

    private void validateJumpCannon(PieceOnPath pieceOnPath) {
        if (pieceOnPath.hasType(getType())) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }
    }

    private void validateEndCannon(Piece endPiece) {
        if (endPiece.isSameType(getType())) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }
}
