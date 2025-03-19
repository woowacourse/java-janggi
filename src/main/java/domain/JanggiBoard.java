package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    public JanggiBoard(BoardGenerator boardGenerator) {
        this.board = boardGenerator.generateBoard();
    }

    public void move(Position startPosition, Position targetPosition) {
        Piece startPiece = findPiece(startPosition);
        Piece targetPositionPiece = findPiece(targetPosition);
        List<Position> path = startPiece.calculatePath(startPosition, targetPosition);
        if (!startPiece.isCanon()) {
            for (Position position : path) {
                isPositionEmpty(position);
            }
            if (findPiece(targetPosition) == null || !startPiece.compareTeam(targetPositionPiece)) {
                board.remove(startPosition);
                board.put(targetPosition, startPiece);
                return;
            }
            if (startPiece.compareTeam(targetPositionPiece)) {
                throw new IllegalArgumentException("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
            }
        }

        if (countPieceInPath(path) != 1) {
            throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
        }
    }

    private int countPieceInPath(List<Position> path) {
        return (int) path.stream().filter(pos -> !isPositionEmpty(pos)).count();
    }

    public boolean isPositionEmpty(Position position) {
        Piece piece = findPiece(position);
        if (piece != null) {
            return false;
        }
        return true;
    }

    public Piece findPiece(Position startPosition) {
        return board.get(startPosition);
    }


}
