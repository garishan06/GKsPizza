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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author gari9870
 * @date Jan 14, 2024
 * @filename SaucingStation
 * @description: This class allows users to add toppings to pizzas in a more user friendly and entertaining manner
 */
public class ToppingStation extends Appliance {
    

    //GUI Components
    private ButtonGroup pizzaTypesGrp;
    private JRadioButton pineappleBtn;
    private JRadioButton cheeseBtn;
    private JRadioButton pepperoniBtn;
    private JRadioButton veggieBtn;

    private JButton finalizeChoiceBtn;

    //IMAGES
    private BufferedImage imgScreen;

    private ImageIcon baseWithSauce;
    private ImageIcon uncookedCheese;
    private ImageIcon uncookedPep;
    private ImageIcon uncookedVeg;
    private ImageIcon uncookedPine;

//important interaction objects and variables
    private int previewChoice;

    private final int NOTOPPING = 0;
    private final int PIZZACHEESE = 1;
    private final int PIZZAPEP = 2;
    private final int PIZZAPINE = 3;
    private final int PIZZAVEG = 4;

    private int orderStage; // keeps track of which part of the pizza creation process the chef is at
 

    private Chef chef;
    private Pizza pizza;

    /**
     * Constructor for the topping station object
     * @param x represents the appliance's x-position
     * @param y represents the appliance's y-position
     * @param width represents the appliance's width;
     * @param height represents the appliance's height
     * @param panGame // represents the game panel from which the game is run
     */
    public ToppingStation(int x, int y, int width, int height, GamePanel panGame) {
        super(x, y, width, height, panGame);
    }

    /**
     *This method opens the InteractionFrame and initiates the interaction between the user and the Topping Station's UI
     */
    @Override
    public void interact() {
        this.chef = this.getPanGame().getChef();

        this.pizza = this.getPanGame().getChef().getOrder().getPizza(0);

        setInitialOrderStage();
      
        this.openFrame();

    }

    /**
     * This method is used to set what type of pizza the chef wants to serve to the customer
     */
    @Override
    public void update() {
        if (getInitialOrderStage() == 3 ) { // if the pizza is not at the topping station stage in its creation process, the user should not be able to add toppings to said pizza
            
            if (this.cheeseBtn.isSelected()) {
                this.pizza.setPizzaType(PIZZACHEESE);
            } else if (this.pepperoniBtn.isSelected()) {
                this.pizza.setPizzaType(PIZZAPEP);
            } else if (this.pineappleBtn.isSelected()) {
                this.pizza.setPizzaType(PIZZAPINE);
            } else if (this.veggieBtn.isSelected()) {
                this.pizza.setPizzaType(PIZZAVEG);
            }

            
            this.chef.getOrder().setOrderStatus("STOVE");
           

        }
    }

    /**
     *
     *This method is used to draw the UI for the topping station's interaction process
     *@param g2 represents an advanced painting tool
     */
    @Override
    public void drawFrame(Graphics2D g2) {
        g2.drawImage(this.imgScreen, 0, 0, this.imgScreen.getWidth(), this.imgScreen.getHeight(), null);

        g2.setColor(Color.white);
        g2.setStroke(new java.awt.BasicStroke(3));
        g2.setFont(new java.awt.Font("Rockwell Condensed", 0, 36));

        switch (this.orderStage) {
            case 1: // you should not be able to add toppings to a pizza you did not take an order for
                g2.drawString("TAKE AN ORDER ", 510, 550);
                g2.drawString("BEFORE ADDING TOPPINGS!!", 460, 600);
                break;

            case 2://you should not be able to add toppings to a pizza that does not have sauce
                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 450, 300, 300, null);
                g2.drawString("ADD SAUCE", 600, 520);
                g2.drawString("TO THE PIZZA", 600, 570);
                g2.drawString("BEFORE ADDING", 600, 620);
                g2.drawString("TOPPINGS!!", 600, 670);
                break;
            case 3:
                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 450, 300, 300, null);
                drawPreviewPizza(g2);
                g2.drawString("PREVIEW", 620, 500);
                g2.drawRect(600, 520, 150, 150);
                break;

