#### TODO:
- add TokenExpired Exception , then redirect to login page
- protect with ssl certificate(user pritamprasad.com cert to create new free certs)
- write unit tests
- String -> constants
- basic validations : 422
- add license / contributing guide
- Add country data pooling



#### Done:
- a user with admin role can be registered by including "adminKey" property in SignupRequest.
this value should match to the admin session key generated during app startup. 
(future: implementation should be get the admin key from some auto-updating key 
configuration server, reason: this feature will fail for horizontal scalling)

#### Available features:
-   user signup
-   user login
-   admin signup - using admin key generated while startup
-   admin login
-   token decode for admins
-   token validation  

### Current configs:
- jwt expiration time:
```yaml
auth:
  jwtExpirationMs: 300000 #in milliseconds
```

### Endpoints:
| Secured? | Verb   |   endpoint    | Body          | Roles         |
| -------- | ------ | ------------- | --------------|---------------|
| open     | POST   | /auth/signin  | SignupRequest | *             |
| open     | POST   | /auth/signup  | LoginRequest  | *             |
| open     | POST   | /auth/token   | String        | *             |
| secured  | GET    | /admin/**     | N/A           | ADMIN         |
| secured  | GET    | /state        | N/A           | USER, ADMIN   |
| secured  | GET    | /state/{}     | N/A           | USER, ADMIN   |
| secured  | GET    | /logs         | N/A           | USER, ADMIN   |

### Notes:
- openapi3: http://localhost:8080/v3/api-docs/
- 