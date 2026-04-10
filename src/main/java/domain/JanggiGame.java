package domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiGame {

    private final Board board;
    private final TurnManager turnManager;

    public JanggiGame(Board board, TurnManager turnManager) {
        this.board = board;
        this.turnManager = turnManager;
    }

    public TeamColor currentTurn() {
        return turnManager.getCurrentTurn();
    }

    public List<Map.Entry<Position, Piece>> currentTurnPieces() {
        return board.findPiecesByTeam(currentTurn());
    }

    public MovableRoutes findMovableRoutes(Piece piece) {
        return board.findMovableRoutes(piece);
    }

    public Optional<Piece> move(Piece piece, Position destination) {
        return board.move(piece, destination);
    }

    public void progressTurn() {
        turnManager.progressTurn();
    }

    public GameSnapshot captureSnapshot() {
        return board.capture();
    }

    public List<Piece> piecesOfTeam(TeamColor teamColor) {
        return board.piecesOfTeam(teamColor);
    }

    public Board board() {
        return board;
    }
}
