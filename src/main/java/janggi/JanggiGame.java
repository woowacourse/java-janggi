package janggi;

import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiGame {

    private static final String END_COMMAND = "end";
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;
    private Board board;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        initializeBoard();
        outputView.printBoard(board.showBoard());
        play();
    }

    private void initializeBoard() {
        String hanSetup = inputView.readHanSetup();
        String choSetup = inputView.readChoSetup();
        board = BoardFactory.create(PieceSetup.from(hanSetup), PieceSetup.from(choSetup));
    }

    private void play() {
        Team currentTeam = Team.CHO;
        while (!board.isGeneralCaptured(currentTeam)) {
            List<String> positions = inputView.readPosition(currentTeam.getDisplayName());
            if (isEndCommand(positions)) {
                outputView.printGameEnd();
                return;
            }
            currentTeam = processTurn(positions, currentTeam);
        }
        outputView.printWinner(currentTeam.convert());
    }

    private boolean isEndCommand(List<String> positions) {
        return positions.getFirst().equals(END_COMMAND);
    }

    private Team processTurn(List<String> positions, Team currentTeam) {
        try {
            board.move(createMovement(positions), currentTeam);
            outputView.printBoard(board.showBoard());
            return currentTeam.convert();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return currentTeam;
        }
    }

    private Movement createMovement(List<String> positions) {
        Position from = Position.from(positions.get(FROM_INDEX));
        Position to = Position.from(positions.get(TO_INDEX));
        return new Movement(from, to);
    }
}
