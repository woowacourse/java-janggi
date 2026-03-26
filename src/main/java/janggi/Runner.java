package janggi;

import janggi.domain.Board;
import janggi.domain.Players;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.dto.BoardDTO;
import janggi.dto.PlayerDTO;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;
    private final Board board;

    public Runner(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.board = Board.initialize();
    }

    public void run() {
        Players players = initialPlayers();
        printBoard();
        play(players);
    }

    private void play(Players players) {
        while (true) {
            PlayerDTO currentPlayer = players.getCurrentPlayer();
            printPlayerTurnNotice(currentPlayer);
            playerTurn(players);
            players.switchTurn();
        }
    }

    private void playerTurn(Players players) {
        Position selected = selectPiecePosition();
        if (!board.isThereOwnPiece(selected, players.getCurrentPlayer())) {
            outputView.printNotOwnPiece();
            players.switchTurn();
            return;
        }
        List<Position> destinations = board.calculateDestinations(selected);
        movePiece(selected, destinations);
    }

    private Position selectPiecePosition() {
        outputView.printSelectPiecePosition();
        Position position = readTargetPosition();
        if (!board.isPieceExist(position)) {
            outputView.printPieceNotExist();
        }
        return position;
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

    private void printPlayerTurnNotice(PlayerDTO currentPlayer) {
        outputView.printPlayerTurnNotice(currentPlayer.name(), currentPlayer.side().getDisplayName());
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

    private void movePiece(Position selected, List<Position> destinations) {
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()), selected, destinations);
        Position target = selectTargetPosition();
        board.movePiece(selected, target);
        outputView.printBoardStatus(new BoardDTO(board.getPiecePosition()), target);
    }

    private Position selectTargetPosition() {
        outputView.printSelectTargetPosition();
        return readTargetPosition();
    }

    private void printBoard() {
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
