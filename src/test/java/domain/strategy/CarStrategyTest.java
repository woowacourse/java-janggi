package domain.strategy;

import domain.TestFixture;
import domain.Team;
import domain.board.PieceProvider;
import domain.piece.Blank;
import domain.piece.Car;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarStrategyTest {

    private CarStrategy carStrategy;
    private TestFixture testBoard;

    @BeforeEach
    void setUp() {
        carStrategy = new CarStrategy();
        testBoard = new TestFixture();
    }

    @Test
    void 차가_북쪽_끝에_있을_때_북쪽_후보는_없어야_하고_시스템은_터지지_않아야_한다() {
        Position position = new Position(0, 4);

        assertThatCode(() -> {
            List<Position> candidates = carStrategy.getMoveCandidates(position, Team.CHO, testBoard);
            assertThat(candidates).noneMatch(p -> p.row() < 0);
        }).doesNotThrowAnyException();
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
