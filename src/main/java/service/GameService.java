package service;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.Game;
import domain.vo.Position;
import repository.game_record.GameRecordRepository;
import repository.game_record.dto.GameRecord;
import repository.move_record.MoveRecordRepository;
import repository.move_record.dto.MoveRecord;

public class GameService {

    private final MoveRecordRepository moveRecordRepository;
    private final GameRecordRepository gameRecordRepository;

    public GameService(MoveRecordRepository moveRecordRepository, GameRecordRepository gameRecordRepository) {
        this.moveRecordRepository = moveRecordRepository;
        this.gameRecordRepository = gameRecordRepository;
    }

    public boolean existsGame() {
        return gameRecordRepository.existsGameRecord();
    }

    public Game loadGame() {
        GameRecord gameRecord = gameRecordRepository.findGameRecord();
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
        gameRecordRepository.deleteAll();
        moveRecordRepository.deleteAll();
        gameRecordRepository.save(new GameRecord(choFormation, hanFormation));

//        Board board = BoardFactory.createBoard(choFormation, hanFormation);
        Board board = BoardFactory.createTestBoard();
        return new Game(board);
    }

    public void moveAndSave(Game game, Position sourcePosition, Position targetPosition) {
        game.move(sourcePosition, targetPosition);

        if (game.isGameEnd()) {
            gameRecordRepository.deleteAll();
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
