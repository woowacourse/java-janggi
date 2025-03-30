package persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.Pieces;

public class FakeJanggiPieceDao implements JanggiPieceDao {

    private final Map<Integer, List<Piece>> pieces = new HashMap<>();

    @Override
    public void savePiece(Piece piece, int turn) {
        pieces.computeIfAbsent(turn, t -> new ArrayList<>()).add(piece);
    }

    @Override
    public Pieces findPiecesByTeamTurn(int turnId) {
        List<Piece> pieces = this.pieces.getOrDefault(turnId, new ArrayList<>());
        return new Pieces(pieces);
    }

    @Override
    public void deleteAll() {
        pieces.clear();
    }
}
