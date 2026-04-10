import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.GameController;
import data.BoardRepository;
import data.TransactionManager;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.piece.Camp;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        try (HikariDataSource hikariDataSource = createDataSource()) {
            TransactionManager transactionManager = new TransactionManager(hikariDataSource);

            BoardRepository boardRepository = new BoardRepository();

            Board board = initBoard(transactionManager, boardRepository);

            GameController gameController = new GameController(board, boardRepository, transactionManager);
            gameController.run();

            OutputView.printBoard(board);
            OutputView.printWinner(board.winner(), board.score(Camp.CHO), board.score(Camp.HAN));

            transactionManager.executeTransaction(connection -> {
                        boardRepository.delete(connection, board);
                        return null;
                    }
            );
        }
    }

    private static HikariDataSource createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:~/janggi;INIT=RUNSCRIPT FROM 'src/main/resources/create_tables.sql'");
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setUsername("sa");
        hikariConfig.setPassword("");

        return new HikariDataSource(hikariConfig);
    }

    private static Board initBoard(TransactionManager transactionManager, BoardRepository boardRepository) {
        int boardId = InputView.readBoardNumber();

        Board board;
        if (boardId == 0) {
            board = Board.from(BoardInitializer.init(InputView.readBoardSetting()));
        } else {
            board = transactionManager.executeTransaction(connection -> boardRepository.findById(connection, (long) boardId));
        }

        transactionManager.executeTransaction(connection -> {
            boardRepository.save(connection, board);
            return null;
        });
        return board;
    }
}
