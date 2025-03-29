package view;

import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public void printBoard(
        final Map<BoardPosition, Piece> pieces,
        final Team team
    ) {
        System.out.println("현재 턴: " + team.getTitle());
        for (int i = BoardPosition.MAX_Y; i >= BoardPosition.MIN_Y; i--) {
            printRowHeader(i);
            for (int j = BoardPosition.MIN_X; j <= BoardPosition.MAX_X; j++) {
                final BoardPosition boardPosition = new BoardPosition(j, i);
                final Piece piece = pieces.get(boardPosition);
                printPiece(piece);
            }
            System.out.println();
        }
        printColumnHeader();
    }

    private void printRowHeader(final int i) {
        System.out.print(i + " ");
    }

    private void printPiece(final Piece piece) {
        if (piece == null) {
            System.out.print(" . ");
            return;
        }

        final String pieceTitle = piece.getPieceType()
            .getTitle();
        final String color = createColorCode(piece.getTeam());
        System.out.print(" " + color + pieceTitle + RESET + " ");
    }

    private void printColumnHeader() {
        System.out.print("  ");
        for (int j = BoardPosition.MIN_Y; j < BoardPosition.MAX_Y; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
    }

    private String createColorCode(final Team team) {
        if (team == Team.RED) {
            return RED;
        }

        return GREEN;
    }

    public void printGameOver(final Team currentTeam) {
        System.out.println("게임 종료");
        System.out.println(currentTeam.getTitle() + "팀의 승리입니다.");
    }
}
