import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class ImageHandler {
    private String filePath; 
    private int height;
    private int width;
    private int[][] imageArray;
    private BufferedImage image;

    public ImageHandler(String filePath){
        this.filePath = filePath;
        try {
            // Read the image file
            this.image = ImageIO.read(new File(filePath));

            // Get image dimensions
            this.width = image.getWidth();
            this.height = image.getHeight();

            // Create a 2D array to store grayscale values
            this.imageArray = new int[height][width];

            // Convert the image to grayscale and store pixel data
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int gray = (rgb >> 16 & 0xFF) * 299 / 1000 + (rgb >> 8 & 0xFF) * 587 / 1000 + (rgb & 0xFF) * 114 / 1000;
                    this.imageArray[y][x] = gray;
                }
            }

            System.out.println("Image size: " + width + "x" + height);
        } catch (IOException e) {
            System.out.println("Error reading the image file: " + e.getMessage());
        }
    }

    // Method to convert the image to ASCII art
    public String naiveConvert(int grayscaleType) {
        final String standardGrayScaleASCII = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
        final String shorterGrayScaleASCII = " .:-=+*#%@";

        // Select the grayscale ASCII string based on type
        String grayScaleASCII = (grayscaleType == 1) ? shorterGrayScaleASCII : standardGrayScaleASCII;

        // Create a StringBuilder to store ASCII result
        StringBuilder asciiArt = new StringBuilder();

        // Find max and min values in the grayscale image array for normalization
        int maxValue = 0;
        int minValue = 255;
        for (int[] row : imageArray) {
            for (int pixel : row) {
                if (pixel > maxValue) maxValue = pixel;
                if (pixel < minValue) minValue = pixel;
            }
        }

        // Convert each pixel to an ASCII character based on brightness
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = imageArray[y][x];
                int index = (grayScaleASCII.length() - 1) * (gray - minValue) / (maxValue - minValue);
                asciiArt.append(grayScaleASCII.charAt(index));
            }
            asciiArt.append('\n'); // Newline after each row
        }

        return asciiArt.toString();
    }

        // Function to convert the image to ASCII using normal threads
        public String convertToASCIIWithThreads(int numThreads, int grayscaleType) throws InterruptedException, ExecutionException {
            final String standardGrayScaleASCII = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
            final String shorterGrayScaleASCII = " .:-=+*#%@";
            String grayScaleASCII = (grayscaleType == 1) ? shorterGrayScaleASCII : standardGrayScaleASCII;
    
            StringBuilder asciiArt = new StringBuilder();
            List<Future<String>> futures = new ArrayList<>();
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    
            int rowsPerThread = height / numThreads;
    
            for (int t = 0; t < numThreads; t++) {
                int startRow = t * rowsPerThread;
                int endRow = (t == numThreads - 1) ? height : startRow + rowsPerThread;
    
                // Each thread processes a chunk of rows
                futures.add(executor.submit(() -> {
                    StringBuilder asciiSegment = new StringBuilder();
                    for (int y = startRow; y < endRow; y++) {
                        for (int x = 0; x < width; x++) {
                            int gray = imageArray[y][x];
                            int index = (grayScaleASCII.length() - 1) * gray / 255;
                            asciiSegment.append(grayScaleASCII.charAt(index));
                        }
                        asciiSegment.append('\n');
                    }
                    return asciiSegment.toString();
                }));
            }
    
            for (Future<String> future : futures) {
                asciiArt.append(future.get());
            }
    
            executor.shutdown();
            return asciiArt.toString();
        }
    
        // Function to convert the image to ASCII using virtual threads (Project Loom)
        public String convertToASCIIWithVirtualThreads(int numThreads, int grayscaleType) throws InterruptedException, ExecutionException {
            final String standardGrayScaleASCII = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ";
            final String shorterGrayScaleASCII = " .:-=+*#%@";
            String grayScaleASCII = (grayscaleType == 1) ? shorterGrayScaleASCII : standardGrayScaleASCII;
    
            StringBuilder asciiArt = new StringBuilder();
            List<Future<String>> futures = new ArrayList<>();
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    
            int rowsPerThread = height / numThreads;
    
            for (int t = 0; t < numThreads; t++) {
                int startRow = t * rowsPerThread;
                int endRow = (t == numThreads - 1) ? height : startRow + rowsPerThread;
    
                // Each virtual thread processes a chunk of rows
                futures.add(executor.submit(() -> {
                    StringBuilder asciiSegment = new StringBuilder();
                    for (int y = startRow; y < endRow; y++) {
                        for (int x = 0; x < width; x++) {
                            int gray = imageArray[y][x];
                            int index = (grayScaleASCII.length() - 1) * gray / 255;
                            asciiSegment.append(grayScaleASCII.charAt(index));
                        }
                        asciiSegment.append('\n');
                    }
                    return asciiSegment.toString();
                }));
            }
    
            for (Future<String> future : futures) {
                asciiArt.append(future.get());
            }
    
            executor.shutdown();
            return asciiArt.toString();
        }

        public int getHeight(){
            return this.height;
        }
        public int getWidth() {
            return this.width;
        }
    }

