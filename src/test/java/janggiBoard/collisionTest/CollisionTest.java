package janggiBoard.collisionTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.CannonStrategy;
import domain.strategy.ChariotStrategy;
import domain.strategy.ElephantStrategy;
import domain.strategy.HorseStrategy;
import domain.strategy.PalaceStrategy;
import domain.strategy.PawnStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CollisionTest {

    private TestPieceProvider testBoard;

    @BeforeEach
    public void setUp() {
        testBoard = new TestPieceProvider();
    }

    @Test
    void 차가_이동하려는_타겟위치에_아군기물이_존재하면_갈수없다() {
        ChariotStrategy chariotStrategy = new ChariotStrategy();
        Position currentPosition = new Position(9, 0);
        Position targetPosition = new Position(6, 0);

        testBoard.setPiece(currentPosition, new Chariot(Team.CHO));
        testBoard.setPiece(targetPosition, new Chariot(Team.CHO));

        assertThat(chariotStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 차가_이동하려는_타겟위치에_적군기물이_존재하면_잡을수있다() {
        ChariotStrategy chariotStrategy = new ChariotStrategy();
        Position currentPosition = new Position(9, 0);
        Position targetPosition = new Position(3, 0);

        testBoard.setPiece(currentPosition, new Chariot(Team.CHO));
        testBoard.setAllBlank();
        testBoard.setPiece(targetPosition, new Pawn(Team.HAN));

        assertThat(chariotStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }

    @Test
    void 마가_이동하려는_타겟위치에_아군기물이_존재하면_갈수없다() {
        HorseStrategy horseStrategy = new HorseStrategy();
        Position currentPosition = new Position(0, 1);
        Position myeokPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 2);

        testBoard.setPiece(currentPosition, new Horse(Team.HAN));
        testBoard.setPiece(myeokPosition, new Blank());
        testBoard.setPiece(targetPosition, new Horse(Team.HAN));

        assertThat(horseStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 마가_이동하려는_타켓위치에_적군기물이_존재하면_잡을수있다() {
        HorseStrategy horseStrategy = new HorseStrategy();
        Position currentPosition = new Position(0, 1);
        Position myeokPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 2);

        testBoard.setPiece(currentPosition, new Horse(Team.HAN));
        testBoard.setPiece(myeokPosition, new Blank());
        testBoard.setPiece(targetPosition, new Pawn(Team.CHO));

        assertThat(horseStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }

    @Test
    void 상이_이동하려는_타켓위치에_아군기물이_존재하면_갈수없다() {
        ElephantStrategy elephantStrategy = new ElephantStrategy();
        Position currentPosition = new Position(0, 3);
        Position straightMyeok = new Position(1, 3);
        Position diagonalMyeok = new Position(2, 4);
        Position targetPosition = new Position(3, 5);

        testBoard.setPiece(currentPosition, new Elephant(Team.HAN));
        testBoard.setPiece(straightMyeok, new Blank());
        testBoard.setPiece(diagonalMyeok, new Blank());
        testBoard.setPiece(targetPosition, new Pawn(Team.HAN));

        assertThat(elephantStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 상이_이동하려는_타켓위치에_적군기물이_존재하면_잡을수있다() {
        ElephantStrategy elephantStrategy = new ElephantStrategy();
        Position currentPosition = new Position(0, 3);
        Position straightMyeok = new Position(1, 3);
        Position diagonalMyeok = new Position(2, 4);
        Position targetPosition = new Position(3, 5);

        testBoard.setPiece(currentPosition, new Elephant(Team.HAN));
        testBoard.setPiece(straightMyeok, new Blank());
        testBoard.setPiece(diagonalMyeok, new Blank());
        testBoard.setPiece(targetPosition, new Pawn(Team.CHO));

        assertThat(elephantStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }

    @Test
    void 졸이_이동하려는_타켓위치에_아군기물이_존재하면_갈수없다() {
        PawnStrategy pawnStrategy = new PawnStrategy();
        Position currentPosition = new Position(6, 4);
        Position targetPosition = new Position(5, 4);

        testBoard.setPiece(currentPosition, new Pawn(Team.CHO));
        testBoard.setPiece(targetPosition, new Pawn(Team.CHO));

        assertThat(pawnStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 졸이_이동하려는_타켓위치에_적군기물이_존재하면_잡을수있다() {
        PawnStrategy pawnStrategy = new PawnStrategy();
        Position currentPosition = new Position(6, 4);
        Position targetPosition = new Position(5, 4);

        testBoard.setPiece(currentPosition, new Pawn(Team.CHO));
        testBoard.setPiece(targetPosition, new Pawn(Team.HAN));

        assertThat(pawnStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }

    @Test
    void 포가_이동하려는_타켓위치에_아군기물이_존재하면_갈수없다() {
        CannonStrategy cannonStrategy = new CannonStrategy();
        Position currentPosition = new Position(7, 1);
        Position bridgePosition = new Position(7, 3);
        Position targetPosition = new Position(7, 5);

        testBoard.setPiece(currentPosition, new Cannon(Team.CHO));
        testBoard.setPiece(bridgePosition, new Guard(Team.CHO));
        testBoard.setPiece(targetPosition, new Cannon(Team.CHO));

        assertThat(cannonStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 포가_이동하려는_타켓위치에_적군기물이_존재하면_잡을수있다() {
        CannonStrategy cannonStrategy = new CannonStrategy();
        Position currentPosition = new Position(7, 1);
        Position bridgePosition = new Position(7, 3);
        Position targetPosition = new Position(7, 5);

        testBoard.setPiece(currentPosition, new Cannon(Team.CHO));
        testBoard.setPiece(bridgePosition, new Guard(Team.CHO));
        testBoard.setPiece(targetPosition, new Chariot(Team.HAN));

        assertThat(cannonStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }

    @Test
    void 사가_이동하려는_타겟위치에_아군기물이_존재하면_갈수없다() {
        PalaceStrategy palaceStrategy = new PalaceStrategy();
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(7, 4);

        testBoard.setPiece(currentPosition, new Guard(Team.CHO));
        testBoard.setPiece(targetPosition, new King(Team.CHO));

        assertThat(palaceStrategy.getMoveCandidates(currentPosition, testBoard)).doesNotContain(targetPosition);
    }

    @Test
    void 사가_이동하려는_타겟위치에_적군기물이_존재하면_잡을수있다() {
        PalaceStrategy palaceStrategy = new PalaceStrategy();
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(7, 4);

        testBoard.setPiece(currentPosition, new Guard(Team.CHO));
        testBoard.setPiece(targetPosition, new Pawn(Team.HAN));

        assertThat(palaceStrategy.getMoveCandidates(currentPosition, testBoard)).contains(targetPosition);
    }


    private static class TestPieceProvider implements PieceProvider {

        private final Map<Position, Piece> pieces = new HashMap<>();

        void setAllBlank() {
            pieces.clear();
        }

        void setPiece(Position position, Piece piece) {
            pieces.put(position, piece);
        }

        @Override
        public boolean isBlank(Position position) {
            Piece piece = getPiece(position);
            return piece instanceof Blank;
        }

        @Override
        public Piece getPiece(Position position) {
            return pieces.getOrDefault(position, new Blank());
        }
    }
}
