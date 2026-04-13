package janggiBoard.strategyTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.PalaceStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalaceStrategyTest {

    private PalaceStrategy palaceStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        palaceStrategy = new PalaceStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 한나라_궁이_중앙에_있고_방해물이_없으면_8가지_방향으로_이동_가능() {
        Position currentPosition = new Position(1, 4);
        testBoard.setPiece(currentPosition, new King(Team.HAN, new PalaceStrategy()));

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, testBoard);

        Assertions.assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(0, 3), new Position(0, 5)
                        , new Position(0, 4), new Position(2, 4)
                        , new Position(1, 3), new Position(1, 5)
                        , new Position(2, 3), new Position(2, 5)
                );
    }

    @Test
    void 초나라_궁이_중앙에_있고_방해물이_없으면_8가지_방향으로_이동_가능() {
        Position currentPosition = new Position(8, 4);
        testBoard.setPiece(currentPosition, new King(Team.CHO, new PalaceStrategy()));

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, testBoard);

        Assertions.assertThat(candidates).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(7, 3), new Position(7, 5)
                        , new Position(7, 4), new Position(9, 4)
                        , new Position(8, 3), new Position(8, 5)
                        , new Position(9, 3), new Position(9, 5)
                );
    }

    @Test
    void 한나라_궁이_모서리에_있으면_궁성_밖의_좌표는_제외() {
        Position currentPosition = new Position(0, 3);
        testBoard.setPiece(currentPosition, new King(Team.HAN, new PalaceStrategy()));

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, testBoard);

        Assertions.assertThat(candidates).hasSize(3)
                .containsExactlyInAnyOrder(new Position(0, 4), new Position(1, 3)
                        , new Position(1, 4)
                );
    }

    @Test
    void 초나라_궁이_모서리에_있으면_궁성_밖의_좌표는_제외() {
        Position currentPosition = new Position(9, 3);
        testBoard.setPiece(currentPosition, new King(Team.CHO, new PalaceStrategy()));

        List<Position> candidates = palaceStrategy.getMoveCandidates(currentPosition, testBoard);

        Assertions.assertThat(candidates).hasSize(3)
                .containsExactlyInAnyOrder(new Position(8, 4), new Position(8, 3)
                        , new Position(9, 4)
                );
    }


    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Piece> boardState = new HashMap<>();

        void setPiece(Position position, Piece piece) {
            boardState.put(position, piece);
        }

        @Override
        public boolean isBlank(Position position) {
            return !boardState.containsKey(position) || boardState.get(position).isBlank();
        }

        @Override
        public Piece getPiece(Position position) {
            return boardState.getOrDefault(position, new Blank());
        }
    }
}
