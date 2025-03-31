package janggi.service;

import janggi.dao.BoardSnapshotDAO;
import janggi.dao.GameDAO;
import janggi.dao.PieceDAO;

public class JanggiService {
    private final BoardSnapshotDAO boardSnapshotDAO;
    private final GameDAO gameDAO;
    private final PieceDAO pieceDAO;

    public JanggiService(final BoardSnapshotDAO boardSnapshotDAO, final GameDAO gameDAO, final PieceDAO pieceDAO) {
        this.boardSnapshotDAO = boardSnapshotDAO;
        this.gameDAO = gameDAO;
        this.pieceDAO = pieceDAO;
    }

}