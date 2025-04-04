package persistence.mapper;

import domain.board.Board;
import domain.board.BoardLocation;
import persistence.entity.JanggiGameEntity;
import persistence.entity.PieceEntity;
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
                    return new PieceEntity(location.column(), location.row(), piece.getType(), piece.getTeam(),
                            piece.getScore(), janggiGameId);
                })
                .toList();
    }

    public JanggiGameEntity mapToUpdateJanggiGameEntity(JanggiGame janggiGame) {
        return new JanggiGameEntity(janggiGame.getTurn());
    }

    public JanggiGame mapToJanggiGame(JanggiGameEntity janggiGameEntity, List<PieceEntity> pieceEntities) {
        Turn turn = janggiGameEntity.turn();
        Board board = mapToBoard(pieceEntities);
        return new JanggiGame(board, turn);
    }

    private Board mapToBoard(List<PieceEntity> pieceEntities) {
        Map<BoardLocation, Piece> pieces = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> new BoardLocation(entity.column(), entity.row()),
                        this::createPiece
                ));
        return new Board(pieces);
    }

    private Piece createPiece(PieceEntity entity) {
        return switch (entity.type()) {
            case CANNON -> new Cannon(entity.team());
            case HORSE -> new Horse(entity.team());
            case CHARIOT -> new Chariot(entity.team());
            case ELEPHANT -> new Elephant(entity.team());
            case KING -> King.createByTeam(entity.team());
            case PAWN -> new Pawn(entity.team());
            case SCHOLAR -> new Scholar(entity.team());
        };
    }
}
