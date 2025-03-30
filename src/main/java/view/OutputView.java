package view;

import domain.Board;
import domain.GameResult;
import domain.Player;
import domain.Result;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

    public void printGameList(final List<String> gameNames) {
        System.out.println("===== 진행중인 게임 목록 =====");
        for (int i = 1; i <= gameNames.size(); i++) {
            System.out.println(i + " : " + gameNames.get(i - 1));
        }
        System.out.println("===========================");
    }

    public void printBoard(final Board board) {
        System.out.println();
        System.out.println("===== 장기판 상태 =====");
        System.out.println("   1 2 3 4 5 6 7 8 9");
        List<ArrayList<String>> defaultBoard = createDefaultBoard();
        updateDefaultBoard(board.gamePlayers(), defaultBoard);
        printBoardDetails(defaultBoard);
        System.out.println("=====================");
        System.out.println();
        printPlayerScores(board);
    }

    public void printGameResult(final Board board, final GameResult result) {
        System.out.println("게임이 종료되었습니다.");
        printPlayerScores(board);

        System.out.println("===== 장기 게임 결과 =====");
        Map<Player, Result> map = result.getResultMap();
        for (Entry<Player, Result> entry : map.entrySet()) {
            System.out.println(entry.getKey().getTeam().getName() + " : " + entry.getValue().getMessage());
        }
    }

    public void printErrorMessage(final String message) {
        System.out.println(System.lineSeparator() + "[ERROR] : " + message);
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
            String color = player.getTeam().getColor();

            List<Piece> pieces = board.get(player).pieces();
            updatePiecesToDefaultBoard(defaultBoard, pieces, color);
        }
    }

    private void updatePiecesToDefaultBoard(final List<ArrayList<String>> defaultBoard, final List<Piece> pieces,
                                            final String color) {
        for (Piece piece : pieces) {
            Position position = piece.getPosition();

            int row = position.row() - 1;
            int column = position.column() - 1;

            ArrayList<String> rows = defaultBoard.get(column);
            rows.set(row, color + PieceView.findNameByClass(piece) + COLOR_RESET);
            defaultBoard.set(column, rows);
        }
    }

    private void printPlayerScores(final Board board) {
        System.out.println("===== 장기 한/초 점수 =====");
        Map<Player, Pieces> playerPiecesMap = board.gamePlayers();
        for (Player player : playerPiecesMap.keySet()) {
            System.out.println(player.getTeam().getName() + " : " + player.getScore().value() + "점");
        }
        System.out.println("========================");
        System.out.println();
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
