package h02;

import fopbot.Direction;
import fopbot.Robot;

import h02.frameWork.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
/**
 * Main entry point in executing the program.
 *
 */
public class Main {
    public static final int WORLD_SIZE = 20;
    private static final int RANDOM_NUMBER = (int) (Math.random() * 3);
    private static final WorldType WORLD_TYPE = WorldType.BLOCK_WORLD; // TODO: change WorldType.WALL_WORLD to WorldType.BLOCK_WORLD if you want to test H2.2

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        AbstractWorld world = WorldFactory.createWorld(WORLD_TYPE, WORLD_SIZE);
        world.start();

        if (WORLD_TYPE == WorldType.WALL_WORLD) {
            // TODO: H2.1.1 (student implementation of robot)
            int number_of_coins = (RANDOM_NUMBER == 0) ? 12 : (RANDOM_NUMBER == 1) ? 18 : 27;
            Robot robot = new Robot(0, 0, Direction.RIGHT, number_of_coins);
            int coins_to_deposit = number_of_coins / 3;
            // TODO: H2.1.2 (student implementation for WALL_WORLD)
            for (int u = 0; u < 3; u++) {
                for (int i = 0; i < 9; i++) {
                    robot.move();
                }
                while (!robot.isFrontClear()) {
                    robot.turnLeft();
                    robot.move();
                    robot.turnLeft();
                    robot.turnLeft();
                    robot.turnLeft();
                }
                robot.move();
                robot.turnLeft();
                robot.turnLeft();
                robot.turnLeft();
                while (robot.isFrontClear()) {
                    robot.move();
                }
                robot.turnLeft();
                for (int i = 0; i < 9; i++) {
                    robot.move();
                }
                for (int i = 0; i < coins_to_deposit; i++){
                    robot.putCoin();
                }
                robot.turnLeft();
            }
        }
        if (WORLD_TYPE == WorldType.BLOCK_WORLD) {
            RobotFactory robotFactory = new RobotFactory(world);
            Robot blockRobot = robotFactory.createRobot();
            // TODO: H2.2 (student implementation for BlOCK_WORLD)
            int x = blockRobot.getX();
            int y = blockRobot.getY();
            if(x < 10 && y < 10){
                while(blockRobot.getX() != 0){
                    while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean moved = false;
                        while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); moved = true;
                            while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                        } else {
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()){
                                blockRobot.move(); moved = true;
                                while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                            }
                        }
                        if(moved && blockRobot.isFrontClear()) blockRobot.move();
                    }
                }
                while(blockRobot.getY() != 0){
                    while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean sapmaBasarili = false;
                        while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); sapmaBasarili = true;
                        }
                        if(sapmaBasarili){
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                        }
                    }
                }
                while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                if(blockRobot.getX() == 0 && blockRobot.getY() == 0){
                    blockRobot.putCoin();
                }
            }
            else if (x >= 10 && y < 10) {
                while(blockRobot.getX() != 19){
                    while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean moved = false;
                        while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); moved = true;
                            while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                        } else {
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()){
                                blockRobot.move(); moved = true;
                                while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                            }
                        }
                        if(moved && blockRobot.isFrontClear()) blockRobot.move();
                    }
                }
                while(blockRobot.getY() != 0){
                    while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean sapmaBasarili = false;
                        while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); sapmaBasarili = true;
                        } else if(blockRobot.getY() == 0){
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()){
                                blockRobot.move();
                                while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                                if(blockRobot.isFrontClear()){
                                    blockRobot.move();
                                    while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                                }
                            }
                        }
                        if(sapmaBasarili){
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                        }
                    }
                }
                while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                if(blockRobot.getX() == 19 && blockRobot.getY() == 0){
                    blockRobot.putCoin();
                }
            }
            else if (x < 10 && y >= 10) {
                while(blockRobot.getX() != 0){
                    while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean moved = false;
                        while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); moved = true;
                            while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                        } else {
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()){
                                blockRobot.move(); moved = true;
                                while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                            }
                        }
                        if(moved && blockRobot.isFrontClear()) blockRobot.move();
                    }
                }
                while(blockRobot.getY() != 19){
                    while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean sapmaBasarili = false;
                        while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); sapmaBasarili = true;
                        }
                        if(sapmaBasarili){
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                        }
                    }
                }
                while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                if(blockRobot.getX() == 0 && blockRobot.getY() == 19){
                    blockRobot.putCoin();
                }
            }
            else if (x >= 10 && y >= 10) {
                while(blockRobot.getX() != 19){
                    while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean moved = false;
                        while(!blockRobot.isFacingDown()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); moved = true;
                            while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                        } else {
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()){
                                blockRobot.move(); moved = true;
                                while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                            }
                        }
                        if(moved && blockRobot.isFrontClear()) blockRobot.move();
                    }
                }
                while(blockRobot.getY() != 19){
                    while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                    if(blockRobot.isFrontClear()){
                        blockRobot.move();
                    } else {
                        boolean sapmaBasarili = false;
                        while(!blockRobot.isFacingLeft()) blockRobot.turnLeft();
                        if(blockRobot.isFrontClear()){
                            blockRobot.move(); sapmaBasarili = true;
                        }
                        if(sapmaBasarili){
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                            if(blockRobot.isFrontClear()) blockRobot.move();
                            while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                        }
                    }
                }
                while(!blockRobot.isFacingRight()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                while(!blockRobot.isFacingUp()) blockRobot.turnLeft();
                while(blockRobot.isFrontClear()) blockRobot.move();
                if(blockRobot.getX() == 19 && blockRobot.getY() == 19){
                    blockRobot.putCoin();
                }
            }
        }
    }
}

