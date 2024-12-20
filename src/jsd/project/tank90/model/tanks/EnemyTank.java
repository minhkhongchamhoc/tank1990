package jsd.project.tank90.model.tanks;

import jsd.project.tank90.utils.Direction;

import java.awt.*;
import java.util.Random;

import static jsd.project.tank90.utils.SoundManager.playExplosionSound;

public abstract class EnemyTank extends Tank {

    private static final int FIRE_INTERVAL = 100; // Frames between shots
    private static final int DIRECTION_CHANGE_INTERVAL = 400; // Frames between direction changes
    private static final int POINTS_DISPLAY_DURATION = 1000; // Points display duration (1sec)
    private final boolean isFlashing;  // Enable/disable flashing
    protected Random random = new Random(); // Random generator for movement
    private int fireCooldown; // Cooldown timer for firing bullets
    private int directionChangeCooldown = DIRECTION_CHANGE_INTERVAL; // Timer for direction changes
    private boolean showPoints = false; // Flag to show points when tank is destroyed
    private long pointsDisplayStartTime; // Time when points start displaying
    private boolean flashToggle = false; // Toggle for switching between normal and flashing images
    private int frameCounter = 0;        // Frame counter for toggling effect


    public EnemyTank(int x, int y, int size, Direction direction) {
        super(x, y, size, direction); // Initial position, size, and default direction
        this.fireCooldown = FIRE_INTERVAL;
        isFlashing = random.nextBoolean();
    }
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
        if (toggleImage) { // toggle image, with or without flash
            switch (newDirection) {
                case UP -> tankImage = !flashToggle ? this.getTankUpImage1() : getTankUpFlashImage1();
                case DOWN -> tankImage = !flashToggle ? this.getTankDownImage1() : getTankDownFlashImage1();
                case LEFT -> tankImage = !flashToggle ? this.getTankLeftImage1() : getTankLeftFlashImage1();
                case RIGHT -> tankImage = !flashToggle ? this.getTankRightImage1() : getTankRightFlashImage1();
            }
        } else {
            switch (newDirection) {
                case UP -> tankImage = !flashToggle ? this.getTankUpImage2() : getTankUpFlashImage2();
                case DOWN -> tankImage = !flashToggle ? this.getTankDownImage2() : getTankDownFlashImage2();
                case LEFT -> tankImage = !flashToggle ? this.getTankLeftImage2() : getTankLeftFlashImage2();
                case RIGHT -> tankImage = !flashToggle ? this.getTankRightImage2() : getTankRightFlashImage2();
            }
        }
    }

    // Return a random direction
    public Direction randomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }

    // Change to a random direction
    public void changeDirection() {
        if (directionChangeCooldown <= 0) {
            setDirection(randomDirection());
            directionChangeCooldown = random.nextInt(100, DIRECTION_CHANGE_INTERVAL);
        } else {
            directionChangeCooldown--;
        }
    }

    // Shoot method with cooldown
    @Override
    public Bullet shoot() {
        if (fireCooldown <= 0) {
            fireCooldown = FIRE_INTERVAL; // Reset cooldown
            return super.shoot();//shoot
        }
        updateCooldown();
        return null;
    }


    // Update cooldown for shooting
    public void updateCooldown() {
        if (fireCooldown > 0) {
            fireCooldown--;
        }
    }

    public boolean isFlashing() {
        return isFlashing;
    }
    // Abstract methods for subclasses
    public abstract int getPoints();

    public abstract int getLife();

    public abstract void setLife(int life);

    // Handle damage and death
    public void takeDamage() {
        setLife(getLife() - 1);
        if (getLife() == 0) markAsDead();
    }

    // Check if the tank is dead (i.e., has zero life)
    public boolean isDead() {
        return getLife() == 0;
    }

    public boolean isShowPoints() {
        return showPoints;
    }

    // Mark the tank as dead and start showing points
    public void markAsDead() {
        setLife(0);
        playExplosionSound();// PLay sound for explosion
        showPoints = true;
        pointsDisplayStartTime = System.currentTimeMillis();
        disable();
    }

    // Check if the points display duration has passed and the tank should be removed
    public boolean shouldRemove() {
        return showPoints && (System.currentTimeMillis() - pointsDisplayStartTime > POINTS_DISPLAY_DURATION);
    }

    // Abstract methods for flashing images
    protected abstract Image getTankUpFlashImage1();

    protected abstract Image getTankDownFlashImage1();

    protected abstract Image getTankLeftFlashImage1();

    protected abstract Image getTankRightFlashImage1();
    protected abstract Image getTankUpFlashImage2();

    protected abstract Image getTankDownFlashImage2();

    protected abstract Image getTankLeftFlashImage2();

    protected abstract Image getTankRightFlashImage2();


    @Override
    public void render(Graphics g) {
        // Render the tank and points if dead
        if (showPoints) {
            super.render(g);
            // Render points in white font
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 14));

            // Display points at the center of the tank
            String points = Integer.toString(getPoints());
            g.drawString(points, getCenterX(), getCenterY());
        } else {
            // Toggle flashing images if isFlashing is enabled
            if (isFlashing) {
                // Every few frames, toggle between the normal and flash images
                if (frameCounter % 10 == 0) {
                    flashToggle = !flashToggle;
                }
                frameCounter++;
            }
            // Render the tank image if it's still alive
            g.drawImage(getTankImage(), x, y, size, size, null);
        }
    }
    @Override
    public int getBulletDamage() {
        return 1;// Default bullet damage
    }
}
