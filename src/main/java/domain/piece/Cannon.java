package domain.piece;

import domain.board.BoardReader;
import domain.board.Palace;
import domain.position.Direction;
import domain.position.MoveDirection;
import domain.position.Position;

import java.util.Optional;

public class Cannon extends Piece {

    public Cannon(Camp camp) {
        super(camp, PieceType.CANNON);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (Direction direction : MoveDirection.ofLinear()) {
            if (canJumpToTarget(from, to, boardReader, direction)) {
                return true;
            }
        }

        if (Palace.isPalacePosition(from) && Palace.isPalacePosition(to)) {
            for (Direction diagonalDirection : MoveDirection.ofDiagonal()) {
                if (canJumpToTarget(from, to, boardReader, diagonalDirection)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canJumpToTarget(Position from, Position to, BoardReader boardReader, Direction direction) {
        Optional<Position> bridge = findBridge(from, direction, boardReader);
        if (bridge.isEmpty()) {
            return false;
        }

        return checkAfterBridge(bridge.get(), to, boardReader, direction);
    }

    private Optional<Position> findBridge(Position current, Direction direction, BoardReader boardReader) {
        while (current.canMove(direction)) {
            current = current.move(direction);
            if (boardReader.isExist(current) && boardReader.isDifferentPieceType(current, this)) {
                return Optional.of(current);
            }
            if (boardReader.isExist(current)) {
                break;
            }
        }
        return Optional.empty();
    }

    private boolean checkAfterBridge(Position bridge, Position to, BoardReader boardReader, Direction direction) {
        Position current = bridge;
        while (current.canMove(direction)) {
            current = current.move(direction);
            if (current.equals(to)) {
                return boardReader.isDifferentPieceType(to, this);
            }
            if (boardReader.isExist(current)) {
                break;
            }
        }
        return false;
    }


}
