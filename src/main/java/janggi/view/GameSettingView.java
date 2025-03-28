package janggi.view;

import janggi.controller.MainOption;
import janggi.domain.piece.TeamColor;
import janggi.dto.GameRoomDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class GameSettingView {
    public static final String WHITE_COLOR = "\u001B[0m";
    public static final String RED_COLOR = "\u001B[31m";
    public static final String BLUE_COLOR = "\u001B[34m";
    public static final String YELLOW_COLOR = "\u001B[33m";

    private static final Scanner scanner = new Scanner(System.in);

    public MainOption readMainOption() {
        System.out.println("게임 옵션을 선택하세요.\n"
                + "1. 새로운 게임 진행\n"
                + "2. 게임 이어서 하기");

        String input = scanner.nextLine();
        validateNumeric(input);

        return MainOption.from(Integer.parseInt(input));
    }

    public int readRoomSelectNumber() {
        System.out.println(setDefaultColor() + "게임을 선택하세요.");
        String input = scanner.nextLine();
        validateNumeric(input);
        return Integer.parseInt(input);
    }

    public void printRooms(List<GameRoomDto> allPlayingRooms) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        StringBuilder sb = new StringBuilder();

        sb.append("================================ 방 목록 ================================");
        sb.append(System.lineSeparator());
        for (int i = 0; i < allPlayingRooms.size(); i++) {
            GameRoomDto room = allPlayingRooms.get(i);
            TeamColor teamColor = TeamColor.valueOf(room.turnColor());
            String teamName = setColorBy(teamColor) + TeamColorName.getNameFrom(teamColor);

            sb.append(String.format(setDefaultColor() + "[%d] 게임 시작: %s / 최근 진행: %s / 현재 턴: %s%n",
                    i + 1, room.startTime().format(formatter), room.last_updated().format(formatter), teamName));
        }
        System.out.println(sb);
    }

    private String setDefaultColor() {
        return TextColor.DEFAULT.getColor();
    }

    private String setColorBy(TeamColor color) {
        if (color == TeamColor.RED) {
            return TextColor.RED.getColor();
        }
        if (color == TeamColor.BLUE) {
            return TextColor.BLUE.getColor();
        }
        return TextColor.DEFAULT.getColor();
    }

    public int readBoardSetup(TeamColor teamColor) {
        StringBuilder sb = new StringBuilder();
        String teamName = TeamColorName.getNameFrom(teamColor);

        sb.append(System.lineSeparator());
        sb.append(teamName)
                .append(" - 상차림 종류 번호를 입력해주세요. ex) 1\n");
        sb.append(setupTypeNotice());
        System.out.println(sb);

        String input = scanner.nextLine();
        validateNumeric(input);

        return Integer.parseInt(input);
    }

    private void validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효한 숫자 입력이 아닙니다.");
        }
    }

    private String setupTypeNotice() {
        StringBuilder sb = new StringBuilder();
        sb.append("1. 마상상마\n")
                .append("2. 상마마상\n")
                .append("3. 마상마상\n")
                .append("4. 상마상마");
        return sb.toString();
    }
}
