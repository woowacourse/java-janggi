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

class ByeongTest {

    @Nested
    class ByeongCoordinateTest {

        @DisplayName("내 기물은 내 기물을 잡으려고 할 수 없다.")
        @Test
        void validateTarget() {
            Piece piece = new Byeong(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate myPiece = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(5, 4);
            JanggiCoordinate ourMa = new JanggiCoordinate(6, 5);

            map.put(myPiece, piece);
            map.put(enemyMa, maEnemy);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, myPiece, enemyMa)),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, myPiece, ourMa))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateMoveToCoordinate() {
            Piece piece = new Byeong(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate reachable1 = new JanggiCoordinate(5, 4);
            JanggiCoordinate reachable2 = new JanggiCoordinate(6, 5);
            JanggiCoordinate reachable3 = new JanggiCoordinate(5, 6);
            JanggiCoordinate unReachable1 = new JanggiCoordinate(7, 4);
            JanggiCoordinate unReachable2 = new JanggiCoordinate(2, 2);
            JanggiCoordinate unReachable3 = new JanggiCoordinate(3, 2);

            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable1)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable2)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable3)),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable1)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable2)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable3)).isInstanceOf(IllegalArgumentException.class)
            );
        }
    }
}