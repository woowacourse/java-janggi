package domain.strategy;

import domain.TestFixture;
import domain.Team;
import domain.piece.Car;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CarStrategyTest {

    private CarStrategy carStrategy;
    private TestFixture testBoard;

    @BeforeEach
    void setUp() {
        carStrategy = new CarStrategy();
        testBoard = new TestFixture();
    }

    @Test
    void 차는_상하좌우_직선_모든칸_후보로_반환한다() {
        Position position = new Position(5, 4);
        testBoard.setAllBlank();

        List<Position> candidates = carStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).hasSize(17);
        assertThat(candidates).contains(new Position(0, 4),
                new Position(9, 4),
                new Position(5, 0),
                new Position(5, 8));
    }

    @Test
    void 이동경로에_장애물_있으면_그_지점까지_이동후_전진하지_않는다() {
        Position position = new Position(5, 4);
        testBoard.setAllBlank();

        Position obstacle = new Position(3, 4);
        testBoard.setPiece(obstacle, new Car(Team.HAN));

        List<Position> candidates = carStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).contains(new Position(4, 4), new Position(3, 4));
        assertThat(candidates).doesNotContain(new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4));
    }


}
