package janggi.persistence.mapper;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

import java.util.Map;

public class GameMapper {

    public GameEntity toGameEntity(String id, String name, Janggi janggi) {
        return new GameEntity(
                id,
                name,
                Status.of(janggi),
                Turn.of(janggi.currentCamp())
        );
    }

    public Janggi toJanggi(GameEntity gameEntity, Map<Position, Piece> board) {
        return Janggi.load(
                BoardFactory.load(board),
                toCamp(gameEntity.turn()),
                isRunning(gameEntity.status())
        );
    }

    private Camp toCamp(Turn turn) {
        if (turn.isCho()) {
            return Camp.CHO;
        }
        return Camp.HAN;
    }

    private boolean isRunning(Status status) {
        return status.isRunning();
    }
}
