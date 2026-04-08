package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

public class OutputView {
    private static final int MAX_ROW = 9;
    private static final int MAX_COLUMN = 8;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_CHO = "\u001B[34m";
    private static final String ANSI_HAN = "\u001B[31m";
    private static final String ANSI_GUIDE = "\u001B[90m";

    public static void printBoard(Board board) {
        System.out.println(ANSI_GUIDE + "   ０ １ ２ ３ ４ ５ ６ ７ ８" + ANSI_RESET);

        for (int row = MAX_ROW; row >= 0; row--) {
            System.out.print(ANSI_GUIDE + row + " ｜" + ANSI_RESET);
            for (int col = 0; col <= MAX_COLUMN; col++) {
                if (!board.isExistPieceAt(new Position(col, row))) {
                    System.out.print(ANSI_GUIDE + "＋" + ANSI_RESET);
                } else {
                    Piece piece = board.pieceAt(new Position(col, row));
                    System.out.print(colorize(piece.camp(), symbolOf(piece.getPieceType(), piece.camp())));
                }
                if (col < MAX_COLUMN) System.out.print(" ");
            }
            System.out.println(ANSI_GUIDE + "｜" + ANSI_RESET);
        }
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printWinner(Camp winner) {
        String winnerName = (winner == Camp.CHO) ? "초" : "한";
        System.out.println(winnerName + "의 승리입니다.");
    }

    private static String symbolOf(PieceType pieceType, Camp camp) {
        return switch (pieceType) {
            case GENERAL -> "궁";
            case GUARD -> "사";
            case CHARIOT -> "차";
            case CANNON -> "포";
            case HORSE -> "마";
            case ELEPHANT -> "상";
            case SOLDIER -> (camp == Camp.CHO) ? "졸" : "병";
        };
    }

    private static String colorize(Camp camp, String symbol) {
        if (camp == Camp.CHO) {
            return ANSI_CHO + symbol + ANSI_RESET;
        }
        return ANSI_HAN + symbol + ANSI_RESET;
    }
}
