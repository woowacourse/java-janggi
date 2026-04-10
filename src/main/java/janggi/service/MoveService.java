package janggi.service;

import janggi.dao.MoveDao;
import janggi.dao.entity.MoveEntity;
import janggi.domain.game.Game;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;

public class MoveService {
    private final MoveDao moveDao;

    public MoveService(MoveDao moveDao) {
        this.moveDao = moveDao;
    }

    public void move(Game game, Point from, Point to) {
        PieceType pieceType = game.getPieceType(from);
        MoveEntity moveEntity = createMoveEntity(game, from, to, pieceType);

        game.move(from, to);
        moveDao.save(moveEntity);
    }

    private MoveEntity createMoveEntity(Game game, Point from, Point to, PieceType pieceType) {
        return new MoveEntity(null, game.getId(), pieceType, game.getTurn(),
                from.x(), from.y(), to.x(), to.y());
    }
}
