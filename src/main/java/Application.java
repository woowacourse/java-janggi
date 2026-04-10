import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import controller.GameController;
import data.BoardRepository;
import data.TransactionManager;
import domain.board.Board;
import domain.board.BoardInitializer;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:~/janggi;INIT=RUNSCRIPT FROM 'src/main/resources/create_tables.sql'");
        hikariConfig.setDriverClassName("org.h2.Driver");
        hikariConfig.setUsername("sa");
        hikariConfig.setPassword("");

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        TransactionManager transactionManager = new TransactionManager(hikariDataSource);

        BoardRepository boardRepository = new BoardRepository();

        Board board;
        int boardId = InputView.readBoardNumber();
        if (boardId == 0) {
            board = Board.from(BoardInitializer.init(InputView.readBoardSetting()));
        } else {
            board = transactionManager.executeTransaction(connection -> boardRepository.findById(connection, (long) boardId));
        }

        transactionManager.executeTransaction(connection -> {
            boardRepository.save(connection, board);
            return null;
        });

        GameController gameController = new GameController(board);
        while (board.isGameInProgress()) {
            gameController.printBoard();

            transactionManager.executeTransaction(connection -> {
                gameController.move();
                boardRepository.save(connection, board);
                return null;
            });
        }

        gameController.printBoard();
        gameController.printWinner();

        transactionManager.executeTransaction(connection -> {
                    boardRepository.delete(connection, board);
                    return null;
                }
        );
    }
}
