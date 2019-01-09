package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientUI extends House{
    private String name;
    private Socket socket;
    private Reviews reviews = new Reviews();

    private static final int GET_PROPERTY = 123;
    private static final int GET_REVIEW = 124;

    private Scanner scanner = new Scanner(System.in);

    ClientUI(){
    }

    void welcome() throws IOException {
        System.out.println("Hello there what is your name?");
        this.name = scanner.nextLine();
        System.out.println("\nWelcome " + this.name + " to Yad2 real estate!");
        menuOptions();
    }

    private void menuOptions() throws IOException {
        System.out.println("MENU:");
        System.out.println("Please press corresponding number to desired menu item.");
        System.out.println("Add property to market       - 1");
        System.out.println("Search market for a property - 2");
        System.out.println("Add a review                 - 3");
        System.out.println("See all Reviews              - 4");
        System.out.println("Close server and save data   - 5");
        System.out.print("\nPlease enter your option: ");
        System.out.println(" ");
        int num = getNum();
        this.socket = Main.connectToServer();
            switch (num) {
                case 1:
                    addPropertyToMarket();
                    break;
                case 2:
                    getHousesOnMarket(socket.getOutputStream(),socket.getInputStream());
                    break;
                case 3:
                    addReview();
                    break;
                case 4:
                    getReviews(socket.getOutputStream(),socket.getInputStream());
                    break;
                case 5:
                    closeServer();
                    break;
                default:
                    System.out.println("Must choose a viable option\n");
                    Main.disconnectFromServer();
                    menuOptions();
            }

    }

    private void closeServer() throws IOException {
        ClientUI.super.closeServerAndSave(socket.getOutputStream());
    }

    private void addPropertyToMarket() throws IOException {

        System.out.println("What kind of property is it?");
        String type = scanner.nextLine();
        super.setTypeOfHouse(type);

        System.out.println("What is the address?");
        String address = scanner.nextLine();
        super.setAddress(address);

        System.out.println("How many bedrooms does the property have?");
        int bedrooms = getNum();
        super.setAmountOfBedrooms(bedrooms);

        System.out.println("What is the size of the property (square meters)?");
        int squareMeters = getNum();
        super.setSquareMeters(squareMeters);

        System.out.println("What is your listing price?");
        int price = getNum();
        super.setPriceOfProperty(price);

        super.setName(this.name);

        Thread tread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ClientUI.super.write(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        tread.start();
        System.out.println("Your house was uploaded!\n");
        menuOrCloseProgram();
    }

    private void addReview() throws IOException {

        System.out.println("We hope you had a great experience!!!");

        System.out.println("Rate us from 1 - 5!");
        int reviewStars = getNum();
        if(reviewStars <= 0 || reviewStars > 5){
            System.out.println("1 - 5 for rating only!");
            addReview();
        }
        reviews.setReviewStars(reviewStars);

        System.out.println("Please add reivew and click enter when done.");
        String review = scanner.nextLine();
        reviews.setReviewContent(review);

        reviews.setName(this.name);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    reviews.write(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("Review added! Thank you.");
        menuOrCloseProgram();
    }

    private void getReviews(OutputStream outputStream, InputStream inputStream) throws IOException {
        outputStream.write(GET_REVIEW);
        reviews.getAllReviews(inputStream);
        menuOrCloseProgram();
    }

    private void getHousesOnMarket(OutputStream outputStream, InputStream inputStream) throws IOException {
        outputStream.write(GET_PROPERTY);
        readAllProperties(inputStream);
        menuOrCloseProgram();
    }

    private void menuOrCloseProgram() throws IOException {
        System.out.println("\n\nWould you like to go back to the main menu or are you done?");
        System.out.println("press 1 for main menu.");
        System.out.println("press any number if you are done.");
        int option = getNum();
        switch (option){
            case 1:
                Main.disconnectFromServer();
                menuOptions();
                break;
            default:
                System.out.println("Thank you for using Yad2 Real Estate. \nGoodbye!");
                break;
        }
    }

    private int getNum() throws IOException {
        String option = scanner.nextLine();
        int num;
        try {
            num = Integer.valueOf(option);
            return num;
        }catch (Exception e){
            System.out.println("ERROR!! must choose viable option!");
            menuOptions();
        }
        return -1;
    }

}
