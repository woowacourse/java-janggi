package domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OuterElephantStrategyTest {

    @Test
    @DisplayName("Outer Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse() {
        OuterElephantStrategy outerElephantStrategy = new OuterElephantStrategy();

        List<Piece> choElephant = outerElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece elephant = choElephant.get(0);

        assertThat(elephant.getPosition()).isEqualTo(Position.of(0, 1));
    }

}
