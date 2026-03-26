package janggi;

import janggi.domain.Board;
import janggi.domain.Player;
import janggi.domain.Players;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.dto.BoardDTO;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;

    public Runner(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        Players players = initialPlayers();
        Board board = Board.initialize();
        printBoard(board);
        Side currentSide = Side.CHO;
        printPlayerTurnNotice(players, currentSide);
        playerTurn(players, board);
        printPlayerTurnNotice(players, currentSide.opposite());

    }

    private void playerTurn(Players players, Board board) {
        Position selected = selectPosition();
//        outputView.printPiecePositionNotice(selected.row(), selected.column());
        List<Position> destinations = board.calculateDestinations(selected);
        System.out.println("destinations" + destinations);
        movePiece(board, selected, destinations);
    }

    private Position selectPosition() {
        outputView.printMovePositionRowNotice();
        int row = inputView.readTargetRow();
        outputView.printMovePositionColumnNotice();
        int column = inputView.readTargetColumn();
        return new Position(row, column);
    }

    private Players initialPlayers() {
        String choPlayerName = readPlayerName(Side.CHO);
        String hanPlayerName = readPlayerName(Side.HAN);
        return Players.from(choPlayerName, hanPlayerName);
    }

    private String readPlayerName(Side side) {
        return retry(() -> {
            outputView.printPlayerNameNotice(side.getDisplayName());
            return inputView.readPlayerName();
        });
    }

    private void printPlayerTurnNotice(Players players, Side currentSide) {
        Player player = players.findBySide(currentSide);
        outputView.printPlayerTurnNotice(currentSide.getDisplayName(), player.getNickname());
    }

    private Position readTargetPosition() {
        return retry(() -> {
            outputView.printMovePositionRowNotice();
            int row = inputView.readTargetRow();

            outputView.printMovePositionColumnNotice();
            int column = inputView.readTargetColumn();

            return new Position(row, column);
        });
    }

    private void movePiece(Board board, Position selected, List<Position> destinations) {
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()), selected, destinations);
        Position target = selectPosition();
        board.movePiece(selected, target);
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()), target);
    }

    private void printBoard(Board board) {
        outputView.printBoardSettingNotice();
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()));
    }

    private <T> T retry(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return retry(supplier);
        }
    }
}
