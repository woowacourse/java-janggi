package service;

import java.util.List;

import dto.BoardDto;
import dto.FinalScoreDto;
import dto.GameDto;
import dto.TeamDto;
import model.Game;
import model.Position;
import model.Team;
import model.board.Board;
import model.board.TableSetting;
import repository.BoardRepository;
import repository.GameRepository;

public class JanggiService {

    private final GameRepository gameRepository = new GameRepository();
    private final BoardRepository boardRepository = new BoardRepository();

    public List<GameDto> gameList() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
            .map(game -> GameDto.from(game))
            .toList();
    }

    public int newGame(String gameName) {
        Game savedGame = gameRepository.save(new Game(gameName, Team.CHO));
        return savedGame.getId();
    }

    public void tableSettingForCurrentTurn(int gameId, TableSetting tableSetting) {
        Game game = gameRepository.findById(gameId);
        Board board = boardRepository.findByGameId(gameId);
        board.addTeamPieces(game.getTurn(), tableSetting);
        boardRepository.setUpTeam(gameId, board.getPieces(game.getTurn()));
    }

    public BoardDto getBoard(int gameId) {
        Board board = boardRepository.findByGameId(gameId);
        return BoardDto.from(board);
    }

    public TeamDto currentTurn(int gameId) {
        Game game = gameRepository.findById(gameId);
        return TeamDto.from(game.getTurn());
    }

    public void move(int gameId, Position source, Position destination) {
        Game game = gameRepository.findById(gameId);
        Board board = boardRepository.findByGameId(gameId);
        board.movePiece(source, destination, game.getTurn());
        boardRepository.save(gameId, board);
    }

    public boolean isPlaying(int gameId) {
        Board board = boardRepository.findByGameId(gameId);
        return board.getWinnerIfGameOver() == null;
    }

    public void nextTurn(int gameId) {
        Game game = gameRepository.findById(gameId);
        game.nextTurn();
        gameRepository.save(game);
    }

    public TeamDto getWinner(int gameId) {
        Board board = boardRepository.findByGameId(gameId);
        return TeamDto.from(board.getWinnerIfGameOver());
    }

    public void abstain(int gameId) {
        Game game = gameRepository.findById(gameId);
        Board board = boardRepository.findByGameId(gameId);
        board.abstain(game.getTurn());
        boardRepository.save(gameId, board);
    }

    public FinalScoreDto finalScore(int gameId) {
        Board board = boardRepository.findByGameId(gameId);
        return FinalScoreDto.of(board.getPieceScore());
    }

    public void endGame(int gameId) {
        Game game = gameRepository.findById(gameId);
        Board board = boardRepository.findByGameId(gameId);
        gameRepository.remove(game);
        boardRepository.removeAllByGameId(gameId);
    }
}
