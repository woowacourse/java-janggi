package janggi.service;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.space.Position;
import janggi.dto.BoardDto;
import janggi.dto.DestinationDto;
import janggi.dto.WinnerDto;

public class GameService {
    private Game game;

    public void initializeGame(Name choName, Name hanName, Formation choFormation, Formation hanFormation) {
        Players players = Players.createInitial(choName, hanName);
        Board board = BoardFactory.create(choFormation, hanFormation);
        this.game = new Game(board, players);
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

    public void move(Position source, Position target) {
        game.move(source, target);
    }

    public WinnerDto getWinnerDto() {
        return WinnerDto.from(game.getWinner());
    }
}
