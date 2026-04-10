package view;

import dao.GameRoomRawData;
import domain.board.Board;
import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class OutputView {
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 10;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 9;

    public void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int row = MAX_ROW; row >= MIN_ROW; row--) {
            sb.append(row).append("\t");
            appendRow(sb, board, row);
            sb.append(System.lineSeparator());
        }
        appendColumnHeader(sb);
        System.out.println(sb);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printRoomList(List<GameRoomRawData> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("저장된 게임방이 없습니다.");
            return;
        }
        System.out.println("=== 게임방 목록 ===");
        for (GameRoomRawData room : rooms) {
            System.out.printf("[%d] %s (턴: %s, 상태: %s)%n",
                    room.id(), room.name(), room.currentTurn(), room.status());
        }
    }

    public void printGameCreated(long roomId) {
        System.out.println("게임방이 생성되었습니다. (ID: " + roomId + ")");
    }

    public void printResult(Team winner, double choScore, double hanScore) {
        System.out.println("게임이 종료되었습니다.");
        System.out.println(Team.CHO + " 점수: " + choScore);
        System.out.println(Team.HAN + " 점수: " + hanScore);
        if (winner == Team.NONE) {
            System.out.println("무승부입니다.");
            return;
        }
        System.out.println("승자: " + winner);
    }

    private void appendColumnHeader(StringBuilder sb) {
        sb.append(" \t");
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column).append("\t");
        }
        sb.append(System.lineSeparator());
    }

    private void appendRow(StringBuilder sb, Board board, int row) {
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.pieceAt(new Position(row, column));
            sb.append(PieceMapper.toDisplayName(piece)).append("\t");
        }
    }
}
