package janggi.domain.game;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.time.LocalDateTime;
import java.util.List;

public class Game {

    private final long gameId;

    private final CampType currnetTurn;

    private final GameStatus gameStatus;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final LocalDateTime lastUpdatedAt;

    private final List<Piece> pieces;

    public Game(long gameId, CampType currnetTurn, GameStatus gameStatus, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime lastUpdatedAt, List<Piece> pieces) {
        this.gameId = gameId;
        this.currnetTurn = currnetTurn;
        this.gameStatus = gameStatus;
        this.startAt = startAt;
        this.endAt = endAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.pieces = pieces;
    }
}
