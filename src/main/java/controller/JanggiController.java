package controller;

import domain.Turn;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.piece.Team;
import domain.util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class JanggiController {

    public void startGame() {
        OutputView.printStart();

        Board board = createJanggiBoard();
        OutputView.printBoard(board);

        Turn turn = new Turn();
        boolean isRunning = true;
        while (isRunning) {
            isRunning = movePieceByTurn(board, turn);
        }
    }

    private boolean movePieceByTurn(Board board, Turn turn) {
        return ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn.team());
            Node sourceNode = board.findNodeByPoint(moveCommand.source());
            Node destinationNode = board.findNodeByPoint(moveCommand.destination());

            if (!board.hasPieceTeamByNode(sourceNode, turn.team())) {
                OutputView.printTurn(turn.team());
                return true;
            }

            board.movePiece(sourceNode, destinationNode, board);
            OutputView.printBoard(board);

            if (board.isOpponentWangDead(turn.team())) {
                OutputView.printMatchResult(turn.team());
                return false;
            }
            turn.changeTurn();
            return true;
        });
    }

    private Board createJanggiBoard() {
        return ErrorHandler.retryUntilSuccess(() -> {
            BoardGenerator boardGenerator = new BoardGenerator();
            SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
            SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);
            return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
        });
    }
}
