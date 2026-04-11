package data;

import domain.Janggi;
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

    public GameDto toGameDto(Janggi janggi) {
        return new GameDto(
                janggi.id(),
                janggi.players().getPlayerBySide(Side.CHO).getName(),
                janggi.players().getPlayerBySide(Side.HAN).getName(),
                janggi.getCurrentPlayer().getSide(),
                janggi.isGameOver()
        );
    }

    public List<PieceDto> toPieceDtos(Board board, Long boardId) {
        List<PieceDto> pieceDtos = new ArrayList<>();

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 9; column++) {
                Optional<Piece> pieceOptional = board.findPiece(new Position(row, column));
                if (pieceOptional.isEmpty()) {
                    continue;
                }

                Piece piece = pieceOptional.get();
                pieceDtos.add(new PieceDto(
                        boardId,
                        piece.getSymbol(),
                        piece.getSide(),
                        row,
                        column
                ));
            }
        }

        return pieceDtos;
    }

    public Board toBoard(List<PieceDto> pieceDtos) {
        Map<Position, Piece> map = new HashMap<>();

        for (PieceDto pieceDto : pieceDtos) {
            Piece piece = toPiece(pieceDto);
            Position position = new Position(pieceDto.row(), pieceDto.column());
            map.put(position, piece);
        }

        return new Board(map, Palace.getInstance());
    }

    public Janggi toDomain(GameDto gameDto, Board board) {
        List<String> names = List.of(gameDto.playerCho(), gameDto.playerHan());
        Players players = Players.from(names);

        return new Janggi(
                gameDto.id(),
                players,
                board,
                gameDto.currentTurn().resolve(players.getPlayerBySide(Side.CHO),
                        players.getPlayerBySide(Side.HAN)),
                gameDto.status(),
                board.calculateScore(Side.CHO),
                board.calculateScore(Side.HAN)
        );
    }

    private Piece toPiece(PieceDto dto) {
        return dto.pieceSymbol().create(dto.side());
    }
}
