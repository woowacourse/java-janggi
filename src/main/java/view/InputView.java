package view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String PLACEMENT_OPTION_HEADER = "\n=== [%s] 진영 마/상 위치 선택 ===";
    private static final String PLACEMENT_OPTION_TABLE =
            "┌────┬──────────────────────────────────┬──────────────────────────────────────┐\n" +
                    "│ 번호│            배치 형태                │               설명                    │\n" +
                    "├────┼──────────────────────────────────┼──────────────────────────────────────┤\n" +
                    "│  1 │ [차][마][상][사][장][사][상][마][차] │ 귀마형   : 마가 바깥쪽                    │\n" +
                    "│  2 │ [차][상][마][사][장][사][마][상][차] │ 원앙마형 : 마가 안쪽                      │\n" +
                    "│  3 │ [차][마][상][사][장][사][마][상][차] │ 좌귀마형 : 왼쪽 귀마, 오른쪽 원앙마          │\n" +
                    "│  4 │ [차][상][마][사][장][사][상][마][차] │ 우귀마형 : 왼쪽 원앙마, 오른쪽 귀마          │\n" +
                    "└────┴──────────────────────────────────┴──────────────────────────────────────┘";
    private static final String PLACEMENT_OPTION_PROMPT = "번호 입력 (1~4): ";

    private static final String PIECE_LOCATION_HEADER = "\n움직일 기물 위치 입력";
    private static final String PIECE_LOCATION_FORMAT = "  형식: 열,행  (예: 5,1)";
    private static final String PIECE_LOCATION_RANGE = "  열: 1~9 (가로), 행: 1~10 (세로)";
    private static final String PIECE_LOCATION_PROMPT = "> ";

    private static final String DESTINATION_HEADER = "\n이동할 위치 입력";
    private static final String DESTINATION_FORMAT = "  형식: 열,행  (예: 5,2)";
    private static final String DESTINATION_RANGE = "  열: 1~9 (가로), 행: 1~10 (세로)";
    private static final String DESTINATION_PROMPT = "> ";

    public String inputPlacementOption(String turn) {
        System.out.printf(PLACEMENT_OPTION_HEADER + "%n", turn);
        System.out.println(PLACEMENT_OPTION_TABLE);
        System.out.print(PLACEMENT_OPTION_PROMPT);
        return scanner.nextLine();
    }

    public List<Integer> inputPieceLocation() {
        System.out.println(PIECE_LOCATION_HEADER);
        System.out.println(PIECE_LOCATION_FORMAT);
        System.out.println(PIECE_LOCATION_RANGE);
        System.out.print(PIECE_LOCATION_PROMPT);
        return InputParser.parse(scanner.nextLine());
    }

    public List<Integer> inputDestination() {
        System.out.println(DESTINATION_HEADER);
        System.out.println(DESTINATION_FORMAT);
        System.out.println(DESTINATION_RANGE);
        System.out.print(DESTINATION_PROMPT);
        return InputParser.parse(scanner.nextLine());
    }
}
