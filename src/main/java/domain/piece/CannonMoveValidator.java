package domain.piece;

import domain.spatial.Position;
import java.util.List;

public class CannonMoveValidator {

    private final Pieces playerPieces;
    private final Pieces opponentPieces;

    public CannonMoveValidator(final Pieces playerPieces, final Pieces opponentPieces) {
        this.playerPieces = playerPieces;
        this.opponentPieces = opponentPieces;
    }

    public void validateCannonCapture(final Position targetPosition) {
        if (opponentPieces.isCannonByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
        }
    }

    public void validateCannonPath(final List<Position> paths) {
        if (paths.stream().anyMatch(playerPieces::isCannonByPosition) ||
                paths.stream().anyMatch(opponentPieces::isCannonByPosition)) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
        }
    }
}
