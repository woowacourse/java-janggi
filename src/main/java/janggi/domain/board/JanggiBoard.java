package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class JanggiBoard {

    private static final Map<Position, BoardPiece> PIECE_INITIAL_POSITIONS = new HashMap<>() {
        {
            put(new Position(1, 1), new BoardPiece(new Chariot(), Dynasty.HAN));
            put(new Position(1, 4), new BoardPiece(new Guard(), Dynasty.HAN));
            put(new Position(1, 6), new BoardPiece(new Guard(), Dynasty.HAN));
            put(new Position(1, 9), new BoardPiece(new Chariot(), Dynasty.HAN));
            put(new Position(2, 5), new BoardPiece(new General(), Dynasty.HAN));
            put(new Position(3, 2), new BoardPiece(new Cannon(), Dynasty.HAN));
            put(new Position(3, 8), new BoardPiece(new Cannon(), Dynasty.HAN));
            put(new Position(4, 1), new BoardPiece(new Soldier(), Dynasty.HAN));
            put(new Position(4, 3), new BoardPiece(new Soldier(), Dynasty.HAN));
            put(new Position(4, 5), new BoardPiece(new Soldier(), Dynasty.HAN));
            put(new Position(4, 7), new BoardPiece(new Soldier(), Dynasty.HAN));
            put(new Position(4, 9), new BoardPiece(new Soldier(), Dynasty.HAN));

            put(new Position(10, 1), new BoardPiece(new Chariot(), Dynasty.CHU));
            put(new Position(10, 4), new BoardPiece(new Guard(), Dynasty.CHU));
            put(new Position(10, 6), new BoardPiece(new Guard(), Dynasty.CHU));
            put(new Position(10, 9), new BoardPiece(new Chariot(), Dynasty.CHU));
            put(new Position(9, 5), new BoardPiece(new General(), Dynasty.CHU));
            put(new Position(8, 2), new BoardPiece(new Cannon(), Dynasty.CHU));
            put(new Position(8, 8), new BoardPiece(new Cannon(), Dynasty.CHU));
            put(new Position(7, 1), new BoardPiece(new Soldier(), Dynasty.CHU));
            put(new Position(7, 3), new BoardPiece(new Soldier(), Dynasty.CHU));
            put(new Position(7, 5), new BoardPiece(new Soldier(), Dynasty.CHU));
            put(new Position(7, 7), new BoardPiece(new Soldier(), Dynasty.CHU));
            put(new Position(7, 9), new BoardPiece(new Soldier(), Dynasty.CHU));
        }
    };

    private final Map<Position, BoardPiece> boardPieces;

    public JanggiBoard(Map<Position, BoardPiece> boardPieces) {
        this.boardPieces = boardPieces;
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        HashMap<Position, BoardPiece> pieceMap = new HashMap<>(PIECE_INITIAL_POSITIONS);
        pieceMap.putAll(hanBoardSetUp.getDynastySetUp(Dynasty.HAN, hanBoardSetUp));
        pieceMap.putAll(chuBoardSetUp.getDynastySetUp(Dynasty.CHU, chuBoardSetUp));
        return new JanggiBoard(pieceMap);
    }

    public boolean isExistPiece(Position position) {
        return boardPieces.containsKey(position);
    }

    public Optional<BoardPiece> findPointPiece(Position target) {
        return boardPieces.keySet().stream()
                .filter(position -> position.equals(target))
                .map(boardPieces::get)
                .findFirst();
    }

    public void move(Dynasty dynasty, Position start, Position end) {
        Optional<BoardPiece> startBoardPiece = findPointPiece(start);
        if (startBoardPiece.isEmpty()) {
            throw new IllegalArgumentException("시작 위치에 기물이 존재하지 않습니다.");
        }
        BoardPiece boardPiece = startBoardPiece.get();
        if (!boardPiece.isSameDynasty(dynasty)) {
            throw new IllegalArgumentException("자신의 나라 기물이 아닙니다.");
        }
        boardPiece.move(this, start, end);
        boardPieces.remove(start);
        boardPieces.put(end, boardPiece);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiBoard that = (JanggiBoard) o;
        return Objects.equals(boardPieces, that.boardPieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(boardPieces);
    }

    public Map<Position, BoardPiece> getBoardPieces() {
        return boardPieces;
    }
}
