package model.testdouble;

import java.util.HashMap;
import java.util.Map;
import model.Team;
import model.board.Board;
import model.coordinate.Position;
import model.piece.General;
import model.piece.Piece;

public class SpyBoard extends Board {

    public SpyBoard(Map<Position, Piece> pieces) {
        super(pieces);
    }

    public static SpyBoard withBothGenerals() {
        Map<Position, Piece> pieces = new HashMap<>(Map.of(
                new Position(1, 4), new General(Team.HAN),
                new Position(8, 4), new General(Team.CHO)
        ));
        return new SpyBoard(pieces);
    }

    public SpyBoard addPiece(Position position, Piece piece) {
        arrangePieces(Map.of(position, piece));
        return new SpyBoard(this.board());
    }

    @Override
    public void move(Position current, Position next) {
        super.move(current, next);
    }
}
