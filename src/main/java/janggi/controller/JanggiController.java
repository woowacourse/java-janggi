package janggi.controller;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.command.SetupCommand;
import janggi.domain.piece.Piece;
import janggi.domain.setup.ElephantFormation;
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
        Team redTeam = setupRedTeam();
        Team blueTeam = setupBlueTeam();
        Board board = BoardGenerator.generate(redTeam, blueTeam);
        TurnManager turnManager = new TurnManager();
        while (true) {
            OutputView.printBoard(BoardDto.from(board), turnManager.currentTeamType());
            Position from = inputFromPosition(board, turnManager);
            Piece piece = board.getPieceInPosition(from);
            List<Position> movable = piece.calculateMovablePositions(from, board);
            OutputView.printBoardWithMovable(BoardDto.from(board, movable));
            Position to = inputToPosition(movable);
            board.changeBoard(from, to);
            turnManager.changeTurn();
        }
    }

    private Team setupRedTeam() {
        OutputView.printSetupGuide(TeamType.RED);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final ElephantFormation elephantFormation = setupCommand.toPolicy();
        return new RedTeam(elephantFormation);
    }

    private Team setupBlueTeam() {
        OutputView.printSetupGuide(TeamType.BLUE);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final ElephantFormation elephantFormation = setupCommand.toPolicy();
        return new BlueTeam(elephantFormation);
    }

    private SetupCommand readSetupCommand() {
        int inputCommand = InputView.readSetupCommand();
        return SetupCommand.values()[inputCommand - 1];
    }

    private Position inputFromPosition(Board board, TurnManager turnManager) {
        while (true) {
            OutputView.printInputFromPosition();
            Position from = InputView.readPosition();
            Piece piece = board.getPieceInPosition(from);
            if (turnManager.checkFromTurn(piece)) {
                return from;
            }
        }
    }

    private Position inputToPosition(List<Position> movable) {
        while (true) {
            OutputView.printInputToPosition();
            Position from = InputView.readPosition();
            if (movable.contains(from)) {
                return from;
            }
        }
    }
}
