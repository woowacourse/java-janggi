package janggi.view;

import janggi.domain.GameStatus;
import janggi.domain.Player;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import java.util.Map;

public class OutputView {

    public static final String EMPTY_POSITION = "＿";

    public void printBoard(final Map<Position, Piece> board) {
        for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
            System.out.printf("%-2d", row);
            for (int column = Position.MIN_COLUMN; column <= Position.MAX_COLUMN; column++) {
                printPieceInBoard(board, row, column);
            }
            System.out.println();
        }
        System.out.println("  １２３４５６７８９");
    }

    private void printPieceInBoard(final Map<Position, Piece> board, final int row, final int column) {
        Position position = Position.of(row, column);
        if (board.containsKey(position)) {
            Piece piece = board.get(position);
            System.out.print(PieceName.getPieceName(piece));
            return;
        }
        System.out.print(EMPTY_POSITION);
    }

    public void printCurrentTurn(final Player player) {
        System.out.println(player.getName() + "(" + TeamName.getCountryName(player.getTeam()) + ") 팀의 턴입니다!");
    }

    public void printGameWinMessage(GameStatus gameStatus) {
        System.out.println(GameStatusText.getText(gameStatus));
    }

    public void printEndMessage() {
        System.out.println("게임을 종료합니다.");
    }
}
