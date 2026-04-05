package view;

import domain.pieces.Side;
import java.util.List;
import view.dto.GameResultDto;
import view.dto.PieceDto;

public class OutputView {
    private final BoardRenderer boardRenderer = new BoardRenderer();

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
        System.out.println(boardRenderer.render(board));
    }

    private String sideName(Side side) {
        if (side.isCho()) {
            return "초나라";
        }
        return "한나라";
    }

    public void printGameResult(GameResultDto gameResult) {
        if (!gameResult.isEnded()) {
            return;
        }

        if (gameResult.winner().isCho()) {
            System.out.println("게임 종료: 초 승리");
            return;
        }
        System.out.println("게임 종료: 한 승리");
    }
}
