package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RightElephantStrategyTest {

    @Test
    @DisplayName("Right Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        RightElephantStrategy rightElephantStrategy = new RightElephantStrategy();

        Map<Position, Piece> elephantHorse = rightElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece piece = elephantHorse.get(Position.of(0, 1));

        assertThat(piece.getType()).isEqualTo(PieceType.HORSE);
    }
}
