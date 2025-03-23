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

class SangTest {
    @Nested
    class MaCoordinateTest {

        @DisplayName("내 상은 내 기물을 잡으려고 할 수 없다.")
        @Test
        void validateTarget() {
            Piece sang = new Sang(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate mySang = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(2, 7);
            JanggiCoordinate ourMa = new JanggiCoordinate(3, 8);

            map.put(mySang, sang);
            map.put(enemyMa, maEnemy);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> sang.validateMove(board, mySang, enemyMa)),
                    () -> assertThatThrownBy(() -> sang.validateMove(board, mySang, ourMa))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("상이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateMoveToCoordinate() {
            Piece sang = new Sang(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate sangCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate reachable = new JanggiCoordinate(2, 7);
            JanggiCoordinate unReachable1 = new JanggiCoordinate(3, 5);
            JanggiCoordinate unReachable2 = new JanggiCoordinate(8, 1);
            JanggiCoordinate unReachable3 = new JanggiCoordinate(10, 2);

            map.put(sangCoordinate, sang);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> sang.validateMove(board, sangCoordinate, reachable)),
                    () -> assertThatThrownBy(() -> sang.validateMove(board, sangCoordinate, unReachable1)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> sang.validateMove(board, sangCoordinate, unReachable2)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> sang.validateMove(board, sangCoordinate, unReachable3)).isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("상의 이동경로에 다른 기물이 막고 있으면 이동할 수 없다")
        @Test
        void validateMoveHasObstacle() {
            Piece sang = new Sang(Country.HAN);
            Ma obstaclePiece = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate sangCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate obstacleCoordinate1 = new JanggiCoordinate(4, 6);
            JanggiCoordinate obstacleCoordinate2 = new JanggiCoordinate(3, 7);


            map.put(sangCoordinate, sang);
            map.put(obstacleCoordinate1, obstaclePiece);
            map.put(obstacleCoordinate2, obstaclePiece);

            JanggiBoard board = new JanggiBoard(map);


            assertAll(
                    () -> assertThatThrownBy(() -> sang.validateMove(board, sangCoordinate, obstacleCoordinate1))
                            .isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> sang.validateMove(board, sangCoordinate, obstacleCoordinate2))
                            .isInstanceOf(IllegalArgumentException.class)
            );
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

            assertDoesNotThrow(() -> sang.validateMove(board, sangCoordinate, moveCoordinate1));
            assertDoesNotThrow(() -> sang.validateMove(board, sangCoordinate, moveCoordinate1));
        }
    }
}