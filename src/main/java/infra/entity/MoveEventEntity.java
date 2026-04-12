package infra.entity;

public class MoveEventEntity {
    private final Long id;
    private final Long gameId;
    private final Long version;
    private final int fromRow;
    private final int fromColumn;
    private final int toRow;
    private final int toColumn;

    public MoveEventEntity(Long id, Long gameId, Long version, int fromRow, int fromColumn, int toRow, int toColumn) {
        this.id = id;
        this.gameId = gameId;
        this.version = version;
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }

    public static MoveEventEntity createWithoutId(Long gameId, Long version, int fromRow, int fromColumn, int toRow, int toColumn) {
        return new MoveEventEntity(null, gameId, version, fromRow, fromColumn, toRow, toColumn);
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getVersion() {
        return version;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToColumn() {
        return toColumn;
    }
}
