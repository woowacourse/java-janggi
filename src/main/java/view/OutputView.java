package view;

import domain.Team;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.util.Map;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public void printBoard(
            final Map<BoardPosition, Piece> pieces,
            final Team team
    ) {
        System.out.println();
        System.out.println("현재 턴: " + team.getTitle());
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 9; j++) {
                final BoardPosition boardPosition = new BoardPosition(j, i);
                final Piece piece = pieces.get(boardPosition);

                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    final String color = createColorCode(piece.getTeam());
                    System.out.print(" " + color + piece.getPieceType().getTitle() + RESET + " ");
                }
            }
            System.out.println();
        }
    }

    public void printInputExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
        System.out.println("다시 입력해주세요.");
    }

    public void printWinnerTeam(final Team winnerTeam) {
        System.out.println();
        System.out.println("게임 종료");
        System.out.println("승리 팀은 " + winnerTeam.getTitle());
    }

    private String createColorCode(final Team team) {
        if (team == Team.RED) {
            return RED;
        }

        return GREEN;
    }
}
