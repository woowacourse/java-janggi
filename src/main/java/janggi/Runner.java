package janggi;

import janggi.domain.Board;
import janggi.domain.Paths;
import janggi.domain.Piece;
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
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()));

        playerTurn(players, board);
    }

    private void playerTurn(Players players, Board board) {

        Position position = new Position(x, y);
        List<Position> destinations = board.determineDestinations(position);
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()), position, destinations);
        movePiece(boardDto, position);
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

    private void printPlayerTurnNotice(Players players, Side side) {
        Player player = players.findBySide(side);
        outputView.printPlayerTurnNotice(side.getDisplayName(), player.getNickname());
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

    private void movePiece(BoardDTO boardDTO, Position position) {
        outputView.printBoardStatus(boardDTO, position);
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
