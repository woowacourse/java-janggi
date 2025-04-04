package service;

import dao.PieceDao;
import dao.PlayerDao;
import domain.Board;
import domain.Game;
import domain.GameStatus;
import domain.Player;
import domain.piece.Pieces;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLoadService {

    private final PlayerDao playerRepository;
    private final PieceDao pieceRepository;

    public GameLoadService(final PlayerDao playerRepository, final PieceDao pieceRepository) {
        this.playerRepository = playerRepository;
        this.pieceRepository = pieceRepository;
    }

    public Game loadGame(final String gameName) {
        List<Player> players = playerRepository.findAllByGameName(gameName);

        Map<Player, Pieces> gamePlayers = new HashMap<>();
        for (Player player : players) {
            Pieces pieces = pieceRepository.findAllByGameNameAndTeam(gameName, player.getTeam());
            gamePlayers.put(player, pieces);
        }
        Board board = new Board(gamePlayers);

        return new Game(gameName, GameStatus.PLAYING, board);
    }
}
