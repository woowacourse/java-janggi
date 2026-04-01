package view;

import domain.coordinate.Position;
import domain.board.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.BoardDto;

import view.message.SideView;

import java.util.Map;

public class OutputView {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final Map<PieceType, String> PIECE_TYPE = Map.of(
            PieceType.PAWN, "卒",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.GUARD, "士",
            PieceType.KING, "將",
            PieceType.EMPTY, "ㅁ"
    );

    public void printBoard(BoardDto boardDto) {
        System.out.println("\n   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < COL_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < ROW_SIZE; j++) {
                printPieceBySide(boardDto.board().get(new Position(i, j)));
            }

            System.out.println();
        }
    }

    private void printPieceBySide(Piece piece) {
        Side side = piece.getSide();

        if (piece.isNeutral()) {
            System.out.print(" " + formatPiece(piece) + " ");
            return;
        }

        System.out.print(" " + SideView.getSideColor(side) + formatPiece(piece) + SideView.getResetColor() + " ");
    }

    private String formatPiece(Piece piece) {
        return PIECE_TYPE.get(piece.getType());
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
