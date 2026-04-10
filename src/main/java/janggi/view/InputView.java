package janggi.view;

import static janggi.util.InputParser.parseGameId;
import static janggi.util.InputParser.parseHorseElephantPositionOrdinal;
import static janggi.util.InputParser.parsePosition;

import janggi.dto.DynastyDto;
import janggi.dto.PositionDto;
import janggi.util.Console;
import java.util.List;

public class InputView {

    private static final String YES = "Y";

    public InputView() {
    }

    public boolean readWantToRestore() {
        System.out.println("저장된 게임을 실행하시겠습니까? (Y/N)");
        return YES.equals(Console.readLine());
    }

    public long readGameIdToRestore() {
        System.out.println("불러오고싶은 게임의 ID를 입력하세요.");
        String input = Console.readLine();
        return parseGameId(input);
    }

    public int readHorseElephantPosition(DynastyDto dynastyDto) {
        System.out.println(dynastyDto.dynastyName() + "나라의 상차림 법을 숫자로 입력해주세요.");
        System.out.println("1: 마상마상, 2: 마상상마, 3: 상마상마, 4: 상마마상");

        String input = Console.readLine();
        return parseHorseElephantPositionOrdinal(input);
    }

    public PositionDto readPieceWantToMove(DynastyDto dynastyDto) {
        System.out.printf("현재 턴은 %s입니다.\n", dynastyDto.dynastyName());
        System.out.println("움직이고 싶은 기물을 선택해주세요.(좌표로 입력해주세요. 예시: 1, 3)");
        return readPosition();
    }

    public PositionDto readDestinationPosition() {
        System.out.println("움직이고 싶은 위치를 선택해주세요.(좌표로 입력해주세요)");
        return readPosition();
    }

    private PositionDto readPosition() {
        String input = Console.readLine();
        List<Integer> rowAndColumn = parsePosition(input);
        return PositionDto.from(rowAndColumn);
    }

}
