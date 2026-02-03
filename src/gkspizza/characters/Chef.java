/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gkspizza.characters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author gari9870
 * @date Jan 14, 2024
 * @filename Chef
 * @description the class for the controllable character "the chef" which inherits from the character class
 */
public class Chef extends Character {
    //images
   
    private ImageIcon idleImage;
    private ImageIcon walkingImage;

    private ImageIcon currentImage;
    //bools for images
    private boolean isWalking;
    //hitbox dimensions
    private final int HITBOXHEIGHT;
    private final int HITBOXWIDTH;

    /**
     *
     * @param x position of chef
     * @param y position of chef
     */
    public Chef(int x, int y) {
        super(x, y,2,5);

        this.HITBOXHEIGHT = 90;
        this.HITBOXWIDTH = 40;
       
        this.isWalking = false;
       
        this.currentImage = this.idleImage;
       
        setCurrentImage();

    }

    /**
     *
     * @param g2 "paintbrush" for 2d graphics
     * draws the image of the chef on the Game Panel's screen
     */
    @Override
    public void draw(Graphics2D g2) {
        setCurrentImage();
        g2.drawImage(currentImage.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);

    }
   
   
   
    /**
     * @return the isWalking
     */
    public boolean getWalkingState() {
        return isWalking;
    }

    /**
     * @param isWalking the isWalking to set
     */
    public void setWalkingState(boolean isWalking) {
        this.isWalking = isWalking;
    }
   
   
    /**
     *sets the current image based on what the chef is doing (walking or standing)
     */
    public void setCurrentImage() {
        if (getWalkingState()) {
            this.currentImage = this.walkingImage;
        } else if (!getWalkingState()) {
            this.currentImage = this.idleImage;
        }

    }

    /**
     *
     * @returns the current image of the chef
     */
    public ImageIcon getCurrentImage() {
        return this.currentImage;
    }

    /**
     *loads the file paths of the images needed for the chef
     */
    @Override
    public void loadImages() {

        this.idleImage = new ImageIcon(this.getClass().getResource("/images/chef/chefIdle.gif"));
        this.walkingImage = new ImageIcon(this.getClass().getResource("/images/chef/chefWalking.gif"));

    }

    /**
     * This method handles the chef's movement
     * @param direction 1-4 representing the direction the character is moving (up, down, left, right)
     * checks for boundaries before letting the player move
     */
   
    public void move(int direction){
        switch (direction){
            case 1: // left
                if(this.getX() >= -50){
                    moveLeft();
                }
                break;
                case 2: // right
                if(this.getX() <= 360){
                    moveRight();
                }
                break;
                case 3: // down
                if(this.getY() <= 640){
                    moveDown();
                }
                break;
                case 4: // up
                if(this.getY() >= 445){
                    moveUp();
                }
                break;
       
       
       
        }
   
    }


    /**
     *
     * @return the hitbox of the chef
     */
   
    public Rectangle getBounds() {
        return new Rectangle(this.getX() + 50, this.getY() + 30, this.HITBOXWIDTH, this.HITBOXHEIGHT);
    }



}