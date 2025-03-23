package fixture;

import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Point;
import domain.board.PointNodeMapper;
import domain.board.PointNodeMapperFactory;
import domain.piece.Piece;
import java.util.Map;

public class BoardFixture {

    private static final BoardGenerator BOARD_GENERATOR = new BoardGenerator();
    private static final PointNodeMapperFactory pointNodeMapperFactory = new PointNodeMapperFactory();

    public static PointNodeMapper createDefaultPointNodeMapper() {
        return pointNodeMapperFactory.createDefaultPointNodeMapper();
    }

    public static Board createTestBoard(Map<Point, Piece> pieceByPoint) {
        return new Board(pieceByPoint, createDefaultPointNodeMapper());
    }
}
