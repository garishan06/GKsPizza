package gkspizza.characters;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import gkspizza.Order;
import gkspizza.Pizza;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author darm4080
 * @date Jan 14, 2024
 * @filename NPC
 * @description the class that controls the npcs that come into the restaurant to order pizza (inherits from the character class)
 */
public class NPC extends Character {
    //images
    private ImageIcon Image1;
    private ImageIcon Image2;
    private ImageIcon Image3;
    private ImageIcon Image4;
    private ImageIcon currentImage;

    //orderstates
    private int orderingStage;
    private final int ENTER = 1;
    private final int WAIT = 2;
    private final int LEAVE = 3;
   //the order of npc
    private Order givenOrder;

    /**
     *
     * @param x position of the npc
     * @param y position of npc
     * constructor of the npc class
     */
    public NPC(int x, int y) {
       
        super(x, y,1,1);

        setCurrentImage();

        this.orderingStage = ENTER;        
    }

    /**
     *
     * @param g2"paintbrush" of 2d graphics
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(getCurrentImage().getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
    }

    /**
     *sets the current image of the npc randomly(diff shirt colours)
     */
    public void setCurrentImage() {
        Random rand = new Random();
        int rn = rand.nextInt(4);
        switch (rn) {
            case 0:
                this.currentImage = Image1;
                break;
            case 1:
                this.currentImage = Image2;
                break;
            case 2:
                this.currentImage = Image3;
                break;
            case 3:
                this.currentImage = Image4;
                break;
            default:
                break;
        }

    }

    /**
     *
     * @returns the current image of the npc
     */
    public ImageIcon getCurrentImage() {
        return this.currentImage;
    }

    /**
     *loads the file paths for all the variants of npc images
     */
    @Override
    public void loadImages() {
        this.Image1 = new ImageIcon(this.getClass().getResource("/images/customer/npc1.png"));
        this.Image2 = new ImageIcon(this.getClass().getResource("/images/customer/npc2.png"));
        this.Image3 = new ImageIcon(this.getClass().getResource("/images/customer/npc3.png"));
        this.Image4 = new ImageIcon(this.getClass().getResource("/images/customer/npc4.png"));
    }

    /**
     *the move method for the npc based on the state that they are in, they will have different movements
     */
    public void move() {
        if (this.orderingStage == ENTER) {
            if (this.getX() > 459) {
                this.moveLeft();
            } else {
                this.orderingStage = WAIT;
            }
        } else if (this.orderingStage == LEAVE) {
            this.moveRight();

        }

    }

    /**
     *
     * @return the state of the npc order
     */
    public int getOrderState() {
        return this.orderingStage;
    }

    /**
     *
     * @param state the int of the state of the order
     */
    public void setOrderState(int state) {
        this.orderingStage = state;
    }

    /**
     *
     * @param chefOrder the chefs copy of the order
     * passing the chefs order to the npc order and makes the npc leave
     */
    public void claimOrder(Order chefOrder) {
        this.givenOrder = chefOrder;
        this.orderingStage = LEAVE;
    }

    /**
     *
     * @returns a positive or negative 50 based on if the order was correct or not
     */
    public int addScore() {

        Pizza pizza = this.givenOrder.getPizza(0);
        Pizza orderedPizza = this.getOrder().getPizza(0);

        if (pizza.getPizzaTypeString().equals(orderedPizza.getPizzaTypeString())) {
            return 50;
        } else {
            return -50;
        }

    }

   
}