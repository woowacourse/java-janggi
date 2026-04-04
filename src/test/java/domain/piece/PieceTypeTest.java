package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @DisplayName("EMPTY 타입은 어떤 상황에서도 이동 가능한 목적지를 반환하지 않는다")
    @Test
    void EMPTY_타입_테스트() {
        PieceType empty = PieceType.EMPTY;
        List<Intersection> movableDestinations =
                empty.movableDestinations(Side.NONE, new Intersection(1, 1), new AlivePieces(Map.of()));

        assertThat(movableDestinations).isEmpty();
    }
}