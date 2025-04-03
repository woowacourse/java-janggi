package application;

import application.persistence.GameRepository;
import application.persistence.PieceRepository;
import domain.board.Board;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.game.Game;
import domain.piece.Country;
import domain.piece.Piece;
import domain.piece.coordiante.Coordinate;
import java.util.List;
import java.util.Map;

public class JanggiUseCase {

    private final PieceRepository pieceRepository;
    private final GameRepository gameRepository;
    private Game game;

    public JanggiUseCase(PieceRepository pieceRepository, GameRepository gameRepository) {
        this.pieceRepository = pieceRepository;
        this.gameRepository = gameRepository;
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public void loadGame(String gameName) {
        this.game = findGame(gameRepository.findAll(), gameName);
    }

    private Game findGame(List<Game> janggiGames, String gameName) {
        return janggiGames.stream()
                .filter(janggiGame -> janggiGame.getName().equals(gameName))
                .findFirst()
                .orElseGet(() -> createGame(gameName));
    }

    private Game createGame(String gameName) {
        Game newGame = new Game(gameName, Country.CHO);
        return gameRepository.save(newGame);
    }

    public Board setUpBoard() {
        Map<Coordinate, Piece> stored = pieceRepository.findByGame(game);
        if (stored.isEmpty()) {
            return null;
        }
        return new Board(stored);
    }

    public Board newBoard(HanSettingUpStrategy hanSetting, ChoSettingUpStrategy choSetting) {
        Board board = new Board(choSetting, hanSetting);
        pieceRepository.savePieces(board.getBoard(), game);
        return board;
    }

    public void movePiece(Board board, Coordinate from, Coordinate to) {
        board.validateIsMyPiece(from, game.getCountry());
        board.movePiece(from, to);
        pieceRepository.deleteByGame(game);
        pieceRepository.savePieces(board.getBoard(), game);
    }

    public boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            pieceRepository.deleteByGame(game);
            gameRepository.deleteGame(game);
            return true;
        }
        return false;
    }

    public int getHanScore(Board board) {
        return board.calculateHanScore();
    }

    public int getChoScore(Board board) {
        return board.calculateChoScore();
    }

    public void nextTurn() {
        game.next();
        gameRepository.updateGame(game);
    }

    public Game getCurrentGame() {
        return game;
    }
}
