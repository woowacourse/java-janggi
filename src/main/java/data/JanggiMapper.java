package data;

import domain.Game;
import domain.board.Board;
import domain.board.Palace;
import domain.place.piece.Piece;
import domain.place.piece.Side;
import domain.player.Players;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiMapper {

    public GameEntity toGameDto(Game game) {
        return new GameEntity(
                game.id(),
                game.players().getPlayerBySide(Side.CHO).getName(),
                game.players().getPlayerBySide(Side.HAN).getName(),
                game.getCurrentPlayer().getSide(),
                game.isGameOver()
        );
    }

    public List<BoardEntity> toPieceDtos(Board board, Long gameId) {
        List<BoardEntity> pieceEntities = new ArrayList<>();

        for (int row = 1; row <= 10; row++) {
            for (int column = 1; column <= 9; column++) {
                Optional<Piece> pieceOptional = board.findPiece(new Position(row, column));
                if (pieceOptional.isEmpty()) {
                    continue;
                }

                Piece piece = pieceOptional.get();
                pieceEntities.add(new BoardEntity(
                        null,
                        gameId,
                        piece.getSymbol(),
                        piece.getSide(),
                        row,
                        column
                ));
            }
        }

        return pieceEntities;
    }

    public Board toBoard(List<BoardEntity> pieceEntities) {
        Map<Position, Piece> map = new HashMap<>();

        for (BoardEntity pieceEntity : pieceEntities) {
            Piece piece = toPiece(pieceEntity);
            Position position = new Position(pieceEntity.row(), pieceEntity.column());
            map.put(position, piece);
        }

        return new Board(map, Palace.getInstance());
    }

    public Game toDomain(GameEntity gameEntity, Board board) {
        List<String> names = List.of(gameEntity.playerCho(), gameEntity.playerHan());
        Players players = Players.from(names);

        return new Game(
                gameEntity.id(),
                players,
                board,
                gameEntity.currentTurn().resolve(players.getPlayerBySide(Side.CHO),
                        players.getPlayerBySide(Side.HAN)),
                gameEntity.status()
        );
    }

    private Piece toPiece(BoardEntity dto) {
        return dto.pieceSymbol().create(dto.side());
    }
}
