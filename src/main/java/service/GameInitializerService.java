package service;

import dao.GameDao;
import dao.PieceDao;
import dao.PlayerDao;
import domain.Board;
import domain.Game;
import domain.GameStatus;
import domain.Player;
import domain.Team;
import domain.piece.Pieces;
import domain.piece.Score;
import domain.strategy.SettingUp;
import domain.strategy.SettingUpInitializer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import view.InputView;
import view.OutputView;

public class GameInitializerService {

    private final OutputView outputView;
    private final InputView inputView;
    private final GameDao gameRepository;
    private final PlayerDao playerRepository;
    private final PieceDao pieceRepository;

    public GameInitializerService(final OutputView outputView,
                                  final InputView inputView,
                                  final GameDao gameRepository,
                                  final PlayerDao playerRepository,
                                  final PieceDao pieceRepository
    ) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.pieceRepository = pieceRepository;
    }

    public Game initializeGame() {
        String gameName = inputView.readCreateGameName();

        Player han = new Player(Team.HAN, new Score(0));
        Player cho = new Player(Team.CHO, new Score(0));

        Board board = createBoard(han, cho);
        Game newGame = new Game(gameName, GameStatus.PLAYING, board);
        gameRepository.save(newGame);

        playerRepository.save(gameName, han);
        playerRepository.save(gameName, cho);

        Map<Player, Pieces> playerPiecesMap = board.gamePlayers();
        for (Entry<Player, Pieces> entry : playerPiecesMap.entrySet()) {
            Player player = entry.getKey();
            Pieces pieces = entry.getValue();
            pieceRepository.saveAll(newGame.getName(), player.getTeam(), pieces);
        }

        return newGame;
    }

    private Board createBoard(final Player han, final Player cho) {
        Map<Player, Pieces> board = new HashMap<>();
        board.put(han, createPiecesByPlayer(han));
        board.put(cho, createPiecesByPlayer(cho));
        return new Board(board);
    }

    private Pieces createPiecesByPlayer(final Player player) {
        while (true) {
            try {
                int command = inputView.readSettingUpStrategyCommand(player);
                SettingUpInitializer strategy = SettingUp.findStrategyByCommand(command);
                return strategy.initPieces(player);
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
