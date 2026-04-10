package janggi.domain.board;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board from(BoardDesignPolicy boardDesignPolicy) {
        return new Board(boardDesignPolicy.initBoard());
    }

    public static Board restore(Map<Position, Piece> boardMap) {
        return new Board(boardMap);
    }

    public List<Position> placeablePositions(Position from, Dynasty currentTurn) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
        Piece piece = board.get(from);
        if (!piece.isSame(currentTurn)) {
            throw new IllegalArgumentException("해당 위치의 기물은 상대 팀의 기물입니다.");
        }
        return piece.placeablePositions(BoardSnapshot.of(board), from);
    }

    public Piece movePiece(Position from, Position to, Dynasty currentTurn) {
        List<Position> positions = placeablePositions(from, currentTurn);
        if (!positions.contains(to)) {
            throw new IllegalArgumentException("해당 위치에 해당 기물을 옮길 수 없습니다.");
        }

        Piece fromPiece = board.remove(from);
        return board.put(to, fromPiece);
    }

    public int sumPointsOf(Dynasty dynasty) {
        return board.values().stream()
                .filter(piece -> piece.isSame(dynasty))
                .mapToInt(piece -> piece.pieceType().points())
                .sum();
    }

    public Map<Position, Piece> pieces() {
        return Map.copyOf(board);
    }

}
