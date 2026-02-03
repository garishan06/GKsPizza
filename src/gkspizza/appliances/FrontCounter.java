/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gkspizza.appliances;

import gkspizza.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 ** @author gari9870
 * @date Jan 14, 2024
 * @filename FrontCounter
 * @description: This class handles all interactions between the chef and the
 * customer. It is also used to take orders and keep track of orders as well.
 */
public class FrontCounter extends Appliance {

//GUI Components
    private JButton takeOrderBtn;
    private JButton serveOrderBtn;

    private JLabel newOrderLbl;
    private JLabel orderDetailsLbl;
    private JLabel orderStatusLbl;
    private JLabel scoreLbl;

//keeps track of score
    private int score;

    //transaction variables
    private int transactionType;
    private final int NOTRANSACTION = 0;
    private final int GIVINGORDER = -1;
    private final int TAKINGORDER = 1;

    //NEW
    private String chefOrderProgress;

    //background images
    private BufferedImage yesCust;
    private BufferedImage noCust;

    /**
     * Constructor for the front counter class
     *
     * @param x represents the appliance's x-position
     * @param y represents the appliance's y-position
     * @param width represents the appliance's width;
     * @param height represents the appliance's height
     * @param panGame // represents the game panel from which the game is run
     */
    public FrontCounter(int x, int y, int width, int height, GamePanel panGame) {
        super(x, y, width, height, panGame);
        this.score = 0;
    }

    /**
     * opens the InteractionFrame initiates the interaction between the chef and
     * the Front Counter's UI
     */
    @Override
    public void interact() {
        this.transactionType = NOTRANSACTION;

        this.chefOrderProgress = this.getPanGame().getChef().getOrder().getOrderStatus();

        this.openFrame();
    }

    /**
     *
     * @return the InteractionFrame's background image
     */
    public BufferedImage getCurrentImage() {
        if (!this.getPanGame().getCustomers().isEmpty()) {
            if (this.getPanGame().getCustomers().get(0).getOrderState() == 2) {
                return this.yesCust;
            }
        }
        return this.noCust;

    }

    /**
     * This method is used to draw the UI for the front counter interaction
     * process
     *
     * @param g2 represents an advanced painting tool
     */
    @Override
    public void drawFrame(Graphics2D g2) {
        g2.drawImage(getCurrentImage(), 0, 0, getCurrentImage().getWidth(), getCurrentImage().getHeight(), null);
        updateText();

    }
 
     /**
     * NEW This method is used to take and serve orders
     *
     */
    private void orderTransaction() {
        if (getCurrentImage() == yesCust) {
            switch (this.transactionType) {

                case TAKINGORDER: //this function will run if the button clicked is the "take order" button. In this scenario, the chef will be able to start making a new pizza
                    if (this.chefOrderProgress.equals("ORDER NOT TAKEN")) {
                        this.getPanGame().getChef().setOrder(this.getPanGame().getCustomers().get(0).getOrder().getPizzasOrdered(), 2);
                        this.getPanGame().getChef().getOrder().setOrderStatus("ORDER TAKEN");
                    }
                    break;
                case GIVINGORDER: //this function will run if the button clicked is the "serve order" button. In this scenario, the chef will be able to serve their created order
                    if (this.chefOrderProgress.equals("READY")) {
                        this.getPanGame().getCustomers().get(0).claimOrder(this.getPanGame().getChef().getOrder());
                        this.setScore(this.getPanGame().getCustomers().get(0).addScore()); // use for scoring purposes

                        this.getPanGame().getChef().setOrder(1, 2); // resets chef's template
                    }
                    break;

            }
        }
    }
    /**
     * This method is used to give the customer the order created by the chef.
     * It also adjusts the score based on comparisons made between the
     * customer's expected and received orders
     */

    /**
     * Updates the front counter's UI's labels
     */
    private void updateText() {
        if (getCurrentImage() == yesCust) {
            this.orderDetailsLbl.setText(this.getPanGame().getCustomers().get(0).getOrder().getPizza(0).getPizzaTypeString());
            this.orderStatusLbl.setText("STATUS: " + this.getPanGame().getChef().getOrder().getOrderStatus());
        } else {
            this.orderDetailsLbl.setText("NO ORDER AVAILABLE");
            this.orderStatusLbl.setText("STATUS: NO ORDER AVAILABLE");
        }

        this.scoreLbl.setText("SCORE: " + this.getScore());
    }

