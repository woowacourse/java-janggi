package janggi.domain.team;

import janggi.domain.game.GameResult;
import janggi.domain.Position;
import janggi.domain.piece.*;
import janggi.dto.BoardSpot;

import java.util.*;

public abstract class Team {

    private final Map<Position, Piece> pieces;
    private final GameResult gameResult;

    public Team(Map<Position, Piece> pieces, GameResult gameResult) {
        this.pieces = pieces;
        this.gameResult = gameResult;
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public boolean isRunning() {
        return gameResult == GameResult.RUNNING;
    }

    public boolean isLose() {
        return gameResult == GameResult.LOSE;
    }

    public boolean isWin() {
        return gameResult == GameResult.WIN;
    }

    public Map<Position, BoardSpot> makeSnapShot() {
        Map<Position, BoardSpot> snapShot = new HashMap<>();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            snapShot.put(position, new BoardSpot(piece.name(), piece.getTeamType()));
        }
        return snapShot;
    }

    public boolean isPieceExists(Position position) {
        return pieces.containsKey(position);
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    protected static void createChas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(1, indexY), new Cha(teamType));
        pieces.put(new Position(9, indexY), new Cha(teamType));
    }

    protected static void createMas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(2, indexY), new Ma(teamType));
        pieces.put(new Position(8, indexY), new Ma(teamType));
    }

    protected static void createSangs(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(7, indexY), new Sang(teamType));
        pieces.put(new Position(10 - 7, indexY), new Sang(teamType));
    }

    protected static void createSas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(4, indexY), new Sa(teamType));
        pieces.put(new Position(10 - 4, indexY), new Sa(teamType));
    }

    protected static void createGung(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(5, indexY), new Gung(teamType));
    }

    protected static void createPos(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(2, indexY), new Po(teamType));
        pieces.put(new Position(10 - 2, indexY), new Po(teamType));
    }

    protected static void createJols(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        for (int i = 1; i < 10; i += 2) {
            pieces.put(new Position(i, indexY), new Jol(teamType));
        }
    }

    public abstract Team move(Position start, Position end);

    public abstract Team remove(Position position);

    public abstract Team updateWin();

    public abstract double calculateTotalScore();
}
