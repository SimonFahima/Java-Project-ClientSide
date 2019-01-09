package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class House implements Writable{

    private String name;
    private String address;
    private String typeOfHouse;
    private int amountOfBedrooms;
    private int squareMeters;
    private int priceOfProperty;

    private static final int ADD_PROPERTY = 121;
    private static final int CLOSE_SERVER = 148;

    public House(){

    }

    public void readAllProperties(InputStream inputStream) throws IOException {
        int propertyAmount = inputStream.read();
        for (int i = 0; i < propertyAmount; i++) {
            read(inputStream);
        }
    }

    public void closeServerAndSave(OutputStream outputStream) throws IOException {
        outputStream.write(CLOSE_SERVER);
        System.out.println("Server closed");
        System.out.println("Thank you for using Yad2 Real Estate. \nGoodbye!");
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(ADD_PROPERTY);
        outputStream.write(typeOfHouse.getBytes().length);
        outputStream.write(typeOfHouse.getBytes());

        outputStream.write(address.getBytes().length);
        outputStream.write(address.getBytes());

        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(amountOfBedrooms);
        outputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(squareMeters);
        outputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(priceOfProperty);
        outputStream.write(buffer);

        outputStream.write(name.getBytes().length);
        outputStream.write(name.getBytes());
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";

    //read address
        int messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String address = new String(msgBytes);
        setAddress(address);

    //read type of property
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String propertyType = new String(msgBytes);
        setTypeOfHouse(propertyType);

    //read amount of bedrooms
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int bedrooms = ByteBuffer.wrap(buffer).getInt();
        setAmountOfBedrooms(bedrooms);

    //read square meters
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int sqmt = ByteBuffer.wrap(buffer).getInt();
        setSquareMeters(sqmt);

    //read price
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int price = ByteBuffer.wrap(buffer).getInt();
        setPriceOfProperty(price);

    //read name
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String name = new String(msgBytes);
        setName(name);

        System.out.println(this);
    }

    @Override
    public String toString(){
        return "\nType of property:                   " + this.typeOfHouse +
                "\nAddress:                           " + this.address +
                "\nAmount of bedrooms:                " + this.amountOfBedrooms + " br" +
                "\nSize of property in square meters: " + squareMeters + "m" +
                "\nListing price:                    $" + this.priceOfProperty +
                "\nListed by:                         " + name +
                "\n-----------------------------------------------------------";
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(String typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
    }

    public int getAmountOfBedrooms() {
        return amountOfBedrooms;
    }

    public void setAmountOfBedrooms(int amountOfBedrooms) {
        this.amountOfBedrooms = amountOfBedrooms;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getPriceOfProperty() {
        return priceOfProperty;
    }

    public void setPriceOfProperty(int priceOfProperty) {
        this.priceOfProperty = priceOfProperty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
