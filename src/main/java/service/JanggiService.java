package service;

import domain.Board;
import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.JanggiGame;
import domain.state.Playing;
import java.util.List;
import java.util.Map;
import repository.BoardRepository;
import repository.GameRepository;
import repository.GameRoomInfo;

public class JanggiService {
    public static final String GAME_DOES_NOT_EXISTS = "게임 정보가 존재하지 않습니다.";
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;

    public JanggiService(BoardRepository boardRepository, GameRepository gameRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public List<GameRoomInfo> getRoomList() {
        return gameRepository.getAll();
    }

    public JanggiGame createGame(String title, SettingType choSettingType, SettingType hanSettingType) {
        long gameId = gameRepository.save(Team.CHO, title);
        JanggiGame game = Playing.init(gameId, choSettingType, hanSettingType);
        boardRepository.saveAll(game);
        return game;
    }

    public JanggiGame joinGame(long gameId) {
        Map<Position, Piece> load = boardRepository.load(gameId);
        Team turn = gameRepository.getInfo(gameId);

        if (load == null || turn == null) {
            throw new IllegalArgumentException(GAME_DOES_NOT_EXISTS);
        }

        return Playing.load(gameId, Board.of(load), turn);
    }

    public JanggiGame move(JanggiGame game, Position from, Position to) {
        JanggiGame updated = game.move(from, to);
        boardRepository.delete(updated, to);
        boardRepository.updatePosition(updated, from, to);
        gameRepository.updateTurn(updated);
        return updated;
    }

    public JanggiGame pass(JanggiGame game) {
        JanggiGame passed = game.pass();
        gameRepository.updateTurn(passed);
        return passed;
    }

    public void endGame(JanggiGame game) {
        gameRepository.updateResult(game);
    }
}
