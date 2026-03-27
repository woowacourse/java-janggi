package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(BoardDesignPolicy boardDesignPolicy) {
        this.board = new HashMap<>(boardDesignPolicy.initBoard());
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

    public List<Position> canMovePosition(Position from, Dynasty currentTurn) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
        Piece piece = board.get(from);
        if (!piece.isSameDynasty(currentTurn)) {
            throw new IllegalArgumentException("해당 위치의 기물은 상대 팀의 기물입니다.");
        }
        return piece.canMovePosition(board, from);
    }

    public void movePiece(Position from, Position to, Dynasty currentTurn) {
        List<Position> positions = canMovePosition(from, currentTurn);
        if(!positions.contains(to)) {
            throw new IllegalArgumentException("해당 위치에 해당 기물을 옮길 수 없습니다.");
        }

        Piece fromPiece = board.remove(from);
        board.put(to, fromPiece);
    }
}
