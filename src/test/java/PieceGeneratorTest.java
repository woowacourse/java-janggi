import domain.piece.Camp;
import domain.position.ElephantFormation;
import domain.piece.PieceGenerator;
import domain.position.Position;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PieceGeneratorTest {

    @Test
    void 한나라_말_16개_생성() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.HAN, ElephantFormation.RIGHT);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 초나라_말_16개_생성() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.CHO, ElephantFormation.RIGHT);

        Assertions.assertEquals(board.size(), 16);
    }

    @Test
    void 한나라_오른상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.HAN, ElephantFormation.RIGHT);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
    }

    @Test
    void 한나라_왼상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.HAN, ElephantFormation.LEFT);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
    }

    @Test
    void 한나라_안상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.HAN, ElephantFormation.INNER);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Horse.class);
    }

    @Test
    void 한나라_바깥상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.HAN, ElephantFormation.OUTER);

        Assertions.assertEquals(board.get(new Position(1, 0)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 0)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 0)).getClass(), Elephant.class);
    }


    @Test
    void 초나라_오른상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.CHO, ElephantFormation.RIGHT);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Elephant.class);
    }

    @Test
    void 초나라_왼상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.CHO, ElephantFormation.LEFT);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Horse.class);
    }

    @Test
    void 초나라_안상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.CHO, ElephantFormation.INNER);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Horse.class);
    }

    @Test
    void 초나라_바깥상차림() {
        Map<Position, Piece> board = PieceGenerator.generatePieces(Camp.CHO, ElephantFormation.OUTER);

        Assertions.assertEquals(board.get(new Position(1, 9)).getClass(), Elephant.class);
        Assertions.assertEquals(board.get(new Position(2, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(6, 9)).getClass(), Horse.class);
        Assertions.assertEquals(board.get(new Position(7, 9)).getClass(), Elephant.class);
    }
}
