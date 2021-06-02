package freelancer;

public final class Evidence {
    private final Sort sort;
    private Object id;
    private String name;
    private String description;

    public enum Sort {
        Profile,
        Item,
        Hearsay,
    }

    public Evidence(Object id, Sort sort, String name, String description) {
        this.id = id;
        this.sort = sort;
        this.name = name;
        this.description = description;
    }

    public Sort getSort() {
        return sort;
    }

    public Object getID() {
        return id;
    }

    public void setID(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
