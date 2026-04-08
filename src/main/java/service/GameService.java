package service;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.Game;
import domain.Position;
import repository.game.GameRecord;
import repository.game.GameRepository;
import repository.move_record.MoveRecord;
import repository.move_record.MoveRecordRepository;

public class GameService {

    private final MoveRecordRepository moveRecordRepository;
    private final GameRepository gameRepository;

    public GameService(MoveRecordRepository moveRecordRepository, GameRepository gameRepository) {
        this.moveRecordRepository = moveRecordRepository;
        this.gameRepository = gameRepository;
    }

    public boolean existsGame() {
        return gameRepository.existsGameRecord();
    }

    public Game loadGame() {
        GameRecord gameRecord = gameRepository.findGameRecord();
        Board board = BoardFactory.createBoard(gameRecord.choFormation(), gameRecord.hanFormation());
        Game game = new Game(board);
        for (MoveRecord moveRecord : moveRecordRepository.findAll()) {
            Position source = Position.of(moveRecord.sourceX(), moveRecord.sourceY());
            Position target = Position.of(moveRecord.targetX(), moveRecord.targetY());
            game.move(source, target);
        }
        return game;
    }

    public Game createGame(Formation choFormation, Formation hanFormation) {
        gameRepository.deleteAll();
        moveRecordRepository.deleteAll();
        gameRepository.save(new GameRecord(choFormation, hanFormation));

//        Board board = BoardFactory.createBoard(choFormation, hanFormation);
        Board board = BoardFactory.createTestBoard();
        return new Game(board);
    }

    public void moveAndSave(Game game, Position sourcePosition, Position targetPosition) {
        game.move(sourcePosition, targetPosition);

        if (game.isGameEnd()) {
            gameRepository.deleteAll();
            moveRecordRepository.deleteAll();
            return;
        }

        moveRecordRepository.save(
            new MoveRecord(
                sourcePosition.getX(),
                sourcePosition.getY(),
                targetPosition.getX(),
                targetPosition.getY(),
                game.getCurrentTurn().opposite()
            )
        );
    }
}
