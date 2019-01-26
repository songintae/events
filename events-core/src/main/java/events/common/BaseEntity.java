package events.common;

public abstract class BaseEntity {
    private boolean delete = false;

    protected void delete() {
        delete = true;
    }
}
