# covid-dashboard
A sample dashboard to be build using reactjs/Spring boot

### Design:

#### User Requirement:
1. Users can signup using:
    - mandatory: email, password
    - optional: username, dob, country
    - future: oauth github, google
2. User can login using:
    - mandatory: username/email and password.
3. After login in User can view dashboard
4. After login in User can view profile page.
    - future: User can update user-props like default_state, so that 
5. After login User can logout
6. future: Admin dashboard
    - for root Admin: to manage/add new admins/moderators

#### Features:
- Covid API data puller -- Done
    -  Admin can set covid19india.pastDataInNumberOfDays variable in app.yaml, to specify how many last days data need to be pulled at startup time.
- Signup page
- Login Page
- Profile page
- Dashboard
- GraphQL Endpoint for state/district data- Enhancements


#### Logical Models:
```yaml
User:
    - email : String, notnull
    - password : char[] , notnull
    - username : String , nullable
    - dob : LocalDate, nullable
    - country : Enum Country, nullable
    - created_timestamp : Long, notnull
    - modified_timestamp : Long, notnull
---
Counts:
    - confirmed: Long
    - deceased: Long
    - recovered: Long
    - tested: Long
    - date: LocalDate
---
State:
    - id: int
    - name: String
    - counts: Counts[] 
    - stateCode: String
---
District:
    - id: int
    - name: String
    - counts: Counts[] 
    - stateId: int     
```

#### JWT Contents:
```yaml
JWTToken:
    - sub -> username
    - iss -> issued Date 
    - exp -> auth.jwtExpirationMs in app.yaml
```

##### Notes:
- Covid India: https://www.covid19india.org/ 
- Saved calls for covid19india: https://www.getpostman.com/collections/2afb23eddb8accf94c3e (Import this json in postman)
- 