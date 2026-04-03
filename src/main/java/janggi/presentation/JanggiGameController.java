package janggi.presentation;

import janggi.application.JanggiGameService;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.status.Team;
import janggi.presentation.dto.GameCommand;
import janggi.presentation.dto.MoveCommand;
import janggi.presentation.dto.PositionInfo;
import janggi.presentation.ui.InputView;
import janggi.presentation.ui.OutputView;
import janggi.presentation.util.Console;
import janggi.presentation.util.FileParser;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JanggiGameController {

    private final JanggiGameService service;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGameController(JanggiGameService service, InputView inputView, OutputView outputView) {
        this.service = service;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        long roomId = chooseBoard(inputView.chooseNewGame());
        outputView.printStartGame();
        outputView.printGameStatus(service.getBoardStatus(roomId));
        outputView.printCurrentScore(service.getHanScore(roomId), service.getChoScore(roomId));
        while (!service.isFinished(roomId)) {
            playGame(roomId);
        }
        outputView.printWinner(service.winner(roomId));
        Console.close();
    }

    private void playGame(Long roomId) {
        try {
            Team team = service.currentTurn(roomId);
            outputView.printCurrentTurn(team);
            MoveCommand points = inputView.readPoints();
            service.play(roomId, points.from(), points.to());
            outputView.printGameStatus(service.getBoardStatus(roomId));
            outputView.printCurrentScore(service.getHanScore(roomId), service.getChoScore(roomId));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private long chooseBoard(GameCommand command) {
        if (command.isNewGame()) {
            return service.startNewGame(readInitBoard());
        }
        Long roomId = inputView.chooseExistsGame();
        service.loadExistsBoard(roomId);
        return roomId;
    }

    private Board readInitBoard() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/janggi.csv");
        positionInfos.forEach(info -> pieces.put(info.point(), info.piece()));
        return new Board(pieces);
    }
}
