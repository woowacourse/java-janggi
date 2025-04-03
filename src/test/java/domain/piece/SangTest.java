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

class SangTest {
    @Nested
    class SangCoordinateTest {

        @DisplayName("기물은 아군 기물을 잡을 수 없다.")
        @Test
        void validateTeamTarget() {
            Piece piece = new Sang(Country.HAN);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate ourMa = new JanggiCoordinate(3, 8);

            map.put(myPiece, piece);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateDestination(board, myPiece, ourMa))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("기물은 적군 기물만 잡을 수 있다.")
        @Test
        void validateEnemyTarget() {
            Piece piece = new Sang(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(3, 8);

            map.put(myPiece, piece);
            map.put(enemyMa, maEnemy);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, myPiece, enemyMa));
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @ParameterizedTest
        @MethodSource("reachableArguments")
        void validateReachableCoordinate(JanggiCoordinate coordinate) {
            Piece piece = new Sang(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate));
        }

        private static Stream<Arguments> reachableArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 8)),
                    Arguments.arguments(new JanggiCoordinate(7, 8)),
                    Arguments.arguments(new JanggiCoordinate(2, 7)),
                    Arguments.arguments(new JanggiCoordinate(2, 3)),
                    Arguments.arguments(new JanggiCoordinate(3, 2)),
                    Arguments.arguments(new JanggiCoordinate(7, 2)),
                    Arguments.arguments(new JanggiCoordinate(8, 3)),
                    Arguments.arguments(new JanggiCoordinate(8, 7))
            );
        }

        @DisplayName("기물이 현재 위치에서 도달 불가능한 위치를 검사한다")
        @ParameterizedTest
        @MethodSource("unreachableArguments")
        void validateUnreachableCoordinate(JanggiCoordinate coordinate) {
            Piece piece = new Sang(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> unreachableArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 5)),
                    Arguments.arguments(new JanggiCoordinate(8, 1)),
                    Arguments.arguments(new JanggiCoordinate(10, 2))
            );
        }

        @DisplayName("상의 이동경로에 다른 기물이 막고 있으면 이동할 수 없다")
        @Test
        void validateMoveHasObstacle() {
            Piece sang = new Sang(Country.HAN);
            Ma obstaclePiece = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate sangCoordinate = new JanggiCoordinate(7, 5);
            JanggiCoordinate obstacleCoordinate1 = new JanggiCoordinate(6, 5);
            JanggiCoordinate obstacleCoordinate2 = new JanggiCoordinate(5, 6);
            JanggiCoordinate dstCoordinate = new JanggiCoordinate(4, 7);

            map.put(sangCoordinate, sang);
            map.put(obstacleCoordinate1, obstaclePiece);
            map.put(obstacleCoordinate2, obstaclePiece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> sang.validatePieceMove(board, sangCoordinate, dstCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("상의 이동경로에 다른 기물이 막고 있지 않으면 이동할 수 있다")
        @Test
        void validateMoveNoObstacle() {
            Piece sang = new Sang(Country.HAN);
            Piece isNotObstaclePiece = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate sangCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate isNotObstacleCoordinate = new JanggiCoordinate(4, 6);
            JanggiCoordinate moveCoordinate1 = new JanggiCoordinate(2, 7);
            JanggiCoordinate moveCoordinate2 = new JanggiCoordinate(3, 8);

            map.put(sangCoordinate, sang);
            map.put(isNotObstacleCoordinate, isNotObstaclePiece);


            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> sang.validatePieceMove(board, sangCoordinate, moveCoordinate1));
            assertDoesNotThrow(() -> sang.validatePieceMove(board, sangCoordinate, moveCoordinate2));
        }
    }
}