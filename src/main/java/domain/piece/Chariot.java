package domain.piece;

import domain.*;
import domain.board.BoardReader;
import domain.board.Palace;
import domain.position.Direction;
import domain.position.MoveDirection;
import domain.position.Position;

public class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp, PieceType.CHARIOT);
    }

    @Override
    public boolean canMove(Position from, Position to, BoardReader boardReader) {
        for (Direction direction : MoveDirection.ofLinear()) {
            if (canReachTarget(from, to, boardReader, direction)) {
                return true;
            }
        }

        if(Palace.isPalacePosition(from) && Palace.isPalacePosition(to)) {
            for (Direction diagonalDirection : MoveDirection.ofDiagonal()) {
                if(canReachTargetAndPassGeneralPosition(from, to, boardReader, diagonalDirection)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canReachTarget(Position from, Position to, BoardReader boardReader, Direction direction) {
        Position current = from;
        while (current.canMove(direction)) {
            current = current.move(direction);
            if (current.equals(to)) {
                return true;
            }

            if (boardReader.isExist(current)) {
                break;
            }
        }
        return false;
    }

    private boolean canReachTargetAndPassGeneralPosition(Position from,
                                                         Position to,
                                                         BoardReader boardReader,
                                                         Direction direction) {
        Position current = from;
        boolean passGeneralPosition = false;
        while (current.canMove(direction)) {
            current = current.move(direction);
            if(Palace.isGeneralPosition(current)) {
                passGeneralPosition = true;
            }
            if (current.equals(to) && passGeneralPosition) {
                return true;
            }

            if (boardReader.isExist(current)) {
                break;
            }
        }

        return false;
    }
}
