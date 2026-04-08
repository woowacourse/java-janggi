package dao;

import common.GameStatus;
import domain.board.Board;
import domain.piece.BasicPiece;
import domain.player.Team;
import domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GamePersistence {
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;

    public GamePersistence(GameRoom gameRoom, BoardRepository boardRepository) {
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public long createNewGame(String choName, String hanName, Board board, Team currentTurn) {
        return TransactionExecutor.execute(connection -> {
            long gameId = gameRoom.createGame(connection, choName, hanName);
            boardRepository.save(connection, gameId, board);
            gameRoom.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
            return gameId;
        });
    }

    public void saveTurnProgress(long gameId, Board board, Team currentTurn) {
        TransactionExecutor.executeVoid(connection -> {
            boardRepository.save(connection, gameId, board);
            gameRoom.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
        });
    }

    public void saveBoard(long gameId, Board board) {
        TransactionExecutor.executeVoid(connection -> boardRepository.save(connection, gameId, board));
    }

    public void finishGame(long gameId, Board board, Team winnerTeam, GameStatus status) {
        TransactionExecutor.executeVoid(connection -> {
            boardRepository.save(connection, gameId, board);
            gameRoom.updateGameState(connection, gameId, winnerTeam, status);
        });
    }

    public List<GameInfo> findAllProgressGames() {
        return gameRoom.findAllProgressGames();
    }

    public Optional<Long> findProgressGame() {
        return gameRoom.findProgressGame();
    }

    public Team getCurrentTurn(long gameId) {
        return gameRoom.getCurrentTurn(gameId);
    }

    public PlayerNames getPlayerNames(long gameId) {
        return gameRoom.getPlayerNames(gameId);
    }

    public Map<Position, BasicPiece> loadBoard(long gameId) {
        return boardRepository.loadBoard(gameId);
    }
}
