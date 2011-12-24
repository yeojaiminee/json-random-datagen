# A Java-based JSON Data Generation Tool with Java Faker

json-data-generator is a lightweight and easy-to-use Java library designed to streamline the process of generating synthetic data for testing. It allows you to define a JSON template specifying the structure of the desired data, and it uses Java Faker to populate the fields with realistic and randomized values.

### Usage
Create a JSON template file specifying the structure and desired data types for each field. This uses Java Faker library class names and method names as reference.

Sample input
```json
{
  "jobTitleName": "Job.title",
  "firstName": "Name.firstName",
  "lastName": "Name.lastName",
  "company": "Company.name",
  "dateOfBirth": "DateAndTime.birthday",
  "address": {
    "streetAddress": "Address.streetAddress",
    "city": "Address.city",
    "state": "Address.state",
    "country": "Address.country",
    "zipCode": "Address.zipCode"
  },
  "phoneNumber": "PhoneNumber.phoneNumber"
}
```

Sample output
```json
{
  "jobTitleName": "Senior Farming Technician",
  "firstName": "Marion",
  "lastName": "Hegmann",
  "company": "Hodkiewicz, Von and Paucek",
  "dateOfBirth": "1977-08-23T17:00:15.017+00:00",
  "address": {
    "streetAddress": "11156 Rodriguez Inlet",
    "city": "East Rossiehaven",
    "state": "Wisconsin",
    "country": "Argentina",
    "zipCode": "52968"
  },
  "phoneNumber": "(769) 980-4552",
  "emailAddress": "xyzt23@gmail.com"
}
```