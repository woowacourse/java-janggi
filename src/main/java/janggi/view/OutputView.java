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

    public void printGameOverMessage(TurnDto turnDto) {
        System.out.println(turnDto.teamName() + " 진영이 패배했습니다!!");
    }

    public void printCurrentScore(Score hanScore, Score choScore) {
        System.out.println("한(HAN): " + hanScore.getValue() + "점 | 초(CHO): " + choScore.getValue() + "점");
    }

    public void printMenu() {
        System.out.println("1. 새 게임방 만들기");
        System.out.println("2. 게임방 목록 보기");
        System.out.println("3. 게임방 입장하기");
        System.out.println("4. 종료");
    }

    public void printRoomCreated(Long gameId) {
        System.out.println("게임방 " + gameId + "번이 생성되었습니다.");
    }

    public void printGameRoomList(List<Long> gameIds) {
        if (gameIds.isEmpty()) {
            System.out.println("진행 중인 게임방이 없습니다.");
            return;
        }
        System.out.println("=----- 게임방 목록 -----=");
        for (Long gameId : gameIds) {
            System.out.println("방 번호: " + gameId);
        }
    }
}
