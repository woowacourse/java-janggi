import java.util.Map;

public class OutputView {
    private static final String BLANK = "＿";

    public void printBoard(Map<Dot, Piece> pieces) {
        for (Dot dot : Dot.CACHE) {
            if (dot.getX() == 0) {
                System.out.println();
            }

            if (!pieces.containsKey(dot)) {
                System.out.printf("%2s", BLANK);
                continue;
            }

            Piece piece = pieces.get(dot);

            if (piece.getDynasty() == Dynasty.CHO) {
                System.out.print("\u001B[32m" + " " + piece.getName() + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + piece.getName() + "\u001B[0m");

        }
    }

    private String getPieceOrBlank(Dot dot, Map<Dot, Piece> pieces) {
        if (pieces.containsKey(dot)) {
            return pieces.get(dot).getName();
        }
        return BLANK;
    }
}

//꾹이(이기욱) if (side == Side.CHO) {
//    return "\u001B[32m" + pieceBehavior.toName() + "\u001B[0m";
//}
//return "\u001B[31m" + pieceBehavior.toName() + "\u001B[0m";

// 9  차 상 마 사 ＿ 사 마 상 차
// 8  ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
// 7  ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
// 6  병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
// 5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
// 4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
// 3  병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
// 2  ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
// 1  ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
// 0  차 상 마 사 ＿ 사 마 상 차
//    0 1 2 3 4 5 6 7 8
//
//