package view;

import util.InputParser;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String PLACEMENT_OPTION_HEADER = "\n=== [%s] 진영 마/상 위치 선택 ===";
    private static final String PLACEMENT_OPTION_TABLE = """
            ┌────┬──────────────────────────────────┬──────────────────────────────────────┐
            │ 번호│            배치 형태                │               설명                    │
            ├────┼──────────────────────────────────┼──────────────────────────────────────┤
            │  1 │ [차][마][상][사][장][사][상][마][차] │ 귀마형   : 마가 바깥쪽                    │
            │  2 │ [차][상][마][사][장][사][마][상][차] │ 원앙마형 : 마가 안쪽                      │
            │  3 │ [차][마][상][사][장][사][마][상][차] │ 좌귀마형 : 왼쪽 귀마, 오른쪽 원앙마          │
            │  4 │ [차][상][마][사][장][사][상][마][차] │ 우귀마형 : 왼쪽 원앙마, 오른쪽 귀마          │
            └────┴──────────────────────────────────┴──────────────────────────────────────┘""";
    private static final String PLACEMENT_OPTION_PROMPT = "번호 입력 (1~4): ";

    private static final String PIECE_LOCATION_MESSAGE = """
            움직일 기물 위치 입력
              형식: 열,행  (예: 5,1)
              열: 1~9 (가로), 행: 1~10 (세로)""";
    private static final String PIECE_LOCATION_PROMPT = "> ";

    private static final String DESTINATION_MESSAGE = """
            이동할 위치 입력
              형식: 열,행  (예: 5,2)
              열: 1~9 (가로), 행: 1~10 (세로)""";
    private static final String DESTINATION_PROMPT = "> ";

    public String inputPlacementOption(String turn) {
        System.out.printf(PLACEMENT_OPTION_HEADER + "%n", turn);
        System.out.println(PLACEMENT_OPTION_TABLE);
        System.out.print(PLACEMENT_OPTION_PROMPT);
        return scanner.nextLine();
    }

    public List<Integer> inputPieceLocation() {
        System.out.println(PIECE_LOCATION_MESSAGE);
        System.out.print(PIECE_LOCATION_PROMPT);
        return InputParser.parse(scanner.nextLine());
    }

    public List<Integer> inputDestination() {
        System.out.println(DESTINATION_MESSAGE);
        System.out.print(DESTINATION_PROMPT);
        return InputParser.parse(scanner.nextLine());
    }
}
