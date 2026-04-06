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
        int moveNumber = moveRepository.findNextMoveNumber(game.getId());
        MoveEntity moveEntity = createMoveEntity(game, from, to, moveNumber);
        game.move(from, to);
        moveRepository.save(moveEntity);
    }

    private MoveEntity createMoveEntity(Game game, Point from, Point to, int moveNumber) {
        return new MoveEntity(null, game.getId(), game.getTurn(), moveNumber,
                from.x(), from.y(), to.x(), to.y());
    }
}
