package service;

import domain.Game;
import domain.board.BoardInitializer;
import domain.board.DatabaseBoardInitializer;
import domain.coordinate.Position;
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
        return Database.executeInTransaction(connection -> {
            Game game = new Game(defaultBoardInitializer);
            gameRepository.createGame(connection, game.getTurn());
            pieceRepository.updatePiecesPosition(connection, game.getBoardPiecesPosition());
            return game;
        }, "게임 초기화 중 오류가 발생했습니다.");
    }

    public void movePiece(Game game, Position start, Position destination) {
        Database.executeInTransaction(connection -> {
            game.move(start, destination);
            pieceRepository.updatePiecesPosition(connection, game.getBoardPiecesPosition());
            gameRepository.updateCurrentTurn(connection, game.getTurn());
            return null;
        }, "기물 이동 중 오류가 발생했습니다.");
    }

    public void resetGame() {
        Database.executeInTransaction(connection -> {
            gameRepository.resetAll(connection);
            pieceRepository.resetAll(connection);
            return null;
        }, "게임 초기화 중 오류가 발생했습니다.");
    }
}
