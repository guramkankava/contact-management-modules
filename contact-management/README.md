This project provides REST API for contact management, see ContactController or swagger at
http://localhost:8081/contactmanagement/swagger-ui/index.html

user can see own contacts at HTTP:GET http://localhost:8081/contactmanagement/contacts?page=0&size=10

with optional query parameters to apply filter, see ContactRequest.java

Contact ownership is controlled by field rowOwner on entity see Contact.java

Authorization is provided by second module see Authorization server;

This project is so called Resource server, for authorization is used OAuth authorization_code flow:

steps 1 authorize the user
from browser use the link 
http://localhost:8080/oauth2/authorize?response_type=code&client_id=contactManager&scope=openid&redirect_uri=http://localhost:8081/authorized
username: bill, password: password
after redirect take value of url's query parameter code

step 2 authorize the client

from HTTP client for example "Postman"
issue HTTP:POST on link http://localhost:8080/oauth2/token?client_id=contacManager&redirect_uri=http://localhost:8081/authorized&grant_type=authorization_code&code={code from step one}
with authorization "basic auth" username:contactManager password:secret
use access_token field value as a token, swagger is configured to accept i, from postman authorization bearer token.

Application needs MySQL DB running, on start up it creates contactmanagement schema and inserts contacts see ContactEntryInitializer.java
Connection string is in application.properties file