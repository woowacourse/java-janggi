package janggi.piece;

import janggi.board.TableOption;
import janggi.position.Position;
import janggi.team.Team;

import java.util.*;

public class PieceGenerator {
    public Map<Position, Piece> generateInitialPieces(TableOption hanTableOption, TableOption choTableOption) {
        Map<Position, Piece> allPieces = new HashMap<>();

        allPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(1, 9), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        allPieces.put(new Position(3, 2), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(3, 8), new DefaultPiece(Team.HAN, PieceType.CANNON));
        allPieces.put(new Position(4, 1), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 3), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 5), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 7), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(4, 9), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        allPieces.put(new Position(1, 4), new PalacePiece(Team.HAN, PieceType.GUARD));
        allPieces.put(new Position(1, 6), new PalacePiece(Team.HAN, PieceType.GUARD));
        allPieces.put(new Position(2, 5), new PalacePiece(Team.HAN, PieceType.KING));

        allPieces.put(new Position(10, 1), new DefaultPiece(Team.CHO, PieceType.CHARIOT));
        allPieces.put(new Position(10, 9), new DefaultPiece(Team.CHO, PieceType.CHARIOT));
        allPieces.put(new Position(8, 2), new DefaultPiece(Team.CHO, PieceType.CANNON));
        allPieces.put(new Position(8, 8), new DefaultPiece(Team.CHO, PieceType.CANNON));
        allPieces.put(new Position(7, 1), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        allPieces.put(new Position(7, 3), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        allPieces.put(new Position(7, 5), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        allPieces.put(new Position(7, 7), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        allPieces.put(new Position(7, 9), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        allPieces.put(new Position(10, 4), new PalacePiece(Team.CHO, PieceType.GUARD));
        allPieces.put(new Position(10, 6), new PalacePiece(Team.CHO, PieceType.GUARD));
        allPieces.put(new Position(9, 5), new PalacePiece(Team.CHO, PieceType.KING));

        allPieces.putAll(Team.CHO.generateTableSetPieces(choTableOption));
        allPieces.putAll(Team.HAN.generateTableSetPieces(hanTableOption));

        return allPieces;
    }
}
