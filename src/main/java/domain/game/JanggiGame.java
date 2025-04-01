package domain.game;

import domain.TeamType;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import domain.turn.Finished;
import domain.turn.GameState;
import domain.turn.Turn;
import domain.turn.TurnState;
import java.util.HashMap;
import java.util.Map;

public class JanggiGame {

    private final Players players;
    private Turn turn;

    private JanggiGame(Players players, Turn turn) {
        this.players = players;
        this.turn = turn;
    }

    public static JanggiGame start(Players players, HorseElephantSetupStrategy choPlayerStrategy,
                                   HorseElephantSetupStrategy hanPlayerStrategy) {
        Map<Position, Piece> pieces = createAllPieces(choPlayerStrategy, hanPlayerStrategy);
        return new JanggiGame(players, Turn.start(pieces));
    }

    public static JanggiGame from(Players players, Turn turn) {
        return new JanggiGame(players, turn);
    }

    private static Map<Position, Piece> createAllPieces(HorseElephantSetupStrategy choPlayerStrategy,
                                                        HorseElephantSetupStrategy hanPlayerStrategy) {
        PieceFactory factory = new PieceFactory();
        return factory.createAllPieces(choPlayerStrategy, hanPlayerStrategy);
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

    public Map<String, Double> calculatePlayerScore() {
        Map<String, Double> playerNameScore = new HashMap<>();
        Map<TeamType, Double> teamScore = turn.calculateTeamScore();

        playerNameScore.put(players.getChoPlayerName(), teamScore.get(TeamType.CHO));
        playerNameScore.put(players.getHanPlayerName(), teamScore.get(TeamType.HAN));

        return playerNameScore;
    }

    public boolean isFinishedByCheckmate() {
        Finished finished = turn.getFinished();
        return finished.isFinishedByCheckmate();
    }

    public GameState getGameState() {
        return turn.getGameState();
    }

    public TurnState getTurnState() {
        return turn.getTurnState();
    }
}
