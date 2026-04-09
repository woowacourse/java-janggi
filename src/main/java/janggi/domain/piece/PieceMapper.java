package janggi.domain.piece;

import janggi.domain.board.BoardDirection;

import java.util.Map;
import java.util.function.Function;

import static janggi.domain.piece.PieceFactory.createSolider;

public class PieceMapper {

    private static final Map<PieceType, Function<Team, Piece>> PIECE_MAPPER = Map.of(
            PieceType.GENERAL, PieceFactory::createGeneral,
            PieceType.GUARD, PieceFactory::createGuard,
            PieceType.CHARIOT, PieceFactory::createChariot,
            PieceType.CANNON, PieceFactory::createCannon,
            PieceType.HORSE, PieceFactory::createHorse,
            PieceType.ELEPHANT, PieceFactory::createElephant,
            PieceType.SOLDIER, team -> createSolider(team, BoardDirection.of(team))
    );

    public static Piece create(PieceType pieceType, Team team) {
        Function<Team, Piece> mapper = PIECE_MAPPER.get(pieceType);
        if (mapper == null) {
            throw new IllegalArgumentException("알 수 없는 기물 타입입니다");
        }
        return mapper.apply(team);
    }
}
