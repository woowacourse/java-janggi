package janggi.view;

import static java.util.stream.Collectors.joining;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.view.dto.CampDto;
import janggi.view.dto.PiecePositionDto;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private static final String TITLE = LINE_SEPARATOR + "[장기판]";
    private static final String EMPTY_CELL = "．";

    private static final String RESET = "\u001B[0m";
    private static final String[] FULL_WIDTH_NUMBERS = {
            "０", "１", "２", "３", "４", "５", "６", "７", "８", "９"
    };

    private static final String SCORE = "%s나라 점수: %.1f";
    private static final String WINNER = "%s나라가 승리하였습니다!! 축하드립니다!!";
    private static final String GAME_ROOM = "현재 게임방: ";
    private static final String EMPTY_GAME = "현재 게임방이 존재하지 않습니다.";

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printBoard(Map<Position, Piece> boardState) {
        System.out.println(renderBoard(toPiecePositions(boardState)) + LINE_SEPARATOR);
    }

    public void printWinner(Camp camp) {
        String winnerName = toCampName(camp);
        System.out.printf((WINNER) + "%n", winnerName);
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.from(entry.getKey(), entry.getValue()))
                .toList();
    }

    private String renderBoard(List<PiecePositionDto> piecePositions) {
        String[][] board = initializeBoard();
        applyPieces(board, piecePositions);
        return TITLE + LINE_SEPARATOR + renderHeader() + LINE_SEPARATOR + renderRows(board);
    }

    private String[][] initializeBoard() {
        String[][] board = new String[ROW_SIZE][COLUMN_SIZE];
        for (String[] row : board) {
            Arrays.fill(row, EMPTY_CELL);
        }
        return board;
    }

    private String renderHeader() {
        return "  " + IntStream.range(0, COLUMN_SIZE)
                .mapToObj(this::fullWidthNumber)
                .collect(joining(" "));
    }

    private void applyPieces(String[][] board, List<PiecePositionDto> piecePositions) {
        for (PiecePositionDto piecePosition : piecePositions) {
            board[piecePosition.row()][piecePosition.col()] = colorize(piecePosition);
        }
    }

    private String renderRows(String[][] board) {
        return IntStream.range(0, ROW_SIZE)
                .mapToObj(row -> renderRow(row, board[row]))
                .collect(joining(LINE_SEPARATOR));
    }

    private String renderRow(int row, String[] cells) {
        return fullWidthNumber(row) + ' ' + String.join(" ", cells);
    }

    private String colorize(PiecePositionDto piecePosition) {
        CampDto camp = piecePosition.camp();
        return camp.color() + piecePosition.type() + RESET;
    }

    private String fullWidthNumber(int number) {
        return FULL_WIDTH_NUMBERS[number];
    }

    public void printScore(Map<Camp, Double> eachCampScore) {
        for (Camp camp : eachCampScore.keySet()) {
            String campName = toCampName(camp);
            System.out.printf(SCORE + "%n", campName, eachCampScore.get(camp));
        }
    }

    private String toCampName(Camp camp) {
        CampDto campDto = CampDto.from(camp);
        return campDto.color() + campDto.name() + RESET;
    }

    public void printExistGameRoom(List<Long> gameIds) {
        if (gameIds.isEmpty()) {
            System.out.println(LINE_SEPARATOR + EMPTY_GAME);
            return;
        }
        System.out.println(LINE_SEPARATOR + GAME_ROOM + gameIds);
    }
}
