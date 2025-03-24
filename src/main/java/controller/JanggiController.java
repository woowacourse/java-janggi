package controller;

import domain.Turn;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.board.Node;
import domain.piece.Team;
import util.ErrorHandler;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class JanggiController {

    public void startGame() {
        OutputView.printStart();
        Board board = createJanggiBoard();

        Turn turn = new Turn();
        do {
            OutputView.printBoard(board);
            moveByTurn(turn, board);
            if (board.isOpponentWangDead(turn.team())) {
                OutputView.printBoard(board);
                OutputView.printMatchResult(turn.team());
                break;
            }
            turn.changeTurn();
        } while (true);
    }

    private void moveByTurn(final Turn turn, final Board board) {
        ErrorHandler.retryUntilSuccess(() -> {
            MoveCommand moveCommand = InputView.inputMoveCommand(turn.team());
            Node sourceNode = board.findNodeByPoint(moveCommand.source());
            Node destinationNode = board.findNodeByPoint(moveCommand.destination());

            if (!board.hasPieceTeamByNode(sourceNode, turn.team())) {
                OutputView.printTurn(turn.team());
            }
            board.movePiece(sourceNode, destinationNode, board);
        });
    }

    private Board createJanggiBoard() {
        BoardGenerator boardGenerator = new BoardGenerator();
        SangMaOrderCommand hanSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = createSangMaOrderCommandByTeam(Team.CHO);
        return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    private SangMaOrderCommand createSangMaOrderCommandByTeam(final Team team) {
        return ErrorHandler.retryUntilSuccess(() -> InputView.inputSangMaOrder(team));
    }
}
