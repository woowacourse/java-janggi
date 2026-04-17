package domain.strategy;

import domain.TestFixture;
import domain.Team;
import domain.piece.Pawn;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ElephantStrategyTest {

    private ElephantStrategy elephantStrategy;
    private TestFixture testBoard;

    @BeforeEach
    public void setUp() {
        elephantStrategy = new ElephantStrategy();
        testBoard = new TestFixture();
    }

    @Test
    void 상_주변에_장애물_없으면_8가지_후보_모두_반환() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        List<Position> candidates = elephantStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(2, 3), new Position(2, 7), // 북쪽 기반
                        new Position(3, 8), new Position(7, 8), // 남쪽 기반
                        new Position(8, 3), new Position(8, 7), // 서쪽 기반
                        new Position(3, 2), new Position(7, 2)  // 동쪽 기반
                );
    }

    @Test
    void 북쪽_멱이_막혀있으면_북서_북동으로_이동할수_없다(){
        Position source = new Position(5, 5);
        testBoard.setAllBlank();

        // 북쪽 멱 위치를 막힌 상태로 설정
        testBoard.setPiece(new Position(4, 5), new Pawn(Team.CHO));
        List<Position> candidates = elephantStrategy.getMoveCandidates(source, Team.CHO, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(2, 3), new Position(2, 7));
    }

    @Test
    void 남쪽_멱이_막혀있으면_남서_남동으로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setPiece(new Position(6, 5), new Pawn(Team.CHO));
        List<Position> candidates = elephantStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(8,3), new Position(8,7));
    }

    @Test
    void 서쪽_멱이_막혀있으면_북서_남서로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setPiece(new Position(5, 4), new Pawn(Team.CHO));
        List<Position> candidates = elephantStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(3,2), new Position(7,2));
    }

    @Test
    void 동쪽_멱이_막혀있으면_북동_남동으로_이동할수_없다() {
        Position position = new Position(5, 5);
        testBoard.setAllBlank();

        testBoard.setPiece(new Position(5, 6), new Pawn(Team.CHO));
        List<Position> candidates = elephantStrategy.getMoveCandidates(position, Team.CHO, testBoard);

        assertThat(candidates).hasSize(6)
                .doesNotContain(new Position(3, 8), new Position(7, 8));
    }
}
