package queue;

import java.util.List;

public record DelayedQuery(String sql, List<Object> params) {

}
