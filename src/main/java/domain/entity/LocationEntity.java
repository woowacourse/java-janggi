package domain.entity;

public class LocationEntity {

    private Long id;

    private int x;

    private int y;

    private Long janggiGameId;

    private Long pieceId;

    public LocationEntity(int x, int y, Long janggiGameId, Long pieceId) {
        this.x = x;
        this.y = y;
        this.janggiGameId = janggiGameId;
        this.pieceId = pieceId;
    }
}
