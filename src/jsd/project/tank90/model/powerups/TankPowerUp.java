package jsd.project.tank90.model.powerups;

import jsd.project.tank90.model.tanks.PlayerTank;
import jsd.project.tank90.ui.GamePanel;
import jsd.project.tank90.utils.Images;

import java.awt.*;

public class TankPowerUp extends PowerUp {
    private static final Image TANK_IMAGE = Images.TANK_PU;

    public TankPowerUp(int x, int y, int size) {
        super(x, y, size);
    }

    @Override
    protected Image getImage() {
        return TANK_IMAGE;
    }

    @Override
    public void activate(PlayerTank playerTank, GamePanel gamePanel) {
        super.activate(playerTank, gamePanel);
        playerTank.setLife(playerTank.getLife() + 1); // +1 life
    }
}
