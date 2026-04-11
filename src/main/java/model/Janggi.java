package model;

import java.util.List;
import model.coordinate.Position;
import model.piece.Piece;

public class Janggi {

    public static final int CANNON_HURDLE_COUNT = 1;

    private final Board board;
    private Team turn;

    public Janggi(Board board) {
        this.board = board;
        this.turn = Team.CHO;
    }

    public void move(Position current, Position next) {
        Piece piece = findPieceAt(current, turn);
        validateMovement(current, next, piece);

        board.movePiece(current, next);
        this.turn = turn.next();
    }

    public Piece findPieceAt(Position position, Team turn) {
        Piece piece = board.pickPiece(position);
        if (piece.isEnemy(turn)) {
            throw new IllegalArgumentException(turn.getName() + "의 기물이 아닙니다.");
        }
        return piece;
    }

    public Team getTurn() {
        return turn;
    }

    private void validateMovement(Position current, Position next, Piece piece) {
        if (!piece.canMove(current, next)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }

        if (piece.isCannon()) {
            validateMovementOfCannon(current, next, piece);
            return;
        }

        List<Position> path = piece.extractPath(current, next);
        if (board.hasPieceAt(path)) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없는 위치입니다.");
        }
    }

    private void validateMovementOfCannon(Position current, Position next, Piece piece) {
        List<Position> path = piece.extractPath(current, next);
        int countedPieces = board.countPiecesAt(path);
        if (countedPieces != CANNON_HURDLE_COUNT) {
            throw new IllegalArgumentException("포는 1개의 기물만 건너 뛰어야 합니다.");
        }
        if (board.hasCannon(path)) {
            throw new IllegalArgumentException("포는 포를 건너뛸 수 없습니다.");
        }
    }
}
