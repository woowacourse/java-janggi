package janggi.controller;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.setup.ElephantFormation;
import janggi.domain.board.setup.SetupStrategy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import janggi.dto.BoardDto;
import janggi.utils.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    public JanggiController() {
    }

    public void run() {
        Board board = BoardGenerator.generate(setupRedTeam(), setupBlueTeam());
        TurnManager turnManager = new TurnManager();
        while (board.hasGeneral(turnManager.currentTeamType())) {
            playTurn(board, turnManager);
        }
        turnManager.changeTurn();
        OutputView.printGameOverMessage(turnManager.currentTeamTypeToName());
    }

    private Team setupRedTeam() {
        OutputView.printSetupGuide(TeamType.RED);
        final SetupStrategy setupStrategyCommand = RetryExecutor.retry(this::readSetupCommand);
        final ElephantFormation elephantFormation = setupStrategyCommand.toPolicy();
        return new RedTeam(elephantFormation);
    }

    private Team setupBlueTeam() {
        OutputView.printSetupGuide(TeamType.BLUE);
        final SetupStrategy setupStrategyCommand = RetryExecutor.retry(this::readSetupCommand);
        final ElephantFormation elephantFormation = setupStrategyCommand.toPolicy();
        return new BlueTeam(elephantFormation);
    }

    private SetupStrategy readSetupCommand() {
        int inputCommand = InputView.readSetupCommand();
        return SetupStrategy.from(inputCommand);
    }

    private void playTurn(Board board, TurnManager turnManager) {
        OutputView.printBoard(BoardDto.from(board), turnManager.currentTeamTypeToName());
        Position from = findFromPosition(board, turnManager);
        List<Position> movable = board.calculateMovablePositions(from);
        OutputView.printBoardWithMovable(BoardDto.from(board), movable);
        Position to = RetryExecutor.retry(() -> inputToPosition(movable));
        board.movePiece(from, to);
        turnManager.changeTurn();
    }

    private Position findFromPosition(Board board, TurnManager turnManager) {
        while (true) {
            Position from = RetryExecutor.retry(() -> inputFromPosition(board, turnManager));
            List<Position> movable = board.calculateMovablePositions(from);
            if (!movable.isEmpty()) {
                return from;
            }
            OutputView.printErrorMessage("이동 가능한 위치가 없습니다. 다른 기물을 선택하세요.");
        }
    }

    private Position inputFromPosition(Board board, TurnManager turnManager) {
        while (true) {
            try {
                OutputView.printInputFromPosition();
                Position from = InputView.readPosition();
                if (!board.isSameTeamType(from, turnManager.currentTeamType())) {
                    throw new IllegalArgumentException("자신의 기물을 선택하세요.");
                }
                return from;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
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
}
