package jsd.project.tank90.model.powerups;

import jsd.project.tank90.model.tanks.PlayerTank;
import jsd.project.tank90.ui.GamePanel;
import jsd.project.tank90.utils.Images;

import java.awt.*;

public class StarPowerUp extends PowerUp {
    private static final Image STAR_IMAGE = Images.STAR_PU;

    public StarPowerUp(int x, int y, int size) {
        super(x, y, size);
    }

    @Override
    protected Image getImage() {
        return STAR_IMAGE;
    }

    @Override
    public void activate(PlayerTank playerTank, GamePanel gamePanel) {
        super.activate(playerTank, gamePanel);
        playerTank.claimStar();
    }
}
