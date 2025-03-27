package view;

import static pieceProperty.PieceType.BYEONG;
import static pieceProperty.PieceType.CHA;
import static pieceProperty.PieceType.JANGGUN;
import static pieceProperty.PieceType.JOL;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.PO;
import static pieceProperty.PieceType.SA;
import static pieceProperty.PieceType.SANG;

import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import pieceProperty.PieceType;
import player.Pieces;

public class OutputView {

    private static final Map<PieceType, String> PIECE_TYPE_NAME_MAP = new HashMap<>(
            Map.of(JANGGUN, "왕", BYEONG, "병", CHA, "차", JOL, "졸", MA, "마",
                    PO, "포", SA, "사", SANG, "상")
    );
    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";
    private static final String PIECE_DELIMITER = " | ";
    private static final String ROW = "  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9";

    public void printJanggiPan(final Pieces hanPieces, final Pieces choPieces) {
        String[][] janggiPan = new String[10][9];


        System.out.println(ROW);

        for (Piece piece : hanPieces.getPieces()) {
            int row = piece.currentPosition().getRow();
            int col = piece.currentPosition().getCol();
            PieceType pieceType = piece.getPieceType();
            janggiPan[row][col] = RED_COLOR_CODE +  PIECE_TYPE_NAME_MAP.get(pieceType) + EXIT_CODE + PIECE_DELIMITER;
        }

        for (Piece piece : choPieces.getPieces()) {
            int row = piece.currentPosition().getRow();
            int col = piece.currentPosition().getCol();
            PieceType pieceType = piece.getPieceType();
            janggiPan[row][col] = GREEN_COLOR_CODE +  PIECE_TYPE_NAME_MAP.get(pieceType) + EXIT_CODE + PIECE_DELIMITER;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (janggiPan[i][j] == null) {
                    janggiPan[i][j] = "  | ";
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < 9; j++) {
                System.out.print(janggiPan[i][j]);
            }
            System.out.println();
        }

    }

    public void printResult(final Boolean isChoKingDie) {
        if (isChoKingDie) {
            System.out.println("한나라 승리!");
            return;
        }

        System.out.println("초나라 승리!");
    }

}
