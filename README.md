# How to use
1) Clone repository
   ```git clone https://github.com/keksmd/CsvReader.git```
3) Open csvReader catalog
   ```cd csvReader```
4) Start service
   ```docker-compose up -d```
5) Add file to running container
    ```docker ps```
   (find id of container csvReader)
   mount your_file.csv in container
    ```docker cp your_file.txt your_container_id:/your_file.csv```
6) Start reading file by http-request, also you may configure size of uploading chunk and number of threads
  ```
curl -G "http://localhost:8080/csv/reader" \
  --data-urlencode "filePath=your_file.csv" \
    --data-urlencode "chunkSize=1" \
    --data-urlencode "threadCount=1"

