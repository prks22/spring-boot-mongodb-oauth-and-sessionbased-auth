# spring-boot-mongodb-oauth-and-sessionbased-auth
This project contains the code for session bases security, oauth (login with facebook), user management, document management, email, spring data, exception handling, spring mvc with spring rest service  and mongodb  with spring boot

How to use:
1. import project and build with maven.
2. install mongodb and edit the application.properties file and create db and collection(users, document_store) in mongodb.
3. Call the signUP api http://localhost:8080/users/signup with json body with username and password
4. Call login api http://localhost:8080/users/login with authorization header of basic, you will get x-auth-token in response header which you have to pass in each request header to access the resource otherwise application will throw 401 error.
5. Call rest of service like get user, update user, upload document etc.

For singIn with facebook.

1. Call url http://localhost:8080/login in browser
2. Click Login with facebook which invoke the http://localhost:8080/login/facebook url and redirect you to facebook login page to authorization of access behalf of application.
3. After granting access on fb, fb redirect you to your redirected url(http://localhost:8080/users/user/facebook ) which you passed in facebook
where you get authorization code, now you using this code to get the access token in UserController oauth() method, now you can access any resource of 
fb using this token. after getting token you set the user info as authentication details in security context of spring and enable the session and write 
x-auth-token for session id.
Obtaining this x-auth-token you can access any resoure like in normal flow of above.

