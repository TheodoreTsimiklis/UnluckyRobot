/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Theodore Tsimiklis
 */
public class PROJECT1 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        int totalScore = 300;
        int itrCount = 0;
        int reward = 0;
        int x = 0;
        int y = 0; 
        // input
        do {
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            itrCount++;
            if (doesExceed(x, y, direction) == true) {
                System.out.println("Exceeded boundary, -2000 damage applied");
                totalScore -= 2000;
            }
            else {
                switch (direction) {
                    case 'u':
                        y++;
                        totalScore -= 10;
                        break;
                    case 'd':
                        y--;
                        totalScore -= 50;
                        break;
                    case 'r':
                        x++;
                        totalScore -= 50;
                        break;
                    case 'l':
                        x--;
                        totalScore -= 50;
                        break;
                }
            }
            reward = reward();
            reward = punishOrMercy(direction, reward);
            totalScore += reward;
            System.out.printf("\n\n");
        } while (!(isGameOver(x, y, totalScore, itrCount)));
        if (isGameOver(x, y, totalScore, itrCount) == true)
            evaluation(totalScore);
    } 
    /**
     * To report the current x and y position of the robot, the total
     * score, and the
     * number of iterations in the console
     * @param x the x coordinate (position)
     * @param y the y coordinate (position)
     * @param itrCount the number of iterations made
     * @param totalScore the score the robot has in total
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.printf("At point (X= %d, Y= %d) at %d iterations, "
                + "the current score is: %d\n", x, y, itrCount, totalScore);
                      
    }
    /**
     * To receive the coordinates together with the direction that the user entered
     * @param x the x coordinate (position)
     * @param y the y coordinate (position
     * @param direction the direction the user inputs (u, d , l, r)
     * @return true if the robot exceeds the grid limits and false otherwise 
     */
    public static boolean doesExceed(int x, int y, char direction) {
        return (y > 4 && Character.toLowerCase(direction) == 'u' || x < 0 &&
                Character.toLowerCase(direction) == 'l' || y < 0 &&
                Character.toLowerCase(direction) == 'd' || x > 4 &&
                Character.toLowerCase(direction) == 'r');
                
    }
    /**
     * To be called when the robot makes a move and ends up in the in of the cells
     * once it get calls this method will roll a dice
     * @return the reward or punishment of entering that cell and the dice will
     * give a number based on the reward
     */
    public static int reward() {
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        
        switch (dice) {
            case 1: 
                System.out.println("Dice 1, reward: -100");
                return -100;
            case 2: 
                System.out.println("Dice 2, reward: -200");
                return -200;
            case 3: 
                System.out.println("Dice 3, reward: -300");
                return -300;
            case 4: 
                System.out.println("Dice 4, reward: 300");
                return 300;
            case 5: 
                System.out.println("Dice 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice: 6, reward: 600");
                
        }
        return reward();
    }
    /**
     * To help the robot in case its faced with a lot of damage in the current step  
     * only when the reward is negative and the direction is Up.
     * @param direction the direction the user input
     * @param reward gives reward to robot
     * @return the the message that removes(mercy) or the message that nothing 
     * changes(Punish)
     */
    public static int punishOrMercy(char direction, int reward) {
        if (reward < 0 && direction == 'u') {
            Random rand = new Random();
            int coin = rand.nextInt(2);
            if (coin == 0) {
                System.out.println("Coin: tail | Mercy, the negative reward is "
                        + "removed.");
                return 0;
            }
            else {
                System.out.println("Coin: head | No Mercy, the negative reward is "
                        + "applied.");
                return reward;
            }
        }
        return reward;
    }
    /**
     * To bring a string to title case.
     * @param str the string
     * @return the string that is title cased.
     */
    public static String toTitleCase(String str) {
        char d = str.charAt(0);
        String string1 = str.substring(1, str.indexOf(' ') + 1);
        String string2 = str.substring(str.indexOf(' '), str.indexOf(' ') + 2);
        char d1 = string2.charAt(1);
        String string3 = str.substring(str.indexOf(' ') + 2, str.length());
        String string4 = Character.toTitleCase(d) + string1.toLowerCase() +
                Character.toTitleCase(d1) + string3.toLowerCase();
        return string4;
    }
    /**
     * To print a message showing if you win or not.
     * @param totalScore the score 
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter your name (only two words): ");
        String name = console.nextLine();
        if (totalScore >= 2000)
            System.out.println("Victory! " + toTitleCase(name) +
                    ", your score is " + totalScore);
        else 
            System.out.println("Mission failed! " + toTitleCase(name) +
                    ", your score is " + totalScore); 
    }
    /**
     * To ask the user to input a direction
     * @return the given direction with the message
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        char cOut = ' ';
        do {
            System.out.print("Please input a valid direction: ");
            char testChar = console.nextLine().toLowerCase().charAt(0);
            if (testChar == 'u' || testChar == 'd' || testChar == 'l' ||
                    testChar == 'r') {
                cOut = testChar;
            }
        }   while (cOut == ' ');
        return cOut;     
    }
    /**
     * To check if the game is over 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param totalScore the end score that the robot has
     * @param itrCount the number of turns/moves the robot did
     * @return true if game is over else return false
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
        return itrCount > 20 || totalScore < -1000 || totalScore > 2000 ||
                x == 4 && y == 4 || y == 0;       
    }
}