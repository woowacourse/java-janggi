package domain.game;

import domain.board.BoardState;
import domain.board.IntersectionState;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreCalculatorTest {
    @Test
    @DisplayName("보드 상태를 기준으로 각 팀의 점수를 합산한다. (한은 +1.5 보정이 적용된다.)")
    void should_calculate_score_with_han_start_bonus() {
        BoardState boardState = new BoardState(List.of(
                new IntersectionState(new Point(0, 0), PieceType.CHARIOT, Team.CHO),
                new IntersectionState(new Point(0, 1), PieceType.SOLDIER, Team.CHO),
                new IntersectionState(new Point(9, 0), PieceType.HORSE, Team.HAN)
        ));

        ScoreCalculator calculator = new ScoreCalculator();

        GameScore score = calculator.calculate(boardState);

        Assertions.assertThat(score.cho()).isEqualTo(15.0);
        Assertions.assertThat(score.han()).isEqualTo(6.5);
    }

    @Test
    @DisplayName("한나라(HAN)는 기물이 없더라도 +1.5 보정 점수를 가진다.")
    void should_grant_han_start_bonus_even_when_han_has_no_piece() {
        BoardState boardState = new BoardState(List.of(
                new IntersectionState(new Point(0, 0), PieceType.SOLDIER, Team.CHO)
        ));

        ScoreCalculator calculator = new ScoreCalculator();

        GameScore score = calculator.calculate(boardState);

        Assertions.assertThat(score.cho()).isEqualTo(2.0);
        Assertions.assertThat(score.han()).isEqualTo(1.5);
    }

    @Test
    @DisplayName("NONE 팀/기물은 점수 합산에서 제외된다.")
    void should_ignore_none_team() {
        BoardState boardState = new BoardState(List.of(
                new IntersectionState(new Point(0, 0), PieceType.CHARIOT, Team.NONE),
                new IntersectionState(new Point(0, 1), PieceType.SOLDIER, Team.CHO),
                new IntersectionState(new Point(9, 0), PieceType.GUARD, Team.HAN)
        ));

        ScoreCalculator calculator = new ScoreCalculator();

        GameScore score = calculator.calculate(boardState);

        Assertions.assertThat(score.cho()).isEqualTo(2.0);
        Assertions.assertThat(score.han()).isEqualTo(4.5);
    }
}

