package domain.piece;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SaTest {
    @Nested
    class SaCoordinateTest {

        @DisplayName("기물은 아군 기물을 잡을 수 없다.")
        @Test
        void validateTeamTarget() {
            Piece piece = new Sa(Country.HAN);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate mySa = new JanggiCoordinate(3, 5);
            JanggiCoordinate ourMa = new JanggiCoordinate(2, 5);

            map.put(mySa, piece);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateDestination(board, mySa, ourMa))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("기물은 적군 기물만 잡을 수 있다.")
        @Test
        void validateEnemyTarget() {
            Piece piece = new Sa(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myGung = new JanggiCoordinate(3, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(3, 4);

            map.put(myGung, piece);
            map.put(enemyMa, maEnemy);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, myGung, enemyMa));
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @ParameterizedTest
        @MethodSource("reachableArguments")
        void validateReachableCoordinate(JanggiCoordinate coordinate) {
            Piece piece = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(2, 5);
            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate));
        }

        private static Stream<Arguments> reachableArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 4)),
                    Arguments.arguments(new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(1, 6)),
                    Arguments.arguments(new JanggiCoordinate(2, 4)),
                    Arguments.arguments(new JanggiCoordinate(2, 6)),
                    Arguments.arguments(new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(3, 6))
            );
        }

        @DisplayName("기물이 현재 위치에서 2보다 큰 거리면 도달 할 수 없다.")
        @ParameterizedTest
        @MethodSource("unreachableArguments")
        void validateUnreachableCoordinate(JanggiCoordinate coordinate) {
            Piece piece = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 4);
            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> unreachableArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(3, 6))
            );
        }

        @DisplayName("해당 기물은 궁성 밖을 나갈 수 없다.")
        @ParameterizedTest
        @MethodSource("outerCastleArguments")
        void outerCastleTest(JanggiCoordinate coordinate) {
            Piece piece = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 4);
            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> outerCastleArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(3, 6))
            );
        }
    }

    @Nested
    class SaCastleTest {

        @DisplayName("사는 궁성 안에서만 움직일 수 있다")
        @ParameterizedTest
        @MethodSource("innerCastle")
        void innerCastleTest(JanggiCoordinate from, JanggiCoordinate to) {
            Piece sa = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            map.put(from, sa);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> sa.validatePieceMove(board, from, to));
        }

        private static Stream<Arguments> innerCastle() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 4), new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(1, 4), new JanggiCoordinate(2, 4)),
                    Arguments.arguments(new JanggiCoordinate(1, 4), new JanggiCoordinate(2, 5)),

                    Arguments.arguments(new JanggiCoordinate(1, 5), new JanggiCoordinate(1, 4)),
                    Arguments.arguments(new JanggiCoordinate(1, 5), new JanggiCoordinate(2, 5)),
                    Arguments.arguments(new JanggiCoordinate(1, 5), new JanggiCoordinate(1, 6)),

                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(1, 4)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(1, 6)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(2, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(2, 6)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(3, 4)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 5), new JanggiCoordinate(3, 6))
            );
        }

        @DisplayName("사는 궁성 밖으로 나갈 수 없다")
        @ParameterizedTest
        @MethodSource("outerCastle")
        void outerCastleTest(JanggiCoordinate from, JanggiCoordinate to) {
            Piece sa = new Sa(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            map.put(from, sa);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> sa.validatePieceMove(board, from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> outerCastle() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 4), new JanggiCoordinate(1, 3)),
                    Arguments.arguments(new JanggiCoordinate(2, 4), new JanggiCoordinate(2, 3)),
                    Arguments.arguments(new JanggiCoordinate(3, 4), new JanggiCoordinate(3, 3)),
                    Arguments.arguments(new JanggiCoordinate(3, 4), new JanggiCoordinate(4, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 6), new JanggiCoordinate(3, 7)),
                    Arguments.arguments(new JanggiCoordinate(3, 6), new JanggiCoordinate(4, 6))
            );
        }

        @DisplayName("사는 궁성 내 선을 따라서만 움직일 수 있다.")
        @ParameterizedTest
        @MethodSource("castleLineCoordinates")
        void castleLineCoordinateTest(JanggiCoordinate from, JanggiCoordinate to) {
            Piece sa = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            map.put(from, sa);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> sa.validatePieceMove(board, from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> castleLineCoordinates() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(2, 4), new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 4), new JanggiCoordinate(3, 5)),

                    Arguments.arguments(new JanggiCoordinate(3, 5), new JanggiCoordinate(2, 4)),
                    Arguments.arguments(new JanggiCoordinate(3, 5), new JanggiCoordinate(2, 6)),

                    Arguments.arguments(new JanggiCoordinate(2, 6), new JanggiCoordinate(1, 5)),
                    Arguments.arguments(new JanggiCoordinate(2, 6), new JanggiCoordinate(3, 5)),

                    Arguments.arguments(new JanggiCoordinate(1, 5), new JanggiCoordinate(2, 4)),
                    Arguments.arguments(new JanggiCoordinate(1, 5), new JanggiCoordinate(2, 6))
            );
        }
    }
}