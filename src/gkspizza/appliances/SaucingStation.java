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
 * @author gari9870
 * @date Jan 14, 2024
 * @filename SaucingStation
 * @description: This class allows users to add sauce to their pizzas in a more user friendly and entertaining manner
 */
public class SaucingStation extends Appliance {

    //GUI components
    private JButton addSauceButton;

    //background images
    private BufferedImage imgScreen;

    //game Objects and variables
    private Pizza currentPizza;

    private Chef chef;

    private boolean sauceAdded;

    /**
     * Constructor for the saucing station object
     * @param x represents the appliance's x-position
     * @param y represents the appliance's y-position
     * @param width represents the appliance's width;
     * @param height represents the appliance's height
     * @param panGame // represents the game panel from which the game is run
     */
    public SaucingStation(int x, int y, int width, int height, GamePanel panGame) {
        super(x, y, width, height, panGame);

        this.sauceAdded = false;

    }

    /**
     * This method opens the InteractionFrame and initiates the interaction between the user and the Saucing Station's UI
     */
    @Override
    public void interact() {
        this.chef = this.getPanGame().getChef();

        this.currentPizza = this.chef.getOrder().getPizza(0);

        if (this.chef.getOrder().getOrderStatus().equals("ORDER TAKEN")) { // ensures that you have taken an order and that there is a pizza to be created
            this.chef.getOrder().setOrderStatus("SAUCING STATION");
        }

        this.setSauceAdded(this.chef.getOrder().getPizza(0).getSauceState());
        this.openFrame();
    }

    /**
     * This method is used to draw the UI for the saucing station's interaction process
     *  @param g2 represents an advanced painting tool
     */
    @Override
    public void drawFrame(Graphics2D g2) {
        g2.drawImage(this.imgScreen, 0, 0, this.imgScreen.getWidth(), this.imgScreen.getHeight(), null);

        if (this.chef.getOrder().getOrderStatus().equals("ORDER NOT TAKEN")) { // if no order was taken, you should not see any pizza dough at the saucing station

            g2.setFont((new java.awt.Font("Rockwell Condensed", 0, 36)));
            g2.setColor(Color.white);

            g2.drawString("TAKE AN ORDER TO START", 30, 100);
            g2.drawString("THE PIZZA MAKING PROCESS!!", 20, 150);
        } else { // any other order state would have at the least, the base of the pizza

            g2.drawImage(this.currentPizza.getCurrentImage().getImage(), 70, 400, 300, 300, null);
            if (this.isSauceAdded()) { // any order state after the saucing station should already have sauce and therefore, you should not be able to add more sauce to the pizza
                g2.setFont((new java.awt.Font("Rockwell Condensed", 0, 36)));
                g2.setColor(Color.white);

                g2.drawString("YOU CANNOT ADD", 80, 100);
                g2.drawString("MORE SAUCE TO THIS PIZZA!!", 30, 150);
            }
        }
    }

    /**
     *This method is called to add sauce to a pizza
     */
    @Override
    public void update() { 

        if (this.chef.getOrder().getOrderStatus().equals("SAUCING STATION")) { // if the user is not at this stage or is past this stage, they should not be able to make much use of the saucing station's UI
            this.setSauceAdded(true);
            this.currentPizza.setSauceState(true);
            this.chef.getOrder().setOrderStatus("TOPPING STATION");
        }
    }

    /**
     *This method loads the saucing station UI's background images
     */
    @Override
    public void loadImages() {
        try {
            this.imgScreen = ImageIO.read(getClass().getResourceAsStream("/images/screens/saucingStationScreen.png"));
        } catch (IOException e) {
            System.out.println("Image Not Found");
        }
    }

    /**
     *This method is used to add new swing components relevant to the Saucing Station's interaction process to the InteractFrame 
     * @param frame represents the Interact frame components are getting added to
     */
    @Override
    public void addComponents(InteractFrame frame) {
        //button to cook pizzas
        this.addSauceButton = new JButton();

        frame.getPanDraw().add(this.addSauceButton);

        //button initialization
        this.addSauceButton.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        this.addSauceButton.setBounds(600, 650, 150, 50);
        this.addSauceButton.setBackground(Color.YELLOW);
        this.addSauceButton.setText("ADD SAUCE");
        this.addSauceButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));

        this.addSauceButton.addActionListener(new java.awt.event.ActionListener() { //adds action listener so that smth happens when the button is clicked
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSauceButtonActionPerformed(evt);
            }

            private void addSauceButtonActionPerformed(ActionEvent evt) { // this method is called when the button is pressed, update the Pizza's characteristics in this method
                update();
            }
        });

        this.addSauceButton.setVisible(true);

        this.getFrmInteract().getStationLbl().setForeground(Color.white);
        this.getFrmInteract().getStationLbl().setText("SAUCING STATION");

        this.getFrmInteract().pack();
    }

    /**
     * @return sauceAdded, the variable which tracks whether or not the addSauceButton added sauce to a pizza
     */
    private boolean isSauceAdded() {
        return sauceAdded;
    }

    /**
     * Used to change the value of sauceAdded
     * @param sauceAdded represents a boolean which tells you if sauce was added to the pizza in the station
     */
    private void setSauceAdded(boolean sauceAdded) {
        this.sauceAdded = sauceAdded;
    }

}
