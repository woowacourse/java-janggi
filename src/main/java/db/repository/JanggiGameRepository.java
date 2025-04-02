package db.repository;

import db.connection.DBConnection;
import db.dao.JanggiGameDao;
import db.dao.JanggiGameDao.GameDto;
import db.dao.JanggiPieceDao;
import janggiGame.JanggiGame;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import janggiGame.state.Running.ChoTurn;
import janggiGame.state.Running.HanTurn;
import janggiGame.state.State;
import java.util.List;
import java.util.Map;

public class JanggiGameRepository {
    private final JanggiGameDao gameDao;
    private final JanggiPieceDao pieceDao;

    public JanggiGameRepository(DBConnection dbConnection) {
        this.gameDao = new JanggiGameDao(dbConnection);
        this.pieceDao = new JanggiPieceDao(dbConnection);
    }

    public JanggiGame load(Long gameId) {
        GameDto gameDto = gameDao.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 ID의 게임이 존재하지 않습니다: " + gameId));

        Map<Position, Piece> pieces = pieceDao.findByGameId(gameId);
        State state = getState(gameDto, pieces);

        JanggiGame game = new JanggiGame();
        game.restoreGameWith(state);
        return game;
    }

    private State getState(GameDto gameDto, Map<Position, Piece> pieces) {
        switch (gameDto.currentDynasty()) {
            case "HAN" -> {
                return new HanTurn(pieces, gameDto.wasLastPassed());
            }
            case "CHO" -> {
                return new ChoTurn(pieces, gameDto.wasLastPassed());
            }
            default -> throw new IllegalArgumentException("[ERROR] 잘못된 턴 정보입니다: " + gameDto.currentDynasty());
        }
    }

    public void updateTurn(Long gameId, String dynasty, boolean wasLastPassed) {
        gameDao.updateGame(gameId, dynasty, wasLastPassed);
    }

    public void markAsFinished(Long gameId) {
        gameDao.markAsFinished(gameId);
    }

    public void deletePieceAt(Long gameId, Position position) {
        pieceDao.deletePieceAt(gameId, position);
    }

    public void updatePiecePosition(Long gameId, Position origin, Position destination) {
        pieceDao.updatePiecePosition(gameId, origin, destination);
    }

    public Long saveGame(String currentDynasty) {
        return gameDao.save(currentDynasty);
    }

    public void saveAllPieces(Long gameId, Map<Position, Piece> pieces) {
        pieceDao.saveAll(gameId, pieces);
    }

    public List<GameDto> findNotFinishedGames() {
        return gameDao.findNotFinishedGames();
    }
}
