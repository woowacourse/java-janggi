package domain.piece;


import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PhoTest {

    @Nested
    class MaCoordinateTest {

        @DisplayName("움직이려는 기물의 시작 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaFromCoordinate() {
            Piece piece = new Pho(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate outOfBoard = new JanggiCoordinate(0, 0);
            JanggiCoordinate inBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> piece.validateMove(board, outOfBoard, inBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("움직이려는 기물의 도착 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaToCoordinate() {
            Piece piece = new Pho(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate inBoard = new JanggiCoordinate(-1, -1);
            JanggiCoordinate outOfBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> piece.validateMove(board, inBoard, outOfBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("내 기물은 내 기물을 잡으려고 할 수 없다.")
        @Test
        void validateTarget() {
            Piece piece = new Pho(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(5, 7);
            JanggiCoordinate ourMa = new JanggiCoordinate(5, 8);

            map.put(myPiece, piece);
            map.put(enemyMa, maEnemy);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertThatThrownBy(() -> piece.validateMove(board, myPiece, ourMa))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateMoveToCoordinate() {
            Piece piece = new Pho(Country.HAN);
            Piece obstacle = new Cha(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(1, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(3, 5);
            JanggiCoordinate reachable = new JanggiCoordinate(4, 5);
            JanggiCoordinate unReachable1 = new JanggiCoordinate(1, 1);
            JanggiCoordinate unReachable2 = new JanggiCoordinate(2, 2);
            JanggiCoordinate unReachable3 = new JanggiCoordinate(3, 2);

            map.put(pieceCoordinate, piece);
            map.put(obstacleCoordinate, obstacle);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable)),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable1))
                            .isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable2))
                            .isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable3))
                            .isInstanceOf(IllegalArgumentException.class)
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
        void validateMoveNoObstacle() {
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