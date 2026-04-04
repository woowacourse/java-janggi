package janggi.controller;

import janggi.infra.ConnectionProvider;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.repository.dto.LatestInProgressGameResponse;
import janggi.repository.gameRepository;
import janggi.view.BoardType;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.GameStatus;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JanggiController {

    private final OutputView outputView;
    private final InputView inputView;
    private final ConnectionProvider connectionProvider;
    private final gameRepository gameRepository;

    public JanggiController(
            OutputView outputView,
            InputView inputView,
            ConnectionProvider connectionProvider,
            gameRepository gameRepository
    ) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.connectionProvider = connectionProvider;
        this.gameRepository = gameRepository;
    }

    public void run() {
        LatestInProgressGameResponse response = setUpJanggi();

        Long gameId = response.gameId();
        Janggi janggi = response.janggi();

        while (!janggi.isGameOver()) {
            janggi = movePiece(janggi);

            if (janggi.isGameOver()) {
                break;
            }

            boolean isDrawAccepted = readDrawAccept();
            if (isDrawAccepted) {
                janggi = janggi.draw();
                break;
            }

            if (!readIsContinued()) {
                break;
            }
        }

        if (janggi.isGameOver()) {
            Team winner = janggi.getWinner();
            outputView.printWinner(winner);
            gameRepository.deleteByGameId(connectionProvider.getConnection(), gameId);
        }
    }

    private LatestInProgressGameResponse setUpJanggi() {
        Connection con = connectionProvider.getConnection();

        Optional<LatestInProgressGameResponse> response = gameRepository
                .findLatestInProgressGame(con);

        if (response.isPresent()) {
            return response.get();
        }

        Janggi newGame = startNewGame();

        Long gameId = gameRepository.saveBoard(
                connectionProvider.getConnection(),
                newGame.getCurrentTeam().name(),
                newGame.getBoard().getBoardInfo()
        );

        return new LatestInProgressGameResponse(
                gameId,
                newGame
        );
    }

    private Janggi startNewGame() {
        outputView.printBoardInitialTypeMessage();
        BoardType boarType = inputView.readBoardInitializeType();
        return Janggi.of(boarType.getBoard());
    }

    private Janggi movePiece(Janggi janggi) {
        outputView.printGameStatus(GameStatus.from(janggi));

        Position from = readPosition();
        Position to = readPosition();

        janggi = janggi.play(from, to);

        gameRepository.updateBoardWith(connectionProvider.getConnection(), from, to);

        return janggi;
    }

    private Position readPosition() {
        outputView.printFromPositionMessage();
        return convertPositionInfoToPosition(inputView.readPosition());
    }


    private Position convertPositionInfoToPosition(List<Integer> positionInfo) {
        int rowIndex = 0;
        int rowNumber = positionInfo.get(rowIndex);

        if (rowNumber == 0) {
            rowNumber = 10;
        }

        int columnIndex = 1;
        Integer columnNumber = positionInfo.get(columnIndex);

        Row row = Row.of(rowNumber);
        Column column = Column.of(columnNumber);

        return new Position(row, column);
    }

    private boolean readDrawAccept() {
        outputView.printInputDrawAcceptPrompt();
        return inputView.readYesOrNo();
    }

    private boolean readIsContinued() {
        outputView.printInputContinuePrompt();
        return inputView.readYesOrNo();
    }
}
