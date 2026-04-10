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
    private Long currentGameId;
    private Game game;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void initializeGame(Name choName, Name hanName, Formation choFormation, Formation hanFormation) {
        Players players = new Players(new Player(choName, Side.CHO), new Player(hanName, Side.HAN));
        Board board = BoardFactory.create(choFormation, hanFormation);
        this.game = Game.startNew(board, players);
        this.currentGameId = gameRepository.save(game);
    }

    public void loadGame(Long gameId) {
        this.game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
        this.currentGameId = gameId;
    }

    public void move(Position source, Position target) {
        game.move(source, target);
        gameRepository.update(currentGameId, game);
    }

    public List<GameDto> findAllGames() {
        return gameRepository.findAllGames();
    }

    public BoardDto getBoardDto() {
        return BoardDto.from(game.getBoard());
    }

    public boolean isPlaying() {
        return game.isPlaying();
    }

    public Side getCurrentSide() {
        return  game.getCurrentSide();
    }

    public DestinationDto selectSource(Position source) {
        return DestinationDto.from(game.selectSource(source));
    }

    public WinnerDto getWinnerDto() {
        return WinnerDto.from(game.getWinner());
    }
}
