package janggi.presentation;

import janggi.application.JanggiGameService;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.status.Team;
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

    public JanggiGameController(JanggiGameService service) {
        this.service = service;
    }

    public void run() {
        Long roomId = chooseBoard(InputView.chooseNewGame());
        OutputView.printStartGame();
        OutputView.printGameStatus(service.getBoardStatus());
        OutputView.printCurrentScore(service.getHanScore(), service.getChoScore());
        while (!service.isFinished()) {
            playGame(roomId);
        }
        OutputView.printWinner(service.winner());
        Console.close();
    }

    private void playGame(Long roomId) {
        try {
            Team team = service.currentTurn();
            MoveCommand points = InputView.readPoints(team);
            service.play(roomId, points.from(), points.to());
            OutputView.printGameStatus(service.getBoardStatus());
            OutputView.printCurrentScore(service.getHanScore(), service.getChoScore());
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
        }
    }

    private Long chooseBoard(String chosen) {
        if (chosen.equals("n")) {
            Long gameRoomId = InputView.chooseExistsGame();
            service.loadExistsBoard(gameRoomId);
            return gameRoomId;
        }
        return service.startNewGame(readInitBoard());
    }

    private Board readInitBoard() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        List<PositionInfo> positionInfos = FileParser.readCsvFile("/janggi.csv");
        positionInfos.forEach(info -> pieces.put(info.point(), info.piece()));
        Board board = new Board();
        board.init(pieces);
        return board;
    }
}
