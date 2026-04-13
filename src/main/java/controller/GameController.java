
package controller;

import data.BoardRepository;
import data.TransactionManager;
import domain.board.Board;
import domain.board.Position;
import view.InputView;
import view.OutputView;

public class GameController {
    private final Board board;
    private final BoardRepository boardRepository;
    private final TransactionManager transactionManager;

    public GameController(Board board, BoardRepository boardRepository, TransactionManager transactionManager) {
        this.board = board;
        this.boardRepository = boardRepository;
        this.transactionManager = transactionManager;
    }

    public void run(){
        while (board.isGameInProgress()) {
            OutputView.printBoard(board);
            try {
                Position departure = parsePosition(InputView.readDeparturePosition());
                Position destination = parsePosition(InputView.readDestinationPosition());

                transactionManager.executeTransaction(connection -> {
                    board.move(departure, destination);
                    boardRepository.save(connection, board);
                    return null;
                });
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IllegalArgumentException cause) {
                    System.out.println(cause.getMessage());
                    continue;
                }
                throw e;
            }
        }
    }

    private Position parsePosition(String value) {
        String[] tokens = value.split(",");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("좌표는 x,y 형식으로 입력해야 합니다.");
        }

        try {
            int column = Integer.parseInt(tokens[0].trim());
            int row = Integer.parseInt(tokens[1].trim());
            return new Position(column, row);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("좌표는 숫자로 입력해야 합니다.");
        }
    }
}
