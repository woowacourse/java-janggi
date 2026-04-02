package domain;

import domain.piece.Cha;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Team;
import domain.piece.policy.NormalMovementPolicy;
import domain.piece.policy.PoMovementPolicy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.position.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathContextTest {
    private static final Position PO_POSITION = Position.of(1, 1);
    private static final Piece PO = new Po(new SlidingMoveStrategy(), new PoMovementPolicy(), Team.CHO);
    private static final Position CHA_POSITION = Position.of(1, 2);
    private static final Piece CHA = new Cha(new SlidingMoveStrategy(), new NormalMovementPolicy(), Team.CHO);

    @Test
    void 포가_포함되었다면_true를_반환해야_한다() {
        PathContext pathContext = PathContext.from(Map.of(PO_POSITION, PO));
        Assertions.assertThat(pathContext.hasPo()).isTrue();
    }


    @Test
    void 포가_포함되지_않았다면_false를_반환해야_한다() {
        PathContext pathContext = PathContext.from(Map.of(CHA_POSITION, CHA));
        Assertions.assertThat(pathContext.hasPo()).isFalse();
    }

    @Test
    void 경로의_기물_개수를_구할_수_있다() {
        PathContext pathContext = PathContext.from(Map.of(CHA_POSITION, CHA, PO_POSITION, PO));
        Assertions.assertThat(pathContext.getPieceCount()).isEqualTo(2);
    }

    @Test
    void 경로의_기물이_없으면_0을_반환해야_한다() {
        PathContext pathContext = PathContext.from(Map.of());
        Assertions.assertThat(pathContext.getPieceCount()).isEqualTo(0);
    }
}
