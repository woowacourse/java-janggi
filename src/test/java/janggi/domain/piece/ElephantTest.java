package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {

    @DisplayName("상을 넘어갈 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeJumpedOver_Always_ReturnTrue() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("포가 상을 잡을 수 있는지 확인하는 테스트 (항상 True)")
    @Test
    void canBeCapturedByCannon_Always_ReturnTrue() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        assertThat(piece.canBeCapturedByCannon()).isTrue();
    }

    @Test
    void 경로에_어떤_기물이라도_있으면_false를_반환한다() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.of(3, 3), new Horse(Camp.HAN, new HorseStrategy()));

        boolean canPassRoute = piece.canPassRoute(board);

        assertThat(canPassRoute).isEqualTo(false);
    }

    @Test
    void 경로에_기물이_없으면_true를_반환한다() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        Map<Position, Piece> board = new HashMap<>();

        boolean canPassRoute = piece.canPassRoute(board);

        assertThat(canPassRoute).isEqualTo(true);
    }

    @Test
    void 도착지물에_기물이_아군일_때_false를_반환한다() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());
        Piece choPiece = new Elephant(Camp.CHO, new ElephantStrategy());

        boolean canCatch = piece.canCatch(choPiece);

        assertThat(canCatch).isEqualTo(false);
    }

    @Test
    void 도착지물에_기물이_적군일_때_true를_반환한다() {
        Piece piece = new Elephant(Camp.CHO, new ElephantStrategy());

        Piece hanPiece = new Elephant(Camp.HAN, new ElephantStrategy());

        boolean canCatch = piece.canCatch(hanPiece);

        assertThat(canCatch).isEqualTo(true);
    }
}
