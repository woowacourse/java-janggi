package janggi.board;

import janggi.Team;
import janggi.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void movePiece(Position start, Position goal, Team team) {
        Piece piece = board.get(start);
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 출발 지점에 기물이 존재하지 않습니다.");
        }
        if (!piece.isSameTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 같은 진영의 기물만 움직일 수 있습니다.");
        }

        piece.validateMovable(board, start, goal);
        move(start, goal, piece);
    }

    private void move(Position start, Position goal, Piece piece) {
        board.put(goal, piece);
        board.remove(start);
    }
}
