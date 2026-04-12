package janggi.persistence.mapper;

import janggi.domain.Camp;
import janggi.domain.Janggi;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;

import java.util.Map;

public class GameMapper {

    public GameEntity toGameEntity(String id, String name, Janggi janggi) {
        return new GameEntity(
                id,
                name,
                Status.of(janggi),
                janggi.currentCamp()
        );
    }

    public Janggi toJanggi(GameEntity gameEntity, Map<Position, Piece> board) {
        return Janggi.load(
                BoardFactory.load(board),
                gameEntity.camp(),
                isRunning(gameEntity.status())
        );
    }


    private boolean isRunning(Status status) {
        return status.isRunning();
    }
}
