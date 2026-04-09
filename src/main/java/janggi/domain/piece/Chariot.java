package janggi.domain.piece;

import janggi.domain.path.PieceOnPath;
import janggi.domain.Team;

public class Chariot extends PalacePiece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateAllPieceEmpty(pieceOnPath);
        validateSameTeam(endPiece);
    }

    private void validateAllPieceEmpty(PieceOnPath piecesOnPath) {
        if (piecesOnPath.countNonEmpty() != 0) {
            throw new IllegalArgumentException("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
