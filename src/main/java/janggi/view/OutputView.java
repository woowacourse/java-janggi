package janggi.view;

import janggi.domain.board.Column;
import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    public static final String WHITE_COLOR = "\u001B[0m";
    public static final String RED_COLOR = "\u001B[31m";
    public static final String BLUE_COLOR = "\u001B[34m";
    public static final String YELLOW_COLOR = "\u001B[33m";

    public void printBoard(PlayingBoard playingBoard) {
        StringBuilder sb = new StringBuilder();

        sb.append(getBoardHeader());
        for (Row row : Row.values()) {
            sb.append(String.format(getDefaultColor() + "%d | ", row.intValue() % 10));
            for (Column column : Column.values()) {
                Position position = new Position(row, column);
                Piece piece = playingBoard.getPieceBy(position);
                String color = getColorFrom(piece.getColor());
                String pieceName = PieceTypeName.getNameFrom(piece);

                if(piece.isPieceType(PieceType.NONE) && position.inPalace()) {
                    color = YELLOW_COLOR;
                }
                sb.append(color + pieceName + " ");
            }
            sb.append(System.lineSeparator());
            sb.append(getDefaultColor());
        }
        System.out.println(sb);
    }

    private String getBoardHeader() {
        return "  | 1  2 3  4 5  6 7  8 9\n"
                + "--|----------------------\n";
    }

    public void printTurnNotice(TeamColor turnColor) {
        StringBuilder sb = new StringBuilder();

        String teamName = TeamColorName.getNameFrom(turnColor);
        sb.append(getColorFrom(turnColor) + teamName);
        sb.append(getDefaultColor() + " 차례입니다");

        System.out.println(sb);
    }

    public void printWinner(TeamColor turnColor) {
        StringBuilder sb = new StringBuilder();

        String teamName = TeamColorName.getNameFrom(turnColor);
        sb.append(getColorFrom(turnColor) + teamName);
        sb.append(getDefaultColor() + " 승리!\n");

        System.out.println(sb);
    }

    private String getDefaultColor() {
        return WHITE_COLOR;
    }

    private String getColorFrom(TeamColor color) {
        if (color == TeamColor.RED) {
            return RED_COLOR;
        }
        if (color == TeamColor.BLUE) {
            return BLUE_COLOR;
        }
        return WHITE_COLOR;
    }

    public void printGameResult(Map<TeamColor, Integer> teamScore) {
        System.out.println("-------게임 결과-------");
        for (Entry<TeamColor, Integer> entry : teamScore.entrySet()) {
            String teamName = TeamColorName.getNameFrom(entry.getKey());
            int score = entry.getValue();
            System.out.printf("%s 점수: %d\n", teamName, score);
        }
    }

    public void printRooms(List<GameRoomDto> allPlayingRooms) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        StringBuilder sb = new StringBuilder();

        sb.append("================================ 방 목록 ================================");
        sb.append(System.lineSeparator());
        for (int i = 0; i < allPlayingRooms.size(); i++) {
            GameRoomDto room = allPlayingRooms.get(i);
            String teamName = TeamColorName.getNameFrom(TeamColor.valueOf(room.turnColor()));
            sb.append(String.format("[%d] 게임 시작: %s / 최근 진행: %s / 현재 턴: %s%n",
                    i + 1, room.startTime().format(formatter), room.last_updated().format(formatter), teamName));
        }
        System.out.println(sb);
    }
}
