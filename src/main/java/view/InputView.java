package view;

import domain.JanggiCoordinate;
import domain.dto.GameRoomDto;

import java.util.List;
import java.util.Scanner;

public class InputView {
    public final static JanggiCoordinate GAME_STOP_COORDINATE = new JanggiCoordinate(-1, -1);

    private static final int ROW_IDX = 0;
    private static final int COL_IDX = 1;

    private final static Scanner scanner = new Scanner(System.in);

    public JanggiCoordinate readMovePiece() {
        System.out.println("옮길 기물을 입력해주세요 :  -1을 입력하면 게임이 종료됩니다.");
        String coordinate = scanner.nextLine();

        if (coordinate.equals("-1")) {
            return GAME_STOP_COORDINATE;
        }

        validateInput(coordinate);
        String[] parsedCoordinate = coordinate.split("");
        return convertToJanggiCoordinate(parsedCoordinate[ROW_IDX], parsedCoordinate[COL_IDX]);
    }

    public JanggiCoordinate readMoveDestination() {
        System.out.println("옮길 위치를 입력해 주세요 : ");
        String coordinate = scanner.nextLine();
        validateInput(coordinate);
        String[] parsedCoordinate = coordinate.split("");
        return convertToJanggiCoordinate(parsedCoordinate[ROW_IDX], parsedCoordinate[COL_IDX]);
    }

    private JanggiCoordinate convertToJanggiCoordinate(String row, String col) {
        int rowNum = Integer.parseInt(row);
        int colNum = Integer.parseInt(col);

        if (rowNum == 0) {
            rowNum = 10;
        }
        return new JanggiCoordinate(rowNum, colNum);
    }

    private void validateInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다. 예시) 23, 35");
        }
    }

    public GameCommand getCreateCommand() {
        System.out.println("게임을 새로 만드시려면 1, 게임을 불러오려면 2를 입력하세요");
        String input = scanner.nextLine();

        return GameCommand.convertToCommand(input);
    }

    public PageCommand getPageMoveCommand() {
        System.out.println("이전 페이지를 불러오려면 1, 다음 페이지를 불러오려면 2, 게임을 불러오려면 3을 입력하세요.");
        String input = scanner.nextLine();

        return PageCommand.convertToCommand(input);
    }

    public String getGameName(List<GameRoomDto> gameRoomDTOs) {
        try {
            System.out.println("불러올 게임 이름을 입력해주세요.");
            String input = scanner.nextLine();
            if (gameRoomDTOs.stream().noneMatch(gameRoomDTO -> gameRoomDTO.gameRoomName().equals(input))) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 게임 이름입니다.");
            }
            return input;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다.");
        }
    }

    public String getCreateGameName() {
        System.out.println("생성할 게임의 이름을 입력해 주세요 : ");
        return scanner.nextLine();
    }
}
