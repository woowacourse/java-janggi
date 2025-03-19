package janggi.domain.board;

import janggi.domain.Dynasty;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.HanPoint;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Rook;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class JanggiBoard {

    private static final Set<BoardPiece> PIECE_INITIAL_POSITIONS = new HashSet<>() {
        {
            add(new BoardPiece(new HanPoint(1, 1), Rook.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(1, 4), Guard.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(1, 6), Guard.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(1, 9), Rook.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(2, 5), King.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(3, 2), Cannon.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(3, 8), Cannon.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(4, 1), Pawn.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(4, 3), Pawn.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(4, 5), Pawn.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(4, 7), Pawn.newInstance(), Dynasty.HAN));
            add(new BoardPiece(new HanPoint(4, 9), Pawn.newInstance(), Dynasty.HAN));

            add(new BoardPiece(new ChuPoint(10, 1), Rook.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(10, 4), Guard.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(10, 6), Guard.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(10, 9), Rook.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(9, 5), King.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(8, 2), Cannon.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(8, 8), Cannon.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(7, 1), Pawn.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(7, 3), Pawn.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(7, 5), Pawn.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(7, 7), Pawn.newInstance(), Dynasty.CHU));
            add(new BoardPiece(new ChuPoint(7, 9), Pawn.newInstance(), Dynasty.CHU));
        }
    };

    private final Set<BoardPiece> boardPieces;

    public JanggiBoard(Set<BoardPiece> boardPieces) {
        this.boardPieces = boardPieces;
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        Set<BoardPiece> boardPieces = new HashSet<>(PIECE_INITIAL_POSITIONS);
        boardPieces.addAll(hanBoardSetUp.getPiecePositions());
        boardPieces.addAll(chuBoardSetUp.getPiecePositions());

        return new JanggiBoard(boardPieces);
    }

    public boolean isExistPiece(Point point) {
        return findPointPiece(point).isPresent();
    }

    public Optional<BoardPiece> findPointPiece(Point point) {
        return boardPieces.stream()
                .filter(pointPiece -> pointPiece.isSamePosition(point))
                .findFirst();
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

    public Set<BoardPiece> getPointPieces() {
        return boardPieces;
    }
}
