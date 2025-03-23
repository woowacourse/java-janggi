package domain.board;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.piece.Ma;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JanggiBoardTest {

    @Nested
    class BoardCoordinateTest {

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생한다.")
        @Test
        void findPieceTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertDoesNotThrow(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(5, 5)));
        }

        @DisplayName("보드에 존재하지 않는 기물을 조회하면 에러가 발생한다.")
        @Test
        void findPieceWrongCoordinateTest() {
            Map<JanggiCoordinate, Piece> map = new HashMap<>();
            map.put(new JanggiCoordinate(5, 5), new Ma(Country.HAN));
            JanggiBoard janggiBoard = new JanggiBoard(map);

            assertThatThrownBy(() -> janggiBoard.findPieceByCoordinate(new JanggiCoordinate(2, 2)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}