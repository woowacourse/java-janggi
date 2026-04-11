package team.janggi.service;

import java.util.List;
import java.util.Map;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardFactory;
import team.janggi.domain.piece.Piece;
import team.janggi.entity.Game;
import team.janggi.infra.TransactionManager;
import team.janggi.repository.BoardPieceRepository;
import team.janggi.repository.GameRepository;

public class GameService {

    private final GameRepository gameRepository;
    private final BoardPieceRepository boardPieceRepository;
    private final TransactionManager transactionManager;

    public GameService(GameRepository gameRepository, BoardPieceRepository boardPieceRepository,
                       TransactionManager transactionManager) {
        this.gameRepository = gameRepository;
        this.boardPieceRepository = boardPieceRepository;
        this.transactionManager = transactionManager;
    }

    public void saveGame(String gameName, Team team, Board board) {
        transactionManager.executeTransaction((connection) -> {
            String currentTurn = team.name();
            int gameId = gameRepository.saveGame(gameName, currentTurn, connection);
            boardPieceRepository.saveBoardPiece(gameId, board.getStatus(), connection);
        });
    }

    public List<Game> getGames() {
        return transactionManager.executeTransaction(gameRepository::loadGame);
    }

    public Board loadBoard(int gameId) {
        return transactionManager.executeTransaction((connection) ->
        {
            Map<Position, Piece> boardPiece = boardPieceRepository.loadBoardPiece(gameId, connection);
            return new Board(boardPiece, new BoardFactory(JanggiFormation.EHHE, JanggiFormation.EHHE));
        });
    }

    public Team loadTurn(int gameId) {
        return transactionManager.executeTransaction((connection) -> {
            Game game = gameRepository.findGameById(gameId, connection);
            return Team.from(game.getCurrentTurn());
        });
    }
}
