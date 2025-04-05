import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.awt.color.*;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class Image_Extract {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the path of the book cover image: ");
        String imagePath = sc.nextLine();

        String themeColor = "#000000";   // Default theme color

        try{
            BufferedImage bookCover = ImageIO.read(new File(imagePath));

            if(bookCover == null){
                System.out.println("Invalid image file. Please check the file path and try again.");
            } else{
                System.out.println("Book cover image loaded successfully.");

                themeColor = getDominantColor(bookCover);
                System.out.println("Dominant color of the book cover image: " + themeColor);
        } 
    }  catch(IOException e){
        System.out.println("An error occurred while loading the image. Please check the file path and try again.");
    }

    System.out.println("Enter Book Title: ");
    String bookTitle = sc.nextLine();

    System.out.println("Enter sort description");
    String shortDescription = sc.nextLine();

    String output = "Book Title: " + bookTitle + "\n" + "Short Description: " + shortDescription + "\n" + "Theme Color: " + themeColor;

    String outputHtml = "<!html>" +
                            "<head><title>Book Summary</title></head>" +
                            "<body style='font-family: Arial, sans-serif; text-align: center; margin: 50px;'>" +
                            "<h1 style='color:#000000; font-size: 28px; font-weight: bold;'>" + bookTitle + "</h1>" +
                            "<p style='color:" + themeColor + "; font-size: 18px; font-weight: bold;'>" + shortDescription + "</p>" +
                            "<p style='font-size: 16px; font-weight: bold;'>Extracted Theme Color: <span style='color:" + themeColor + ";'>" + themeColor + "</span></p>" +
                            "</body></!html>";



    try(FileWriter writer = new FileWriter("book_summary.html")){
        writer.write(outputHtml);
        System.out.println("Book summary saved to 'book_summary.html' file.");
    } catch(IOException e){
        System.out.println("An error occurred while saving the book summary.");
    }

    sc.close();
    }

    private static String getDominantColor(BufferedImage image){
        Map<Integer, Integer> colorCount = new HashMap<>();

        int width = image.getWidth();
        int height = image.getHeight();

        for(int x = 0;x < width;x++){
            for(int y = 0;y<height;y++){
                int rgb = image.getRGB(x, y) & 0xFFFFFF; // Ignore alpha (transparency)
                colorCount.put(rgb, colorCount.getOrDefault(rgb, 0) + 1);
            }
        }

        int dominantRGB = 0;
        int maxCount = 0;

        for(Map.Entry<Integer, Integer> entry : colorCount.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                dominantRGB = entry.getKey();
            }
        }

        return String.format("#%06X", dominantRGB);
    }
}


