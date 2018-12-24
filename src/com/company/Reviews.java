package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Reviews implements Writable{

    private String name;
    private int reviewStars;
    private String reviewContent;

    private static final int ADD_REVIEW = 122;

    public Reviews(){

    }

    public void getAllReviews(InputStream inputStream) throws IOException {
        int reviewsAmount = inputStream.read();
        for (int i = 0; i < reviewsAmount; i++) {
            read(inputStream);
        }
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(ADD_REVIEW);
        outputStream.write(name.getBytes().length);
        outputStream.write(name.getBytes());

        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(reviewStars);
        outputStream.write(buffer);

        outputStream.write(reviewContent.getBytes().length);
        outputStream.write(reviewContent.getBytes());
    }

    public void read(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";

        //read name
        int messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String reviewName = new String(msgBytes);
        setName(reviewName);

        //read rating
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int stars = ByteBuffer.wrap(buffer).getInt();
        setReviewStars(stars);

        //read review
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String review = new String(msgBytes);
        setReviewContent(review);

        System.out.println(this);

    }

    @Override
    public String toString() {
        return "------------------- " + name +
                "\nRated: " + rating(reviewStars) +
                "\n" + reviewContent +
                "\n---------------------------";
    }

    public String rating(int stars){
        String rating = "";
        for (int i = 0; i < stars; i++) {
            rating += " * ";
        }
        return rating;
    }


    public int getReviewStars() {
        return reviewStars;
    }

    public void setReviewStars(int reviewStars) {
        this.reviewStars = reviewStars;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
