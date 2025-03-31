import static view.RecoveryUtil.executeWithRetry;

import database.connector.MySQLConnector;
import database.repository.game.GameDto;
import database.repository.game.GameRepository;
import database.repository.game.JdbcGameRepository;
import database.repository.piece.JdbcPieceRepository;
import database.repository.piece.PieceDto;
import database.repository.piece.PieceRepository;
import java.util.Map;
import object.board.Board;
import object.board.creator.MaSangMaSangCreator;
import object.board.creator.MaSangSangMaCreator;
import object.board.creator.SangMaMaSangCreator;
import object.board.creator.SangMaSangMaCreator;
import object.board.creator.TableSettingCreator;
import object.team.Country;
import object.team.Score;
import object.team.Team;
import object.team.Teams;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Application {

    private static final Map<Integer, TableSettingCreator> BOARD_CREATE_STRATEGY = Map.of(
            1, new MaSangSangMaCreator(),
            2, new MaSangMaSangCreator(),
            3, new SangMaSangMaCreator(),
            4, new SangMaMaSangCreator()
    );

    private final InputView inputView;
    private final OutputView outputView;
    private final JdbcGameRepository gameRepository;
    private final JdbcPieceRepository pieceRepository;

    public Application(InputView inputView,
                       OutputView outputView,
                       JdbcGameRepository gameRepository,
                       JdbcPieceRepository pieceRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public static void main(String[] args) {
        new Application(
                new InputView(),
                new OutputView(),
                new GameRepository(new MySQLConnector()),
                new PieceRepository(new MySQLConnector())
        ).run();
    }

    private void run() {
        Board board = loadOrInitializeBoard();
        outputView.printBoard(board.getUnmodifiablePieces());

        startGame(board);
        outputView.printWinner(board.judgeWinner());
    }

    private Board loadOrInitializeBoard() {
        try {
            PieceDto pieceDto = pieceRepository.loadPieces();
            GameDto gameDto = gameRepository.loadGame();

            if (gameDto.isEnd()) {
                return initializeBoard();
            }
            return loadBoard(pieceDto, gameDto);

        } catch (RuntimeException e) {
            return initializeBoard();
        }
    }

    private Board loadBoard(PieceDto pieceDto, GameDto gameDto) {
        return new Board(new Teams(
                new Team(Country.HAN, pieceDto.hanPieces(), new Score(gameDto.scoreHan())),
                new Team(Country.CHO, pieceDto.choPieces(), new Score(gameDto.scoreCho()))
        ));
    }

    private Board initializeBoard() {
        outputView.printGameStartMessage();
        gameRepository.initializeTable();
        pieceRepository.initializeTable();

        int hanTableSetting = executeWithRetry(() -> inputView.readTableSetting(Country.HAN));
        int choTableSetting = executeWithRetry(() -> inputView.readTableSetting(Country.CHO));

        return Board.create(
                BOARD_CREATE_STRATEGY.get(hanTableSetting),
                BOARD_CREATE_STRATEGY.get(choTableSetting)
        );
    }

    private void startGame(Board board) {
        while (!board.isEnd()) {
            GameDto gameDto = gameRepository.loadGame();
            executeWithRetry(() -> playTurn(board, gameDto.turnTeam()));
        }
    }

    private void playTurn(Board board, Country country) {
        CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(country);

        board.move(country, coordinatesPair.departure(), coordinatesPair.arrival());

        pieceRepository.savePieces(board.getUnmodifiablePieces());
        gameRepository.saveGame(
                country.nextCountry(),
                board.isEnd(),
                board.getUnmodifiableScores().get(Country.HAN),
                board.getUnmodifiableScores().get(Country.CHO)
        );

        outputView.printBoard(board.getUnmodifiablePieces());
        outputView.printScore(board.getUnmodifiableScores());
    }
}
