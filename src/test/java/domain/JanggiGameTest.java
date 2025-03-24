package domain;

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

class JanggiGameTest {

    @Nested
    class PieceMoveTest {

        @DisplayName("시작위치와 도착위치가 적합하면 기물을 움직일 수 있다.")
        @ParameterizedTest
        @MethodSource("moveAbleArguments")
        void validatePieceMoveTest(Piece piece, JanggiCoordinate dstCoordinate) {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            map.put(pieceCoordinate, piece);

            JanggiGame janggiGame = new JanggiGame(map);

            assertDoesNotThrow(() -> janggiGame.movePlayerPiece(pieceCoordinate, dstCoordinate));
        }

        private static Stream<Arguments> moveAbleArguments() {
            return Stream.of(
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(4, 7)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(6, 7)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(3, 6)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(4, 3)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(6, 3)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(7, 4)),
                    Arguments.arguments(new Ma(Country.CHO), new JanggiCoordinate(7, 6)),

                    Arguments.arguments(new Byeong(Country.CHO), new JanggiCoordinate(4, 5)),
                    Arguments.arguments(new Byeong(Country.CHO), new JanggiCoordinate(5, 4)),
                    Arguments.arguments(new Byeong(Country.CHO), new JanggiCoordinate(5, 6)),

                    Arguments.arguments(new Cha(Country.CHO), new JanggiCoordinate(4, 5)),
                    Arguments.arguments(new Cha(Country.CHO), new JanggiCoordinate(10, 5)),
                    Arguments.arguments(new Cha(Country.CHO), new JanggiCoordinate(5, 3))
            );
        }

        @DisplayName("시작위치와 도착위치가 적합하지 않으면 기물을 움직일 수 없다.")
        @ParameterizedTest
        @MethodSource("isNotMoveAbleArguments")
        void validatePieceMoveErrorTest(Piece piece, JanggiCoordinate dstCoordinate) {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            map.put(pieceCoordinate, piece);

            JanggiGame janggiGame = new JanggiGame(map);

            assertThatThrownBy(() -> janggiGame.movePlayerPiece(pieceCoordinate, dstCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> isNotMoveAbleArguments() {
            return Stream.of(
                    Arguments.arguments(new Sang(Country.CHO), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new Sang(Country.CHO), new JanggiCoordinate(8, 1)),
                    Arguments.arguments(new Sang(Country.CHO), new JanggiCoordinate(10, 2)),

                    Arguments.arguments(new Sa(Country.CHO), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new Sa(Country.CHO), new JanggiCoordinate(4, 3)),
                    Arguments.arguments(new Sa(Country.CHO), new JanggiCoordinate(5, 3)),

                    Arguments.arguments(new Gung(Country.CHO), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new Gung(Country.CHO), new JanggiCoordinate(4, 3)),
                    Arguments.arguments(new Gung(Country.CHO), new JanggiCoordinate(5, 3))
            );
        }

        @DisplayName("현재 플레이어의 턴이 아닌 경우 기물을 움직일 수 없다.")
        @ParameterizedTest
        @MethodSource("hanMoveArguments")
        void validatePlayerTurnMoveTest(Piece piece, JanggiCoordinate dstCoordinate) {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            map.put(pieceCoordinate, piece);

            JanggiGame janggiGame = new JanggiGame(map);

            assertThatThrownBy(() -> janggiGame.movePlayerPiece(pieceCoordinate, dstCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> hanMoveArguments() {
            return Stream.of(
                    Arguments.arguments(new Sang(Country.HAN), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new Sang(Country.HAN), new JanggiCoordinate(8, 1)),
                    Arguments.arguments(new Sang(Country.HAN), new JanggiCoordinate(10, 2)),

                    Arguments.arguments(new Sa(Country.HAN), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new Sa(Country.HAN), new JanggiCoordinate(4, 3)),
                    Arguments.arguments(new Sa(Country.HAN), new JanggiCoordinate(5, 3))
            );
        }
    }

    @Nested
    class PlayerTurnTest {

        @DisplayName("기물을 움직이면 플레이어의 턴이 바뀐다.")
        @Test
        void validatePieceMoveTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            Piece choPiece = new Ma(Country.CHO);
            JanggiCoordinate choCoordinate = new JanggiCoordinate(3, 5);
            map.put(choCoordinate, choPiece);

            Piece hanPiece = new Gung(Country.HAN);
            JanggiCoordinate hanCoordinate = new JanggiCoordinate(3, 3);
            map.put(hanCoordinate, hanPiece);

            JanggiGame janggiGame = new JanggiGame(map);

            Country firstTurn = janggiGame.getCurrTurn();
            janggiGame.movePlayerPiece(choCoordinate, new JanggiCoordinate(5, 6));

            Country secondTurn = janggiGame.getCurrTurn();
            janggiGame.movePlayerPiece(hanCoordinate, new JanggiCoordinate(3, 2));

            Country thirdTurn = janggiGame.getCurrTurn();

            assertAll(
                    () -> assertThat(firstTurn).isEqualTo(Country.CHO),
                    () -> assertThat(secondTurn).isEqualTo(Country.HAN),
                    () -> assertThat(thirdTurn).isEqualTo(Country.CHO)
            );
        }
    }
}