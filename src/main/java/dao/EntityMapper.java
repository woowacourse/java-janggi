package dao;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.entity.JanggiGameEntity;
import domain.entity.PieceEntity;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Scholar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityMapper {

    public JanggiGameEntity mapToCreateJanggiGameEntity(JanggiGame janggiGame) {
        return new JanggiGameEntity(janggiGame.getTurn());
    }

    public List<PieceEntity> mapToCreatePieceEntities(Board board, Long janggiGameId) {
        Map<BoardLocation, Piece> pieces = board.getPieces();
        return pieces.entrySet().stream()
                .map(entry -> {
                    BoardLocation location = entry.getKey();
                    Piece piece = entry.getValue();
                    return new PieceEntity(location.x(), location.y(), piece.getType(), piece.getTeam(),
                            piece.getScore(), janggiGameId);
                })
                .toList();
    }

    public JanggiGameEntity mapToUpdateJanggiGameEntity(JanggiGame janggiGame) {
        return new JanggiGameEntity(janggiGame.getTurn());
    }

    public JanggiGame mapToJanggiGame(JanggiGameEntity janggiGameEntity, List<PieceEntity> pieceEntities) {
        Turn turn = janggiGameEntity.getTurn();
        Board board = mapToBoard(pieceEntities);
        return new JanggiGame(board, turn);
    }

    private Board mapToBoard(List<PieceEntity> pieceEntities) {
        Map<BoardLocation, Piece> pieces = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> new BoardLocation(entity.getX(), entity.getY()),
                        this::createPiece
                ));
        return new Board(pieces);
    }

    private Piece createPiece(PieceEntity entity) {
        return switch (entity.getType()) {
            case CANNON -> new Cannon(entity.getTeam());
            case HORSE -> new Horse(entity.getTeam());
            case CHARIOT -> new Chariot(entity.getTeam());
            case ELEPHANT -> new Elephant(entity.getTeam());
            case KING -> King.createByTeam(entity.getTeam());
            case PAWN -> new Pawn(entity.getTeam());
            case SCHOLAR -> new Scholar(entity.getTeam());
        };
    }
}
