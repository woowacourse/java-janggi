package domain.piece;

import domain.Position;
import domain.move.Moves;
import domain.player.Player;
import java.util.List;

public abstract class PalaceFixedMovePiece extends Piece {

    public PalaceFixedMovePiece(Player player, int point) {
        super(player, point);
    }

    public abstract List<Moves> getMoveList(Position startPosition);

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        List<Moves> movesList = getMoveList(startPosition);
        for (Moves moves : movesList) {
            boolean compareResult = moves.comparePath(startPosition, targetPosition);
            if (compareResult) {
                return moves.convertToPath(startPosition);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }
}
