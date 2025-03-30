package janggi.dao;

import janggi.dto.GameDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.game.Board;
import janggi.game.Game;
import java.util.ArrayList;
import java.util.List;

public class GameRecord {
    private static final List<GameRecord> gameRecords = new ArrayList<>();

    private final int id;
    private Game game;

    protected GameRecord(int id, Game game) {
        this.id = id;
        this.game = game;
    }

    public static GameRecord addRecord(int id, Game game) {
        GameRecord gameRecord = new GameRecord(id, game);
        gameRecords.add(gameRecord);
        return gameRecord;
    }

    public static Game recreateGameFrom(PiecesOnBoardDto piecesOnBoardDto, GameDto gameDto) {
        Game game = new Game(
                new Board(piecesOnBoardDto.getRunningPieces()),
                piecesOnBoardDto.getAttackedPieces(),
                gameDto.turn(),
                gameDto.createdAt()
        );
        addRecord(gameDto.id(), game);
        return game;
    }

    public static GameRecord findByGame(Game game) {
        return gameRecords.stream().
                filter(record -> record.game.equals(game))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getTurn() {
        return game.getTurn().name();
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }
}
