package database;

import lombok.Getter;

@Getter
/**
 * represents a parameter in a prepared statement
 * @param <T> type of parameter
 */
class StatementParam<T> {

    private T parameter;
    private Class<? extends T> cls;

    StatementParam(T param, Class<? extends T> cls) {

        this.parameter = param;
        this.cls = cls;
    }


}
