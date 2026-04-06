package janggi.service;

import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.entity.MoveEntity;
import janggi.repository.MoveRepository;

public class MoveService {
    private static final int SAVE_TRY_COUNT = 5;
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void move(Game game, Point from, Point to) {
        game.move(from, to);
        for (int i = 0; i < SAVE_TRY_COUNT; i++) {
            try {
                int moveNumber = moveRepository.findNextMoveNumber(game.getId());
                MoveEntity moveEntity = createMoveEntity(game, from, to, moveNumber);
                moveRepository.save(moveEntity);
                return;
            } catch (IllegalArgumentException e) {
                if (i == SAVE_TRY_COUNT - 1) {
                    throw new IllegalStateException("이동 저장에 반복 실패했습니다.", e);
                }
            }
        }

    }

    private MoveEntity createMoveEntity(Game game, Point from, Point to, int moveNumber) {
        return new MoveEntity(null, game.getId(), moveNumber, game.getTurn(),
                from.x(), from.y(), to.x(), to.y());
    }
}
