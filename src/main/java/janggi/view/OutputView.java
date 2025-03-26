package janggi.view;

import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Map;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String GRAY = "\u001B[37m";
    private static final String EXIT = "\u001B[0m";

    public void printJanggiBoard(JanggiBoard janggiBoard) {
        Map<Position, Piece> pieceMap = janggiBoard.getPlacedPieces().getValues();
        for (int y = 9; y >= 0; y--) {
            System.out.print(y + " ");
            for (int x = 0; x < 9; x++) {
                Position position = new Position(x, y);
                Piece piece = pieceMap.get(position);
                System.out.print(getSymbol(piece) + " ");
            }
            System.out.println();
        }
        System.out.println("  0ㅤ1ㅤ2ㅤ3  4ㅤ5ㅤ6ㅤ7ㅤ8");
    }

    private String getSymbol(Piece piece) {
        if (piece == null) {
            return String.format(GRAY + "ㅁ" + EXIT);
        }

        String color = piece.getSide() == Side.CHO ? RED : BLUE;

        return switch (piece.getPieceType()) {
            case CANNON -> String.format(color + "포" + EXIT);
            case ELEPHANT -> String.format(color + "상" + EXIT);
            case GUARD -> String.format(color + "사" + EXIT);
            case KING -> String.format(color + "왕" + EXIT);
            case KNIGHT -> String.format(color + "마" + EXIT);
            case PAWN -> String.format(color + "졸" + EXIT);
            case ROOK -> String.format(color + "차" + EXIT);
        };
    }

    public void printWinner(Side winner) {
        if (winner == Side.HAN) {
            System.out.println("한나라 승리!");
            return;
        }
        System.out.println("초나라 승리!");
    }
}
