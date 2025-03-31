package janggi.service;

import janggi.GameContext;
import janggi.GameStatus;
import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Players;
import janggi.player.Score;
import janggi.player.Team;
import janggi.player.Turn;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.view.command.MoveCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class JanggiService {

    private final GameRepository gameRepository;
    private final PieceRepository pieceRepository;

    public JanggiService(final GameRepository gameRepository, final PieceRepository pieceRepository) {
        this.gameRepository = gameRepository;
        this.pieceRepository = pieceRepository;
    }

    public List<Integer> getRunningGameIds() {
        return gameRepository.findIdsByStatus(GameStatus.RUNNING);
    }

    public GameContext createNewContext() {
        final LocalDateTime startAt = LocalDateTime.now();
        final Turn turn = Turn.start();
        final Players players = Players.create(turn);
        final Board board = players.createBoard();

        return new GameContext(null, startAt, players, board, turn);
    }

    public GameContext loadSavedContext(final Long selectGameId) {
        final List<Piece> piecesList = pieceRepository.findAllByGameId(selectGameId);
        final Pieces pieces = Pieces.from(piecesList);
        final Turn turn = gameRepository.findTurnByGameId(selectGameId)
                .orElseThrow(() -> new NoSuchElementException("저장된 게임이 없습니다"));
        final LocalDateTime startAt = null;
        final Score choScore = gameRepository.findChoScoreByGameId(selectGameId).orElseThrow();
        final Score hanScore = gameRepository.findHanScoreByGameId(selectGameId).orElseThrow();
        final Players players = Players.of(pieces, turn, choScore, hanScore);
        final Board board = Board.from(pieces);

        return new GameContext(selectGameId, startAt, players, board, turn);
    }

    public void movePiece(final Board board,
                          final Player player,
                          final MoveCommand command) {
        board.movePiece(player, command.getDeparturePosition(), command.getDestinationPosition());
    }

    public boolean isGameOver(final Players players) {
        for (final Team team : Team.values()) {
            if (players.getScore(team).isGreaterThan(Score.win())) {
                return true;
            }
        }
        return false;
    }

    public void saveGame(Long gameId, final Players players, final Turn turn, final List<Piece> pieces) {
        if (gameId == null) {
            gameId = gameRepository.save(turn, players.getScore(Team.CHO), players.getScore(Team.HAN));
        } else {
            gameId = gameRepository.save(gameId, turn, players.getScore(Team.CHO), players.getScore(Team.HAN));
        }

        pieceRepository.saveAll(gameId, pieces);
    }
}
