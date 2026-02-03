/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gkspizza.appliances;

import gkspizza.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 ** @author gari9870
 * @date Jan 17, 2024
 * @filename Appliance
 * @description: This abstract class is used to share the key methods and
 * variables of appliances to its child classes
 *
 */
public abstract class Appliance extends JPanel {

    //object variables
    private final int X;
    private final int Y;
    private final int OBJECTWIDTH;
    private final int OBJECTHEIGHT;

    private final Rectangle bounds;

    //GUI Components
    private GamePanel panGame;
    private InteractFrame frmInteract;

    // interactPanel Variables
    private final int FRAMEHEIGHT;
    private final int FRAMEWIDTH;

    /**
     * This method is a constructor for appliances
     *
     * @param x represents the appliance's x-position
     * @param y represents the appliance's y-position
     * @param width represents the appliance's width
     * @param height represents the appliance's height
     * @param panGame represents the game panel from which the main parts of the
     * game are run
     */
    public Appliance(int x, int y, int width, int height, GamePanel panGame) {
        this.X = x;
        this.Y = y;
        this.OBJECTWIDTH = width;
        this.OBJECTHEIGHT = height;

        this.FRAMEHEIGHT = 820;
        this.FRAMEWIDTH = 800;
        this.bounds = new Rectangle(this.X, this.Y, this.OBJECTWIDTH, this.OBJECTHEIGHT);
        this.panGame = panGame;

        loadImages();

    }

    /**
     *
     * @return the appliance's boundaries as an object in the gamePanel
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * will be called to initiate the interaction between the chef and a
     * gameobject
     */
    public abstract void interact();

    /**
     * This method is used to draw unique interaction screens for each appliance
     *
     * @param g2 a more advanced painting tool used to draw on the
     * interactFrame's JPanel
     */
    public abstract void drawFrame(Graphics2D g2);

    /**
     * This method is used to open a frame and start a thread separate to the
     * main game frame and thread. This sub-thread will be used to run all
     * interaction processes while ensuring the main game does not stop running
     */
    public void openFrame() {
        this.getPanGame().setGameState("INTERACT STATE");
        this.frmInteract = new InteractFrame(this);
        addComponents(this.getFrmInteract());

        this.getFrmInteract().setSize(getFrmHeight(), getFrmWidth());
        this.getFrmInteract().setVisible(true);
        this.getFrmInteract().startThread();
    }

    /**
     * This method will be used to get rid of the InteractFrame and return to
     * the main GameFrame
     */
    public void exitFrame() {
        this.getPanGame().setGameState("PLAY STATE");

        this.getFrmInteract().stopThread();
        this.getFrmInteract().dispose();

    }

    /**
     * This method will be used to update key variables in the interaction
     * process
     */
    public abstract void update();

    /**
     * This method will be used to load any images used/drawn by each child
     * appliance
     */
    public abstract void loadImages();

    /**
     * This method adds new swing components to the interact frame. Instead of
     * making a separate frame for each appliance, we decided to create this
     * method to add buttons and labels specific to each child appliance and
     * their interaction processes.
     *
     * @param frame represents the interactFrame that the components will get
     * added to
     */
    public abstract void addComponents(InteractFrame frame);

    /**
     * @return the game panel
     */
    public GamePanel getPanGame() {
        return panGame;
    }

    /**
     * @return the InteractFrame
     */
    public InteractFrame getFrmInteract() {
        return frmInteract;
    }

    /**
     * @return the FRAMEHEIGHT
     */
    public int getFrmHeight() {
        return FRAMEHEIGHT;
    }

    /**
     * @return the FRAMEWIDTH
     */
    public int getFrmWidth() {
        return FRAMEWIDTH;
    }

}
