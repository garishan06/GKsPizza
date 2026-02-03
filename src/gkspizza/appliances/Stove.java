/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gkspizza.appliances;

import gkspizza.characters.Chef;
import gkspizza.Pizza;
import gkspizza.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 *  @author gari9870
 * @date Jan 14, 2024
 * @filename Stove
 * @description: This class allows users to cook pizzas in a more user friendly and entertaining manner
 */
public class Stove extends Appliance {

    //additional components
    private JButton cookButton;

    
    //background image
    private BufferedImage imgScreen;

    //Objects and variables important for the interaction process
    private Chef chef;
    private Pizza pizza;

    private int orderStage;

    private int cookTimer;
    private final int COOKTIMECOUNTER = 600;

    private int cookStatus;
    private final int NOTCOOKED = 1;
    private final int COOKING = 2;
    private final int COOKED = 3;

    /**
     *
     * Constructor for the Stove 
     * @param x represents the appliance's x-position
     * @param y represents the appliance's y-position
     * @param width represents the appliance's width;
     * @param height represents the appliance's height
     * @param panGame // represents the game panel from which the game is run
     */
    public Stove(int x, int y, int width, int height, GamePanel panGame) {
        super(x, y, width, height, panGame);

    }

    /**
     * This method opens the InteractionFrame and initiates the interaction between the user and the Saucing Station's UI
     */
    @Override
    
    public void interact() {
        this.chef = this.getPanGame().getChef();
        this.pizza = this.chef.getOrder().getPizza(0);
        setInitialOrderStage();
        this.cookStatus = NOTCOOKED;
        this.cookTimer = 0;
        this.openFrame();
    }

    /**
     *
     * This method is used to draw the UI for the stove's interaction process
     *  @param g2 represents an advanced painting tool
     */
    @Override
    public void drawFrame(Graphics2D g2) {
        g2.drawImage(this.imgScreen, 0, 0, this.imgScreen.getWidth(), this.imgScreen.getHeight(), null);

        cookPizza(g2);

        g2.setColor(Color.white);
        g2.setFont(new java.awt.Font("Rockwell Condensed", 0, 36));

        switch (getInitialOrderStage()) {

            case 1:
                g2.drawString("THERE IS NO PIZZA TO COOK!!", 200, 395);

                break;

            case 2:
                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 50, 300, 300, null);
                g2.drawString("THIS PIZZA CANNOT BE COOKED YET!!", 200, 395);
                break;

            case 3:
                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 50, 300, 300, null);
                if (this.cookStatus == COOKING) {
                    g2.drawString("DO NOT LEAVE THE STOVE UNATTENDED", 140, 440);
                }
                break;
            case 4:
                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 50, 300, 300, null);

                if (this.cookStatus == NOTCOOKED) {
                    g2.drawString("THIS PIZZA IS ALREADY COOKED ", 200, 395);
                }
                break;

        }

    }

    /**
     * This method is used to visually display the pizza getting cooked
     *  @param g2 represents an advanced painting tool
     */
    public void cookPizza(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(new java.awt.Font("Rockwell Condensed", 0, 36));
        if (this.cookStatus == COOKING) {
            this.cookTimer++;
            g2.drawString("COOKING PIZZA!!", 300, 395);

            if (this.cookTimer >= COOKTIMECOUNTER) {
                this.cookStatus = COOKED;
                this.cookTimer = 0;
            }
        } else if (this.cookStatus == COOKED) {

            g2.drawString("PIZZA COOKED!!", 300, 395);

            this.pizza.setCooked(true);
            this.chef.getOrder().setOrderStatus("READY");
        }

    }

    /**
     * This method is used to update whether or not the stove is cooking a pizza
     */
    @Override
    public void update() { // use this method to show the pizzas getting cooked
        if (getInitialOrderStage() == 3 && this.cookStatus == NOTCOOKED) {
            this.cookStatus = COOKING;

        }
    }

    /**
     *This method loads the stove UI's background images
     */
    @Override
    public void loadImages() {
        try {
            this.imgScreen = ImageIO.read(getClass().getResourceAsStream("/images/screens/stoveScreen.png"));
        } catch (IOException e) {

        }
    }

    /**
     *     *This method is used to add new swing components relevant to the Stove's interaction process to the InteractFrame 
     * @param frame represents the Interact frame components are getting added to
     */
    @Override
    public void addComponents(InteractFrame frame) {
        //button to cook pizzas
        this.cookButton = new JButton();
        frame.getPanDraw().add(this.cookButton);

        this.cookButton.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        this.cookButton.setBounds(650, 400, 100, 50);
        this.cookButton.setForeground(Color.white);
        this.cookButton.setBackground(Color.gray);
        this.cookButton.setText("COOK");
        this.cookButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));

        this.cookButton.addActionListener(new java.awt.event.ActionListener() { //adds action listener so that smth happens when the button is clicked
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cookButtonActionPerformed(evt);
            }

            private void cookButtonActionPerformed(ActionEvent evt) { // this method is called when the button is pressed, update the pizza's characteristics in this method
                update();
            }
        });

        this.cookButton.setVisible(true);

        this.getFrmInteract().getStationLbl().setForeground(Color.white);
        this.getFrmInteract().getStationLbl().setText("STOVE");
        this.getFrmInteract().pack();

    }

    /**
     *
     * @return the stage of the pizza creation process the chef is at.
     */
    private int getInitialOrderStage() {
        return this.orderStage;
    }

    /**
     * This method is used to set the stage of the order creation process the chef is at when the InteractFrame is opened
     */
    private void setInitialOrderStage() {
        switch (this.chef.getOrder().getOrderStatus()) { //variables would be different based on these three stages

            case "ORDER NOT TAKEN": // will occur when the chef interacts with the appliances without taking an order first. This stage prevents them from using the appliance when they do not have a pizza ready to make
                this.orderStage = 1;
                break;

            case "ORDER TAKEN", "SAUCING STATION","TOPPING STATION": //orders in these stages do not meet the requirements to proceed to cooking the pizzas. They are either missing sauce or toppings.
                this.orderStage = 2;

                break;
            case "STOVE": //orders in this stage are ready to be cooked
                this.orderStage = 3;

                break;
            case "READY": // orders in this stage have already been cooked
                this.orderStage = 4;

                break;
        }
    }
}
