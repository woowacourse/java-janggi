import board.Board;
import board.BoardFactory;
import board.Palace;
import dao.BoardDao;
import dao.CountryDao;
import dao.PieceDao;
import piece.Country;
import piece.Piece;
import position.LineDirection;
import position.Position;
import position.PositionFactory;
import view.InputView;
import view.LineSettingDto;
import view.OutputView;

import java.util.List;
import java.util.Map;

public class Application {

    private static final int MAX_TRY_COUNT = 150;

    public static void main(String[] args) {
        initializeBoardSettings();

        PieceDao pieceDao = new PieceDao();
        BoardDao boardDao = new BoardDao();
        CountryDao countryDao = new CountryDao();

        final Board board = loadOrInitializeBoard(pieceDao, boardDao, countryDao);

        Country currentTurn = Country.getDefaultTeam();
        int turnCount = 0;

        while (++turnCount < MAX_TRY_COUNT) {
            currentTurn = currentTurn.opposite();

            OutputView.printBoard(board, currentTurn);

            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), currentTurn);

            saveGameState(pieceDao, boardDao, board);
        }
    }

    private static Board loadOrInitializeBoard(PieceDao pieceDao, BoardDao boardDao, CountryDao countryDao) {
        Map<Country, LineDirection> savedDirections = countryDao.loadDirections();

        if (savedDirections.isEmpty()) {
            return startNewGame(countryDao);
        }

        boolean useSavedData = InputView.readClientIntent();
        if (!useSavedData) {
            return startNewGame(countryDao);
        }

        savedDirections.forEach(Country::assignDirection);

        List<Piece> loadedPieces = pieceDao.loadPieces();
        Map<Country, Integer> scores = boardDao.loadScore();
        return BoardFactory.fromDatabase(loadedPieces, scores, savedDirections);
    }

    private static Board startNewGame(CountryDao countryDao) {
        LineSettingDto settingDto = InputView.readLineSettingByCountry();

        Country.assignDirection(settingDto.country(), settingDto.direction());
        countryDao.saveDirection(Country.getDirectionByCountry());

        final BoardFactory boardFactory = new BoardFactory(settingDto.country(), settingDto.direction());
        return boardFactory.generateBoard();
    }

    private static void initializeBoardSettings() {
        OutputView.printIntroduce();

        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());
    }

    private static void saveGameState(PieceDao pieceDao, BoardDao boardDao, Board board) {
        pieceDao.clearPieces();
        pieceDao.savePieces(board.getPieceList());
        boardDao.saveScore(board.getScoreByCountry());
    }
}
