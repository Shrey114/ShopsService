# ShopsService
Commit

REST API URI endpoint
1) Find Nearest shop -> http://localhost:8080/shops?page=0,size=10,latitude=18.5,longitude=73.70
2) Add shop -> http://localhost:8080/shops

I have added support for Swagger API docs
Swagger UI -> http://localhost:8080/swagger-ui.html#!/shop-mgmt/ 

a) I have used gradle.
b) I have also used in memory database.
c) I have used testng and mockito frameworks for tests.
d) I have added custom exceptions.
e) I have also added controller advice to handle exceptions from controller at global level.

POST JSON request:- 
{
 "shopName": "Abcd",
 "shopAddress": {
  "shopNumber": "22",
  "completeAddress": "Mother's Kitchen, Vishal Nagar, Pimpri-Chinchwad, Maharashtra",
  "postCode": "411038"
 }
} 
