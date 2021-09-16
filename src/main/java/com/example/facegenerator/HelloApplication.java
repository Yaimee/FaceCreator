package com.example.facegenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

public class HelloApplication extends Application {

    static int width = 600;
    static int height = 600;

    static Canvas canvas = new Canvas(width, height);
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static boolean run = false;
    static String amount;
    static String gender;
    static int number;
    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();
    static boolean go = true;
    static boolean limited = false;



    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        stage.setTitle("Face");
        stage.setScene(scene);

        gc.clearRect(0, 0, width, height);
        root.getChildren().add(canvas);
        System.out.println("Welcome to the random face generator! This program will generate male, female or neutral gender faces.");
        String str = selectionOfGenderAndAmountOfFaces();
        System.out.println("Your system has been set to generate " + str + " faces." );
        stage.show();
        //her eksekveres generering af ansigter kontinuerligt, ud fra det skal som er blevet valgt
        if (!limited) {
            drawFacesOverTime(2147483647);

        } else if (amount.equals("r")) {
            int r = rand.nextInt(200);
            System.out.println(r + " faces will be generated!");
            drawFacesOverTime(r -1);
        } else {
            drawFacesOverTime(number -1);
        }


    }

    public static  void drawFacesOverTime(int cycles) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            gc.clearRect(0, 0, width, height);
            drawFace();
        }));
        //antallet af rutiner ses i setCycleCount
        timeline.setCycleCount(cycles);
        timeline.play();
    }

    public static void drawFace() {
        //her bruges et random tal (0-24) som overføres til ansigtsdelenes funktioner, til at generere ansigter med tilfældige træk
        int random = rand.nextInt(24);
        drawShape(random);
        drawEyes(random);
        drawNose(random, gender);
        drawMouth(random);
    }

    public static void drawShape(int random) {
        int rand = 1 + random / 8;
        //her skabes ansigtsforme, alt efter hvilket køn der er valgt
        if ( gender.equals("han")) {
            gc.setFill(Color.rgb(37*rand, 29*rand,22*rand));
            gc.fillRoundRect(150,150,300,300,20,20);
        } else if (gender.equals("hun")) {
            gc.setFill(Color.rgb(37*rand, 29*rand,22*rand));
            gc.fillOval(150, 150, 300, 300);
        } else if (gender.equals("hen")) {
            if (random % 2 == 0) {
                gc.setFill(Color.rgb(37*rand, 29*rand,22*rand));
                gc.fillRoundRect(150,150,300,300,20,20);
            } else {
                gc.setFill(Color.rgb(37*rand, 29*rand,22*rand));
                gc.fillOval(150, 150, 300, 300);
            }
        }
    }

    public static void drawNose(int random, String gender) {
        if (gender.equals("hun")) {
            gc.strokeLine(300, 290, 300, 300 + random/3);
        } else if (gender.equals("han")) {
            gc.strokeOval(290, 290, 20 , 30+random/2);
        } else {
            if (random % 2 == 0) {
                gc.strokeLine(300, 290, 300, 300 + random/3);
            } else {
                gc.strokeOval(290, 290, 20 , 30+random/2);
            }
        }
    }

    public static void drawMouth(/*int xPosition, int yPosition,*/ int mouthSize) {
        gc.setFill(Color.DARKRED);
        gc.fillOval(280,370,50+mouthSize*(3/2),25+mouthSize);
    }

    public static void drawEyes(int random) {
        gc.setFill(Color.WHITE);
        gc.fillOval(220,220,20 + random,20);
        gc.fillOval(360,220,20 + random,20);
        gc.setFill(Color.BLACK);
        gc.fillOval(225,225,5+ random/4,5+random/4);
        gc.fillOval(365,225,5+ random/4,5+random/4);
    }

    public static String selectionOfGenderAndAmountOfFaces() {
        do {
            run = false;
            System.out.print("How many faces do you want to generate? (Enter \"i\" for generating face for 136 years/\"r\" for random number of faces(random in the range 0-200)/\"number\" of your choice for \"number\" of generated faces: ");
            amount = scanner.nextLine();
            System.out.print("Which gender would you like to generate? (Enter \"han\" for male/\"hun\" for female or /\"hen\" for neutral gender): ");
            gender = scanner.nextLine();
            String temporaryGender = genderSelection(gender);
            if (amount.equals("i") && (gender.equals("han") || gender.equals("hun") || gender.equals("hen"))) {
                System.out.println("Infinite " + temporaryGender + " faces will be generated!");
                drawFace();
            } else if (amount.equals("r") && (gender.equals("han") || gender.equals("hun") || gender.equals("hen"))) {
                System.out.println("A random amount of" + temporaryGender + " faces will be generated!");
                limited = true;
                drawFace();
            } else if ((gender.equals("han") || gender.equals("hun") || gender.equals("hen"))) {
                try {
                    number = Integer.parseInt(amount);
                    if (number == 1) {
                        System.out.println(number + " " + temporaryGender + " face will be generated!");
                    } else {
                        System.out.println(number + " " + temporaryGender + " faces will be generated!");
                    }
                    limited = true;
                    drawFace();
                } catch (Exception e) {
                    System.out.println("Not a valid input for amount of faces! Please try again!");
                    run = true;
                }
            } else {
                System.out.println("Not a valid gender!");
                run = true;
            }
        } while (run);

        return "" + gender;
    }

    public static String genderSelection (String gender) {
        if (gender.equals("han")) {
            return "male";
        } else if (gender.equals("hun")) {
            return "female";
        } else {
            return "neutral gender";
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

