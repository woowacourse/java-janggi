package janggi.controller;

import janggi.domain.SetupPolicy;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.command.SetupCommand;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import janggi.utils.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    public JanggiController() {
    }

    private Team setupRedTeam() {
        OutputView.printSetupGuide(TeamType.RED);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final SetupPolicy setupPolicy = setupCommand.toPolicy();
        return new RedTeam(setupPolicy);
    }

    private Team setupBlueTeam() {
        OutputView.printSetupGuide(TeamType.BLUE);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        final SetupPolicy setupPolicy = setupCommand.toPolicy();
        return new BlueTeam(setupPolicy);
    }

    public void run() {
        Team redTeam = setupRedTeam();
        Team blueTeam = setupBlueTeam();
        Board board = BoardGenerator.generate(redTeam, blueTeam);
        OutputView.printBoard(BoardDto.from(board));
    }

    private SetupCommand readSetupCommand() {
        int commandNumber = InputView.readSetupCommand();
        return SetupCommand.pick(commandNumber);
    }
}
