package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantTest {
    @DisplayName("상이 포에 의해 잡힐 수 있다.")
    @Test
    void canBeCaughtByCannon() {
        Piece piece = new Elephant(Camp.CHO);
        assertThat(piece.canBeCaughtByCannon()).isTrue();
    }

    @DisplayName("상은 넘을 수 있다.")
    @Test
    void canBeJumpedOver() {
        Piece piece = new Elephant(Camp.CHO);
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @Test
    void 경로에_어떤_기물이라도_있으면_false를_반환한다() {
        Piece piece = new Elephant(Camp.CHO);
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.of(3, 3), new Horse(Camp.HAN));

        boolean canPassRoute = piece.canPassRoute(board);

        assertThat(canPassRoute).isEqualTo(false);
    }

    @Test
    void 경로에_기물이_없으면_true를_반환한다() {
        Piece piece = new Elephant(Camp.CHO);
        Map<Position, Piece> board = new HashMap<>();

        boolean canPassRoute = piece.canPassRoute(board);

        assertThat(canPassRoute).isEqualTo(true);
    }

    @Test
    void 도착지물에_기물이_아군일_때_false를_반환한다() {
        Piece piece = new Elephant(Camp.CHO);
        Piece choPiece = new Elephant(Camp.CHO);

        boolean canCatch = piece.canCatch(choPiece);

        assertThat(canCatch).isEqualTo(false);
    }

    @Test
    void 도착지물에_기물이_적군일_때_true를_반환한다() {
        Piece piece = new Elephant(Camp.CHO);

        Piece hanPiece = new Elephant(Camp.HAN);

        boolean canCatch = piece.canCatch(hanPiece);

        assertThat(canCatch).isEqualTo(true);
    }

    @Test
    void 필수적인_기물이_아니면_false를_출력한다() {
        Piece piece = new Elephant(Camp.CHO);
        boolean essential = piece.isEssential();

        assertThat(essential).isFalse();
    }
}
