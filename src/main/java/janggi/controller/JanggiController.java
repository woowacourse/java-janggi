package janggi.controller;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFormation;
import janggi.domain.board.BoardInitiator;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Optional;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final BoardInitiator boardInitiator = new BoardInitiator();

    public void run() {
        boolean isChoTurn = true;
        boolean isGameOver = false;

        Board board = new Board();
        choiceBoardFormation(board);
        outputView.printBoard(board.getBoard());

        while (!isGameOver) {
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

        Position movePiecePosition = askMovePiecePositionUntilValid(board);

        List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);

        outputView.printAvailablePositions(board.getBoard(), availablePositions);

        Position movePosition = askMovePositionUntilValid(board, movePiecePosition);

        board.movePiece(movePiecePosition, movePosition);

        outputView.printBoard(board.getBoard());
    }

    private boolean changeTurn(boolean isChoTurn) {
        return !isChoTurn;
    }

    private Position askMovePiecePositionUntilValid(Board board) {
        Optional<Position> position = Optional.empty();
        while (position.isEmpty()) {
            position = askMovePiecePosition(board, position);
        }
        return position.get();
    }

    private Optional<Position> askMovePiecePosition(Board board, Optional<Position> position) {
        try {
            outputView.printMoveInfo();
            Position input = inputView.readPosition();
            board.validateMovePiecePosition(input);
            board.findAvailablePositions(input);
            position = Optional.of(input);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        return position;
    }

    private Position askMovePositionUntilValid(Board board, Position movePiecePosition) {
        Optional<Position> position = Optional.empty();
        while (position.isEmpty()) {
            position = askMovePosition(board, movePiecePosition, position);
        }
        return position.get();
    }

    private Optional<Position> askMovePosition(Board board, Position movePiecePosition, Optional<Position> position) {
        try {
            outputView.printMoveChoiceInfo();
            Position input = inputView.readPosition();
            board.validateDestination(movePiecePosition, input);
            position = Optional.of(input);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        return position;
    }
}
