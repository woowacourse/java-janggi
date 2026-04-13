package repository.mapper;

import domain.board.Board;
import domain.movestrategy.MoveStrategyType;
import domain.piece.Piece;
import domain.piece.PieceStatus;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
import repository.entity.PieceEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceMapper {

    public List<PieceEntity> toEntities(final long gameId, final Board board) {
        final List<PieceEntity> pieceEntities = new ArrayList<>();

        addPieceEntities(pieceEntities, gameId, board, Team.CHO);
        addPieceEntities(pieceEntities, gameId, board, Team.HAN);

        return pieceEntities;
    }

    public Board toBoard(final List<PieceEntity> pieceEntities) {
        final Map<Position, Piece> pieces = new HashMap<>();

        for (final PieceEntity pieceEntity : pieceEntities) {
            pieces.put(positionOf(pieceEntity), pieceOf(pieceEntity));
        }

        return Board.of(pieces);
    }

    private void addPieceEntities(
            final List<PieceEntity> pieceEntities,
            final long gameId,
            final Board board,
            final Team team
    ) {
        for (final Position position : board.findPositionsByTeam(team)) {
            final Piece piece = board.getPiece(position);
            pieceEntities.add(toEntity(gameId, piece, position));
        }
    }

    private PieceEntity toEntity(
            final long gameId,
            final Piece piece,
            final Position position
    ) {
        return new PieceEntity(
                null,
                gameId,
                piece.getTeam().name(),
                piece.getPieceType().name(),
                piece.getMoveStrategyType().name(),
                position.row(),
                position.column()
        );
    }

    private Position positionOf(final PieceEntity pieceEntity) {
        return Position.of(
                pieceEntity.getBoardRow(),
                pieceEntity.getBoardColumn()
        );
    }

    private Piece pieceOf(final PieceEntity pieceEntity) {
        final PieceType pieceType = PieceType.valueOf(pieceEntity.getPieceType());
        final MoveStrategyType moveStrategyType = MoveStrategyType.valueOf(pieceEntity.getMoveStrategyType());
        final Team team = Team.valueOf(pieceEntity.getTeam());

        return Piece.of(new PieceStatus(pieceType, moveStrategyType), team);
    }
}
