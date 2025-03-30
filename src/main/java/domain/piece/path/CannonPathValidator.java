package domain.piece.path;

import domain.piece.Board;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.List;

public class CannonPathValidator implements PathValidator {

    private static final int CANNON_JUMP_COUNT = 1;

    @Override
    public void validatePath(List<Position> positions, Position to, Board board, Piece movePiece) {
        validateMovePath(positions, board);
        validateDestination(to, board, movePiece);
    }

    private void validateMovePath(List<Position> positions, Board board) {
        if (isCannonOnPath(positions, board)) {
            throw new IllegalArgumentException("포는 포를 뛰어넘을 수 없습니다.");
        }

        if (countPieceOnPath(positions, board) != CANNON_JUMP_COUNT) {
            throw new IllegalArgumentException("포는 하나의 기물을 뛰어넘어야 합니다.");
        }

    }

    private void validateDestination(Position to, Board board, Piece movePiece) {
        if (board.isEmptyPosition(to)) {
            return;
        }

        if (isSameTeamPieceTo(to, board, movePiece)) {
            throw new IllegalArgumentException("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
        }

        if (isOnCannon(to, board)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    private boolean isCannonOnPath(List<Position> positions, Board board) {
        return positions.stream()
                .filter(position -> !board.isEmptyPosition(position))
                .anyMatch(position -> isOnCannon(position, board));
    }

    private boolean isOnCannon(Position position, Board board) {
        Piece foundPiece = board.findPieceByPosition(position);
        return foundPiece.isSameType(PieceType.CANNON);
    }

    private int countPieceOnPath(List<Position> positions, Board board) {
        return (int) positions.stream()
                .filter(position -> !board.isEmptyPosition(position))
                .count();
    }

    private boolean isSameTeamPieceTo(Position to, Board board, Piece movePiece) {
        Piece foundPiece = board.findPieceByPosition(to);
        return foundPiece.isSameTeam(movePiece);
    }
}
