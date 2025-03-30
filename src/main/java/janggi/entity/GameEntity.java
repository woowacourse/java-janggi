package janggi.entity;

import janggi.dto.GameDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.game.Board;
import janggi.game.Game;
import java.util.ArrayList;
import java.util.List;

public class GameEntity {
    private static final List<GameEntity> GAME_ENTITIES = new ArrayList<>();

    private final int id;
    private Game game;

    protected GameEntity(int id, Game game) {
        this.id = id;
        this.game = game;
    }

    public static GameEntity addRecord(int id, Game game) {
        GameEntity gameEntity = new GameEntity(id, game);
        GAME_ENTITIES.add(gameEntity);
        return gameEntity;
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

    public static GameEntity findByGame(Game game) {
        return GAME_ENTITIES.stream().
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
