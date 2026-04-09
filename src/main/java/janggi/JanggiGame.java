package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.ElephantFormation;
import janggi.domain.board.InitialPiecePlacement;
import janggi.domain.game.Game;
import janggi.domain.game.GameSelectionFormat;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PlacedPiece;
import janggi.domain.piece.camp.CampType;
import janggi.dto.MoveResultDto;
import janggi.dto.PiecePositionDto;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.util.RetryHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.format.ElephantSetUpFormat;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiGame(GameRepository gameRepository, PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public void run() {
        GameSelectionFormat gameSelectionFormat = InputView.readGameSelection();
        if (gameSelectionFormat == GameSelectionFormat.NEW_GAME) {
            Board board = createBoard();
            List<Piece> pieces = board.getBoard().values().stream().toList();
            Game game = new Game(CampType.CHO, GameStatus.PLAYING, pieces);
            long gameId = gameRepository.save(game);
            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                PlacedPiece placedPiece = new PlacedPiece(gameId, piece.campType(), piece.pieceRule(), position.row(), position.column());

                pieceRepository.save(placedPiece);
            }
            OutputView.printBoard(toPiecePositions(board.getBoard()));
            play(board, game);
            return;
        }
        List<Long> gameIds = gameRepository.findAllGameIds();
        long gameId = InputView.readGameId(gameIds);
        Game game = gameRepository.findById(gameId);
        Map<Position, Piece> pieces = pieceRepository.findByGameId(gameId);
        OutputView.printBoard(toPiecePositions(pieces));
        Board board = Board.restore(pieces);
        play(board, game);
    }

    private Board createBoard() {
        ElephantFormation hanElephantFormation = readElephantFormation(CampType.HAN);
        ElephantFormation choElephantFormation = readElephantFormation(CampType.CHO);
        return InitialPiecePlacement.initialize(hanElephantFormation, choElephantFormation);
    }

    private ElephantFormation readElephantFormation(CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            ElephantSetUpFormat elephantSetUpFormat = InputView.readElephantSettingCommand(campType);
            return elephantSetUpFormat.toElephantFormation(campType);
        });
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void play(Board board, Game game) {
        Turn turn = new Turn(); // TODO: db 조회해서 턴 가져와야 할 듯
        while (true) {
            CampType campType = turn.currentTurn();
            RetryHandler.retryOnInvalidInput(() -> {
                MoveResultDto moveResultDto = playTurn(board, campType);
                PlacedPiece placedPiece = new PlacedPiece(game.getGameId(), moveResultDto.campType(), moveResultDto.pieceRule(), moveResultDto.destination().row(), moveResultDto.destination().column());
                if (moveResultDto.captured()) {
                    pieceRepository.delete(placedPiece);
                }
                pieceRepository.update(placedPiece);
            });
            OutputView.printBoard(toPiecePositions(board.getBoard()));
            if (board.isRivalGeneralKilled(turn)) {
                break;
            }
            turn.finishTurn();
            gameRepository.update(game);
        }
        CampType campType = turn.currentTurn();
        if (campType == CampType.CHO) {
            gameRepository.update(game);
            OutputView.printWinner(campType);
        }
        gameRepository.update(game);
        OutputView.printWinner(campType);
    }

    private MoveResultDto playTurn(Board board, CampType campType) {
        OutputView.printScore(board.getScoreBoard());
        Position source = readSource(board, campType);
        Position destination = readDestination(board, source, campType);
        return board.movePiece(source, destination, campType);
    }

    private Position readSource(Board board, CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position source = Position.from(InputView.readSource(campType));
            board.validateSource(source, campType);
            return source;
        });
    }

    private Position readDestination(Board board, Position source, CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position destination = Position.from(InputView.readDestination());
            board.validateDestination(destination, source, campType);
            return destination;
        });
    }
}
