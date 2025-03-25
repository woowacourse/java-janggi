package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LeftElephantStrategyTest {

    @Test
    @DisplayName("Left Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        LeftElephantStrategy leftElephantStrategy = new LeftElephantStrategy();

        Map<Position, Piece> elephantHorse = leftElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece piece = elephantHorse.get(Position.of(0, 2));

        assertThat(piece.getType()).isEqualTo(PieceType.HORSE);
    }
}
