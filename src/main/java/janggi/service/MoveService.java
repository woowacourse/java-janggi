package janggi.service;

import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.entity.MoveEntity;
import janggi.repository.MoveRepository;

public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void move(Game game, Point from, Point to) {
        MoveEntity moveEntity = createMoveEntity(game, from, to);
        game.move(from, to);
        moveRepository.save(moveEntity);
    }

    private MoveEntity createMoveEntity(Game game, Point from, Point to) {
        MoveEntity moveEntity = new MoveEntity(null, game.getId(), game.getTurn(), from.x(), from.y(), to.x(), to.y());
        return moveEntity;
    }
}
