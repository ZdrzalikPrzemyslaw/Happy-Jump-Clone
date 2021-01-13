package tech.szymanskazdrzalik.weather_game.game.entities;

// FIXME: 13.01.2021
public class SnowballEntityTurnable extends SnowballEntity {
    public SnowballEntityTurnable(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public SnowballEntityTurnable(int xPos, int yPos, HostileEntityDirections directions) {
        super(xPos, yPos, directions);
    }

    @Override
    public void changeXPos(int delta) {
        super.changeXPos(delta);
        this.setMovedAmount(this.getMovedAmount() + delta);
        if (Math.abs(this.getMovedAmount()) >= 200) {
            HostileEntityDirections directions = this.getDirections() == HostileEntityDirections.LEFT ? HostileEntityDirections.RIGHT : HostileEntityDirections.LEFT;
            this.setDirections(directions);
        }
    }
}
