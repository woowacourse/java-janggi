package domain;

import domain.boardgenerator.JanggiBoardGenerator;
import domain.dao.GameDao;
import domain.dao.PieceDao;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JanggiService(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public JanggiGame findGameByName(String roomName) {
        Long gameId = gameDao.findIdByName(roomName);
        Team turn = gameDao.findTurn(gameId);
        List<Piece> allPieces = pieceDao.findAll(gameId);
        return JanggiGame.create(gameId, JanggiBoard.create(allPieces), turn);
    }

    public List<String> findAllRoomNames() {
        return gameDao.findAllName();
    }

    public boolean isEmpty() {
        return gameDao.countAll().equals(0L);
    }

    public void removeAll(Long gameId) {
        pieceDao.removeAll(gameId);
    }

    public JanggiGame addGame(final String name) {
        Long id = gameDao.add(name, Team.getFirstTeam());
        return JanggiGame.init(id, JanggiBoard.init(new JanggiBoardGenerator()));
    }

    public void saveGame(JanggiGame game, List<Piece> pieces) {
        removeAll(game.getGameId());
        pieceDao.addAll(game.getGameId(), pieces);
        gameDao.changeTurn(game.getGameId(), game.getThisTurnTeam());
    }
}
