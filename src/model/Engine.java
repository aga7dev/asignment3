package model;

public class Engine {
    private int id;
    private String engineType;
    private double volume;

    public Engine(int id, String engineType, double volume) {
        this.id = id;
        this.engineType = engineType;
        this.volume = volume;
    }

    public Engine(String engineType, double volume) {
        this(0, engineType, volume);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
}
