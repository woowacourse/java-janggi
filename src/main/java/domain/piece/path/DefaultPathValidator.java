package domain.piece.path;

import domain.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class DefaultPathValidator implements PathValidator {

    @Override
    public void validateMovePath(List<Position> positions, Board board) {
        if (hasPieceOnPath(positions, board)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateDestination(Position to, Board board, Piece movePiece) {
        if (board.isEmptyPosition(to)) {
            return;
        }
        Piece foundPiece = board.findPieceByPosition(to);
        if (foundPiece.isSameTeam(movePiece)) {
            throw new IllegalArgumentException("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
        }
    }

    private boolean hasPieceOnPath(List<Position> positions, Board board) {
        return positions.stream()
                .anyMatch(position -> !board.isEmptyPosition(position));
    }

}
