package model;

public abstract class BaseEntity {
    private int id;
    private String name;

    public BaseEntity(){
    }

    public BaseEntity(int id, String name){
        this.id = id;
        this.name = name;
    }

    public abstract String getEntityType();
    public abstract String shortInfo();

    public boolean hasValidId(){
        return id > 0;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
