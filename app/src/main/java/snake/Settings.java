package snake;

public class Settings {

    private Double speed;
    private boolean wallsOn;
    private boolean warpsOn;
    private boolean bombsOn;

    public Settings() {
        this.speed = (double) 20;
        this.wallsOn = true;
        this.warpsOn = true;
        this.bombsOn = true;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public boolean isWallsOn() {
        return wallsOn;
    }

    public void setWallsOn(boolean wallsOn) {
        this.wallsOn = wallsOn;
    }

    public boolean isWarpsOn() {
        return warpsOn;
    }

    public void setWarpsOn(boolean warpsOn) {
        this.warpsOn = warpsOn;
    }

    public boolean isBombsOn() {
        return bombsOn;
    }

    public void setBombsOn(boolean bombsOn) {
        this.bombsOn = bombsOn;
    }

}
