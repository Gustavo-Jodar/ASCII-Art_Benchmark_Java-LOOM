import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class App {
    public static int NUM_IMAGES = 100;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Initialize the ImageHandler with a sample image path
        ImageHandler handler = new ImageHandler("src\\big_image.jpg");

        // Define the range of thread counts to test
        int[] threadCounts = {1, 2, 4, 5, 6, 7, 8, 16, 32, 64, 128, 256};
        List<Long> naives = new ArrayList<>();
        List<Long> normals = new ArrayList<>();
        List<Long> virtuals = new ArrayList<>();

        // Perform benchmarking for each number of threads
        for (int numThreads : threadCounts) {
            // Benchmark for the naive method
            long naiveTime = benchmarkNaiveMethod(handler, numThreads);
            naives.add(naiveTime);

            // Benchmark for the normal thread-based method
            long threadsTime = benchmarkWithThreads(handler, numThreads);
            normals.add(threadsTime);

            // Benchmark for the virtual thread-based method
            long virtualThreadsTime = benchmarkWithVirtualThreads(handler, numThreads);
            virtuals.add(virtualThreadsTime);
        }

        // Results for Naive Method (single-thread)
        System.out.println("Results for Naive Method (single-thread):");
        System.out.println("Number of Threads\tAverage Time (ms)\tImages per second");

        for (int i = 0; i < threadCounts.length; i++) {
            int numThreads = threadCounts[i];
            double averageTimeNaiveNs = (double) naives.get(i) / NUM_IMAGES;
            double averageTimeNaiveMs = averageTimeNaiveNs / 1_000_000.0;
            double imagesPerSecondNaive = 1_000_000_000.0 / averageTimeNaiveNs;

            System.out.printf("%d\t\t\t%.2f ms\t\t\t%.2f images/sec%n", 1, averageTimeNaiveMs, imagesPerSecondNaive);
        }

        // Results for Normal Thread Method
        System.out.println("\nResults for Normal Thread Method:");
        System.out.println("Number of Threads\tAverage Time (ms)\tImages per second");

        for (int i = 0; i < threadCounts.length; i++) {
            int numThreads = threadCounts[i];
            double averageTimeNormalNs = (double) normals.get(i) / NUM_IMAGES;
            double averageTimeNormalMs = averageTimeNormalNs / 1_000_000.0;
            double imagesPerSecondNormal = 1_000_000_000.0 / averageTimeNormalNs;

            System.out.printf("%d\t\t\t%.2f ms\t\t\t%.2f images/sec%n", numThreads, averageTimeNormalMs, imagesPerSecondNormal);
        }

        // Results for Virtual Thread Method
        System.out.println("\nResults for Virtual Thread Method:");
        System.out.println("Number of Threads\tAverage Time (ms)\tImages per second");

        for (int i = 0; i < threadCounts.length; i++) {
            int numThreads = threadCounts[i];
            double averageTimeVirtualNs = (double) virtuals.get(i) / NUM_IMAGES;
            double averageTimeVirtualMs = averageTimeVirtualNs / 1_000_000.0;
            double imagesPerSecondVirtual = 1_000_000_000.0 / averageTimeVirtualNs;

            System.out.printf("%d\t\t\t%.2f ms\t\t\t%.2f images/sec%n", numThreads, averageTimeVirtualMs, imagesPerSecondVirtual);
        }
    }

    // Method to benchmark the naive conversion
    public static long benchmarkNaiveMethod(ImageHandler handler, int numThreads) throws InterruptedException, ExecutionException {
        long totalTime = 0;

        // Run 100 executions to get the mean execution time
        for (int i = 0; i < NUM_IMAGES; i++) {
            long startTime = System.nanoTime();
            handler.naiveConvert(1); // Using normal threads
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }

        return totalTime; // Return total time in nanoseconds
    }

    // Method to benchmark the normal threads-based conversion
    public static long benchmarkWithThreads(ImageHandler handler, int numThreads) throws InterruptedException, ExecutionException {
        long totalTime = 0;

        // Run 100 executions to get the mean execution time
        for (int i = 0; i < NUM_IMAGES; i++) {
            long startTime = System.nanoTime();
            handler.convertToASCIIWithThreads(numThreads, 1); // Using normal threads
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }

        return totalTime; // Return total time in nanoseconds
    }

    // Method to benchmark the virtual threads-based conversion
    public static long benchmarkWithVirtualThreads(ImageHandler handler, int numThreads) throws InterruptedException, ExecutionException {
        long totalTime = 0;

        // Run 100 executions to get the mean execution time
        for (int i = 0; i < NUM_IMAGES; i++) {
            long startTime = System.nanoTime();
            handler.convertToASCIIWithVirtualThreads(numThreads, 1); // Using virtual threads
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);
        }

        return totalTime; // Return total time in nanoseconds
    }
}
