package janggi.service;

import janggi.dao.game.GameDao;
import janggi.dao.game.GameEntity;
import janggi.dao.piece.PieceDao;
import janggi.dao.piece.PieceEntity;
import janggi.infra.transaction.TransactionExecutor;
import janggi.model.Janggi;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.BoardType;
import janggi.model.board.PlayingBoard;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.turn.Turn;
import janggi.model.turn.playing.ChoTurn;
import janggi.model.turn.playing.HanTurn;
import janggi.service.dto.GameDetailResponse;
import janggi.service.dto.GameOptionResponse;
import java.util.List;
import java.util.Map;
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

    public List<GameOptionResponse> loadAllGames() {
        return transactionExecutor.execute(connection -> {
            List<GameEntity> gameEntities = gameDao.findAllGames(connection);
            return gameEntities.stream()
                    .map(game ->
                            new GameOptionResponse(
                                    game.id(),
                                    Team.valueOf(game.currentTurn()
                                    )
                            )
                    ).toList();
        });
    }

    public GameDetailResponse loadGameByGameId(Long gameId) {
        return transactionExecutor.execute(connection -> {
            GameEntity gameEntity = gameDao.findGameByGameId(connection, gameId);

            List<PieceEntity> pieceEntities =
                    pieceDao.findAllPiecesByGameId(connection, gameEntity.id());

            Board board = toBoard(pieceEntities);

            Turn turn = toTurn(gameEntity, board);

            return new GameDetailResponse(
                    gameEntity.id(),
                    Janggi.continueFrom(turn)
            );
        });
    }

    private PlayingBoard toBoard(List<PieceEntity> pieceEntities) {
        Map<Position, Piece> boarInfo = pieceEntities.stream()
                .collect(Collectors.toMap(
                                this::getPositionFrom
                                , this::getPieceFrom
                        )
                );

        return PlayingBoard.of(boarInfo);
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

    private Turn toTurn(GameEntity foundGame, Board board) {
        Team team = Team.valueOf(foundGame.currentTurn());

        if (team == Team.CHO) {
            return new ChoTurn(board);
        }

        return new HanTurn(board);
    }

    public GameDetailResponse initGame(BoardType boardType) {
        return transactionExecutor.execute(connection -> {
            Board board = boardType.getBoard();
            Janggi newGame = Janggi.of(board);

            Long gameId = gameDao.saveGame(connection, newGame.getCurrentTeam().name());
            pieceDao.saveBoard(connection, newGame.getBoard().getBoardInfo(), gameId);

            return new GameDetailResponse(
                    gameId,
                    newGame
            );
        });
    }

    public boolean isGameContinued(Janggi janggi) {
        return !janggi.isGameOver();
    }

    public Janggi updateBoardWith(
            Janggi janggi,
            Position from,
            Position to
    ) {
        return transactionExecutor.execute(connection -> {
            Janggi moved = janggi.play(from, to);

            PieceEntity piece = pieceDao.findPieceByPosition(connection, from)
                    .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));

            if (pieceDao.findPieceByPosition(connection, to).isPresent()) {
                pieceDao.deletePieceByPosition(connection, to);
            }

            pieceDao.updatePieceOfPosition(
                    connection,
                    piece.id(),
                    to
            );

            gameDao.updateGameOfCurrentTurn(
                    connection,
                    piece.gameId(),
                    janggi.getCurrentTeam().name()
            );

            return moved;
        });
    }

    public Janggi draw(Janggi janggi) {
        return janggi.draw();
    }

    public void removeGame(Long gameId) {
        transactionExecutor.executeWithoutResult(connection ->
                gameDao.deleteGameByGameId(connection, gameId)
        );
    }

    public BoardType createBoardTypeOf(int typeValue) {
        return BoardType.of(typeValue);
    }

    public Position createPositionOf(int rowNumber, Integer columnNumber) {
        return new Position(Row.of(rowNumber), Column.of(columnNumber));
    }
}
