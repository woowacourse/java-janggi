package janggi.domain.mouveRule;

import janggi.domain.board.BoardView;
import janggi.domain.piece.PieceType;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CannonMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (!(board.isOnDiagonalPath(from, to) || from.isStraightLine(to))){
            return false;
        }

        List<PieceType> pieceTypesBetween = findPieceTypesBetween(from, to, board);

        return hasOneBridgeNotCannon(pieceTypesBetween) && !isTargetCannon(to, board);
    }

    private boolean hasOneBridgeNotCannon(List<PieceType> types) {
        return types.size() == 1 && types.get(0) != PieceType.CANNON;
    }

    private boolean isTargetCannon(Position to, BoardView board) {
        return board.findPieceByPosition(to).isSameType(PieceType.CANNON);
    }

    private List<PieceType> findPieceTypesBetween(Position from, Position to, BoardView board) {
        List<PieceType> pieces = new ArrayList<>();
        Path path = Path.between(from, to);

        for (int i = 0; i < path.size(); i++) {
            pieces.add(board.findPieceByPosition(path.positionAt(i)).pieceType());
        }

        return pieces.stream()
                .filter(type -> type != PieceType.EMPTY)
                .collect(Collectors.toList());
    }
}
