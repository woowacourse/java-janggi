import domain.Camp;
import domain.ElephantFormation;
import domain.PieceGenerator;
import domain.Position;
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
}
