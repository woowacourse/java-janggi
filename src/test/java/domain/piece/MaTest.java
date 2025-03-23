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

class MaTest {

    @Nested
    class MaCoordinateTest {

        @DisplayName("내 말은 내 기물을 잡으려고 할 수 없다.")
        @Test
        void validateTarget() {
            Ma ma = new Ma(Country.HAN);
            Ma maEnemy = new Ma(Country.CHO);
            Ma maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myMa = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(4, 7);
            JanggiCoordinate ourMa = new JanggiCoordinate(6, 7);

            map.put(myMa, ma);
            map.put(enemyMa, maEnemy);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> ma.validateMove(board, myMa, enemyMa)),
                    () -> assertThatThrownBy(() -> ma.validateMove(board, myMa, ourMa))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("말이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateMoveToCoordinate() {
            Ma ma = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate maCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate reachable = new JanggiCoordinate(4, 7);
            JanggiCoordinate unReachable1 = new JanggiCoordinate(6, 5);
            JanggiCoordinate unReachable2 = new JanggiCoordinate(2, 2);
            JanggiCoordinate unReachable3 = new JanggiCoordinate(3, 2);

            map.put(maCoordinate, ma);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> ma.validateMove(board, maCoordinate, reachable)),
                    () -> assertThatThrownBy(() -> ma.validateMove(board, maCoordinate, unReachable1)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> ma.validateMove(board, maCoordinate, unReachable2)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> ma.validateMove(board, maCoordinate, unReachable3)).isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("말의 이동경로에 다른 기물이 막고 있으면 이동할 수 없다")
        @Test
        void validateMoveHasObstacle() {
            Ma ma = new Ma(Country.HAN);
            Ma obstaclePiece = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate maCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate obstacleCoordinate = new JanggiCoordinate(5, 6);
            JanggiCoordinate moveCoordinate = new JanggiCoordinate(4, 7);

            map.put(maCoordinate, ma);
            map.put(obstacleCoordinate, obstaclePiece);

            JanggiBoard board = new JanggiBoard(map);

            assertThatThrownBy(() -> ma.validateMove(board, maCoordinate, moveCoordinate))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("말의 이동경로에 다른 기물이 막고 있지 않으면 이동할 수 있다")
        @Test
        void validateMoveNoObstacle() {
            Ma ma = new Ma(Country.HAN);
            Ma obstaclePiece = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate maCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate isNotObstacleCoordinate = new JanggiCoordinate(5, 4);
            JanggiCoordinate moveCoordinate = new JanggiCoordinate(4, 7);

            map.put(maCoordinate, ma);
            map.put(isNotObstacleCoordinate, obstaclePiece);

            JanggiBoard board = new JanggiBoard(map);

            assertDoesNotThrow(() -> ma.validateMove(board, maCoordinate, moveCoordinate));
        }
    }
}