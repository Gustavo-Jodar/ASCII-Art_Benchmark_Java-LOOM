# ASCII-Art_Benchmark_Java-LOOM

This small project benchmarks the performance of image-to-ASCII conversion in Java, comparing three threading models:
- **No Thread** (single-threaded execution)
- **Normal Threads** (traditional thread pools)
- **Virtual Threads** introduced with **Project Loom**

The image processing portion of an existing project was re-implemented to focus on concurrency and performance efficiency, especially in handling CPU-bound tasks.

## Project Overview
This benchmark aims to measure and compare:
- **Execution Time**: Average time taken per image for each threading model.
- **Throughput**: Number of images processed per second across different concurrency levels.

## Threading Models Tested
1. **No Thread**: Runs the conversion on a single thread (baseline).
2. **Normal Threads**: Uses Java’s standard thread pools to parallelize processing.
3. **Virtual Threads**: Utilizes virtual threads from Project Loom for lightweight, scalable concurrency.

## Benchmark Parameters
- **Images Processed**: 100 (640 x 418) images per test.
- **Concurrency Levels**: Various thread counts (1, 2, 4, 5, 6, 7, 8, 16, 32).
- **Metrics Collected**:
  - **Average Time per Image** (converted to milliseconds for reporting)
  - **Throughput** (images processed per second)

## Results
![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXd1QB7uwRVdWDsDYjhvA4-voek2GOOOEBtOIwrv3gOER_7MwrfeKo3RjfDRS0MOB8wOwGQeJwtehiJdRGxuKCf9lOgN29_B01YkXUGAzafCeEdurlYXb_Jus6vlwkHBYOWKpb8CsQ?key=2F4sCWfk5OAxXT1ffoXXncpv "Points scored")

![](https://lh7-rt.googleusercontent.com/docsz/AD_4nXeq4Y2OmVOsoCobrj7VwbEgEvHxQ9K7wkRAyDry9dDhclRqDxRu78ZZ7xgSOuoKqB5WUH395Za5FIAlryyj9b65h6zJU6A3Pik7KskIHKpvnnpp5_96HBNWKlMhlJpt7y4ESTfH?key=2F4sCWfk5OAxXT1ffoXXncpv "Points scored")


## How to Run the Project
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/Gustavo-Jodar/ASCII-Art_Benchmark_Java-LOOM
    cd ASCII-Art_Benchmark_Java-LOOM
    ```

2. **Set Up the Environment**:
   Ensure Java is installed, with support for Project Loom if using virtual threads.

3. **Run the Benchmark**:
   Run the main application to initiate the benchmark and view results.
   
## Repository
[GitHub Repository](https://github.com/Gustavo-Jodar/ASCII-Art_Benchmark_Java-LOOM)

## License
This project is licensed under the MIT License.
