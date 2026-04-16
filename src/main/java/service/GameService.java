package service;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.Game;
import domain.game.GameStatus;
import domain.piece.Side;
import domain.vo.Position;
import java.util.List;
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
        List<GameRecord> inProgressGames = gameRecordRepository.findAllGameRecordsByGameStatus(GameStatus.IN_PROGRESS);
        return inProgressGames.size() == 1;
    }

    public Game loadGame() {
        GameRecord gameRecord = findInProgressGameRecord();
        Board board = BoardFactory.createBoard(gameRecord.choFormation(), gameRecord.hanFormation());
        Game game = new Game(board);
        for (MoveRecord moveRecord : moveRecordRepository.findAllByGameRecordId(gameRecord.id())) {
            Position source = Position.of(moveRecord.sourceX(), moveRecord.sourceY());
            Position target = Position.of(moveRecord.targetX(), moveRecord.targetY());
            game.move(source, target);
        }

        return game;
    }

    public Game finishGamesAndCreateGame(Formation choFormation, Formation hanFormation) {
        finishInProgressGames();
        gameRecordRepository.save(new GameRecord(choFormation, hanFormation));
        Board board = BoardFactory.createBoard(choFormation, hanFormation);
        return new Game(board);
    }

    public void moveAndSave(Game game, Position sourcePosition, Position targetPosition) {
        GameRecord gameRecord = findInProgressGameRecord();
        Side movingSide = game.getCurrentTurn();
        game.move(sourcePosition, targetPosition);

        moveRecordRepository.save(gameRecord.id(),
            new MoveRecord(sourcePosition.getX(), sourcePosition.getY(), targetPosition.getX(), targetPosition.getY(),
                movingSide));

        if (game.isGameEnd()) {
            finishInProgressGames();
        }
    }

    private GameRecord findInProgressGameRecord() {
        return gameRecordRepository.findGameRecordByGameStatus(GameStatus.IN_PROGRESS);
    }

    private void finishInProgressGames() {
        gameRecordRepository.updateGameStatuses(GameStatus.IN_PROGRESS, GameStatus.FINISHED);
    }
}
