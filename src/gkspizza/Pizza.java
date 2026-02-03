package gkspizza;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.ImageIcon;

/**
 *
 * @author darm4080
 * @date Jan 14, 2024
 * @filename Pizza
 * @description: This class is used to make pizzas
 */
public class Pizza {

//pizza characteristic variables
    private boolean hasSauce;
    private boolean isCooked;

//int to check type of pizza
    private int typeOfPizza;
    private final int PIZZACHEESE = 1;
    private final int PIZZAPEP = 2;
    private final int PIZZAPINE = 3;
    private final int PIZZAVEG = 4;
   
//pizza images
    private ImageIcon base;
    private ImageIcon baseWithSauce;
    private ImageIcon uncookedCheese;
    private ImageIcon cookedCheese;
    private ImageIcon uncookedPep;
    private ImageIcon cookedPep;
    private ImageIcon uncookedVeg;
    private ImageIcon cookedVeg;
    private ImageIcon uncookedPine;
    private ImageIcon cookedPine;
   
    private ImageIcon currentImage;

    /**
     *
     * @param pizzaType the type of pizza(cheese, veggie, pep, pine)
     * the constructor of the pizza class
     */
    public Pizza(String pizzaType) {

        loadImages();
        //setting the type of pizza that was a parameter in the method to the actual type of the pizza
        //in int form
        if (pizzaType.equals("blank")) {
            hasSauce = false;
            isCooked = false;
            this.typeOfPizza = 0;
        } else {
            hasSauce = true;
            isCooked = true;
            switch (pizzaType) {

                case "cheese":
                    this.typeOfPizza = PIZZACHEESE;
                    break;
                case "pepperoni":
                    this.typeOfPizza = PIZZAPEP;
                    break;
                case "veggie":
                    this.typeOfPizza = PIZZAVEG;
                    break;
                case "pineapple":
                    this.typeOfPizza = PIZZAPINE;
                    break;
            }

        }

        this.currentImage = base;

    }

    /**
     * loads the file paths of each of the images required for the class
     */
    public void loadImages() {
        this.base = new ImageIcon(this.getClass().getResource("/images/pizza/base.png"));
        this.baseWithSauce = new ImageIcon(this.getClass().getResource("/images/pizza/baseWithSauce.png"));
        this.uncookedCheese = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedCheesePizza.png"));
        this.cookedCheese = new ImageIcon(this.getClass().getResource("/images/pizza/cheezePizza.png"));
        this.uncookedPep = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedPepPizza.png"));
        this.cookedPep = new ImageIcon(this.getClass().getResource("/images/pizza/cookedPepPizza.png"));
        this.uncookedVeg = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedVeggiePizza.png"));
        this.cookedVeg = new ImageIcon(this.getClass().getResource("/images/pizza/cookedVeggiePizza.png"));
        this.uncookedPine = new ImageIcon(this.getClass().getResource("/images/pizza/uncookedPinePizza.png"));
        this.cookedPine = new ImageIcon(this.getClass().getResource("/images/pizza/cookedPinePizza.png"));
    }

    
    /**
     * @return the typeOfPizza as an integer value
     */
    public int getTypeOfPizza() {
        return typeOfPizza;
    }
    
    /**
     *
     * @returns the type of pizza as a string value
     */
    public String getPizzaTypeString() {
        String strType = "";
        switch (this.getTypeOfPizza()) {
            case PIZZACHEESE:
                strType = "CHEESE PIZZA";
                break;
            case PIZZAPEP:
                strType = "PEPPERONI PIZZA";
                break;
            case PIZZAVEG:
                strType = "VEGGIE PIZZA";
                break;
            case PIZZAPINE:
                strType = "PINEAPPLE PIZZA";
                break;

        }

        return strType;
    }

    /**
     * This method sets the type of pizza
     * @param i number corresponding to the type of pizza you want to create
     */
    public void setPizzaType(int i) {
        this.typeOfPizza = i;
    }

    /**
     *
     * @returns if the pizza has sauce
     */
    public boolean getSauceState() {
        return this.hasSauce;
    }

    /**
     *  This method changes the pizza's sauce value. 
     * @param sauced the boolean value that tracks if the pizza has been sauced
     *
     */
    public void setSauceState(boolean sauced) {
        this.hasSauce = sauced;
    }

    /**
     *
     * @return a boolean value to see if the pizza is cooked
     */
    public boolean getCooked() {
        return this.isCooked;
    }

    /**
     * This method changes the pizza's cook status
     * @param cooked represents a boolean which tracks to see if the pizza is cooked
     */
    public void setCooked(boolean cooked) {
        this.isCooked = cooked;
    }

    /**
     *  This method analyzes the pizza's characteristics and displays the appropriate image 
     * @returns the image that the pizza should be based on its pizzaType int
     */
    public ImageIcon getCurrentImage() {
        if (this.getSauceState()) {
            switch (this.getTypeOfPizza()) {
                case PIZZACHEESE:
                    this.currentImage = (getCooked()) ? this.cookedCheese : this.uncookedCheese;
                    break;
                case PIZZAPEP:
                    this.currentImage = (getCooked()) ? this.cookedPep : this.uncookedPep;
                    break;
                case PIZZAVEG:
                    this.currentImage = (getCooked()) ? this.cookedVeg : this.uncookedVeg;
                    break;
                case PIZZAPINE:
                    this.currentImage = (getCooked()) ? this.cookedPine : this.uncookedPine;
                    break;
                default:
                    this.currentImage = this.baseWithSauce;
                    break;
            }

        } else {
            this.currentImage = this.base;
        }

        return this.currentImage;
    }
    
}