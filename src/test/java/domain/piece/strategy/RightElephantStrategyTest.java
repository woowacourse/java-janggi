package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RightElephantStrategyTest {

    @Test
    @DisplayName("Right Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        // given
        RightElephantStrategy rightElephantStrategy = new RightElephantStrategy();

        // when
        Map<Position, Piece> choElephant = rightElephantStrategy.createElephantHorse(TeamType.CHO);

        // then
        Position elephantPosition = Position.of(0, 2);
        assertThat(choElephant.get(elephantPosition).isSameType(PieceType.ELEPHANT)).isTrue();
    }
}
