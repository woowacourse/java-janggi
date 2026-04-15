package janggi.view;

import janggi.domain.board.BoardFormation;
import janggi.domain.common.Position;
import janggi.dto.BoardResponse;
import janggi.dto.GameResponse;
import janggi.dto.PieceResponse;
import janggi.dto.PositionResponse;
import janggi.dto.ScoreResponse;
import janggi.dto.TeamResponse;
import java.util.List;

public class OutputView {

    private static final String[] X_VALUES = {"１", "２", "３", "４", "５", "６", "７", "８", "９"};

    public void printErrorMessage(String message) {
        System.out.printf("%s%n", message);
    }

    public void printBoard(BoardResponse board) {
        System.out.print("   ");
        for (int x = 1; x <= 9; x++) {
            System.out.print(X_VALUES[x - 1] + " ");
        }
        System.out.println();

        for (int y = 1; y <= 10; y++) {
            System.out.print(String.format("%2d ", y));
            printRow(board, y);
        }
        System.out.println();
    }

    public void printTurnMessage(TeamResponse team) {
        System.out.printf("현재 %s 차례입니다%n", team.getName());
    }

    public void printMoveInfo() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력하세요.");
    }

    public void printMoveChoiceInfo() {
        System.out.println("이동하고자 하는 목표 지점의 좌표를 입력하세요.");
    }

    public void printBoardFormation(TeamResponse team) {
        System.out.printf("%s 상차림을 선택하세요.%n", team.getName());
        for (BoardFormation boardFormation : BoardFormation.values()) {
            System.out.printf("%d. %s%n", boardFormation.getChoice(), boardFormation.getName());
        }
    }

    private void printRow(BoardResponse board, int y) {
        for (int x = 1; x <= 9; x++) {
            PositionResponse targetPos = PositionResponse.from(new Position(x, y));

            PieceResponse piece = board.getBoardInfos().get(targetPos);

            System.out.print(piece.getDisplayPiece());
        }
        System.out.println();
    }

    public void printTeamScore(ScoreResponse teamScores) {
        System.out.println(teamScores.getHanName() + " " + teamScores.getHanScore());
        System.out.println(teamScores.getChoName() + " " + teamScores.getChoScore() + "\n");
    }

    public void printWinner(TeamResponse winner) {
        System.out.println("승자는 " + winner.getName() + "입니다.");
        System.out.println("축하합니다!");
    }

    public void printStartOption() {
        System.out.println("게임 옵션을 선택해주세요.");
        System.out.println("1. 새로하기 2. 이어하기");
    }

    public void printOngoingGames(List<GameResponse> gameResponses) {
        for (GameResponse gameResponse : gameResponses) {
            System.out.println("게임 ID: " + gameResponse.getId() + " / 차례: " + gameResponse.getTurn());
        }
    }
}