            case 4: // if a pizza ready to be cooked or already finished, you should not be able to change its toppings

                g2.drawImage(this.pizza.getCurrentImage().getImage(), 250, 450, 300, 300, null);
               
                    g2.drawString("THIS PIZZA", 600, 520);
                    g2.drawString("ALREADY HAS", 600, 570);
                    g2.drawString("TOPPINGS!!", 600, 620);
        }
    }

    /**
     * This method shows the user a preview of the type of pizza the they want to make
     * @param g2 represents an advanced painting tool
     */
    private void drawPreviewPizza(Graphics2D g2) {
        switch (this.getPreviewChoice()) {
            case NOTOPPING:
                g2.drawImage(this.baseWithSauce.getImage(), 600, 520, 150, 150, null);
                break;
            case PIZZACHEESE:
                g2.drawImage(this.uncookedCheese.getImage(), 600, 520, 150, 150, null);
                break;
            case PIZZAPEP:
                g2.drawImage(this.uncookedPep.getImage(), 600, 520, 150, 150, null);
                break;
            case PIZZAVEG:
                g2.drawImage(this.uncookedVeg.getImage(), 600, 520, 150, 150, null);
                break;
            case PIZZAPINE:
                g2.drawImage(this.uncookedPine.getImage(), 600, 520, 150, 150, null);
                break;

        }
    }

    /**
     *This method loads the saucing station UI's background images
     */
    @Override
    public void loadImages() {
        try {
            this.imgScreen = ImageIO.read(getClass().getResourceAsStream("/images/screens/toppingStationScreen.png"));
            this.baseWithSauce = new ImageIcon(this.getClass().getResource("/images/pizza/baseWithSauce.png"));
            this.uncookedCheese = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedCheesePizza.png"));
            this.uncookedPep = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedPepPizza.png"));
            this.uncookedVeg = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedVeggiePizza.png"));
            this.uncookedPine = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedPinePizza.png"));
        } catch (IOException e) {

        }

    }

    /**
     *This method is used to add new swing components relevant to the Topping Station's interaction process to the InteractFrame 
     * @param frame represents the Interact frame components are getting added to
     */
    @Override
    public void addComponents(InteractFrame frame) {

        this.pizzaTypesGrp = new ButtonGroup();

        this.pepperoniBtn = new JRadioButton();
        this.veggieBtn = new JRadioButton();
        this.cheeseBtn = new JRadioButton();
        this.pineappleBtn = new JRadioButton();

        this.finalizeChoiceBtn = new JButton();

        this.pizzaTypesGrp.add(this.pepperoniBtn);
        this.pizzaTypesGrp.add(this.veggieBtn);
        this.pizzaTypesGrp.add(this.cheeseBtn);
        this.pizzaTypesGrp.add(this.pineappleBtn);

        this.getFrmInteract().getPanDraw().add(this.pepperoniBtn);
        this.getFrmInteract().getPanDraw().add(this.veggieBtn);
        this.getFrmInteract().getPanDraw().add(this.cheeseBtn);
        this.getFrmInteract().getPanDraw().add(this.pineappleBtn);
        this.getFrmInteract().getPanDraw().add(this.finalizeChoiceBtn);

        this.pepperoniBtn.setBounds(10, 500, 200, 50);
        this.veggieBtn.setBounds(10, 550, 200, 50);
        this.pineappleBtn.setBounds(10, 600, 200, 50);
        this.cheeseBtn.setBounds(10, 650, 200, 50);
        this.finalizeChoiceBtn.setBounds(10, 700, 200, 50);

        this.pepperoniBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));
        this.veggieBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));
        this.pineappleBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));
        this.cheeseBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));
        this.finalizeChoiceBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));

        this.pepperoniBtn.setBorderPainted(true);
        this.veggieBtn.setBorderPainted(true);
        this.pineappleBtn.setBorderPainted(true);
        this.cheeseBtn.setBorderPainted(true);

        this.pepperoniBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.veggieBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.pineappleBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.cheeseBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.finalizeChoiceBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));

        this.pepperoniBtn.setText("PEPPERONI PIZZA");
        this.veggieBtn.setText("VEGGIE PIZZA");
        this.pineappleBtn.setText("PINEAPPLE PIZZA");
        this.cheeseBtn.setText("CHEESE PIZZA");
        this.finalizeChoiceBtn.setText("CHOOSE PIZZA TYPE");

        this.pepperoniBtn.addActionListener(new java.awt.event.ActionListener() { 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pepActionPerformed(evt);
            }

            private void pepActionPerformed(ActionEvent evt) { // this method is called when the pepperoni pizza radio button is pressed,  
                if (getInitialOrderStage() == 3) {
                    setPreviewChoice(PIZZAPEP);
                }
            }
        });

        this.veggieBtn.addActionListener(new java.awt.event.ActionListener() { 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veggieActionPerformed(evt);
            }

            private void veggieActionPerformed(ActionEvent evt) {  // this method is called when the veggie pizza radio button is pressed, 
                if (getInitialOrderStage() == 3) {
                    setPreviewChoice(PIZZAVEG);
                }
            }
        });

        this.pineappleBtn.addActionListener(new java.awt.event.ActionListener() { 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pineActionPerformed(evt);
            }

            private void pineActionPerformed(ActionEvent evt) { // this method is called when the pineapple pizza radio button is pressed,  
                if (getInitialOrderStage() == 3) {
                    setPreviewChoice(PIZZAPINE);
                }
            }
        });

        this.cheeseBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cheeseActionPerformed(evt);
            }

            private void cheeseActionPerformed(ActionEvent evt) { // this method is called when the cheese pizza radio button is pressed, 
                if (getInitialOrderStage() == 3) {
                    setPreviewChoice(PIZZACHEESE);
                }
            }
        });

        this.finalizeChoiceBtn.addActionListener(new java.awt.event.ActionListener() { 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizePizzaActionPerformed(evt);
            }

            private void finalizePizzaActionPerformed(ActionEvent evt) { // this method is called when the finalize choice button is pressed

                update();
            }
        });

        this.pepperoniBtn.setVisible(true);
        this.veggieBtn.setVisible(true);
        this.pineappleBtn.setVisible(true);
        this.cheeseBtn.setVisible(true);
        this.finalizeChoiceBtn.setVisible(true);

        this.getFrmInteract().getStationLbl().setForeground(Color.white);
        this.getFrmInteract().getStationLbl().setText("TOPPING STATION");

        this.getFrmInteract().pack();

    }

