package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import java.util.List;
import java.util.Map;

public class GameContext {
    private final TurnManager turnManager;
    private final Board board;

    public GameContext(final TurnManager turnManager, final Board board) {
        this.turnManager = turnManager;
        this.board = board;
    }

    public boolean canContinueGame() {
        return board.hasGeneral(turnManager.currentTeamType());
    }

    public void changeTurn() {
        turnManager.changeTurn();
    }

    public String currentTeamTypeToName() {
        return turnManager.currentTeamTypeToName();
    }

    public List<Position> calculateMovablePositions(Position from) {
        return board.calculateMovablePositions(from);
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to);
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return board.getPositionPieceMap();
    }

    public boolean isSameTeamType(Position from) {
        return board.isSameTeamType(from, currentTeamType());
    }

    public TeamType currentTeamType() {
        return turnManager.currentTeamType();
    }
}
