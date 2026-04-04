package janggi.service;

import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.repository.MoveRepository;

public class MoveService {
    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void move(Game game, Point from, Point to) {

    }
}