    /**
     * This method loads the front counter UI's background images
     */
    @Override
    public void loadImages() {
        try {

            this.yesCust = ImageIO.read(getClass().getResourceAsStream("/images/screens/orderWithCustomerScreen.png"));
            this.noCust = ImageIO.read(getClass().getResourceAsStream("/images/screens/orderWithoutCustomerScreen.png"));
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to serve or take new orders depending on which of the
     * Front Counter's buttons were pressed in the InteractFrame
     */
    @Override
    public void update() {
        this.orderTransaction();
    }

    /**
     * This method is used to add new swing components relevant to the Front
     * Counter's interaction process to the InteractFrame
     *
     * @param frame represents the Interact frame components are getting added
     * to
     */
    @Override
    public void addComponents(InteractFrame frame) {
        this.newOrderLbl = new JLabel();
        this.orderDetailsLbl = new JLabel();
        this.takeOrderBtn = new JButton();
        this.orderStatusLbl = new JLabel();
        this.serveOrderBtn = new JButton();
        this.scoreLbl = new JLabel();

        frame.getPanDraw().add(this.newOrderLbl);
        frame.getPanDraw().add(this.orderDetailsLbl);
        frame.getPanDraw().add(this.takeOrderBtn);
        frame.getPanDraw().add(this.orderStatusLbl);
        frame.getPanDraw().add(this.serveOrderBtn);
        frame.getPanDraw().add(this.scoreLbl);

        this.newOrderLbl.setFont(new java.awt.Font("Rockwell Condensed", 0, 36)); // NOI18N
        this.newOrderLbl.setText("ORDER:");
        this.newOrderLbl.setForeground(Color.white);
        this.newOrderLbl.setBounds(120, 600, 200, 50);

        this.orderDetailsLbl.setBounds(60, 670, 200, 50);
        this.orderDetailsLbl.setFont(new java.awt.Font("Rockwell Condensed", 0, 24));
        this.orderDetailsLbl.setText("NO ORDER AVAILABLE");
        this.orderDetailsLbl.setForeground(Color.white);

        this.scoreLbl.setBounds(360, 560, 400, 50);
        this.scoreLbl.setFont(new java.awt.Font("Rockwell Condensed", 0, 36));
        this.scoreLbl.setText("SCORE: " + this.score);
        this.scoreLbl.setForeground(Color.white);

        this.takeOrderBtn.setBounds(450, 650, 150, 50);
        this.takeOrderBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 30));
        this.takeOrderBtn.setText("TAKE ORDER");
        this.takeOrderBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.takeOrderBtn.addActionListener(new java.awt.event.ActionListener() { //adds action listener so that smth happens when the button is clicked

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeOrderBtnActionPerformed(evt);
            }

            private void takeOrderBtnActionPerformed(ActionEvent evt) { // this method is called when the button is pressed, update the pizza's characteristics in this method
                setTransactionType(TAKINGORDER);
                update();

            }
        });

        this.serveOrderBtn.setBounds(450, 700, 150, 50);
        this.serveOrderBtn.setFont(new java.awt.Font("Rockwell Condensed", 0, 30));
        this.serveOrderBtn.setText("SERVE ORDER");
        this.serveOrderBtn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, false));
        this.serveOrderBtn.addActionListener(new java.awt.event.ActionListener() { //adds action listener so that smth happens when the button is clicked

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serveOrderBtnActionPerformed(evt);
            }

            private void serveOrderBtnActionPerformed(ActionEvent evt) { // this method is called when the button is pressed, update the pizza's characteristics in this method
                setTransactionType(GIVINGORDER);
                update();
            }
        });

        this.orderStatusLbl.setFont(new java.awt.Font("Rockwell Condensed", 0, 20)); // NOI18N
        this.orderStatusLbl.setText("STATUS: NO ORDER AVAILABLE");
        this.orderStatusLbl.setBounds(60, 710, 300, 50);
        this.orderStatusLbl.setForeground(Color.white);

        this.newOrderLbl.setVisible(true);
        this.takeOrderBtn.setVisible(true);
        this.orderStatusLbl.setVisible(true);
        this.orderDetailsLbl.setVisible(true);
        this.serveOrderBtn.setVisible(true);
        this.scoreLbl.setVisible(true);

        this.getFrmInteract().getStationLbl().setText("FRONT COUNTER");
        this.getFrmInteract().getStationLbl().setForeground(Color.white);

        this.getFrmInteract().pack();

    }

    /**
     * @return the score
     */
    private int getScore() {
        return score;
    }

    /**
     * This method changes the score value
     *
     * @param scoreChange represents the amount by which the score should be
     * increased/decreased
     */
    private void setScore(int scoreChange) {
        this.score += scoreChange;
    }

    /**
     * This method is used to change the type of transaction is taking place
     *
     * @param type represents the type of transaction
     */
    private void setTransactionType(int type) {
        this.transactionType = type;
    }

}
