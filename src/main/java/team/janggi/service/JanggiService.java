package team.janggi.service;

import team.janggi.application.TransactionManager;
import team.janggi.config.AppConfig;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.NormalBoardPiecesInitializer;
import team.janggi.domain.board.NormalSetup;
import team.janggi.domain.Game;
import team.janggi.exception.GameNotFinishedException;
import team.janggi.exception.GameNotFoundException;
import team.janggi.exception.GameOverException;
import team.janggi.repository.BoardRepository;
import team.janggi.repository.GameRoomRepository;
import team.janggi.repository.dto.BoardViewDTO;
import team.janggi.repository.dto.GameRoomInfoDTO;

public class JanggiService {
    private final GameRoomRepository gameRoomRepository;
    private final BoardRepository boardRepository;
    private final TransactionManager transactionManager;

    public JanggiService(GameRoomRepository gameRoomRepository,
                         BoardRepository boardRepository,
                         TransactionManager transactionManager) {
        this.gameRoomRepository = gameRoomRepository;
        this.boardRepository = boardRepository;
        this.transactionManager = transactionManager;
    }

    public long createGameRoom(NormalSetup choSetup, NormalSetup hanSetup) {
        return transactionManager.execute(() -> {
            final Game game = new Game(Team.CHO);
            final long gameRoomId = gameRoomRepository.save(game);

            final Board board = new Board(new NormalBoardPiecesInitializer(choSetup, hanSetup));
            boardRepository.save(gameRoomId, board);
            return gameRoomId;
        });
    }

    public GameRoomInfoDTO findGameRoom(long gameRoomId) {
        final Game game = gameRoomRepository.findById(gameRoomId);
        if (game == null) {
            throw new GameNotFoundException();
        }

        return new GameRoomInfoDTO(game.getId(), game.getCurrentTurn());
    }

    public void move(long gameRoomId, Team team, Position from, Position to) {
        final Game game = gameRoomRepository.findById(gameRoomId);
        if (game == null) {
            throw new GameNotFoundException();
        }

        final Board board = boardRepository.findById(gameRoomId);
        if (board == null) {
            throw new GameNotFoundException();
        }

        if (board.isGameFinished()) {
            throw new GameOverException();
        }

        transactionManager.execute(() -> {
            game.changeTurn();
            board.move(team, from, to);

            gameRoomRepository.save(game);
            boardRepository.save(gameRoomId, board);
            return null;
        });
    }

    public boolean isGameOver(long gameRoomId) {
        final Board board = boardRepository.findById(gameRoomId);

        if (board == null) {
            throw new GameNotFoundException();
        }

        return board.isGameFinished();
    }

    public Team getWinnerTeam(long gameRoomId) {
        final Board board = boardRepository.findById(gameRoomId);
        if (board == null) {
            throw new GameNotFoundException();
        }

        if (!board.isGameFinished()) {
            throw new GameNotFinishedException();
        }

        return board.getWinner();
    }

    public Team getTurn(long gameRoomId) {
        final Game game = gameRoomRepository.findById(gameRoomId);

        if (game == null) {
            throw new GameNotFoundException();
        }
        return game.getCurrentTurn();
    }

    public BoardViewDTO getBoardView(long gameRoomId) {
        final Board board = boardRepository.findById(gameRoomId);
        if (board == null) {
            throw new GameNotFoundException();
        }

        return new BoardViewDTO(getTurn(gameRoomId),
                board.getScore(Team.CHO), board.getScore(Team.HAN),
                board.getStateReader());
    }
}
