package janggi;

import janggi.dao.DbConnection;
import janggi.dao.JanggiDao;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.Turn;
import janggi.view.OutputView;
import java.util.Scanner;
import java.util.function.Supplier;

public class Application {
    public static void main(String[] args) {
        DbConnection.initializeDatabase();
        JanggiDao janggiDAO = new JanggiDao();

        System.out.println("장기게임을 시작합니다");
        Scanner scanner = new Scanner(System.in);
        String choice = readChoice(scanner);

        Board board = createBoard(choice, janggiDAO);
        Turn turn = createTurn(choice, janggiDAO);

        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.printInitialBoard();
        while (!turn.isFinished()) {
            turn = janggiGame.playTurn(turn);
            if (!turn.isFinished()) {
                janggiDAO.saveGame(turn, janggiGame.getBoard());
            }
        }
        janggiGame.printWinner(turn);
        janggiDAO.deleteGame();
    }

    private static String readChoice(Scanner scanner) {
        return retry(() -> {
            System.out.println("새로하기는 \"1\", 이어하기는 \"2\"를 입력해주세요");
            String choice = scanner.nextLine();
            if (choice.equals("1") || choice.equals("2")) {
                return choice;
            }
            throw new IllegalArgumentException("[ERROR] 1 또는 2만 입력해주세요.");
        });
    }

    private static Board createBoard(String choice, JanggiDao janggiDAO) {
        if (choice.equals("2") && janggiDAO.hasSavedGame()) {
            return new Board(janggiDAO.loadBoard());
        }
        return new Board(BoardFactory.settingUpBoard());
    }

    private static Turn createTurn(String choice, JanggiDao janggiDAO) {
        if (choice.equals("2") && janggiDAO.hasSavedGame()) {
            return janggiDAO.loadCurrentTurn();
        }
        return new ChoTurn();
    }

    public static <T> T retry(Supplier<T> inputFunction) {
        while (true) {
            try {
                return inputFunction.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
