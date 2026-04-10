package janggi.service;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.player.Player;
import janggi.domain.player.Players;
import janggi.domain.repository.GameRepository;
import janggi.domain.space.Position;
import janggi.dto.BoardDto;
import janggi.dto.DestinationDto;
import janggi.dto.GameDto;
import janggi.dto.WinnerDto;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Long initializeGame(Name choName, Name hanName, Formation choFormation, Formation hanFormation) {
        Players players = new Players(new Player(choName, Side.CHO), new Player(hanName, Side.HAN));
        Board board = BoardFactory.create(choFormation, hanFormation);
        Game game = Game.startNew(board, players);
        return gameRepository.save(game);
    }

    public void move(Long gameId, Position source, Position target) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        game.move(source, target);
        gameRepository.update(gameId, game);
    }

    public List<GameDto> findAllGames() {
        return gameRepository.findAllGames();
    }

    public BoardDto getBoardDto(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return BoardDto.from(game.getBoard());
    }

    public boolean isPlaying(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return game.isPlaying();
    }

    public Side getCurrentSide(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return  game.getCurrentSide();
    }

    public DestinationDto selectSource(Long gameId, Position source) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return DestinationDto.from(game.selectSource(source));
    }

    public WinnerDto getWinnerDto(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        return WinnerDto.from(game.getWinner());
    }
}
