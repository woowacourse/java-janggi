package janggi.view;

import janggi.constants.Color;
import janggi.domain.piece.Score;
import janggi.dto.BoardDto;
import janggi.dto.PositionPieceDto;
import janggi.dto.TurnDto;

import java.util.List;

public class OutputView {

    public void printBoardMap(BoardDto boardDto) {
        List<String> numbers = List.of("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        System.out.println("    一 二 三 四 五 六 七 八 九");
        for (int y = 10; y >= 1; y--) {
            System.out.printf("%s ", numbers.get(y - 1));
            for (int x = 1; x <= 9; x++) {
                PositionPieceDto positionPieceDto = boardDto.findPiece(x, y);
                if (positionPieceDto == null) {
                    System.out.printf(Color.RESET + " %s", "一");
                    continue;
                }
                System.out.printf(positionPieceDto.teamColor() + " %s" + Color.RESET, positionPieceDto.pieceName());
            }
            System.out.println();
        }
    }

    public void printCurrentTurn(TurnDto turn) {
        System.out.println("현재는 " + turn.teamName() + "턴입니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printHasOnGoingGameMessage() {
        System.out.println("기존 게임을 불러왔습니다!!");
    }

    public void printGameOverMessage(TurnDto turnDto) {
        System.out.println(turnDto.teamName() + " 진영이 패배했습니다!!");
    }

    public void printCurrentScore(Score hanScore, Score choScore) {
        System.out.println("한(HAN): " + hanScore.getValue() + "점 | 초(CHO): " + choScore.getValue() + "점");
    }

    public void printNewGameMessage() {
        System.out.println("새로운 게임을 생성했습니다.");
    }
}
