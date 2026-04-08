package janggi.controller;

import janggi.db.GameDao;
import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.setup.ElephantFormation;
import janggi.domain.board.setup.SetupStrategy;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import janggi.dto.BoardDto;
import janggi.utils.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {
    private final GameDao gameDao = new GameDao();
    private GameContext gameContext;

    public JanggiController() {
    }

    public void run() {
        OutputView.printStartJanggi();
        int inputCommand = RetryExecutor.retry(this::inputStarCommand);
        if (inputCommand == 1) {
            gameContext = createNewGameContext();
        }
        if (inputCommand == 2) {
            gameContext = loadPreviousGameContext();
        }
        startGame(gameContext);
    }

    private int inputStarCommand() {
        return InputView.readIntegerCommand();
    }

    private GameContext createNewGameContext() {
        Board board = BoardGenerator.generate(setupTeam(TeamType.RED), setupTeam(TeamType.BLUE));
        return new GameContext(new TurnManager(), board);
    }

    private void startGame(GameContext gameContext) {
        while (gameContext.canContinueGame()) {
            playTurn(gameContext);
            gameDao.saveGame(gameContext);
        }
        gameContext.changeTurn();
        OutputView.printGameOverMessage(gameContext.currentTeamTypeToName());
    }

    private Team setupTeam(TeamType teamType) {
        OutputView.printSetupGuide(teamType);
        final SetupStrategy setupStrategyCommand = RetryExecutor.retry(this::readSetupCommand);
        final ElephantFormation elephantFormation = setupStrategyCommand.toPolicy();
        return new Team(teamType, elephantFormation);
    }

    private SetupStrategy readSetupCommand() {
        int inputCommand = InputView.readIntegerCommand();
        return SetupStrategy.from(inputCommand);
    }

    private void playTurn(GameContext gameContext) {
        OutputView.printBoard(BoardDto.from(gameContext), gameContext.currentTeamTypeToName());
        Position from = findFromPosition(gameContext);
        List<Position> movable = gameContext.calculateMovablePositions(from);
        OutputView.printBoardWithMovable(BoardDto.from(gameContext), movable);
        Position to = RetryExecutor.retry(() -> inputToPosition(movable));
        gameContext.movePiece(from, to);
        gameContext.changeTurn();
    }

    public Position findFromPosition(GameContext gameContext) {
        while (true) {
            Position from = RetryExecutor.retry(() -> inputFromPosition(gameContext));
            List<Position> movable = gameContext.calculateMovablePositions(from);
            if (!movable.isEmpty()) {
                return from;
            }
            OutputView.printErrorMessage("이동 가능한 위치가 없습니다. 다른 기물을 선택하세요.");
        }
    }

    private Position inputToPosition(List<Position> movable) {
        while (true) {
            try {
                OutputView.printInputToPosition();
                Position to = InputView.readPosition();
                if (!movable.contains(to)) {
                    throw new IllegalArgumentException("이동 불가능한 위치입니다.");
                }
                return to;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position inputFromPosition(GameContext gameContext) {
        while (true) {
            try {
                OutputView.printInputFromPosition();
                Position from = InputView.readPosition();
                if (!gameContext.isSameTeamType(from)) {
                    throw new IllegalArgumentException("자신의 기물을 선택하세요.");
                }
                return from;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private GameContext loadPreviousGameContext() {
        return gameDao.loadPreviousGame();
    }
}
