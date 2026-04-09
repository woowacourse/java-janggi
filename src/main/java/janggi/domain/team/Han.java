package janggi.domain.team;

import janggi.domain.GameResult;
import janggi.domain.Position;
import janggi.domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class Han extends Team {

    public Han(Map<Position, Piece> pieces, GameResult gameResult) {
        super(pieces, gameResult);
    }

    public static Han createInitialHan() {
        return new Han(initializePieces(), GameResult.RUNNING);
    }

    public static Han loadLastHan(Map<Position, Piece> pieces) {
        return new Han(pieces, GameResult.RUNNING);
    }

    @Override
    public Team move(Position start, Position end) {
        Map<Position, Piece> pieces = getPieces();
        Piece piece = pieces.get(start);
        Map<Position, Piece> updatedPieces = new HashMap<>(pieces);
        updatedPieces.remove(start);
        updatedPieces.put(end, piece);
        return new Han(updatedPieces, GameResult.RUNNING);
    }

    @Override
    public Team remove(Position position) {
        Map<Position, Piece> updatedPieces = new HashMap<>(getPieces());
        Piece removedPiece = updatedPieces.remove(position);
        if (removedPiece.getPieceType() == PieceType.GUNG) {
            return new Han(updatedPieces, GameResult.LOSE);
        }
        return new Han(updatedPieces, GameResult.RUNNING);
    }

    @Override
    public Team updateWin() {
        return new Han(getPieces(), GameResult.WIN);
    }

    @Override
    public double calculateTotalScore() {
        double totalScore = 0;
        for (Piece piece : getPieces().values()) {
            totalScore += piece.score();
        }
        return totalScore;
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
