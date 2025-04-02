package game;

import dao.PieceDao;
import location.PathManagerImpl;
import location.Position;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.GreenSoldier;
import piece.Guard;
import piece.Horse;
import piece.RedSoldier;

public class BoardInitializer {
    private final PieceDao pieceDao;

    public BoardInitializer(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public void initialize() {
        pieceDao.deleteAllPieces();
        addAllPieces();
    }

    private void addAllPieces() {
        pieceDao.add(new Chariot(1, Team.GREEN, new PathManagerImpl(), new Position(1, 10)));
        pieceDao.add(new Chariot(2, Team.GREEN, new PathManagerImpl(), new Position(9, 10)));

        pieceDao.add(new Elephant(3, Team.GREEN, new Position(2, 10)));
        pieceDao.add(new Elephant(4, Team.GREEN, new Position(7, 10)));

        pieceDao.add(new Horse(5, Team.GREEN, new Position(3, 10)));
        pieceDao.add(new Horse(6, Team.GREEN, new Position(8, 10)));

        pieceDao.add(new Guard(7, Team.GREEN, new PathManagerImpl(), new Position(4, 10)));
        pieceDao.add(new Guard(8, Team.GREEN, new PathManagerImpl(), new Position(6, 10)));

        pieceDao.add(new General(9, Team.GREEN, new PathManagerImpl(), new Position(5, 9)));

        pieceDao.add(new Cannon(10, Team.GREEN, new PathManagerImpl(), new Position(2, 8)));
        pieceDao.add(new Cannon(11, Team.GREEN, new PathManagerImpl(), new Position(8, 8)));

        pieceDao.add(new GreenSoldier(12, Team.GREEN, new Position(1, 7)));
        pieceDao.add(new GreenSoldier(13, Team.GREEN, new Position(3, 7)));
        pieceDao.add(new GreenSoldier(14, Team.GREEN, new Position(5, 7)));
        pieceDao.add(new GreenSoldier(15, Team.GREEN, new Position(7, 7)));
        pieceDao.add(new GreenSoldier(16, Team.GREEN, new Position(9, 7)));


        pieceDao.add(new Chariot(17, Team.RED, new PathManagerImpl(), new Position(1, 1)));
        pieceDao.add(new Chariot(18, Team.RED, new PathManagerImpl(), new Position(9, 1)));

        pieceDao.add(new Elephant(19, Team.RED, new Position(3, 1)));
        pieceDao.add(new Elephant(20, Team.RED, new Position(7, 1)));

        pieceDao.add(new Horse(21, Team.RED, new Position(2, 1)));
        pieceDao.add(new Horse(22, Team.RED, new Position(8, 1)));

        pieceDao.add(new Guard(23, Team.RED, new PathManagerImpl(), new Position(4, 1)));
        pieceDao.add(new Guard(24, Team.RED, new PathManagerImpl(), new Position(6, 1)));

        pieceDao.add(new General(25, Team.RED, new PathManagerImpl(), new Position(5, 2)));

        pieceDao.add(new Cannon(26, Team.RED, new PathManagerImpl(), new Position(2, 3)));
        pieceDao.add(new Cannon(27, Team.RED, new PathManagerImpl(), new Position(8, 3)));

        pieceDao.add(new RedSoldier(28, Team.RED, new Position(1, 4)));
        pieceDao.add(new RedSoldier(29, Team.RED, new Position(3, 4)));
        pieceDao.add(new RedSoldier(30, Team.RED, new Position(5, 4)));
        pieceDao.add(new RedSoldier(31, Team.RED, new Position(7, 4)));
        pieceDao.add(new RedSoldier(32, Team.RED, new Position(9, 4)));
    }
}
