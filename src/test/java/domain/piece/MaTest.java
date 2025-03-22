package domain.piece;

import domain.Country;
import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
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

        @DisplayName("움직이려는 말의 시작 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaFromCoordinate() {
            Ma ma = new Ma(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate outOfBoard = new JanggiCoordinate(0, 0);
            JanggiCoordinate inBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> ma.validateMove(board, outOfBoard, inBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("움직이려는 말의 도착 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaToCoordinate() {
            Ma ma = new Ma(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate inBoard = new JanggiCoordinate(-1, -1);
            JanggiCoordinate outOfBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> ma.validateMove(board, inBoard, outOfBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

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
    }
}