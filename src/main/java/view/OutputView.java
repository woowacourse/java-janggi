package view;

import domain.Board;
import domain.PieceColor;
import domain.Player;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OutputView {

    private static final String COLOR_RESET = "\033[0m";

    public void printGameStart() {
        System.out.print("""
                 장기 게임을 시작합니다.
                               
                 - K : 왕
                 - h : 마
                 - r : 차
                 - e : 상
                 - a : 사
                 - c : 포
                 - p : 졸
                """);
    }

    public void printBoard(final Board board) {
        System.out.println();
        System.out.println("===== 장기판 상태 =====");
        System.out.println("   1 2 3 4 5 6 7 8 9");
        List<ArrayList<String>> defaultBoard = createDefaultBoard();
        updateDefaultBoard(board.getBoard(), defaultBoard);
        printBoardDetails(defaultBoard);
        System.out.println("=====================");
        System.out.println();
    }

    public void printWinner(final Player winner) {
        System.out.println(winner.getName() + "의 승리로 게임이 종료되었습니다.");
        System.out.println("우승자 : " + winner.getName());
    }

    private List<ArrayList<String>> createDefaultBoard() {
        List<ArrayList<String>> result = Stream.generate(() -> new ArrayList<String>())
                .limit(10)
                .collect(Collectors.toList());

        result.forEach(list -> IntStream.range(0, 9)
                .forEach(i -> list.add("-")));
        return result;
    }

    private void updateDefaultBoard(final Map<Player, Pieces> board, final List<ArrayList<String>> defaultBoard) {
        for (Player player : board.keySet()) {
            PieceColor color = player.getColor();

            List<Piece> pieces = board.get(player).getPieces();
            updatePiecesToDefaultBoard(defaultBoard, pieces, color);
        }
    }

    private void updatePiecesToDefaultBoard(final List<ArrayList<String>> defaultBoard, final List<Piece> pieces,
                                            final PieceColor color) {
        for (Piece piece : pieces) {
            Position position = piece.getPosition();

            int row = position.getRow() - 1;
            int column = position.getColumn() - 1;

            ArrayList<String> rows = defaultBoard.get(column);
            rows.set(row, color.getColor() + piece.getName() + COLOR_RESET);
            defaultBoard.set(column, rows);
        }
    }

    private void printBoardDetails(final List<ArrayList<String>> board) {
        IntStream.range(1, 11)
                .forEach(i -> {
                    System.out.printf("%2d|", i);
                    System.out.print(String.join(" ", board.get(i - 1)));
                    System.out.println("|");
                });
    }
}
