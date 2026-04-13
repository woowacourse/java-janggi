package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.dto.MoveCommand;
import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.piece.Team;
import janggi.repositiory.game.GameData;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.game.JdbcGameRepository;
import janggi.repositiory.piece.BoardSnapshot;
import janggi.repositiory.piece.JdbcPieceRepository;
import janggi.repositiory.piece.PieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JanggiController {
    final JanggiGame janggiGame;
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;
    private final JdbcDataSource dataSource;

    private JanggiController(GameRepository gameRepository, PieceRepository pieceRepository, JdbcDataSource dataSource, JanggiGame janggiGame) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
        this.dataSource = dataSource;
        this.janggiGame = janggiGame;
    }

    public static JanggiController startJanggi(JdbcDataSource dataSource) throws SQLException {
        GameRepository gameRepository = new JdbcGameRepository(dataSource);
        PieceRepository pieceRepository = new JdbcPieceRepository(dataSource);

        Connection conn = dataSource.getConnection();

        JanggiGame game = gameRepository.findLatestGame(conn)
                .filter(data -> !data.isFinished())
                .map(data -> resumeGame(conn, data, pieceRepository))
                .orElseGet(() -> {
                    try {
                        return createNewGame(conn, gameRepository, pieceRepository);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

        return new JanggiController(gameRepository, pieceRepository, dataSource, game);
    }

    private static JanggiGame createNewGame(Connection conn, GameRepository gameRepository, PieceRepository pieceRepository) throws SQLException {
        try {
            conn.setAutoCommit(false);

            JanggiGame game = new JanggiGame(new Board(BoardInitializer.createBoard()));
            Long id = gameRepository.save(conn, game.getFinishStatus(), game.getCurrentTeam());

            game.initSnapshot(id);

            pieceRepository.updateALL(conn, game.snapshot());

            conn.commit();
            return game;

        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException("새 게임 생성 중 오류가 발생하여 롤백되었습니다.", e);
        }
    }

    private static JanggiGame resumeGame(Connection conn, GameData data, PieceRepository pieceRepo) {
        JanggiGame game = new JanggiGame(new Board(pieceRepo.findAll(conn, data.gameId())), data.currentTurn());
        game.initSnapshot(data.gameId());
        game.resume();
        return game;
    }

    public void run() {
        printIntroMessage();

        while (!janggiGame.getFinishStatus().isFinished()) {
            Team currentTeam = janggiGame.getCurrentTeam();
            TurnCommand command = TurnCommand.fromOption(inputView.readTurnBehavior(currentTeam));

            if (command == TurnCommand.PLAY) {
                MoveCommand moveCommand = inputView.readMovePositions(currentTeam);
                playTurn(moveCommand);
                outputView.printBoard(janggiGame.getBoard());
            }

            if (command == TurnCommand.SKIP) {
                skipTurn();
                outputView.skipTurn();
            }

            if (command == TurnCommand.RESIGN) {
                resignTurn();
                outputView.printResign(currentTeam);
            }
        }

        outputView.printWinner(janggiGame.decideWinner());
    }

    void resignTurn() {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                janggiGame.resign();
                gameRepository.updateStatus(conn, janggiGame.snapshot().gameId(), janggiGame);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("resign 처리 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        }
    }

    void skipTurn() {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                janggiGame.skipTurn();
                gameRepository.updateStatus(conn, janggiGame.snapshot().gameId(), janggiGame);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("skip 처리 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        }
    }

    void playTurn(MoveCommand moveCommand) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                janggiGame.move(moveCommand.getFrom(), moveCommand.getTo());
                BoardSnapshot snapshot = janggiGame.snapshot();

                pieceRepository.updateALL(conn, snapshot);
                gameRepository.updateStatus(conn, snapshot.gameId(), janggiGame);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("이동 처리 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        }
    }

    private void printIntroMessage() {
        if (janggiGame.isResumed()) {
            outputView.printResumed();
        } else {
            outputView.printIntroduce();
        }

        outputView.printBoard(janggiGame.getBoard());
    }
}


