package janggi.domain.score;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.*;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static janggi.domain.team.Team.CHO;
import static janggi.domain.team.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ScoreTest {

    @DisplayName("장기판에 말이 없으면 한나라 1.5점, 초나라 0점이다")
    @Test
    void 기본_점수_테스트() {
        // given
        Map<Position, Piece> board = new LinkedHashMap<>();
        Score score = Score.from(board);

        // when
        double hanScore = score.getHanScore();
        double choScore = score.getChoScore();

        // then
        assertAll(
                () -> assertThat(hanScore).isEqualTo(1.5),
                () -> assertThat(choScore).isEqualTo(0.0));
    }

    @ParameterizedTest
    @MethodSource("setupPieces")
    void 기물별_점수_테스트(Piece hanPiece, Piece choPiece, double expectedHanScore, double expectedChoScore) {
        // given
        Map<Position, Piece> board = new LinkedHashMap<>();
        board.put(Position.from("25"), hanPiece);
        board.put(Position.from("95"), choPiece);
        Score score = Score.from(board);

        // when
        double hanScore = score.getHanScore();
        double choScore = score.getChoScore();

        // then
        assertAll(
                () -> assertThat(hanScore).isEqualTo(expectedHanScore),
                () -> assertThat(choScore).isEqualTo(expectedChoScore));
    }

    public static Stream<Arguments> setupPieces() {
        return Stream.of(
                Arguments.of(new Chariot(HAN), new Chariot(CHO), 14.5, 13.0),
                Arguments.of(new Cannon(HAN), new Cannon(CHO), 8.5, 7.0),
                Arguments.of(new Horse(HAN), new Horse(CHO), 6.5, 5.0),
                Arguments.of(new Elephant(HAN), new Elephant(CHO), 4.5, 3.0),
                Arguments.of(new Guard(HAN), new Guard(CHO), 4.5, 3.0),
                Arguments.of(new Soldier(HAN), new Soldier(CHO), 3.5, 2.0),
                Arguments.of(new General(HAN), new General(CHO), 1.5, 0),
                Arguments.of(new EmptyPiece(), new EmptyPiece(), 1.5, 0));
    }

    @Test
    void 게임_시작_시_점수_합계_테스트() {
        // given
        Board board = BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT);
        Score score = Score.from(board.showBoard());

        // when
        double hanScore = score.getHanScore();
        double choScore = score.getChoScore();

        // then
        assertAll(
                () -> assertThat(hanScore).isEqualTo(73.5),
                () -> assertThat(choScore).isEqualTo(72.0));
    }
}
