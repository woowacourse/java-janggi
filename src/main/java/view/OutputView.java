package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.BasicPiece;
import domain.player.PlayerProfile;
import domain.position.Position;

public class OutputView {

    private static final String COLUMN_GAP = " ---- ";
    private static final String VERTICAL_LINE = "|";
    private static final String DEFAULT_VERTICAL_GAP = "      ";

    public void printBoard(Board board) {
        printColumnHeader();

        for (int row = MIN_ROW; row < MAX_ROW; row++) {
            printPieceRow(row, board);
            printVerticalRow(row);
        }
        printPieceRow(MAX_ROW, board);
        System.out.println();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPlayerTurnMessage(PlayerProfile profile) {
        System.out.println(profile.name().value() + "(" + profile.team() + ") 님의 차례입니다.");
    }

    public void printResult(PlayerProfile winnerProfile) {
        System.out.println("게임 종료");
        System.out.println("승리 팀: " + winnerProfile.team());
        System.out.println("승리 플레이어: " + winnerProfile.name().value());
    }

    public void printAvailableGames(java.util.List<dao.GameInfo> games) {
        System.out.println("\n=== 진행 중인 게임 목록 ===");
        for (int i = 0; i < games.size(); i++) {
            dao.GameInfo game = games.get(i);
            String currentTurnName = resolveCurrentTurnName(game);
            System.out.println((i + 1) + ". " + game.choName() + " vs " + game.hanName() +
                             " (현재 차례: " + currentTurnName + ")");
        }
        System.out.println((games.size() + 1) + ". 새 게임 시작");
        System.out.println();
    }

    private String resolveCurrentTurnName(dao.GameInfo game) {
        if ("CHO".equals(game.currentTurn())) return game.choName();
        if ("HAN".equals(game.currentTurn())) return game.hanName();
        return game.currentTurn();
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder("       ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column);
            if (column != MAX_COLUMN) {
                sb.append(COLUMN_GAP);
            }
        }
        System.out.println(sb);
    }

    private void printPieceRow(int row, Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            BasicPiece piece = board.findPiece(new Position(row, column));
            sb.append(ConsolePieceMapper.toViewString(piece));

            if (column != MAX_COLUMN) {
                sb.append("---");
            }
        }
        System.out.println(sb);
    }

    private void printVerticalRow(int row) {
        StringBuilder sb = new StringBuilder("       ");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(VERTICAL_LINE);
            if (column != MAX_COLUMN) {
                sb.append(getVerticalGap(row, column));
            }
        }
        System.out.println(sb);
    }

    private String getVerticalGap(int row, int col) {
        if (row == 0 && col == 3) return "  \\   ";
        if (row == 0 && col == 4) return "   /  ";
        if (row == 1 && col == 3) return "  /   ";
        if (row == 1 && col == 4) return "   \\  ";

        if (row == 7 && col == 3) return "  \\   ";
        if (row == 7 && col == 4) return "   /  ";
        if (row == 8 && col == 3) return "  /   ";
        if (row == 8 && col == 4) return "   \\  ";

        return DEFAULT_VERTICAL_GAP;
    }
}