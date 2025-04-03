package game;

import dao.PieceDao;
import java.util.List;
import location.PathManager;
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
    private final PathManager pathManager;
    private final PieceDao pieceDao;

    public BoardInitializer(PathManager pathManager, PieceDao pieceDao) {
        this.pathManager = pathManager;
        this.pieceDao = pieceDao;
    }

    public void initialize() {
        pieceDao.deleteAllPieces();
        addAllPieces();
    }

    private void addAllPieces() {
        pieceDao.addAll(
                List.of(new Chariot(1, Team.GREEN, pathManager, new Position(1, 10)),
                        new Chariot(2, Team.GREEN, pathManager, new Position(9, 10)),

                        new Elephant(3, Team.GREEN, new Position(2, 10)),
                        new Elephant(4, Team.GREEN, new Position(7, 10)),

                        new Horse(5, Team.GREEN, new Position(3, 10)),
                        new Horse(6, Team.GREEN, new Position(8, 10)),

                        new Guard(7, Team.GREEN, pathManager, new Position(4, 10)),
                        new Guard(8, Team.GREEN, pathManager, new Position(6, 10)),

                        new General(9, Team.GREEN, pathManager, new Position(5, 9)),

                        new Cannon(10, Team.GREEN, pathManager, new Position(2, 8)),
                        new Cannon(11, Team.GREEN, pathManager, new Position(8, 8)),

                        new GreenSoldier(12, Team.GREEN, new Position(1, 7)),
                        new GreenSoldier(13, Team.GREEN, new Position(3, 7)),
                        new GreenSoldier(14, Team.GREEN, new Position(5, 7)),
                        new GreenSoldier(15, Team.GREEN, new Position(7, 7)),
                        new GreenSoldier(16, Team.GREEN, new Position(9, 7)),

                        new Chariot(17, Team.RED, pathManager, new Position(1, 1)),
                        new Chariot(18, Team.RED, pathManager, new Position(9, 1)),

                        new Elephant(19, Team.RED, new Position(3, 1)),
                        new Elephant(20, Team.RED, new Position(7, 1)),

                        new Horse(21, Team.RED, new Position(2, 1)),
                        new Horse(22, Team.RED, new Position(8, 1)),

                        new Guard(23, Team.RED, pathManager, new Position(4, 1)),
                        new Guard(24, Team.RED, pathManager, new Position(6, 1)),

                        new General(25, Team.RED, pathManager, new Position(5, 2)),

                        new Cannon(26, Team.RED, pathManager, new Position(2, 3)),
                        new Cannon(27, Team.RED, pathManager, new Position(8, 3)),

                        new RedSoldier(28, Team.RED, new Position(1, 4)),
                        new RedSoldier(29, Team.RED, new Position(3, 4)),
                        new RedSoldier(30, Team.RED, new Position(5, 4)),
                        new RedSoldier(31, Team.RED, new Position(7, 4)),
                        new RedSoldier(32, Team.RED, new Position(9, 4))
                )
        );
    }
}
