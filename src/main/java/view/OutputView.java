package view;

import domain.state.Side;
import domain.piece.PieceType;
import dto.BoardDto;

import dto.PieceDto;
import dto.PositionDto;
import dto.ScoreDto;
import view.message.PieceFormatter;
import view.message.SideView;

public class OutputView {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    public void printBoard(BoardDto boardDto) {
        System.out.println("\n  0ㅤㅤㅤ1ㅤㅤ 2ㅤㅤ 3ㅤ ㅤ4ㅤ ㅤ5ㅤㅤㅤ6ㅤ ㅤ7ㅤㅤ 8");
        for (int i = 0; i < COL_SIZE; i++) {
            System.out.print(i);
            for (int j = 0; j < ROW_SIZE; j++) {
                printPieceBySide(boardDto.board().get(new PositionDto(i, j)));

                if (j < 8) {
                    System.out.print("ㅡ");
                }
            }
            System.out.println();
            drawBoardGrid(i);
        }
    }

    public void printScore(ScoreDto scoreDto) {
        System.out.printf(
                "%s 점수: %s %s 점수: %s\n",
                SideView.from(Side.CHU),
                formatScore(scoreDto.chuSideScore()),
                SideView.from(Side.HAN),
                formatScore(scoreDto.hanSideScore())
        );
    }

    public void printTurnPassMessage(Side side) {
        System.out.println("\n" + SideView.from(side) + "가 한 수를 쉽니다.");
    }

    public void printSurrenderMessage(Side side) {
        System.out.println("\n" + SideView.from(side) + "가 기권했습니다.");
    }

    public void printVictoryMessage(Side side) {
        System.out.println("\n" + SideView.from(side) + "가 승리했습니다 !!!");
    }

    public void printCheckMessage() {
        System.out.println("\n!!! 장군 !!!");
    }

    public void printCheckMateMessage() {
        System.out.println("\n!!! 외통수 !!!");
    }

    public void printKingDeadMessage(Side side) {
        System.out.println("\n" + SideView.from(side) + "의 장이 잡혔습니다...");
    }

    public void printLoadGameMessage() {
        System.out.println("이전 게임을 불러옵니다.");
    }

    private void drawBoardGrid(int i) {
        if (i < 9) {
            for (int c = 0; c < 9; c++) {
                if (c == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print("ㅤ");
                }

                String line = " ㅣ ";
                System.out.print(getPalaceLine(i, c, line));
            }

            System.out.println();
        }
    }

    private String getPalaceLine(int i, int c, String line) {
        if (i == 0 || i == 7) {
            if (c == 3) {
                line = " ㅣ \\";
            } else if (c == 4) {
                line = "ㅣ /";
            } else if (c == 5) {
                line = "ㅣ ";
            }
        }

        if (i == 1 || i == 8) {
            if (c == 3) {
                line = " ㅣ /";
            } else if (c == 4) {
                line = "ㅣ \\";
            } else if (c == 5) {
                line = "ㅣ ";
            }
        }
        return line;
    }

    private void printPieceBySide(PieceDto pieceDto) {
        Side side = pieceDto.getSide();

        if (pieceDto.getPieceType() == PieceType.EMPTY) {
            System.out.print(" " + PieceFormatter.from(pieceDto.getPieceType()) + " ");
            return;
        }

        System.out.print(" " + SideView.getSideColor(side) + PieceFormatter.from(pieceDto.getPieceType()) + SideView.getResetColor() + " ");
    }

    private String formatScore(double score) {
        if (score == (long) score) {
            return String.valueOf((long) score);
        }
        return String.valueOf(score);
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
