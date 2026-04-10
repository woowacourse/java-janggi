package janggi.view;

import janggi.application.dto.GameRoomDto;
import janggi.domain.dynasty.Dynasty;
import janggi.view.dto.PositionDto;
import janggi.view.mapper.DynastyMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String INVALID_INPUT_RANGE_MESSAGE = "%d ~ %d 중 하나의 숫자를 입력해주세요. 입력값: %s";
    private static final String INVALID_INPUT_FORMAT_MESSAGE = "%s을(를) 숫자로 입력해주세요. 입력값: %s";
    private static final String INVALID_POSITION_FORMAT_MESSAGE = "위치를 콤마로 구분된 두 개의 숫자로 올바르게 입력해주세요. 입력값: %s";

    public int readHorseElephantPosition(Dynasty dynasty) {
        String dynastyKorean = DynastyMapper.toKoreanWithColor(dynasty);
        System.out.println(dynastyKorean + "나라의 상차림 법을 숫자로 입력해주세요.");
        System.out.println("1: 마상마상, 2: 마상상마, 3: 상마상마, 4: 상마마상");
        String position = scanner.nextLine();
        try {
            int ordinal = Integer.parseInt(position);
            validateRange(ordinal, 1, 4);
            return ordinal;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format(INVALID_INPUT_FORMAT_MESSAGE, "상차림 법", position));
        }
    }

    public PositionDto readPieceWantToMove(Dynasty dynasty) {
        System.out.printf("현재 턴은 %s입니다.\n", DynastyMapper.toKoreanWithColor(dynasty));
        System.out.println("움직이고 싶은 기물을 선택해주세요.(좌표로 입력해주세요. 예시: 1, 3)");
        return readPosition();
    }

    public PositionDto readPositionToMove() {
        System.out.println("움직이고 싶은 위치를 선택해주세요.(좌표로 입력해주세요. 예시: 1, 3)");
        return readPosition();
    }

    private PositionDto readPosition() {
        String input = scanner.nextLine();
        String[] split = input.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException(String.format(INVALID_POSITION_FORMAT_MESSAGE, input));
        }
        List<Integer> position = convertPositionToInt(split, input);

        return PositionDto.from(position.getFirst(), position.getLast());
    }

    private static List<Integer> convertPositionToInt(String[] split, String input) {
        return Arrays.stream(split)
                .map(str -> {
                    try {
                        int parsed = Integer.parseInt(str.trim());
                        if(parsed == 0) { // 출력은 0으로, 내부적으로는 10으로 처리되므로
                            return 10;
                        }
                        return parsed;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(String.format(INVALID_POSITION_FORMAT_MESSAGE, input));
                    }
                }).toList();
    }

    public int readGameOption() {
        System.out.println("1. 새 게임 만들기 2. 기존 게임 이어서 하기");
        String strOption = scanner.nextLine();
        try {
            int option = Integer.parseInt(strOption);
            validateRange(option, 1,2);
            return option;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format(INVALID_INPUT_FORMAT_MESSAGE, "게임 옵션", strOption));
        }
    }

    public String readRoomName() {
        System.out.println("방 이름을 입력해주세요");
        return scanner.nextLine();
    }

    private static void validateRange(int input, int min, int max) {
        if (input < min || input > max) {
            throw new IllegalArgumentException(
                    String.format(INVALID_INPUT_RANGE_MESSAGE, min, max, input));
        }
    }

    public Long readSelectedGame(List<GameRoomDto> gameRoomDtos) {

        String strGameNum = scanner.nextLine();
        try {
            int option = Integer.parseInt(strGameNum);
            validateRange(option, 1, gameRoomDtos.size());
            return gameRoomDtos.get(option - 1).id();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format(INVALID_INPUT_FORMAT_MESSAGE, "게임번호", strGameNum));
        }
    }
}
