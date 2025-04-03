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
    class PhoLinearTest {

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

            assertThatThrownBy(() -> piece.validateDestination(board, myPiece, ourMa))
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

            assertDoesNotThrow(() -> piece.validatePieceMove(board, myPiece, enemyMa));
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

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, reachable));
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

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, coordinate))
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

            assertThatThrownBy(() -> piece.validatePieceMove(board, maCoordinate, moveCoordinate))
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

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class PhoDiagonalTest {

        @DisplayName("기물이 궁성 LeftUp 있으면 오른쪽 아래로 이동할 수 있다")
        @ParameterizedTest
        @MethodSource("phoLeftUpCastleMove")
        void pieceCastleDiagonalTest(JanggiCoordinate moveCoordinate) {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 4);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(2, 5);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate));
        }

        private static Stream<Arguments> phoLeftUpCastleMove() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 6))
            );
        }

        @DisplayName("기물이 궁성 RightUp 있으면 왼쪽 아래로 이동할 수 있다")
        @ParameterizedTest
        @MethodSource("phoRightUpCastleMove")
        void pieceCastleDiagonalTest2(JanggiCoordinate moveCoordinate) {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 6);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(2, 5);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate));
        }

        private static Stream<Arguments> phoRightUpCastleMove() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(3, 4))
            );
        }

        @DisplayName("기물이 궁성 LeftDown 있으면 오른쪽 위로 이동할 수 있다")
        @ParameterizedTest
        @MethodSource("phoLeftDownCastleMove")
        void pieceCastleDiagonalTest4(JanggiCoordinate moveCoordinate) {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(3, 4);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(2, 5);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate));
        }

        private static Stream<Arguments> phoLeftDownCastleMove() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 6))
            );
        }

        @DisplayName("기물이 궁성 RightDown 있으면 왼쪽 위로 이동할 수 있다")
        @ParameterizedTest
        @MethodSource("phoRightDownCastleMove")
        void pieceCastleDiagonalTest5(JanggiCoordinate moveCoordinate) {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(3, 6);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(2, 5);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate));
        }

        private static Stream<Arguments> phoRightDownCastleMove() {
            return Stream.of(
                    Arguments.arguments(new JanggiCoordinate(1, 4))
            );
        }

        @DisplayName("기물이 궁성 대각선 방향과 동일하더라도 궁성 밖의 좌표면 이동할 수 없다")
        @Test
        void pieceCastleOuterDiagonalTest() {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);

            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 4);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(2, 5);

            JanggiCoordinate moveCoordinate = new JanggiCoordinate(4, 7);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);


            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> piece.validatePieceMove(board, pieceCoordinate, moveCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
