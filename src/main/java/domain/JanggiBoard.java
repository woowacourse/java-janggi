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
        if (startPiece.isCanon()) {
            if (countPieceInPath(path) != 1) {
                throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
            }
            for (Position position : path) {
                if (findPiece(position) != null && findPiece(position).isCanon()) {
                    throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
                }
            }
            if (targetPositionPiece != null && targetPositionPiece.isCanon()) {
                throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다");
            }
        }
        if (!startPiece.isCanon()) {
            for (Position position : path) {
                if (!isPositionEmpty(position)) {
                    throw new IllegalArgumentException("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
                }
            }
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
