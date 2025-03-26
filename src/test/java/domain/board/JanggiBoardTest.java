package domain.board;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JanggiBoardTest {

    @Nested
    class BoardCoordinateTest {

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생하지 않는다.")
        @Test
        void findPieceTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertDoesNotThrow(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(5, 5)));
        }

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생한다.")
        @Test
        void findPieceWrongCoordinateTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertThatThrownBy(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(2, 2)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("보드에 기물이 있는 경우 기물을 이동 시킬 수 있다")
        @Test
        void boardPieceMoveTest() {
            Piece piece = new Pho(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate from = new JanggiCoordinate(5, 5);
            JanggiCoordinate to = new JanggiCoordinate(3, 3);

            map.put(from, piece);
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> janggiBoard.movePiece(from, to)),
                    () -> assertThat(janggiBoard.isOccupied(from)).isFalse(),
                    () -> assertThat(janggiBoard.isOccupied(to)).isTrue()
            );
        }

        @DisplayName("좌표가 궁성 내부인지 판별한다")
        @ParameterizedTest
        @MethodSource("innerCastleArguments")
        void isInnerCastle(JanggiCoordinate coordinate) {
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            boolean isCastleCoordinate = board.isCastleCoordinate(coordinate);

            assertThat(isCastleCoordinate).isTrue();
        }

        private static Stream<Arguments> innerCastleArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 4)),
                    Arguments.arguments(new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(1, 6)),
                    Arguments.arguments(new JanggiCoordinate(2, 4)),
                    Arguments.arguments(new JanggiCoordinate(2, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 6)),
                    Arguments.arguments(new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(3, 6)),

                    Arguments.arguments(new JanggiCoordinate(8, 4)),
                    Arguments.arguments(new JanggiCoordinate(8, 5)),
                    Arguments.arguments(new JanggiCoordinate(8, 6)),
                    Arguments.arguments(new JanggiCoordinate(9, 4)),
                    Arguments.arguments(new JanggiCoordinate(9, 5)),
                    Arguments.arguments(new JanggiCoordinate(9, 6)),
                    Arguments.arguments(new JanggiCoordinate(10, 4)),
                    Arguments.arguments(new JanggiCoordinate(10, 5)),
                    Arguments.arguments(new JanggiCoordinate(10, 6))
            );
        }

        @DisplayName("궁성 내부 좌표가 아닌 경우를 판별한다")
        @ParameterizedTest
        @MethodSource("outerCastleArguments")
        void isOuterCastle(JanggiCoordinate coordinate) {
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            boolean isCastleCoordinate = board.isCastleCoordinate(coordinate);

            assertThat(isCastleCoordinate).isFalse();
        }

        private static Stream<Arguments> outerCastleArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(4, 4)),
                    Arguments.arguments(new JanggiCoordinate(4, 5)),
                    Arguments.arguments(new JanggiCoordinate(4, 6)),
                    Arguments.arguments(new JanggiCoordinate(4, 4)),
                    Arguments.arguments(new JanggiCoordinate(5, 5)),
                    Arguments.arguments(new JanggiCoordinate(5, 6)),
                    Arguments.arguments(new JanggiCoordinate(6, 4)),
                    Arguments.arguments(new JanggiCoordinate(6, 5)),
                    Arguments.arguments(new JanggiCoordinate(6, 6)),

                    Arguments.arguments(new JanggiCoordinate(8, 1)),
                    Arguments.arguments(new JanggiCoordinate(8, 2)),
                    Arguments.arguments(new JanggiCoordinate(8, 3)),
                    Arguments.arguments(new JanggiCoordinate(5, 1)),
                    Arguments.arguments(new JanggiCoordinate(2, 2)),
                    Arguments.arguments(new JanggiCoordinate(10, 10)),
                    Arguments.arguments(new JanggiCoordinate(10, 7)),
                    Arguments.arguments(new JanggiCoordinate(10, 8)),
                    Arguments.arguments(new JanggiCoordinate(10, 9))
            );
        }
    }

    @Nested
    class PieceFindTest {

        @DisplayName("보드에 궁이 존재하는지 판별한다")
        @ParameterizedTest
        @MethodSource("gungArguments")
        void containsGungTest(Piece piece, boolean ans) {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), piece);
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertThat(janggiBoard.isChoGungAlive()).isEqualTo(ans);
        }

        private static Stream<Arguments> gungArguments() {
            return Stream.of(
                    Arguments.arguments(new Cha(Country.CHO), false),
                    Arguments.arguments(new Gung(Country.CHO), true),
                    Arguments.arguments(new Gung(Country.HAN), false)
            );
        }

        @DisplayName("궁이 잡히면 보드에 궁이 존재하지 않는다")
        @Test
        void containsGungTest2() {
            Piece gung = new Gung(Country.CHO);
            Piece cha = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), gung);
            map.put(new JanggiCoordinate(7, 5), cha);
            JanggiBoard janggiBoard = new JanggiBoard(map);

            janggiBoard.movePiece(new JanggiCoordinate(7, 5), new JanggiCoordinate(5, 5));

            assertThat(janggiBoard.isChoGungAlive()).isFalse();
        }
    }
}