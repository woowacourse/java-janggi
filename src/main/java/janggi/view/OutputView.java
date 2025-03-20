package janggi.view;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.Map;

public class OutputView {

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String EXIT = "\u001B[0m";

    public void printJanggiBoard(JanggiBoard janggiBoard) {
        Map<Position, Piece> pieceMap = janggiBoard.getPieceMap();
        for (int y = 9; y >= 0; y--) {
            for (int x = 0; x < 9; x++) {
                Position position = new Position(x, y);
                Piece piece = pieceMap.get(position);
                System.out.print(getSymbol(piece) + " ");
            }
            System.out.println();
        }
    }

    private String getSymbol(Piece piece) {
        if (piece == null) {
            return String.format(BLACK + "ㅁ" + EXIT);
        }

        String color = piece.getSide() == Side.CHO ? RED : BLUE;

        if (piece.getClass() == Cannon.class) {
            return String.format(color + "포" + EXIT);
        }
        if (piece.getClass() == Elephant.class) {
            return String.format(color + "상" + EXIT);
        }
        if (piece.getClass() == Guard.class) {
            return String.format(color + "사" + EXIT);
        }
        if (piece.getClass() == King.class) {
            return String.format(color + "왕" + EXIT);
        }
        if (piece.getClass() == Knight.class) {
            return String.format(color + "마" + EXIT);
        }
        if (piece.getClass() == Pawn.class) {
            return String.format(color + "졸" + EXIT);
        }
        if (piece.getClass() == Rook.class) {
            return String.format(color + "차" + EXIT);
        }
        throw new IllegalArgumentException("잘못된 기물입니다.");
    }

    public void printWinner(Side winner) {
        if (winner == Side.HAN) {
            System.out.println("한나라 승리!");
            return;
        }
        System.out.println("초나라 승리!");
    }
}
