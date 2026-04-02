package janggi.view;

import janggi.domain.dynasty.Dynasty;
import janggi.view.dto.PositionDto;
import janggi.view.mapper.DynastyColorMapper;
import janggi.view.mapper.DynastyMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String INVALID_HORSE_ELEPHANT_POSITION_INPUT_FORMAT_MESSAGE = "상차림 법을 숫자로 입력해주세요. 입력값: %s";
    private static final String INVALID_HORSE_ELEPHANT_POSITION_INPUT_RANGE_MESSAGE = "1, 2, 3, 4 중 하나의 숫자를 입력해주세요. 입력값: %s";
    private static final String INVALID_POSITION_FORMAT_MESSAGE = "위치를 콤마로 구분된 두 개의 숫자로 올바르게 입력해주세요. 입력값: %s";

    public int readHorseElephantPosition(Dynasty dynasty) {
        String dynastyKorean = DynastyMapper.toKoreanWithColor(dynasty);
        System.out.println(dynastyKorean + "나라의 상차림 법을 숫자로 입력해주세요.");
        System.out.println("1: 마상마상, 2: 마상상마, 3: 상마상마, 4: 상마마상");
        String position = scanner.nextLine();
        try {
            int ordinal = Integer.parseInt(position);
            if (ordinal < 1 || ordinal > 4) {
                throw new IllegalArgumentException(
                        String.format(INVALID_HORSE_ELEPHANT_POSITION_INPUT_RANGE_MESSAGE, ordinal));
            }
            return ordinal;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format(INVALID_HORSE_ELEPHANT_POSITION_INPUT_FORMAT_MESSAGE, position));
        }
    }

    public PositionDto readPieceWantToMove(Dynasty dynasty) {
        System.out.printf("현재 턴은 %s입니다.\n", DynastyMapper.toKoreanWithColor(dynasty));
        System.out.println("움직이고 싶은 기물을 선택해주세요.(좌표로 입력해주세요. 예시: 1, 3)");
        return readPosition();
    }

    public PositionDto readPositionToMove() {
        System.out.println("움직이고 싶은 위치를 선택해주세요.(좌표로 입력해주세요)");
        return readPosition();
    }

    private PositionDto readPosition() {
        String input = scanner.nextLine();
        String[] split = input.split(",");
        if (split.length != 2) {
            throw new IllegalArgumentException(String.format(INVALID_POSITION_FORMAT_MESSAGE, input));
        }
        List<Integer> position = Arrays.stream(split)
                .map(str -> {
                    try {
                        return Integer.parseInt(str.strip());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(String.format(INVALID_POSITION_FORMAT_MESSAGE, input));
                    }
                }).toList();

        return PositionDto.from(position.getFirst(), position.getLast());
    }
    
}
