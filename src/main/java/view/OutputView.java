package view;

import domain.CellSnapshot;
import domain.Side;
import domain.coordinate.Position;
import view.message.PieceView;

import java.util.List;
import view.message.SideView;

public class OutputView {


    public void printBoard(CellSnapshot[][] board) {
        System.out.println("   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 9; j++) {
                printCell(board[i][j]);
            }
            System.out.println();
        }
    }

    public void printAvailablePositions(List<Position> possibleMoves) {
        int index = 1;
        for (Position possibleMove : possibleMoves) {
            System.out.printf("%d. (%d, %d)\n", index++, possibleMove.row(), possibleMove.col());
        }
    }

    public void printWinner(Side winner) {
        System.out.println(
                "\n게임이 종료되었습니다. 승자는 " + SideView.getSideColor(winner) + SideView.from(winner)
                        + SideView.getResetColor() + "입니다.");
    }

    private void printCell(CellSnapshot cell) {
        String name = PieceView.from(cell.type());

        if (cell.side() == Side.NEUTRAL) {
            System.out.print(" " + name + " ");
            return;
        }
        System.out.print(" " + SideView.getSideColor(cell.side()) + name + SideView.getResetColor() + " ");
    }

    public void printEachScores(double hanScore, double chuScore) {
        System.out.println("\n최종 기물 점수:");
        System.out.println(SideView.getSideColor(Side.HAN) + "한나라: " + hanScore + SideView.getResetColor());
        System.out.println(SideView.getSideColor(Side.CHU) + "초나라: " + chuScore + SideView.getResetColor());
    }

    public void printCanNotMovablePieceError() {
        System.out.println("\n해당 기물은 움직일 수 있는 좌표가 없습니다. 다른 기물을 선택해주세요.\n");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
