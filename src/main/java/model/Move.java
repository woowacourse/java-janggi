package model;

import model.pieces.Piece;
import model.pieces.rule.MovePattern;
import model.pieces.rule.Step;

import java.util.List;

public class Move {
    private final Position from;
    private final Position to;

    private Move(Position from, Position to) {
        validate(from, to);
        this.from = from;
        this.to = to;
    }

    public static Move of(Position from, Position to) {
        return new Move(from, to);
    }

    public void move(Board board, List<MovePattern> moveRule) {
        for (MovePattern pattern : moveRule) {
            Position temp = from;
            for (Step step : pattern.steps()) {
                int nx = temp.row().move(step.direction());
                int ny = temp.column().move(step.direction());
                if (nx < 1 || nx > 10 || ny < 1 || ny > 9) {
                    break;
                }
                temp = Position.of(nx, ny);
                // path검사
                if (step.mustBeEmpty() && !board.isPathEmpty(temp)) {
                    break;
                }
            }

            // 도착지 검사
            if (temp.equals(to)) {
                Piece fromPiece = board.findPiece(from);
                Piece toPiece = board.findPiece(to);
                // 적군, 아군 판별
                if (toPiece == null || (fromPiece.country() != toPiece.country())) {
                    board.place(to, fromPiece);
                    board.remove(from);
                    return;
                }
            }
        }
    }

    private void validate(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로는 이동할 수 없습니다.");
        }
    }
}
