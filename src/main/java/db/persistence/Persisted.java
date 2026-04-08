package db.persistence;

public record Persisted<T>(
        T data,
        int id
) {
}
