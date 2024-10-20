package ab.eclipse.system.setting;

public interface ISetting<T> {
    T getValue();
    Object setValue(T value);

    T getInitValue();

    String getName();
    Object setName(String name);

    String getDescription();
    Object setDescription(String description);

    boolean isVisible();
    Object setVisible(IVisible visible);

    Object build();
}