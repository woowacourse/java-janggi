package domain.turn;

import domain.piece.Board;
import domain.TeamType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public abstract class Turn {

    protected final Board board;
    protected final TurnState turnState;

    protected Turn(Board board, TurnState turnState) {
        this.board = board;
        this.turnState = turnState;
    }

    public abstract Turn undo();

    public abstract Turn movePiece(Position moveFrom, Position moveTo);

    public abstract Map<TeamType, Double> calculateTeamScore();

    public abstract TeamType findWinTeam();

    public abstract boolean isFinished();

    public abstract GameState getGameState();

    public abstract Finished getFinished();

    public static Turn start(Map<Position, Piece> pieces) {
        return new Playing(new Board(pieces), new TurnState(false, TeamType.CHO));
    }

    public Map<Position, Piece> getAlivePieces() {
        return board.getAlivePieces();
    }

    public TeamType getPlayerTeam() {
        return turnState.playerTeam();
    }

    protected TeamType getNextPlayerTeam() {
        return turnState.playerTeam().otherTeam();
    }

    public TurnState getTurnState() {
        return this.turnState;
    }
}
