package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OuterElephantStrategyTest {

    @Test
    @DisplayName("Outer Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        // given
        OuterElephantStrategy outerElephantStrategy = new OuterElephantStrategy();

        // when
        Map<Position, Piece> choElephant = outerElephantStrategy.createElephantHorse(TeamType.CHO);

        // then
        Position elephantPosition = Position.of(0, 1);
        assertThat(choElephant.get(elephantPosition).isSameType(PieceType.ELEPHANT)).isTrue();
    }
}
