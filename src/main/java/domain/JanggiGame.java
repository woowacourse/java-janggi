package domain;

import domain.piece.Piece;
import domain.position.Position;
import domain.turn.Finished;
import domain.turn.Turn;
import java.util.HashMap;
import java.util.Map;

public class JanggiGame {

    private final Players players;
    private Turn turn;

    public JanggiGame(Players players, Map<Position, Piece> pieces) {
        this.players = players;
        this.turn = Turn.start(pieces);
    }

    public void movePiece(Position startPosition, Position endPosition) {
        this.turn = turn.movePiece(startPosition, endPosition);
    }

    public Player findWinner() {
        TeamType winTeam = turn.findWinTeam();
        return players.getTeamPlayer(winTeam);
    }

    public Map<Position, Piece> getAlivePieces() {
        return turn.getAlivePieces();
    }

    public boolean isInProgress() {
        return !turn.isFinished();
    }

    public void undo() {
        this.turn = turn.undo();
    }

    public Player getCurrentPlayer() {
        TeamType playerTeam = turn.getPlayerTeam();
        return players.getTeamPlayer(playerTeam);
    }

    public Map<Player, Double> calculatePlayerScore() {
        Map<Player, Double> playerScore = new HashMap<>();
        Map<TeamType, Double> teamScore = turn.calculateTeamScore();

        for (TeamType team : teamScore.keySet()) {
            playerScore.put(players.getTeamPlayer(team), teamScore.get(team));
        }

        return playerScore;
    }

    public boolean isFinishedByCheckmate() {
        Finished finished = turn.getFinished();
        return finished.isFinishedByCheckmate();
    }
}
