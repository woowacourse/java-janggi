package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RightElephantStrategyTest {

    @Test
    @DisplayName("Right Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        RightElephantStrategy rightElephantStrategy = new RightElephantStrategy();

        List<Piece> choElephant = rightElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece horse = choElephant.get(0);

        assertThat(horse.getPosition()).isEqualTo(Position.of(0, 1));
    }
}
