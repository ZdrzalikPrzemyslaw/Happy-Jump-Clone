package tech.szymanskazdrzalik.weather_game.game.entities;

// FIXME: 13.01.2021
public class SnowballEntityTurnable extends SnowballEntity {
    private double movedAmount = 0;

    public SnowballEntityTurnable(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public SnowballEntityTurnable(int xPos, int yPos, EntityDirections directions) {
        super(xPos, yPos, directions);
    }

    public double getMovedAmount() {
        return movedAmount;
    }

    public void setMovedAmount(double movedAmount) {
        this.movedAmount = movedAmount;
    }

    @Override
    public void changeXPos(int delta) {
        super.changeXPos(delta);
        this.setMovedAmount(this.getMovedAmount() + delta);
        if (Math.abs(this.getMovedAmount()) >= 200) {
            EntityDirections directions = this.getDirections() == EntityDirections.LEFT ? EntityDirections.RIGHT : EntityDirections.LEFT;
            this.setDirections(directions);
        }
    }
}
