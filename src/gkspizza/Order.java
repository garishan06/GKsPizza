package gkspizza;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gari9870
 * @date Jan 14, 2024
 * @filename Order
 * @description This class is used to track orders that customers have placed
 *
 */
import java.util.ArrayList;

public class Order {

    //tells how many pizzas a customer ordered
    private int orderQuantity;
    //arraylist to store instances of Pizzas
    private ArrayList<Pizza> pizzas;

    //string to track the status of an order
    private String orderStatus;
    //the various stages of the pizza making process
    private final String[] STATUSES = {"ORDER NOT TAKEN", "ORDER TAKEN", "SAUCING STATION", "TOPPING STATION", "STOVE", "READY"};

    //the copy of the order depending on who is using it(customer or player)
    private final int CUSTOMERCOPY = 1;
    private final int CHEFCOPY = 2;

    /**
     *
     * @param orderCopy the copy of the order depending on who is using
     * it(customer or player)
     * @param pizzasOrdered //The number of pizzas that are in the customers
     * order this is the constructor for the order class
     */
    public Order(int orderCopy, int pizzasOrdered) {
        this.orderQuantity = pizzasOrdered;
        this.pizzas = new ArrayList();
        this.orderStatus = STATUSES[0];

       addPizzas(pizzasOrdered, orderCopy);

    }

    /**
     * This method is used to add pizzas to the "pizzas" arraylist which stores all food items related to the current order.  
     * @param orderCopy the copy of the order depending on who is using
     * it(customer or player)
     * @param pizzasOrdered //number of pizzas ordered by the npc creates an
     * arrayList of pizzas that the customer should expect to recieve
     */
  
    public void addPizzas(int pizzasOrdered, int orderCopy) {
        switch (orderCopy) {
            case CUSTOMERCOPY: // this generates the pizzas for the customers order
                this.orderQuantity = pizzasOrdered;

                int counter = this.orderQuantity;

                //randomly adds pizzas to an arraylist to keep track of all pizzas apart of the order
                while (counter != 0) {
                    // random pizza type generator
                    int randPizza = (int) (Math.random() * 4);

                    switch (randPizza) { // switch case is used to randomly choose one of the four pizzas the customer wants to order
                        case 0:
                            this.pizzas.add(new Pizza("cheese"));
                            break;
                        case 1:
                            this.pizzas.add(new Pizza("pepperoni"));
                            break;
                        case 2:
                            this.pizzas.add(new Pizza("veggie"));
                            break;
                        case 3:
                            this.pizzas.add(new Pizza("pineapple"));
                            break;

                    }
                    counter--;

                }
                break;
            case CHEFCOPY: // this creates a blank template of a pizza for the chef to work on and serve
                this.orderQuantity = pizzasOrdered;

                for (int x = 0; x < this.orderQuantity; x++) {
                    this.pizzas.add(new Pizza("blank"));
                }

                break;
        }

    }

  

//GETTERS AND SETTERS
    /**
     * gets a specific pizza from an order
     *
     * @param idx index of the pizza from the order
     * @return a pizza from the arrayList of pizzas
     */
    public Pizza getPizza(int idx) {
        return this.pizzas.get(idx);
    }

    /**
     *
     * @returns the amount of pizzas ordered
     */
    public int getPizzasOrdered() {
        return this.orderQuantity;
    }

    /**
     *
     * @returns the status of the order, which tells you where in the food
     * making process the order is currently at.
     */
    public String getOrderStatus() {
        return this.orderStatus;
    }

    /**
     * This method sets a new status for an order. It will typically be called
     * when the user completes an interaction with one of the appliances, as
     * these appliances are vital to the pizza creation process
     *
     * @param status represents the order's new status
     */
    public void setOrderStatus(String status) {

        for (int x = 0; x < STATUSES.length; x++) {
            if (STATUSES[x].equals(status)) {
                this.orderStatus = status;
            }
        }

    }
}
