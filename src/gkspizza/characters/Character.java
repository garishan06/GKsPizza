/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gkspizza.characters;

import gkspizza.Order;
import java.awt.Graphics2D;


/**
 *
 * @author gari9870
 * @date Jan 14, 2024
 * @filename Character
 * @description the parent class for both of the types of characters that appear in GKs pizzeria
 */
public abstract class Character {

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }
    private int xPosition;
    private int yPosition;
   
    private int speed;
   
    final private int HEIGHT;
   
    final private int WIDTH;
   
   
     private Order order;
   
    /**
     *
     * @param x position of the char
     * @param y position of the character
     * @param characterType is the type of character
     * @param speed the speed at which the character moves
     * the constructor of the character
     */
    public Character(int x, int y, int characterType, int speed){
        loadImages();
        setOrder(1,characterType);
       
        this.xPosition = x;
        this.yPosition = y;
       
        //both characters have the same proportions so the HEIGHT and WIDTH would be the same for both
        this.HEIGHT = 150;
        this.WIDTH = 150;
        this.speed = speed;
    }
   
    /**
     *
     * @param g2 "paintbrush" for 2d graphics
     */
    public abstract void draw(Graphics2D g2);

    /**
     *loads the file paths of the images needed
     */
    public abstract void loadImages();
   
    /**
     *move the character up
     */
    public void moveUp() {
        this.yPosition -= this.speed;
    }

    /**
     *move the character down
     */
    public void moveDown() {
        this.yPosition += this.speed;
    }

    /**
     *move the character left
     */
    public void moveLeft() {
        this.xPosition -= this.speed;
    }

    /**
     *move the character right
     */
    public void moveRight() {
        this.xPosition += this.speed;
    }
   
    /**
     *
     * @returns the x position
     */
    public int getX(){
        return this.xPosition;
    }
   
    /**
     *
     * @returns y position
     */
    public int getY(){
        return this.yPosition;
    }

    /**
     *
     * @returns the width
     */
    public int getWidth(){
        return this.WIDTH;
    }

    /**
     *
     * @returns the height
     */
    public int getHeight(){
        return this.HEIGHT;
    }
   
    /**
     *
     * @returns the speed
     */
    public int getSpeed(){
        return this.speed;
    }
   
    /**
     *
     * @param speed the speed which we want to set
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }

    /**
     *
     * @returns the order of the character (npc has type 1, chef has type 2)
     */
    public Order getOrder(){
        return this.order;
    }
   
    /**
     *
     * @param pizzasOrdered number of pizzas ordered
     * @param characterType type of character
     */
    public void setOrder(int pizzasOrdered, int characterType) {
        this.order = new Order(characterType, pizzasOrdered);  
    }
}