package entity;

import domain.Player;
import domain.Team;
import domain.board.Board;
import domain.board.BoardPoint;
import domain.pieces.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameInitializer {
    private final BoardRepository boardRepository;
    private final PieceRepository pieceRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public GameInitializer(BoardRepository boardRepository, PieceRepository pieceRepository,
                           TeamRepository teamRepository,
                           PlayerRepository playerRepository) {
        this.boardRepository = boardRepository;
        this.pieceRepository = pieceRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Board createBoard() {
        List<BoardEntity> boardEntities = boardRepository.getAllBoards();

        Map<BoardPoint, Piece> locations = new HashMap<>();

        for (BoardEntity boardEntity : boardEntities) {
            BoardPoint boardPoint = new BoardPoint(boardEntity.getRowIndex(), boardEntity.getColumnIndex());

            PieceEntity pieceEntity = pieceRepository.findById(boardEntity.getPieceId());
            TeamEntity teamEntity = teamRepository.findById(pieceEntity.getTeamId());

            Piece piece = PieceMapper.toPiece(pieceEntity, teamEntity);
            locations.put(boardPoint, piece);
        }

        return new Board(locations);
    }

    public List<Player> getAllPlayers() {
        List<PlayerEntity> players = playerRepository.getAllPlayers();

        return players.stream()
                .map(playerEntity ->
                {
                    TeamEntity teamEntity = teamRepository.findById(playerEntity.getTeamId());
                    return PlayerMapper.toPlayer(Team.findByName(teamEntity.getName()));
                })
                .collect(Collectors.toList());
    }
}
