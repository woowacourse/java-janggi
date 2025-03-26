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

class PhoTest {

    @Nested
    class PhoCoordinateTest {

        @DisplayName("기물은 아군 기물을 잡을 수 없다.")
        @Test
        void validateTarget() {
            Piece piece = new Pho(Country.HAN);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(5, 7);
            JanggiCoordinate ourMa = new JanggiCoordinate(5, 8);

            map.put(myPiece, piece);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateMove(board, myPiece, ourMa))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("기물은 적군 기물만 잡을 수 있다.")
        @Test
        void validateEnemyTarget() {
            Piece piece = new Pho(Country.HAN);
            Piece maOurTeam = new Ma(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate ourMa = new JanggiCoordinate(5, 7);
            JanggiCoordinate enemyMa = new JanggiCoordinate(5, 8);

            map.put(myPiece, piece);
            map.put(ourMa, maOurTeam);
            map.put(enemyMa, maEnemy);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validateMove(board, myPiece, enemyMa));
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateReachableCoordinate() {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(3, 5);
            JanggiCoordinate reachable = new JanggiCoordinate(4, 5);
            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable));
        }

        @DisplayName("기물이 현재 위치에서 도달 불가능한 위치를 검사한다")
        @ParameterizedTest
        @MethodSource("unreachableArguments")
        void validateUnreachableCoordinate(JanggiCoordinate coordinate) {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(3, 5);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, coordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private static Stream<Arguments> unreachableArguments() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 2)),
                    Arguments.arguments(new JanggiCoordinate(2, 2)),
                    Arguments.arguments(new JanggiCoordinate(3, 2))
            );
        }

        @DisplayName("포는 이동경로에 포가 있으면 넘어갈 수 없다")
        @Test
        void validateMoveHasObstacle() {
            Piece piece = new Pho(Country.HAN);
            Piece obstaclePiece = new Pho(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate maCoordinate = new JanggiCoordinate(1, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate moveCoordinate = new JanggiCoordinate(9, 5);

            map.put(maCoordinate, piece);
            map.put(obstacleCoordinate, obstaclePiece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateMove(board, maCoordinate, moveCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("포는 포를 잡을 수 없다")
        @Test
        void validateCannotCapturePho() {
            Piece piece = new Pho(Country.HAN);
            Piece obstaclePiece = new Ma(Country.HAN);
            Piece oppositePho = new Pho(Country.CHO);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(5, 4);
            JanggiCoordinate moveCoordinate = new JanggiCoordinate(5, 7);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstaclePiece);
            map.put(moveCoordinate, oppositePho);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, moveCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
