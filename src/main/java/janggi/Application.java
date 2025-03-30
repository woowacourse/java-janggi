package janggi;

import janggi.dao.DaoSettings;
import janggi.dao.JanggiDao;
import janggi.domain.Board;
import janggi.domain.PieceFactory;
import janggi.domain.Round;
import janggi.domain.Side;
import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.dto.PieceDto;
import janggi.manager.JanggiData;
import janggi.manager.JanggiGame;
import janggi.view.Viewer;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        DaoSettings daoSettings = new DaoSettings("localhost:13306", "janggi", "root", "root");
        JanggiDao janggiDao = new JanggiDao(daoSettings);
        JanggiData janggiData = new JanggiData(janggiDao);

        JanggiGame janggiGame = setGame(janggiData);

        janggiGame.start();
        janggiGame.finish();
    }

    private static JanggiGame setGame(JanggiData janggiData) {
        List<PieceDto> pieceDtos = janggiData.loadFromDatabase();

        if (pieceDtos.isEmpty()) {
            return setNewGame(janggiData);
        }
        return loadGame(janggiData, pieceDtos);
    }

    private static JanggiGame setNewGame(JanggiData janggiData) {
        Map<Position, Piece> initialPieces = PieceFactory.initialize();
        Viewer viewer = new Viewer();
        Board board = new Board(initialPieces);
        Round round = new Round(board, Side.CHO);

        janggiData.setupDatabase(initialPieces);

        return new JanggiGame(janggiData, viewer, round);
    }

    private static JanggiGame loadGame(JanggiData janggiData, List<PieceDto> loadedData) {
        Viewer viewer = new Viewer();
        Board board = new Board(janggiData.convertFromDto(loadedData));
        Side currentTurn = janggiData.loadCurrentTurn();
        Round round = new Round(board, currentTurn);

        return new JanggiGame(janggiData, viewer, round);
    }
}
