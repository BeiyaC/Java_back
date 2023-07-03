package app.module;

public class CharacterForm {
    private String name;
    private String type;
    private int id;

    public CharacterForm(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }
}
