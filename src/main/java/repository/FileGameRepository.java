package repository;

import static domain.board.Board.MAX_COLUMN;
import static domain.board.Board.MAX_ROW;
import static domain.board.Board.MIN_COLUMN;
import static domain.board.Board.MIN_ROW;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.FileException;
import domain.board.Board;
import domain.game.Game;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import dto.BoardPieceData;
import dto.GameData;
import dto.PieceData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileGameRepository implements GameRepository {
    private static final String SAVE_DIRECTORY = "data";
    public static final String FILE_PREFIX = "game-";
    public static final String FILE_SUFFIX = ".json";
    private static final String GAME_FILE_NAME_FORMAT = FILE_PREFIX + "%d" + FILE_SUFFIX;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public long create(Game game) {
        long gameId = nextGameId();
        update(game, gameId);
        return gameId;
    }

    @Override
    public void update(Game game, long id) {
        GameData gameData = GameData.from(game);
        String gameJson = toJson(gameData);
        writeFile(gameFilePath(id), gameJson);
    }

    @Override
    public Game findBy(long id) {
        String json = readFile(gameFilePath(id));
        GameData gameData = fromJson(json);
        return toGame(gameData);
    }

    @Override
    public List<GameInformation> findAll() {
        Path saveDirectory = Path.of(SAVE_DIRECTORY);
        if (Files.notExists(saveDirectory)) {
            return List.of();
        }

        try {
            return Files.list(saveDirectory)
                    .filter(Files::isRegularFile)
                    .filter(this::isGameFile)
                    .sorted(Comparator.comparingLong(this::extractGameId))
                    .map(this::toGameInformation)
                    .toList();
        } catch (IOException e) {
            throw new FileException("저장된 게임 목록을 읽을 수 없습니다.");
        }
    }

    @Override
    public long count() {
        Path saveDirectory = Path.of(SAVE_DIRECTORY);
        if (Files.notExists(saveDirectory)) {
            return 0;
        }
        try {
            return Files.list(saveDirectory)
                    .filter(this::isGameFile)
                    .count();
        } catch (IOException e) {
            throw new FileException("저장된 게임 목록을 읽을 수 없습니다.");
        }
    }

    private String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new FileException("데이터를 JSON으로 변환할 수 없습니다.");
        }
    }

    private void writeFile(Path filePath, String data) {
        Path saveDirectory = Path.of(SAVE_DIRECTORY);
        try {
            Files.createDirectories(saveDirectory);
            Files.writeString(filePath, data);
        } catch (IOException e) {
            throw new FileException("파일 저장에 실패했습니다.");
        }
    }

    private String readFile(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new FileException("저장 파일을 읽을 수 없습니다.");
        }
    }

    private GameData fromJson(String json) {
        try {
            return objectMapper.readValue(json, GameData.class);
        } catch (JsonProcessingException e) {
            throw new FileException("JSON을 GameData로 변환할 수 없습니다.");
        }
    }

    private GameInformation toGameInformation(Path path) {
        long gameId = extractGameId(path);
        GameData gameData = fromJson(readFile(path));
        return new GameInformation(
                gameId,
                gameData.choPlayerName(),
                gameData.hanPlayerName(),
                gameData.currentTeam()
        );
    }

    private Game toGame(GameData gameData) {
        Players players = new Players(List.of(
                Player.of(gameData.choPlayerName(), Team.CHO),
                Player.of(gameData.hanPlayerName(), Team.HAN)
        ));
        Board board = createBoard(gameData.boardPieces());
        List<Piece> caughtPieces = createPieces(gameData.caughtPieces());
        Team currentTeam = Team.valueOf(gameData.currentTeam());
        return Game.restore(players, board, caughtPieces, currentTeam);
    }

    private Board createBoard(Set<BoardPieceData> boardPieces) {
        Map<Position, Piece> boardMap = createEmptyBoard();
        for (BoardPieceData boardPieceData : boardPieces) {
            Position position = new Position(boardPieceData.row(), boardPieceData.column());
            Piece piece = createPiece(boardPieceData.pieceData());
            boardMap.put(position, piece);
        }
        return new Board(boardMap);
    }

    private Map<Position, Piece> createEmptyBoard() {
        Map<Position, Piece> board = new HashMap<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                board.put(new Position(row, column), PieceType.NONE.create(null));
            }
        }
        return board;
    }

    private List<Piece> createPieces(List<PieceData> pieceDataList) {
        return pieceDataList.stream()
                .map(this::createPiece)
                .toList();
    }

    private Piece createPiece(PieceData pieceData) {
        Team team = Team.valueOf(pieceData.team());
        PieceType pieceType = PieceType.valueOf(pieceData.pieceType());
        return pieceType.create(team);
    }

    private Path gameFilePath(long gameId) {
        return Path.of(SAVE_DIRECTORY).resolve(GAME_FILE_NAME_FORMAT.formatted(gameId));
    }

    private long nextGameId() {
        Path saveDirectory = Path.of(SAVE_DIRECTORY);
        if (Files.notExists(saveDirectory)) {
            return 1L;
        }

        try {
            return Files.list(saveDirectory)
                    .filter(this::isGameFile)
                    .mapToLong(this::extractGameId)
                    .max()
                    .orElse(1L);
        } catch (IOException e) {
            throw new FileException("다음 게임 ID를 생성할 수 없습니다.");
        }
    }

    private boolean isGameFile(Path path) {
        String fileName = path.getFileName().toString();
        if (!fileName.startsWith(FILE_PREFIX) || !fileName.endsWith(FILE_SUFFIX)) {
            return false;
        }

        String numberPart = fileName.substring(FILE_PREFIX.length(), fileName.length() - FILE_SUFFIX.length());
        if (numberPart.isEmpty()) {
            return false;
        }
        return numberPart.chars()
                .allMatch(Character::isDigit);
    }

    private long extractGameId(Path path) {
        String fileName = path.getFileName().toString();
        String id = fileName.substring(FILE_PREFIX.length(), fileName.length() - FILE_SUFFIX.length());

        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new FileException("파일 이름이 잘못되었습니다. (%s-숫자-%s)".formatted(FILE_PREFIX, FILE_SUFFIX));
        }
    }
}
