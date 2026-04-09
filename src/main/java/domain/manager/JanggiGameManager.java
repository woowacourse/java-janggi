package domain.manager;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.BasicPiece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import domain.rule.GameResultEngine;

import java.util.Map;

public class JanggiGameManager {

    private final Board board;
    private final GameResultEngine gameResultEngine;
    private Player currentPlayer;
    private Player standbyPlayer;
    private GameState gameState;

    public JanggiGameManager(
            Player choPlayer,
            Player hanPlayer,
            Formation choFormation,
            Formation hanFormation,
            GameResultEngine gameResultEngine
    ) {
        this.currentPlayer = choPlayer;
        this.standbyPlayer = hanPlayer;
        this.board = BoardFactory.createWithFormation(choFormation, hanFormation);
        this.gameResultEngine = gameResultEngine;
        this.gameState = GameState.RUNNING;
    }

    private JanggiGameManager(
            Player choPlayer,
            Player hanPlayer,
            Board loadedBoard,
            Team currentTeam,
            GameResultEngine gameResultEngine
    ) {
        this.board = loadedBoard;
        this.gameResultEngine = gameResultEngine;
        this.gameState = GameState.RUNNING;

        Map<Team, Player> players = Map.of(
                Team.CHO, choPlayer,
                Team.HAN, hanPlayer
        );

        this.currentPlayer = players.get(currentTeam);
        this.standbyPlayer = players.get(currentTeam.opposite());
    }

    public static JanggiGameManager fromLoadedState(
            Player choPlayer,
            Player hanPlayer,
            Board loadedBoard,
            Team currentTeam,
            GameResultEngine gameResultEngine
    ) {
        return new JanggiGameManager(choPlayer, hanPlayer, loadedBoard, currentTeam, gameResultEngine);
    }

    public void move(Position source, Position destination) {
        BasicPiece caughtPiece = board.move(source, destination, currentPlayer);

        if (caughtPiece.isType(PieceType.JANG)) {
            gameState = GameState.FINISHED;
            return;
        }

        if (gameResultEngine.isDraw(board)) {
            gameState = GameState.DRAW_FINISHED;
            return;
        }

        switchTurn();
    }

    public void validateSource(Position source) {
        board.validateSource(source, currentPlayer);
    }

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = standbyPlayer;
        standbyPlayer = temp;
    }

    public void endGame() {
        gameState = GameState.FINISHED;
    }

    public PlayerProfile calculateFinalScore() {
        return gameResultEngine.calculateFinalScore(
                board,
                currentPlayer,
                standbyPlayer,
                gameState == GameState.DRAW_FINISHED
        );
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
