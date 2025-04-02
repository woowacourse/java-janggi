package domain;


import domain.participants.Player;
import domain.participants.Players;
import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.Map;

public class JanggiGame {
    private final GameStatus gameStatus;
    private final Players players;
    private final Board board;

    public JanggiGame(Players players, Board board, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.players = players;
        this.board = board;
    }

    public JanggiGame(Players players, Map<Position, Piece> pieces, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.players = players;
        this.board = new Board(pieces);
    }

    public void movePiece(Position startPosition, Position endPosition) {
        board.movePiece(startPosition, endPosition, gameStatus.getTurn());
        gameStatus.changeTurn();
    }

    public Player findWinner() {
        TeamType winTeam = board.findWinTeam();
        return findPlayerByTeam(winTeam);
    }

    public Map<Position, Piece> getAlivePiecesInfo() {
        return board.getAlivePieces();
    }

    public ScoreCalculator getScoreInfo() {
        return board.createScoreCalculator();
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public Player findPlayerByTeam(TeamType playerTeam) {
        return players.getTeamPlayer(playerTeam);
    }

    public TeamType getTurn() {
        return gameStatus.getTurn();
    }
}