//GETTERS AND SETTERS
    /**
     * @return the previewChoice
     */
    public int getPreviewChoice() {
        return previewChoice;
    }

    /**
     *  This method is used to change what type of pizza is being previewed
     * @param choice represents the type of pizza that will be previewed
     */
    public void setPreviewChoice(int choice) {
        this.previewChoice = choice;
    }

    /**
     *
     * @return the stage of the pizza creation process the chef is at.
     */
    public int getInitialOrderStage() {
        return this.orderStage;
    }

    /**
     *  This method is used to set the stage of the order creation process the chef is at when the InteractFrame is opened
     */
    public void setInitialOrderStage() {
        switch (this.chef.getOrder().getOrderStatus()) { // This interaction's variables would be vary in value based on these four groups of stages

            case "ORDER NOT TAKEN":
                this.orderStage = 1;
                this.setPreviewChoice(-1);
                break;

            case "ORDER TAKEN", "SAUCING STATION":
                this.orderStage = 2;
                this.setPreviewChoice(-1);
                break;
            case "TOPPING STATION":
                this.orderStage = 3;
                this.setPreviewChoice(NOTOPPING);
                break;
            case "STOVE", "READY":
                this.orderStage = 4;
                this.setPreviewChoice(-1);
                break;
        }
    }

   
}
