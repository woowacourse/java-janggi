package janggi.view;

import static janggi.util.InputParser.parseHorseElephantPositionOrdinal;
import static janggi.util.InputParser.parsePosition;

import janggi.dto.DynastyDto;
import janggi.dto.PositionDto;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public InputView() {
    }

    public int readHorseElephantPosition(DynastyDto dynastyDto) {
        System.out.println(dynastyDto.dynastyName() + "나라의 상차림 법을 숫자로 입력해주세요.");
        System.out.println("1: 마상마상, 2: 마상상마, 3: 상마상마, 4: 상마마상");

        String input = scanner.nextLine();
        return parseHorseElephantPositionOrdinal(input);
    }

    public PositionDto readPieceWantToMove(DynastyDto dynastyDto) {
        System.out.printf("현재 턴은 %s입니다.\n", dynastyDto.dynastyName());
        System.out.println("움직이고 싶은 기물을 선택해주세요.(좌표로 입력해주세요. 예시: 1, 3)");
        return readPosition();
    }

    public PositionDto readPositionToMove() {
        System.out.println("움직이고 싶은 위치를 선택해주세요.(좌표로 입력해주세요)");
        return readPosition();
    }

    private PositionDto readPosition() {
        String input = scanner.nextLine();
        List<Integer> rowAndColumn = parsePosition(input);
        return PositionDto.from(rowAndColumn);
    }

}
