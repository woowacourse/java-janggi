package view;

import domain.dto.BoardDto;
import domain.dto.PieceDto;
import domain.dto.ResultDto;
import domain.dto.ScoreDto;

import java.util.List;

public class OutputView {

    public static void printJanggiBoard(BoardDto boardDto) {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        List<List<PieceDto>> board = boardDto.board();

        for (int i = 0; i < board.size(); i++) {
            System.out.print(i + " ");
            for (PieceDto piece : board.get(i)) {
                System.out.print(piece.name() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printCurrentScore(ScoreDto scoreDto) {
        System.out.println("현재 점수");
        System.out.printf("초: %.1f, 한: %.1f\n", scoreDto.choScore(), scoreDto.hanScore());
    }

    public static void printWinnerTeam(ResultDto resultDto) {
        System.out.println("===== 게임 결과 =====");
        System.out.printf("초: %.1f, 한: %.1f\n", resultDto.choScore(), resultDto.hanScore());
        System.out.println("최종 승리팀: " + resultDto.winnerTeam());
    }
}
