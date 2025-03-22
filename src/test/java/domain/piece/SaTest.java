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

class SaTest {
    @Nested
    class SagCoordinateTest {

        @DisplayName("움직이려는 기물의 시작 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaFromCoordinate() {
            Piece piece = new Sa(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate outOfBoard = new JanggiCoordinate(0, 0);
            JanggiCoordinate inBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> piece.validateMove(board, outOfBoard, inBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("움직이려는 기물의 도착 위치가 보드의 밖이면 에러를 반환한다.")
        @Test
        void validateMaToCoordinate() {
            Piece piece = new Sa(Country.HAN);
            JanggiBoard board = new JanggiBoard(new HashMap<>());

            JanggiCoordinate inBoard = new JanggiCoordinate(-1, -1);
            JanggiCoordinate outOfBoard = new JanggiCoordinate(5, 5);

            assertThatThrownBy(() -> piece.validateMove(board, inBoard, outOfBoard))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("내 기물은 내 기물을 잡으려고 할 수 없다.")
        @Test
        void validateTarget() {
            Piece myPiece = new Sa(Country.HAN);
            Piece maEnemy = new Ma(Country.CHO);
            Piece maOurTeam = new Ma(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate enemyMa = new JanggiCoordinate(4, 4);
            JanggiCoordinate ourMa = new JanggiCoordinate(4, 5);

            map.put(pieceCoordinate, myPiece);
            map.put(enemyMa, maEnemy);
            map.put(ourMa, maOurTeam);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> myPiece.validateMove(board, pieceCoordinate, enemyMa)),
                    () -> assertThatThrownBy(() -> myPiece.validateMove(board, pieceCoordinate, ourMa))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @DisplayName("기물이 현재 위치에서 도달 가능한 위치를 검사한다")
        @Test
        void validateMoveToCoordinate() {
            Piece piece = new Sa(Country.HAN);
            Map<JanggiCoordinate, Piece> map = new HashMap<>();

            JanggiCoordinate pieceCoordinate = new JanggiCoordinate(5, 5);
            JanggiCoordinate reachable1 = new JanggiCoordinate(4, 5);
            JanggiCoordinate reachable2 = new JanggiCoordinate(4, 6);
            JanggiCoordinate reachable3 = new JanggiCoordinate(5, 6);
            JanggiCoordinate reachable4 = new JanggiCoordinate(6, 6);
            JanggiCoordinate reachable5 = new JanggiCoordinate(6, 5);
            JanggiCoordinate reachable6 = new JanggiCoordinate(6, 4);
            JanggiCoordinate reachable7 = new JanggiCoordinate(5, 4);
            JanggiCoordinate reachable8 = new JanggiCoordinate(4, 4);

            JanggiCoordinate unReachable1 = new JanggiCoordinate(3, 5);
            JanggiCoordinate unReachable2 = new JanggiCoordinate(4, 3);
            JanggiCoordinate unReachable3 = new JanggiCoordinate(5, 3);

            map.put(pieceCoordinate, piece);

            JanggiBoard board = new JanggiBoard(map);

            assertAll(
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable1)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable2)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable3)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable4)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable5)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable6)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable7)),
                    () -> assertDoesNotThrow(() -> piece.validateMove(board, pieceCoordinate, reachable8)),

                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable1)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable2)).isInstanceOf(IllegalArgumentException.class),
                    () -> assertThatThrownBy(() -> piece.validateMove(board, pieceCoordinate, unReachable3)).isInstanceOf(IllegalArgumentException.class)
            );
        }
    }
}