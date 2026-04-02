package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class Han extends Team {

    public Han(Map<Position, Piece> pieces) {
        super(pieces);
    }

    public static Han createInitialHan() {
        return new Han(initializePieces());
    }

    @Override
    public Team move(Position start, Position end) {
        Map<Position, Piece> pieces = getPieces();
        Piece piece = pieces.get(start);
        Map<Position, Piece> updatedPieces = new HashMap<>(pieces);
        updatedPieces.remove(start);
        updatedPieces.put(end, piece);
        return new Han(updatedPieces);
    }

    @Override
    public Team remove(Position position) {
        Map<Position, Piece> updatedPieces = new HashMap<>(getPieces());
        updatedPieces.remove(position);
        return new Han(updatedPieces);
    }

    private static Map<Position, Piece> initializePieces() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 10, TeamType.HAN);
        createMas(pieces, 10, TeamType.HAN);
        createSangs(pieces, 10, TeamType.HAN);
        createSas(pieces, 10, TeamType.HAN);
        createGung(pieces, 9, TeamType.HAN);
        createPos(pieces, 8, TeamType.HAN);
        createJols(pieces, 7, TeamType.HAN);
        return pieces;
    }
}
