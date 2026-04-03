package domain.board;

import java.util.HashMap;
import java.util.Map;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.Gung;
import domain.pieces.JolByeong;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Sa;
import domain.pieces.Side;
import domain.position.Position;

public abstract class GeneralSangSetup implements SangSetup {

    private final static Piece EMPTY_PIECE = new EmptyPiece();

    @Override
    public Board initialize(Side side) {
        return new Board(setupBoard(side));
    }

    private Map<Position, Piece> setupBoard(Side side) {
        Map<Position, Piece> board = new HashMap<>();
        if (side.isCho()) {
            board.putAll(emptyChoBoard());
            board.putAll(putChoPieces());
            board.putAll(getSangAndMaPositions(side));
            return board;
        }
        board.putAll(emptyHanBoard());
        board.putAll(putHanPieces());
        board.putAll(getSangAndMaPositions(side));
        return board;
    }

    private Map<Position, Piece> emptyChoBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 0; row <= 4; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }

    private Map<Position, Piece> emptyHanBoard() {
        Map<Position, Piece> board = new HashMap<>();
        for (int row = 5; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                board.put(new Position(row, column), EMPTY_PIECE);
            }
        }
        return board;
    }

    private Map<Position, Piece> putChoPieces() {
        return Map.ofEntries(
                Map.entry(new Position(0, 0), new Cha(Side.CHO)),
                Map.entry(new Position(0, 3), new Sa(Side.CHO)),
                Map.entry(new Position(1, 4), new Gung(Side.CHO)),
                Map.entry(new Position(0, 5), new Sa(Side.CHO)),
                Map.entry(new Position(0, 8), new Cha(Side.CHO)),

                Map.entry(new Position(2, 1), new Po(Side.CHO)),
                Map.entry(new Position(2, 7), new Po(Side.CHO)),

                Map.entry(new Position(3, 0), new JolByeong(Side.CHO)),
                Map.entry(new Position(3, 2), new JolByeong(Side.CHO)),
                Map.entry(new Position(3, 4), new JolByeong(Side.CHO)),
                Map.entry(new Position(3, 6), new JolByeong(Side.CHO)),
                Map.entry(new Position(3, 8), new JolByeong(Side.CHO))
        );
    }

    private Map<Position, Piece> putHanPieces() {
        return Map.ofEntries(
                Map.entry(new Position(9, 0), new Cha(Side.HAN)),
                Map.entry(new Position(9, 3), new Sa(Side.HAN)),
                Map.entry(new Position(8, 4), new Gung(Side.HAN)),
                Map.entry(new Position(9, 5), new Sa(Side.HAN)),
                Map.entry(new Position(9, 8), new Cha(Side.HAN)),
                Map.entry(new Position(7, 1), new Po(Side.HAN)),
                Map.entry(new Position(7, 7), new Po(Side.HAN)),

                Map.entry(new Position(6, 0), new JolByeong(Side.HAN)),
                Map.entry(new Position(6, 2), new JolByeong(Side.HAN)),
                Map.entry(new Position(6, 4), new JolByeong(Side.HAN)),
                Map.entry(new Position(6, 6), new JolByeong(Side.HAN)),
                Map.entry(new Position(6, 8), new JolByeong(Side.HAN))
        );
    }

    protected abstract Map<Position, Piece> getSangAndMaPositions(Side side);
}
