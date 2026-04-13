package view;

import static view.Formatter.CAMP_NAMES;

import domain.Camp;
import domain.PieceType;
import dto.BoardStatusDto;
import dto.PositionStatusDto;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    // 전각 문자 통일을 위한 설정
    private static final String EMPTY_SPACE = "．"; // 전각 마침표 (U+FF0E)
    private static final String[] FULL_WIDTH_NUMBERS = {
            "０", "１", "２", "３", "４", "５", "６", "７", "８", "９"
    };
    private static final Map<PieceType, String> CHO_SYMBOLS = new EnumMap<>(PieceType.class);
    private static final Map<PieceType, String> HAN_SYMBOLS = new EnumMap<>(PieceType.class);

    static {
        CHO_SYMBOLS.put(PieceType.GENERAL, "楚");
        CHO_SYMBOLS.put(PieceType.CHARIOT, "車");
        CHO_SYMBOLS.put(PieceType.CANNON, "包");
        CHO_SYMBOLS.put(PieceType.HORSE, "馬");
        CHO_SYMBOLS.put(PieceType.ELEPHANT, "象");
        CHO_SYMBOLS.put(PieceType.GUARD, "士");
        CHO_SYMBOLS.put(PieceType.SOLDIER, "卒");

        HAN_SYMBOLS.put(PieceType.GENERAL, "漢");
        HAN_SYMBOLS.put(PieceType.CHARIOT, "車");
        HAN_SYMBOLS.put(PieceType.CANNON, "砲");
        HAN_SYMBOLS.put(PieceType.HORSE, "馬");
        HAN_SYMBOLS.put(PieceType.ELEPHANT, "相");
        HAN_SYMBOLS.put(PieceType.GUARD, "士");
        HAN_SYMBOLS.put(PieceType.SOLDIER, "兵");
    }

    public void printBoardStatus(BoardStatusDto boardStatusDto) {
        List<List<PositionStatusDto>> rows = boardStatusDto.positionStatusDtos();

        // 1. 상단 열 인덱스 출력 (전각 숫자 사용, 앞부분 공백 2칸으로 시작점 정렬)
        System.out.println("\n  ０ １ ２ ３ ４ ５ ６ ７ ８");

        for (int i = 0; i < rows.size(); i++) {
            // 2. 좌측 행 인덱스 출력 (전각 숫자 사용)
            System.out.print(FULL_WIDTH_NUMBERS[i] + " ");

            List<PositionStatusDto> row = rows.get(i);
            for (PositionStatusDto cell : row) {
                // 3. 기물 출력 및 띄어쓰기 1칸
                System.out.print(formatPiece(cell) + " ");
            }
            System.out.println();
        }
    }

    public void printWrongChoice() {
        System.out.println("잘못된 말 선택입니다.");
    }

    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    private String formatPiece(PositionStatusDto cell) {
        if (cell.pieceType() == PieceType.NONE) {
            return EMPTY_SPACE; // 전각 마침표 반환
        }

        String symbol = (cell.camp() == Camp.CHO)
                ? CHO_SYMBOLS.get(cell.pieceType())
                : HAN_SYMBOLS.get(cell.pieceType());

        String color = (cell.camp() == Camp.CHO) ? ANSI_GREEN : ANSI_RED;

        return color + symbol + ANSI_RESET;
    }

    public void printResult(Camp camp, double hanScore, double choScore) {
        System.out.println(CAMP_NAMES.get(camp) + "의 승리입니다.");
        System.out.println(CAMP_NAMES.get(camp) + " 점수: " + hanScore);
        System.out.println(CAMP_NAMES.get(camp) + " 점수: " + choScore);
    }
}
