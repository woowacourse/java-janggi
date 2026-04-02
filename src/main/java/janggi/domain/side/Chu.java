package janggi.domain.side;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class Chu extends Team {

    public Chu(Map<Position, Piece> pieces) {
        super(pieces);
    }

    public static Chu createInitialChu() {
        return new Chu(initializePieces());
    }

    @Override
    public Team move(Position start, Position end) {
        Map<Position, Piece> pieces = getPieces();
        Piece piece = pieces.get(start);
        Map<Position, Piece> updatedPieces = new HashMap<>(pieces);
        updatedPieces.remove(start);
        updatedPieces.put(end, piece);
        return new Chu(updatedPieces);
    }

    @Override
    public Team remove(Position position) {
        Map<Position, Piece> updatedPieces = new HashMap<>(getPieces());
        updatedPieces.remove(position);
        return new Chu(updatedPieces);
    }

    private static Map<Position, Piece> initializePieces() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 1, TeamType.CHU);
        createMas(pieces, 1, TeamType.CHU);
        createSangs(pieces, 1, TeamType.CHU);
        createSas(pieces, 1, TeamType.CHU);
        createGung(pieces, 2, TeamType.CHU);
        createPos(pieces, 3, TeamType.CHU);
        createJols(pieces, 4, TeamType.CHU);
        return pieces;
    }
}
