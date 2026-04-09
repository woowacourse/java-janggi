package service;

import domain.Game;
import domain.board.BoardInitializer;
import domain.board.DatabaseBoardInitializer;
import domain.coordinate.Position;
import java.sql.Connection;
import java.sql.SQLException;
import repository.Database;
import repository.GameRepository;
import repository.PieceRepository;

public class JanggiService {

    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private final BoardInitializer defaultBoardInitializer;

    public JanggiService(GameRepository gameRepository, PieceRepository pieceRepository,
                         BoardInitializer defaultBoardInitializer) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
        this.defaultBoardInitializer = defaultBoardInitializer;
    }

    public Game initializeGame() {
        if (gameRepository.doesGameExist()) {
            BoardInitializer dbInitializer = new DatabaseBoardInitializer(pieceRepository, gameRepository);
            return new Game(dbInitializer);
        }
        try (Connection connection = Database.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Game game = new Game(defaultBoardInitializer);
                gameRepository.createGame(connection, game.getTurn());
                pieceRepository.updatePiecesPosition(connection, game.getBoardSnapshotMap());
                connection.commit();
                return game;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("게임 초기화 중 오류가 발생했습니다.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 초기화 중 오류가 발생했습니다.", e);
        }
    }

    public void movePiece(Game game, Position start, Position destination) {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);
            try {
                game.move(start, destination);
                pieceRepository.updatePiecesPosition(connection, game.getBoardSnapshotMap());
                gameRepository.updateCurrentTurn(connection, game.getTurn());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("기물 이동 중 오류가 발생했습니다.", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 이동 중 오류가 발생했습니다.", e);
        }
    }
}