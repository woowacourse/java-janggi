package janggi.view;

import janggi.domain.Dynasty;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.Point;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JanggiBoardView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final Map<Piece, String> PIECE_LABELS = Map.of(
            King.newInstance(), "궁",
            Guard.newInstance(), "사",
            Rook.newInstance(), "차",
            Cannon.newInstance(), "포",
            Knight.newInstance(), "마",
            Elephant.newInstance(), "상",
            Pawn.newInstance(), "졸"
    );

    public void printBoard(Set<BoardPiece> boardPieces) {
        Map<Point, BoardPiece> boardPieceMap = createBoardPiecesMap(boardPieces);

        for (int x = 1; x <= 10; x++) {
            for (int y = 1; y <= 9; y++) {
                DefaultPoint point = new DefaultPoint(x, y);
                if (boardPieceMap.containsKey(point)) {
                    BoardPiece boardPiece = boardPieceMap.get(point);
                    printPointPiece(boardPiece);
                    continue;
                }
                System.out.print("ㅁ");
            }
            System.out.println();
        }
    }

    private static Map<Point, BoardPiece> createBoardPiecesMap(Set<BoardPiece> boardPieces) {
        Map<Point, BoardPiece> pieceMap = new HashMap<>();
        for (BoardPiece boardPiece : boardPieces) {
            Point currentPoint = boardPiece.getCurrentPoint();
            pieceMap.put(new DefaultPoint(currentPoint.getX(), currentPoint.getY()), boardPiece);
        }
        return pieceMap;
    }

    private void printPointPiece(BoardPiece boardPiece) {
        String pieceLabel = PIECE_LABELS.get(boardPiece.getPiece());
        if (boardPiece.getDynasty() == Dynasty.HAN) {
            System.out.print(RED + pieceLabel + RESET);
            return;
        }
        System.out.print(BLUE + pieceLabel + RESET);
    }
}
