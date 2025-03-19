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

    public void printBoard(final Board board) {
        List<ArrayList<String>> defaultBoard = createDefaultBoard();
        updateDefaultBoard(board.getBoard(), defaultBoard);
        printBoardDetails(defaultBoard);
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
        board.forEach(pieces -> {
            pieces.forEach(System.out::print);
            System.out.println();
        });
    }
}
