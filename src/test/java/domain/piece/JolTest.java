package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JolTest {

    @ParameterizedTest
    @CsvSource({
            "6, 5",
            "7, 4",
            "7, 6"
    })
    void 졸은_앞_또는_옆으로_이동할_수_있다(int targetRow, int targetColumn) {
        // given
        Jol jol = new Jol(Team.CHO);
        Position startPosition = new Position(7, 5);
        Position targetPosition = new Position(targetRow, targetColumn);
        // when
        List<Position> path = jol.calculatePath(startPosition, targetPosition);
        // then
        assertThat(path).isEqualTo(List.of());
    }

}