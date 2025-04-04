package service;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Palace;
import dao.BoardDao;
import dao.CountryDao;
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
    private final CountryDao countryDao;

    public JanggiService(PieceDao pieceDao, BoardDao boardDao, CountryDao countryDao) {
        this.pieceDao = pieceDao;
        this.boardDao = boardDao;
        this.countryDao = countryDao;
    }

    public Board initializeGame() {
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

        return BoardFactory.fromDatabase(loadedPieces, scores, savedDirections);
    }

    private Board startNewGame() {
        LineSettingDto settingDto = InputView.readLineSettingByCountry();

        Country.assignDirection(settingDto.country(), settingDto.direction());
        countryDao.saveDirection(Country.getDirectionByCountry());

        BoardFactory boardFactory = new BoardFactory(settingDto.country(), settingDto.direction());
        return boardFactory.generateBoard();
    }

    public Board processTurn(Board board, Country currentTurn, List<Position> positions) {
        board.updatePosition(positions.get(0), positions.get(1), currentTurn);
        return board;
    }

    public void save(Board board) {
        pieceDao.clearPieces();
        pieceDao.savePieces(board.getPieceList());
        boardDao.saveScore(board.getScoreByCountry());
    }
}
