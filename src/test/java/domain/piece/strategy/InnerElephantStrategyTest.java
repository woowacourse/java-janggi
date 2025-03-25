package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InnerElephantStrategyTest {

    @Test
    @DisplayName("Inner Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        InnerElephantStrategy innerElephantStrategy = new InnerElephantStrategy();

        Map<Position, Piece> elephantHorse = innerElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece piece = elephantHorse.get(Position.of(0, 1));

        assertThat(piece.getType()).isEqualTo(PieceType.HORSE);
    }

}
