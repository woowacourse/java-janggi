package infra.entity;

public class GameEntity {
    private final Long id;
    private final String name;
    private final String currentTurn;
    private final Long choFormationId;
    private final Long hanFormationId;

    private GameEntity(Long id, String name, String currentTurn, Long choFormationId, Long hanFormationId) {
        this.id = id;
        this.name = name;
        this.currentTurn = currentTurn;
        this.choFormationId = choFormationId;
        this.hanFormationId = hanFormationId;
    }

    /**
     * 영속화 이전 엔티티 생성시 사용
     */
    public static GameEntity createWithoutId(String name, String currentTurn, Long choFormationId,
                                             Long hanFormationId) {
        return new GameEntity(null, name, currentTurn, choFormationId, hanFormationId);
    }

    /**
     * 영속화 이후 엔티티 로딩시 사용
     */
    public static GameEntity createWithId(Long id, String name, String currentTurn, Long choFormationId,
                                          Long hanFormationId) {
        return new GameEntity(id, name, currentTurn, choFormationId, hanFormationId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public Long getChoFormationId() {
        return choFormationId;
    }

    public Long getHanFormationId() {
        return hanFormationId;
    }
}
