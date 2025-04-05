package service;

import dao.GameStatusDao;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.GameStatus;
import domain.board.Palace;
import dao.BoardDao;
import dao.CountryDirectionDao;
import dao.PieceDao;
import domain.piece.Country;
import domain.piece.Piece;
import domain.position.LineDirection;
import domain.position.Position;
import domain.position.PositionFactory;
import view.InputView;
import view.LineSettingDto;

import java.util.List;
import java.util.Map;

public class JanggiService {

    private final PieceDao pieceDao;
    private final BoardDao boardDao;
    private final CountryDirectionDao countryDao;
    private final GameStatusDao gameStatusDao;

    public JanggiService(PieceDao pieceDao, BoardDao boardDao, CountryDirectionDao countryDao, GameStatusDao gameStatusDao) {
        this.pieceDao = pieceDao;
        this.boardDao = boardDao;
        this.countryDao = countryDao;
        this.gameStatusDao = gameStatusDao;
    }

    public GameData initializeGame() {
        PositionFactory positionFactory = new PositionFactory();
        positionFactory.basicSettingGraph();
        positionFactory.diagonalSettingGraph(Palace.getAllPositions());

        Map<Country, LineDirection> savedDirections = countryDao.loadDirections();

        if (savedDirections.isEmpty() || !InputView.readClientIntent()) {
            return startNewGame();
        }

        savedDirections.forEach(Country::assignDirection);

        List<Piece> loadedPieces = pieceDao.loadPieces();
        Map<Country, Integer> scores = boardDao.loadScore();
        GameStatus gameStatus = gameStatusDao.loadTurn();

        Board board = BoardFactory.fromDatabase(loadedPieces, scores, savedDirections);
        return new GameData(board, gameStatus);
    }

    private GameData startNewGame() {
        LineSettingDto settingDto = InputView.readLineSettingByCountry();

        Country.assignDirection(settingDto.country(), settingDto.direction());
        countryDao.saveDirection(Country.getDirectionByCountry());

        BoardFactory boardFactory = new BoardFactory(settingDto.country(), settingDto.direction());
        GameStatus gameStatus = new GameStatus(Country.getDefaultTeam(), 0);
        return new GameData(boardFactory.generateBoard(), gameStatus);
    }

    public void processTurn(Board board, Country currentTurn, List<Position> positions) {
        board.updatePosition(positions.get(0), positions.get(1), currentTurn);
    }

    public void save(Board board, Country turnCountry, int turnCount) {
        pieceDao.clearPieces();
        pieceDao.savePieces(board.getPieceList());
        boardDao.saveScore(board.getScoreByCountry());
        countryDao.saveDirection(Country.getDirectionByCountry());
        gameStatusDao.saveTurn(turnCountry, turnCount);
    }
}
