package janggi;

import janggi.domain.piece.Piece;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;

public class JanggiGame {

    private static final String END_COMMAND = "end";
    private static final String KING_NAME = "장";

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
        String choSetup = inputView.readChosetup();
        board = BoardFactory.create(hanSetup, choSetup);
    }

    private void play() {
        Team currentTeam = Team.CHO;
        while (currentTeam != Team.NONE) {
            String input = inputView.readPosition(currentTeam.getDisplayName());
            if (input.equals(END_COMMAND)) {
                outputView.printGameEnd();
                return;
            }
            currentTeam = processTurn(input, currentTeam);
        }
    }

    private Team processTurn(String input, Team currentTeam) {
        try {
            Map<Position, Piece> updatedBoard = movePiece(input);
            if (isGeneralCaptured(updatedBoard)) {
                outputView.printBoard(updatedBoard);
                outputView.printWinner(currentTeam);
                return Team.NONE;
            }
            outputView.printBoard(updatedBoard);
            return currentTeam.convert();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return currentTeam;
        }
    }

    private Map<Position, Piece> movePiece(String input) {
        String[] positions = input.split(" ");
        Position from = Position.from(positions[0]);
        Position to = Position.from(positions[1]);
        return board.move(from, to);
    }

    private boolean isGeneralCaptured(Map<Position, Piece> updatedBoard) {
        return updatedBoard.values().stream()
                .filter(piece -> !piece.isEmptyPiece())
                .filter(piece -> piece.getDisplayName().equals(KING_NAME))
                .count() < 2;
    }
}
