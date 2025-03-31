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

// TODO 2025. 3. 29. 13:33: <전역>
// TODO 2025. 3. 29. 13:32: 테스트 코드 제네릭
// TODO 2025. 3. 29. 13:32: y up, down 방향 바꿀 때 한번에 바꿀 수 있도록 코드 결합도 고려
// TODO 2025. 3. 29. 13:33: row, column 처리
// TODO 2025. 3. 29. 13:34: turn 처리 좀 더 설계적으로

public class Application {

    private static final int MAX_TRY_COUNT = 150;

    public static void main(String[] args) {
        initSetting();

        PieceDao pieceDao = new PieceDao();
        BoardDao boardDao = new BoardDao();
        CountryDao countryDao = new CountryDao();

        final Board board = getBoardLoad(pieceDao, boardDao, countryDao);

        Country type = Country.getDefaultTeam();
        int count = 0;


        while (++count < MAX_TRY_COUNT) {
            type = type.opposite();
            OutputView.printBoard(board, type);
            final List<Position> positions = InputView.readPositions();
            board.updatePosition(positions.get(0), positions.get(1), type);

            pieceDao.clearPieces();
            pieceDao.savePieces(board.getPieceList());
            boardDao.saveScore(board.getScoreByCountry());
        }
    }

    private static Board getBoardLoad(PieceDao pieceDao, BoardDao boardDao, CountryDao countryDao) {
        Map<Country, LineDirection> loadedDirections = countryDao.loadDirections();

        if (loadedDirections.isEmpty()) {
            return getNewGameInitSetting(countryDao);
        }
        boolean intent = InputView.readClientIntent();
        if (intent == Boolean.FALSE) {
            return getNewGameInitSetting(countryDao);
        }
        loadedDirections.forEach(Country::assignDirection);

        List<Piece> loadedPieces = pieceDao.loadPieces();
        Map<Country, Integer> scores = boardDao.loadScore();
        return BoardFactory.fromDatabase(loadedPieces, scores, loadedDirections);
    }

    private static Board getNewGameInitSetting(CountryDao countryDao) {
        LineSettingDto settingDto = InputView.readLineSettingByCountry();

        Country.assignDirection(settingDto.country(), settingDto.direction());
        countryDao.saveDirection(Country.getDirectionByCountry());

        final BoardFactory boardFactory = new BoardFactory(settingDto.country(), settingDto.direction());
        return boardFactory.generateBoard();
    }

    private static void initSetting() {
        OutputView.printIntroduce();
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getCenterPositions());
    }
}
