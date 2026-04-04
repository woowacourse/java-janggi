package janggi.repository;

import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.turn.Turn;
import janggi.model.turn.playing.ChoTurn;
import janggi.model.turn.playing.HanTurn;
import janggi.repository.dao.GameEntityDao;
import janggi.repository.dao.PieceEntityDao;
import janggi.repository.dto.LatestInProgressGameResponse;
import janggi.repository.entity.GameEntity;
import janggi.repository.entity.PieceEntity;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcGameRepository implements gameRepository {

    private final GameEntityDao gameEntityDao;
    private final PieceEntityDao pieceEntityDao;

    public JdbcGameRepository(
            GameEntityDao gameEntityDao,
            PieceEntityDao pieceEntityDao
    ) {
        this.gameEntityDao = gameEntityDao;
        this.pieceEntityDao = pieceEntityDao;
    }

    public static JdbcGameRepository of() {
        return new JdbcGameRepository(
                new GameEntityDao(),
                new PieceEntityDao()
        );
    }

    @Override
    public Optional<LatestInProgressGameResponse> findLatestInProgressGame(Connection con){
        Optional<GameEntity> gameEntity = gameEntityDao.findLatestGame(con);
        if (gameEntity.isEmpty()) {
            return Optional.empty();
        }

        GameEntity foundGame = gameEntity.get();
        List<PieceEntity> pieceEntities = pieceEntityDao.findAllByGameId(con, foundGame.id());

        PlayingBoard board = toBoard(pieceEntities);
        Turn turn = toTurn(foundGame, board);

        LatestInProgressGameResponse response = new LatestInProgressGameResponse(
                foundGame.id(),
                Janggi.continueFrom(turn)
        );

        return Optional.of(response);
    }

    private PlayingBoard toBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> boardInfo = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> new Position(
                                Row.of(entity.positionRow()),
                                Column.of(entity.positionColumn())
                        ),
                        entity -> PieceType
                                .valueOf(entity.pieceType())
                                .createPieceWith(Team.valueOf(entity.team()))
                ));

        return PlayingBoard.of(boardInfo);
    }

    private Turn toTurn(GameEntity foundGame, PlayingBoard board) {
        Team team = Team.valueOf(foundGame.currentTurn());

        if (team == Team.CHO) {
            return new ChoTurn(board);
        }

        return new HanTurn(board);
    }


    @Override
    public Long saveBoard(
            Connection con,
            String team,
            Map<Position, Piece> boardInfo
    ) {
        Long gameId = gameEntityDao.save(con, team);
        pieceEntityDao.saveBoard(con, boardInfo, gameId);

        return gameId;
    }

    @Override
    public  void updateBoardWith(
            Connection con,
            Position from,
            Position to
    ) {
        Optional<PieceEntity> found =
                pieceEntityDao.findByPosition(con, from);

        if (found.isEmpty()) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        PieceEntity piece = found.get();

        if (pieceEntityDao.findByPosition(con, to).isPresent()) {
            pieceEntityDao.deleteByPosition(con, to);
        }

        pieceEntityDao.updatePosition(
                con,
                piece.id(),
                to
        );
    }

    @Override
    public void deleteByGameId(Connection con, Long gameId) {
        gameEntityDao.deleteByGameId(con, gameId);
    }
}
