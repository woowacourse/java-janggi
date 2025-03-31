package infra.repository;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.repository.BoardRepository;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import infra.dao.PieceDao;
import infra.entity.PieceEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardRepositoryAdapter implements BoardRepository {

    private final PieceDao pieceDao;

    public BoardRepositoryAdapter(final PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    @Override
    public void save(final Board board) {
        pieceDao.deleteAll();

        board.getPieces()
            .forEach((position, piece) -> {
                    final PieceEntity entity = toEntity(position, piece);
                    pieceDao.save(entity);
                }
            );
    }

    @Override
    public boolean hasAnyPiece() {
        return pieceDao.exists();
    }

    @Override
    public Optional<Board> load() {
        final List<PieceEntity> pieceEntities = pieceDao.findAll();
        if (pieceEntities.isEmpty()) {
            return Optional.empty();
        }

        final Map<BoardPosition, Piece> pieces = new HashMap<>();
        pieceEntities.forEach(pieceEntity -> {
            final BoardPosition boardPosition = new BoardPosition(
                pieceEntity.getColumnIndex(), pieceEntity.getRowIndex());
            final Piece piece = toDomain(pieceEntity);

            pieces.put(boardPosition, piece);
        });

        return Optional.of(new Board(pieces));
    }

    @Override
    public void deleteAll() {
        pieceDao.deleteAll();
    }

    private PieceEntity toEntity(
        final BoardPosition boardPosition,
        final Piece piece
    ) {
        return new PieceEntity(piece.getPieceType().name(), piece.getTeam().name(),
            boardPosition.x(), boardPosition.y());
    }

    private Piece toDomain(final PieceEntity pieceEntity) {
        final PieceType pieceType = PieceType.valueOf(pieceEntity.getDtype());
        final Team team = Team.valueOf(pieceEntity.getTeam());

        return pieceType.pieceCreator(team);
    }
}
