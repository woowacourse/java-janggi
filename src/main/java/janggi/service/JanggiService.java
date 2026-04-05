package janggi.service;

import janggi.dao.game.GameDao;
import janggi.dao.game.GameEntity;
import janggi.dao.piece.PieceDao;
import janggi.dao.piece.PieceEntity;
import janggi.infra.transaction.TransactionExecutor;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.turn.Turn;
import janggi.model.turn.playing.ChoTurn;
import janggi.model.turn.playing.HanTurn;
import janggi.service.dto.LatestInProgressGameResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JanggiService {

    private final GameDao gameDao;
    private final PieceDao pieceDao;
    private final TransactionExecutor transactionExecutor;

    public JanggiService(
            GameDao gameDao,
            PieceDao pieceDao,
            TransactionExecutor transactionExecutor
    ) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
        this.transactionExecutor = transactionExecutor;
    }

    public Optional<LatestInProgressGameResponse> loadGame(){
        return transactionExecutor.execute(con -> {
            Optional<GameEntity> gameEntityOpt =
                    gameDao.findLatestGame(con);

            if (gameEntityOpt.isEmpty()) {
                return Optional.empty();
            }

            GameEntity gameEntity = gameEntityOpt.get();
            List<PieceEntity> pieceEntities =
                    pieceDao.findAllByGameId(con, gameEntity.id());

            PlayingBoard board = toBoard(pieceEntities);
            Turn turn = toTurn(gameEntity, board);

            LatestInProgressGameResponse response = new LatestInProgressGameResponse(
                    gameEntity.id(),
                    Janggi.continueFrom(turn)
            );

            return Optional.of(response);
        });
    }

    private PlayingBoard toBoard(List<PieceEntity> pieceEntities) {
        return PlayingBoard.of(
                getBoardInfoFrom(pieceEntities)
        );
    }

    private Map<Position, Piece> getBoardInfoFrom(List<PieceEntity> pieceEntities) {
        return pieceEntities.stream()
                .collect(Collectors.toMap(
                                this::getPositionFrom
                                ,this::getPieceFrom
                        )
                );
    }

    private Position getPositionFrom(PieceEntity pieceEntity) {
        return new Position(
                Row.of(pieceEntity.positionRow()),
                Column.of(pieceEntity.positionColumn())
        );
    }

    private Piece getPieceFrom(PieceEntity pieceEntity) {
        return PieceType
                .valueOf(
                        pieceEntity.pieceType()
                ).createPieceWith(
                        Team.valueOf(pieceEntity.team())
                );
    }

    private Turn toTurn(GameEntity foundGame, PlayingBoard board) {
        Team team = Team.valueOf(foundGame.currentTurn());

        if (team == Team.CHO) {
            return new ChoTurn(board);
        }

        return new HanTurn(board);
    }

    public LatestInProgressGameResponse initGame(Board board) {
        Janggi newGame = Janggi.of(board);

        Long gameId = transactionExecutor.execute(con ->{
                    Long result = gameDao.save(con, newGame.getCurrentTeam().name());
                    pieceDao.saveBoard(con, newGame.getBoard().getBoardInfo(), result);
                    return result;
        });

        return new LatestInProgressGameResponse(
                gameId,
                newGame
        );
    }

    public Janggi updateBoardWith(
            Janggi janggi,
            Position from,
            Position to
    ) {
        Janggi moved = janggi.play(from, to);

        transactionExecutor.executeWithoutResult(con -> {
            Optional<PieceEntity> pieceEntityOpt =
                    pieceDao.findByPosition(con, from);

            if (pieceEntityOpt.isEmpty()) {
                throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
            }

            PieceEntity piece = pieceEntityOpt.get();

            if (pieceDao.findByPosition(con, to).isPresent()) {
                pieceDao.deleteByPosition(con, to);
            }

            pieceDao.updatePosition(
                    con,
                    piece.id(),
                    to
            );

            gameDao.updateCurrentTurn(
                    con,
                    piece.gameId(),
                    janggi.getCurrentTeam().name()
            );
        });

        return moved;
    }

    public void removeGame(Long gameId) {
        transactionExecutor.executeWithoutResult(con ->
                gameDao.deleteByGameId(con, gameId)
        );
    }
}
