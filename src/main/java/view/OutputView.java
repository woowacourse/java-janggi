package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

public class OutputView {

    public void printBoard(Board board) {
        System.out.println("\n  0   1   2   3   4   5   6   7   8 (X)");
        for (int y = 9; y >= 0; y--) {
            System.out.print(y + " ");
            for (int x = 0; x <= 8; x++) {
                Position currentPosition = new Position(x, y);
                Piece piece = board.getPiece(currentPosition);
                System.out.print(getPieceSymbol(piece) + " ");
            }
            System.out.println();
        }
        System.out.println("(Y)\n");
    }

    public void printError(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    private String getPieceSymbol(Piece piece) {
        if (piece == null) {
            return " . ";
        }

        String teamPrefix = piece.team() == Team.CHO ? "초" : "한";
        String typeName = switch (piece.pieceType()) {
            case GENERAL -> "장";
            case CHARIOT -> "차";
            case CANNON -> "포";
            case HORSE -> "마";
            case ELEPHANT -> "상";
            case GUARD -> "사";
            case SOLDIER -> piece.team() == Team.CHO ? "졸" : "병";
        };

        return teamPrefix + typeName;
    }
}
