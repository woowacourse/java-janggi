package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFormation;
import janggi.domain.board.BoardInitiator;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final BoardInitiator boardInitiator = new BoardInitiator();

    public void run() {
        Board board = new Board();

        choiceBoardFormation(board);

        boolean isChoTurn = true;

        outputView.printBoard(board.getBoard());

        while (true) {
            playGame(isChoTurn, board);

            isChoTurn = changeTurn(isChoTurn);
        }
    }

    private void choiceBoardFormation(Board board) {
        askBoardFormation(board, Team.HAN);
        askBoardFormation(board, Team.CHO);
    }

    private void askBoardFormation(Board board, Team team) {
        outputView.printBoardFormation(team);
        int boardFormationChoice = inputView.readBoardFormationChoice();
        BoardFormation formation = BoardFormation.selectByChoice(boardFormationChoice);
        boardInitiator.initializeByFormation(board, formation, team);
    }

    private void playGame(boolean isChoTurn, Board board) {
        outputView.printTurnMessage(isChoTurn);

        Position movePiecePosition = doLoop(() -> askMovePiecePosition(board));

        List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);

        outputView.printAvailablePositions(board.getBoard(), availablePositions);

        Position movePosition = doLoop(() -> askMovePosition(board, movePiecePosition));

        board.movePiece(movePiecePosition, movePosition);

        outputView.printBoard(board.getBoard());
    }

    private boolean changeTurn(boolean isChoTurn) {
        return !isChoTurn;
    }

    private Position askMovePosition(Board board, Position movePiecePosition) {
        outputView.printMoveChoiceInfo();
        Position position = inputView.readPosition();
        board.validateDestination(movePiecePosition, position);
        return position;
    }

    private Position askMovePiecePosition(Board board) {
        outputView.printMoveInfo();
        Position position = inputView.readPosition();
        board.findAvailablePositions(position);
        return position;
    }

    private <T> T doLoop(Supplier<T> inputFunction) {
        while (true) {
            try {
                return inputFunction.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
