package domain.piece;

import domain.spatial.Position;

public class PieceMoveValidator {

    private final Pieces playerPieces;

    public PieceMoveValidator(final Pieces playerPieces) {
        this.playerPieces = playerPieces;
    }

    public void validateTeamPieceInTargetPosition(final Position targetPosition) {
        if (playerPieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }
}
