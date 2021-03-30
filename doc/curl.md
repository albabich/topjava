curl http://localhost:8080/topjava/rest/meals

curl http://localhost:8080/topjava/rest/meals/100004

curl -X DELETE http://localhost:8080/topjava/rest/meals/100005

curl  "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=00:00:00&endDate=2020-01-30&endTime=16:59:59"

curl -i -X POST -H "Content-Type: application/json" -d "{\"dateTime\":\"2021-03-30T11:10:00\",\"description\":\"newMeal\",\"calories\":1000}"  -v http://localhost:8080/topjava/rest/meals

curl -i -X PUT -H "Content-Type: application/json" -d "{\"id\":100003,\"dateTime\":\"2021-03-30T12:00:00\",\"description\":\"updatedMeal\",\"calories\":1100}" http://localhost:8080/topjava/rest/meals/100003
