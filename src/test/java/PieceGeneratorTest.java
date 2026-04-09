import domain.Camp;
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

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.HAN,
                1);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 초나라_말_16개_생성() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.CHO,
                1);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 한나라_오른상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.HAN,
                1);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
    }

    @Test
    void 한나라_왼상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.HAN,
                3);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
    }

    @Test
    void 한나라_안상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.HAN,
                2);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
    }

    @Test
    void 한나라_바깥상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.HAN,
                4);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
    }


    @Test
    void 초나라_오른상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.CHO,
                1);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Elephant.class);
    }

    @Test
    void 초나라_왼상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.CHO,
                3);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Horse.class);
    }

    @Test
    void 초나라_안상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.CHO,
                2);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Horse.class);
    }

    @Test
    void 초나라_바깥상차림() {
        PieceGenerator pieceGenerator = new PieceGenerator();

        Map<Position, Piece> board = pieceGenerator.generateInitialPieces(Camp.CHO,
                4);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Elephant.class);
    }
}
