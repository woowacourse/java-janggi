package domain;

import domain.game.dto.JanggiGameDto;
import domain.piece.Piece;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import util.ConnectionFactory;
import util.TransactionManager;

public class JanggiTransactionManager {

    private final JanggiManager janggiManager;
    private final TransactionManager transactionManager;

    public JanggiTransactionManager(ConnectionFactory connectionFactory) {
        this.janggiManager = new JanggiManager();
        this.transactionManager = new TransactionManager(connectionFactory);
    }

    public List<JanggiGameDto> findInProgressGames() {
        return transactionManager.execute(janggiManager::findInProgressGames);
    }

    public long saveNewGame(Players players, HorseElephantSetupStrategy choStrategy,
                            HorseElephantSetupStrategy hanStrategy) {
        return transactionManager.execute(
                connection -> janggiManager.saveNewGame(players, choStrategy, hanStrategy, connection));
    }

    public void undo(Long gameId) {
        transactionManager.executeWithoutResult(connection -> janggiManager.undo(gameId, connection));
    }

    public void movePiece(Long gameId, Position from, Position to) {
        transactionManager.executeWithoutResult(connection -> janggiManager.movePiece(gameId, from
                , to, connection));
    }

    public Map<Position, Piece> getGamePieces(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.getGamePieces(gameId, connection));
    }

    public boolean isInProgress(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.isInProgress(gameId, connection));
    }

    public Player getCurrentPlayer(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.getCurrentPlayer(gameId, connection));
    }

    public Player findWinner(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.findWinner(gameId, connection));
    }

    public boolean isFinishedByCheckmate(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.isFinishedByCheckmate(gameId, connection));
    }

    public Map<String, Double> calculatePlayerScore(Long gameId) {
        return transactionManager.execute(connection -> janggiManager.calculatePlayerScore(gameId, connection));
    }
}
