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
import janggi.infrastructure.db.TransactionTemplate;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository;
    private final TransactionTemplate transactionTemplate;

    public GameService(GameRepository gameRepository, TransactionTemplate transactionTemplate) {
        this.gameRepository = gameRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public Long initializeGame(Name choName, Name hanName, Formation choFormation, Formation hanFormation) {
        return transactionTemplate.execute(() -> {
            Players players = new Players(new Player(choName, Side.CHO, choFormation), new Player(hanName, Side.HAN, hanFormation));
            Board board = BoardFactory.create(choFormation, hanFormation);
            Game game = Game.startNew(board, players);
            return gameRepository.save(game);
        });
    }

    public void move(Long gameId, Position source, Position target) {
        transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            game.move(source, target);
            gameRepository.update(gameId, game);
        });
    }

    public List<GameDto> findAllGames() {
        return transactionTemplate.execute(() ->
            gameRepository.findAllGames().stream()
                    .map(GameDto::from)
                    .toList()
        );
    }

    public BoardDto getBoardDto(Long gameId) {
        return transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            return BoardDto.from(game);
        });
    }

    public boolean isPlaying(Long gameId) {
        return transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            return game.isPlaying();
        });
    }

    public Side getCurrentSide(Long gameId) {
        return transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            return game.getCurrentSide();
        });
    }

    public DestinationDto selectSource(Long gameId, Position source) {
        return transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            return DestinationDto.from(game.selectSource(source));
        });
    }

    public WinnerDto getWinnerDto(Long gameId) {
        return transactionTemplate.execute(() -> {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
            return WinnerDto.from(game.getWinner());
        });
    }
}
