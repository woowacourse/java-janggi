import domain.Camp;
import domain.ElephantFormation;
import domain.PieceGenerator;
import domain.Position;
import domain.pieces.Elephant;
import domain.pieces.Horse;
import domain.pieces.Piece;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceGeneratorTest {

    @Test
    void 한나라_말_16개_생성() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.HAN,
                ElephantFormation.RIGHT);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 초나라_말_16개_생성() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.CHO,
                ElephantFormation.RIGHT);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 한나라_오른상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.HAN,
                ElephantFormation.RIGHT);

        Assertions.assertEquals(board.get(new Position(2, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(3, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(8, 1)).getClass(), Horse.class);
    }

    @Test
    void 한나라_왼상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.HAN,
                ElephantFormation.LEFT);

        Assertions.assertEquals(board.get(new Position(2, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(3, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(8, 1)).getClass(), Elephant.class);
    }

    @Test
    void 한나라_안상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.HAN,
                ElephantFormation.INNER);

        Assertions.assertEquals(board.get(new Position(2, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(3, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(8, 1)).getClass(), Horse.class);
    }

    @Test
    void 한나라_바깥상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.HAN,
                ElephantFormation.OUTER);

        Assertions.assertEquals(board.get(new Position(2, 1)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(3, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 1)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(8, 1)).getClass(), Elephant.class);
    }


    @Test
    void 초나라_오른상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.CHO,
                ElephantFormation.RIGHT);

        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(3, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(8, 0)).getClass(), Elephant.class);
    }

    @Test
    void 초나라_왼상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.CHO,
                ElephantFormation.LEFT);

        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(3, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(8, 0)).getClass(), Horse.class);
    }

    @Test
    void 초나라_안상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.CHO,
                ElephantFormation.INNER);

        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(3, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(8, 0)).getClass(), Horse.class);
    }

    @Test
    void 초나라_바깥상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generatePieces(Camp.CHO,
                ElephantFormation.OUTER);

        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(3, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(8, 0)).getClass(), Elephant.class);
    }
}
