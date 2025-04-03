package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.coordinate.JanggiPosition;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Country;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {

    private final Map<JanggiPosition, Piece> janggiBoard = new HashMap<>();

    @BeforeEach
    void initJanggiBoard() {
        janggiBoard.clear();
    }

    public void placePieceOnJanggiBoard(final JanggiPosition janggiPosition, final Piece piece) {
        janggiBoard.put(janggiPosition, piece);
    }


    @Nested
    @DisplayName("보드 생성")
    class ConstructBoard {

        @DisplayName("보드를 생성했을 때 null이 아니어야 한다.")
        @Test
        void construct1() {
            // given & when
            final Board board = new Board(janggiBoard);

            // then
            assertThat(board.getJanggiBoard()).isNotNull();
        }

        @DisplayName("보드 사이즈가 올바르게 생성된다.")
        @Test
        void boardFactory() {
            // given
            final int expected = 32;

            // when
            final Board board = Board.createInitializedJanggiBoard();

            // then
            Assertions.assertThat(board.getJanggiBoard()).hasSize(expected);
        }

    }

    @Nested
    @DisplayName("보드의 특정 위치에 피스가 존재하는지 검사")
    class ContainsPiece {

        @DisplayName("보드의 특정 위치에 피스가 존재한다면 true, 아니라면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void existPieceByPosition(final JanggiPosition janggiPosition, final boolean expected) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new General(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = board.existPieceByPosition(janggiPosition);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> existPieceByPosition() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 1), true),
                    Arguments.of(new JanggiPosition(1, 2), false)
            );
        }

        @DisplayName("보드의 특정 위치가 포라면 true를 반환한다. 아니라면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void isCannonByPosition(final JanggiPosition janggiPosition, final boolean expected) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.HAN));
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Soldier(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = board.isCannonByPosition(janggiPosition);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> isCannonByPosition() {
            return Stream.of(
                    Arguments.of(new JanggiPosition(1, 1), true),
                    Arguments.of(new JanggiPosition(1, 2), false)
            );
        }

        @DisplayName("Positions 내부에 포가 존재한다면 true를 반환한다. 존재하지 않는다면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void containsCannonByPositions(final List<JanggiPosition> janggiPositions, final boolean expected) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.HAN));
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Soldier(Country.HAN));
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = board.containsCannonByPositions(janggiPositions);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> containsCannonByPositions() {
            return Stream.of(
                    Arguments.of(List.of(new JanggiPosition(1, 1), new JanggiPosition(1, 2)), true),
                    Arguments.of(List.of(new JanggiPosition(2, 2), new JanggiPosition(2, 3)), false)
            );
        }

        @DisplayName("보드의 특정 위치의 기물이 주어진 팀과 같다면 true를 반환한다. 다르다면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void equalsTeamTypeByPosition(final Country country, final boolean expected) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.HAN));
            final Board board = new Board(janggiBoard);

            final JanggiPosition janggiPosition = new JanggiPosition(1, 1);

            // when
            final boolean actual = board.equalsTeamTypeByPosition(janggiPosition, country);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> equalsTeamTypeByPosition() {
            return Stream.of(
                    Arguments.of(Country.HAN, true),
                    Arguments.of(Country.CHO, false)
            );
        }

        @DisplayName("보드에 주어진 범위에 존재하는 기물의 수를 반환")
        @Test
        void calculatePieceCountByPositions() {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.HAN));
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), new Cannon(Country.HAN));
            placePieceOnJanggiBoard(new JanggiPosition(1, 3), new Cannon(Country.HAN));
            final Board board = new Board(janggiBoard);

            final List<JanggiPosition> janggiPositions = List.of(
                    new JanggiPosition(1, 1), new JanggiPosition(1, 2)
            );
            final int expected = 2;

            // when
            final int actual = board.calculatePieceCountByPositions(janggiPositions);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("기물 위치 이동")
    class UpdateJanggiPosition {

        @Test
        @DisplayName("src 위치에 있는 기물이 dest로 옮겨질 수 있다면 이동시킨다.")
        void updatePosition3() {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Chariot(Country.HAN));
            final Board board = new Board(janggiBoard);

            final JanggiPosition src = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 5);
            final Country currentTurnCountry = Country.HAN;

            // when
            board.updatePosition(src, dest, currentTurnCountry);

            // then
            Assertions.assertThat(board.getJanggiBoard())
                    .containsKey(dest)
                    .doesNotContainKey(src);
        }


        @Test
        @DisplayName("src 위치에 기물이 존재하지 않으면 예외가 발생한다")
        void updatePosition() {
            // given
            final Board board = new Board(janggiBoard);

            final JanggiPosition src = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 2);
            final Country currentTurnCountry = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, currentTurnCountry))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("src 위치에 기물이 현재 턴의 팀이 아니라면 예외가 발생한다")
        void updatePosition1() {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.CHO));
            final Board board = new Board(janggiBoard);

            final JanggiPosition src = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 2);
            final Country currentTurnCountry = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, currentTurnCountry))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("src 위치에 기물이 dest로 갈 수 없다면 예외가 발생한다")
        void updatePosition2() {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 1), new Cannon(Country.HAN));
            final Board board = new Board(janggiBoard);

            final JanggiPosition src = new JanggiPosition(1, 1);
            final JanggiPosition dest = new JanggiPosition(1, 2);
            final Country currentTurnCountry = Country.HAN;

            // when & then
            Assertions.assertThatThrownBy(() -> board.updatePosition(src, dest, currentTurnCountry))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("보드의 상태 검증")
    class Is {

        @DisplayName("장군들이 모두 살아있다면 true, 아니라면 false를 반환한다.")
        @ParameterizedTest
        @MethodSource
        void isAliveAllGenerals(final Map<JanggiPosition, Piece> janggiBoard, final boolean expected) {
            // given
            final Board board = new Board(janggiBoard);

            // when
            final boolean actual = board.isAliveAllGenerals();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> isAliveAllGenerals(){
            final Map<JanggiPosition, Piece> aliveAll = Map.of(
                    new JanggiPosition(2, 5), new General(Country.HAN),
                    new JanggiPosition(9, 5), new General(Country.CHO)
            );
            final Map<JanggiPosition, Piece> aliveOnlyChoGeneral = Map.of(
                    new JanggiPosition(2, 5), new General(Country.HAN)
            );
            final Map<JanggiPosition, Piece> aliveOnlyHanGeneral = Map.of(
                    new JanggiPosition(9, 5), new General(Country.CHO)
            );

            return Stream.of(
                    Arguments.of( aliveAll, true),
                    Arguments.of( aliveOnlyHanGeneral, false),
                    Arguments.of( aliveOnlyChoGeneral, false)
            );
        }
    }

    @Nested
    @DisplayName("플레이어들의 점수 계산")
    class CalculateJanggiScore {

        @DisplayName("남은 기물수를 기준으로 점수를 계산한다.")
        @ParameterizedTest
        @MethodSource
        void kill(final Piece piece, final JanggiScore expected, final Country country) {
            // given
            placePieceOnJanggiBoard(new JanggiPosition(1, 2), piece);
            final Board board = new Board(janggiBoard);

            // when
            final JanggiScore actual = board.calculateScore(country);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> kill() {
            final double MAX_SCORE_OF_CHO = 72;
            final double MAX_SCORE_OF_HAN = 73.5;
            final Country HAN = Country.HAN;
            final Country CHO = Country.CHO;

            return Stream.of(
                    Arguments.of(new Cannon(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 7), CHO),
                    Arguments.of(new Chariot(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 13), CHO),
                    Arguments.of(new Elephant(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3), CHO),
                    Arguments.of(new General(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 0), CHO),
                    Arguments.of(new Guard(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 3), CHO),
                    Arguments.of(new Horse(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 5), CHO),
                    Arguments.of(new Soldier(HAN), new JanggiScore(MAX_SCORE_OF_CHO - 2), CHO),
                    Arguments.of(new Cannon(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 7), HAN),
                    Arguments.of(new Chariot(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 13), HAN),
                    Arguments.of(new Elephant(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 3), HAN),
                    Arguments.of(new General(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 0), HAN),
                    Arguments.of(new Guard(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 3), HAN),
                    Arguments.of(new Horse(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 5), HAN),
                    Arguments.of(new Soldier(CHO), new JanggiScore(MAX_SCORE_OF_HAN - 2), HAN)
            );
        }
    }
}
