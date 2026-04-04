package view;

import domain.pieces.PieceType;
import domain.pieces.Side;
import java.util.List;
import view.dto.PieceDto;

public class OutputView {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public void printSangSetupType(Side side) {
        System.out.printf("%s의 상차림을 선택하세요.%n", sideName(side));
        System.out.println("1. 왼상차림(象馬象馬, 상마상마)");
        System.out.println("2. 오른상차림(馬象馬象, 마상마상)");
        System.out.println("3. 안상차림(馬象象馬, 마상상마)");
        System.out.println("4. 바깥상차림(象馬馬象, 상마마상)");
    }

    public void printTurn(Side side) {
        if (side.isCho()) {
            System.out.println("현재 턴: 초");
        }
        if (side.isHan()) {
            System.out.println("현재 턴: 한");
        }
    }

    public void printMoveGuide() {
        System.out.println("이동할 기물의 출발지와 도착지를 입력하세요.");
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printBoard(List<List<PieceDto>> board) {
        for (int row = 9; row >= 0; row--) {
            System.out.printf("%2d ", row);
            for (int column = 0; column <= 8; column++) {
                System.out.print("|" + displayPiece(board.get(row).get(column)));
            }
            System.out.println("|");
            System.out.println("   ---------------------------------------------");
        }
        System.out.println("     0    1    2    3    4    5    6    7    8");
    }

    private String sideName(Side side) {
        if (side.isCho()) {
            return "초나라";
        }
        return "한나라";
    }

    private String displayPiece(PieceDto pieceDto) {
        String text = pieceSymbol(pieceDto);
        String padded = String.format(" %-2s", text);

        if (pieceDto.isEmpty()) {
            return padded;
        }
        if (pieceDto.side().isCho()) {
            return GREEN + padded + RESET;
        }
        return RED + padded + RESET;
    }

    private String pieceSymbol(PieceDto pieceDto) {
        if (pieceDto.pieceType() == PieceType.EMPTY) {
            return "・";
        }
        if (pieceDto.pieceType() == PieceType.GUNG) {
            if (pieceDto.side().isCho()) {
                return "將";
            }
            return "宮";
        }
        if (pieceDto.pieceType() == PieceType.JOL_BYEONG) {
            if (pieceDto.side().isCho()) {
                return "兵";
            }
            return "卒";
        }
        return basicSymbol(pieceDto.pieceType());
    }

    private String basicSymbol(PieceType pieceType) {
        if (pieceType == PieceType.CHA) {
            return "車";
        }
        if (pieceType == PieceType.MA) {
            return "馬";
        }
        if (pieceType == PieceType.SANG) {
            return "象";
        }
        if (pieceType == PieceType.SA) {
            return "士";
        }
        if (pieceType == PieceType.PO) {
            return "包";
        }
        throw new IllegalArgumentException("지원하지 않는 기물입니다.");
    }
}
