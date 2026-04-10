package db.persistence;

public class Persisted<T> {

    private T data;
    private final int id;

    public Persisted(T data, int id) {
        this.data = data;
        this.id = id;
    }

    public void update(T nextData) {
        this.data = nextData;
    }

    public T getData() {
        return data;
    }

    public int getId() {
        return id;
    }
}
