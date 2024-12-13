# How to use
1) Clone repository
   ```git clone https://github.com/keksmd/CsvReader.git```
3) Open csvReader catalog
   ```cd CsvReader```
4) Start service (you need 8080 port opened)
   ```docker-compose up -d```
5) Mount your_file.csv into container
    ```docker cp path/to/your/file/your_file.csv csvreader-csv-reader-1:/your_file.csv```
6) Start reading file by http-request, also you may configure size of uploading chunk and number of threads
  ```
curl -G "http://localhost:8080/csv/reader" \
  --data-urlencode "filePath=your_file.csv" \
    --data-urlencode "chunkSize=1" \
    --data-urlencode "threadCount=1"

