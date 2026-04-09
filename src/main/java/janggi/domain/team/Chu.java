package janggi.domain.team;

import janggi.domain.game.GameResult;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.HashMap;
import java.util.Map;

public class Chu extends Team {

    public Chu(Map<Position, Piece> pieces, GameResult gameResult) {
        super(pieces, gameResult);
    }

    public static Chu createInitialChu() {
        return new Chu(initializePieces(), GameResult.RUNNING);
    }

    public static Chu loadLastChu(Map<Position, Piece> pieces) {
        return new Chu(pieces, GameResult.RUNNING);
    }

    @Override
    public Team move(Position start, Position end) {
        Map<Position, Piece> pieces = getPieces();
        Piece piece = pieces.get(start);
        Map<Position, Piece> updatedPieces = new HashMap<>(pieces);
        updatedPieces.remove(start);
        updatedPieces.put(end, piece);
        return new Chu(updatedPieces, GameResult.RUNNING);
    }

    @Override
    public Team remove(Position position) {
        Map<Position, Piece> updatedPieces = new HashMap<>(getPieces());
        Piece removedPiece = updatedPieces.remove(position);
        if (removedPiece.getPieceType() == PieceType.GUNG) {
            return new Chu(updatedPieces, GameResult.LOSE);
        }
        return new Chu(updatedPieces, GameResult.RUNNING);
    }

    @Override
    public Team updateWin() {
        return new Chu(getPieces(), GameResult.WIN);
    }

    @Override
    public double calculateTotalScore() {
        int totalScore = 0;
        for (Piece piece : getPieces().values()) {
            totalScore += piece.score();
        }
        return totalScore + 1.5;
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
