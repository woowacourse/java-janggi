package view;

import domain.Camp;
import domain.PieceType;
import domain.Position;
import domain.piece.Piece;

import java.util.EnumMap;
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

    public void printBoardStatus(Map<Position, Piece> boardStatus) {
        System.out.println("\n  ０ １ ２ ３ ４ ５ ６ ７ ８");

        for (int y = 0; y < 10; y++) {
            System.out.print(FULL_WIDTH_NUMBERS[y] + " ");

            for (int x = 0; x < 9; x++) {
                Position position = new Position(x, y);
                Piece piece = boardStatus.get(position);

                System.out.print(formatPiece(piece) + " ");
            }
            System.out.println();
        }
    }

    private String formatPiece(Piece piece) {
        if (piece == null || piece.getPieceType() == PieceType.NONE) {
            return EMPTY_SPACE;
        }

        String symbol = (piece.getCamp() == Camp.CHO)
                ? CHO_SYMBOLS.get(piece.getPieceType())
                : HAN_SYMBOLS.get(piece.getPieceType());

        String color = (piece.getCamp() == Camp.CHO) ? ANSI_GREEN : ANSI_RED;

        return color + symbol + ANSI_RESET;
    }

    public void printScoreByCamp(Map<Camp, Integer> scoreByCamp) {
        for (Camp camp : scoreByCamp.keySet()) {
            System.out.println(camp.getCampName() + "의 점수: " + scoreByCamp.get(camp));
        }
    }

    public void printWinner(Camp camp) {
        System.out.println(camp.getCampName() + "가 이겼습니다!");
    }

    public void printSavedComplete() {
        System.out.println("저장 완료");
    }

    public void printWrongChoice() {
        System.out.println("잘못된 말 선택입니다.");
    }

    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
