package service;

import domain.Board;
import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.GameInitializer;
import domain.state.JanggiGame;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import repository.BoardRepository;
import repository.ConnectionManager;
import repository.GameRepository;
import repository.GameRoomInfo;

public class JanggiService {
    public static final String GAME_DOES_NOT_EXISTS = "게임 정보가 유효하지 존재하지 않습니다.";
    public static final String GAME_HAS_BEEN_FINISHED = "이미 종료된 게임입니다.";
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;


    public JanggiService(BoardRepository boardRepository, GameRepository gameRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public List<GameRoomInfo> getRoomList() {
        return gameRepository.getAll();
    }

    public long createGame(String title, SettingType choSettingType, SettingType hanSettingType) {
        long gameId = gameRepository.save(Team.CHO, title);
        JanggiGame game = GameInitializer.init(choSettingType, hanSettingType);
        boardRepository.saveAll(game, gameId);
        return gameId;
    }

    public void move(long gameId, Position from, Position to) {
        JanggiGame game = loadGame(gameId);
        JanggiGame updated = game.move(from, to);

        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();

            conn.setAutoCommit(false);

            boardRepository.delete(conn, gameId, to);
            boardRepository.updatePosition(conn, gameId, from, to);
            gameRepository.updateGame(conn, gameId, updated);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public JanggiGame pass(long gameId) {
        JanggiGame game = loadGame(gameId);
        JanggiGame passed = game.pass();

        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();

            conn.setAutoCommit(false);
            gameRepository.updateGame(conn, gameId, passed);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
        return passed;
    }

    public JanggiGame loadGame(long gameId) {
        Map<Position, Piece> load = boardRepository.load(gameId);
        Team turn = gameRepository.getCurrentTeam(gameId);

        if (load == null || turn == null) {
            throw new IllegalArgumentException(GAME_DOES_NOT_EXISTS);
        }

        return GameInitializer.load(Board.of(load), turn);
    }

    public boolean isFinished(long gameId) {
        return gameRepository.isFinished(gameId);
    }

    public Team getCurrentTeam(long gameId) {
        return gameRepository.getCurrentTeam(gameId);
    }

    public Team getWinner(long gameId) {
        JanggiGame game = loadGame(gameId);
        return game.judgeWinner();
    }

    public void validateIsRoomAvailable(long gameId) {
        if (!gameRepository.isExists(gameId)) {
            throw new IllegalArgumentException(GAME_DOES_NOT_EXISTS);
        }
        if (gameRepository.isFinished(gameId)) {
            throw new IllegalArgumentException(GAME_HAS_BEEN_FINISHED);
        }
    }

    public Map<Position, Piece> getGameStatus(long gameId) {
        JanggiGame game = loadGame(gameId);
        return game.getBoard();
    }

    public double getTeamScore(long gameId, Team team) {
        JanggiGame game = loadGame(gameId);
        return game.getScoreByTeam(team);
    }
}
