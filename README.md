# ip-blacklisting-service
The main use case for our API is to manage IP Range filters. Service provides API to add, delete, list IP filters.

User can also check if given IP is black listed or not.

## Implementations
Service allows user to use CIDR Notation and Mask notation

### POST /ipblacklisting/addCidrFilter
This API is to add new CIDR filter 

    {
      "cidrAddress" : "192.168.0.0/23"
    }
    
where

    cidrAddress - cidr notation for ip range

### POST /ipblacklisting/addMaskFilter
This API is to add new Ip Filter filter 

    {
      "address" : "192.168.0.0",
      "mask" : "255.255.254.0"
    }
    
where

    address - ip address
    mask - mask value
    

### DELETE /ipblacklisting/removeCidrFilter
This API is used to remove new CIDR filter 

    {
      "cidrAddress" : "192.168.0.0/23"
    }
    
where

    cidrAddress - cidr notation for ip range

### DELETE /ipblacklisting/removeMaskFilter
This API can be used to remove new Ip Mask filter 

    {
      "address" : "192.168.0.0",
      "mask" : "255.255.254.0"
    }
    
where

    address - ip address
    mask - mask value
    

### GET /ipblacklisting/isBlacklisted
This API is used to check if the given IP is blacklised or not, API return true or false as response

    /ipblacklisting/isBlacklisted?ip=192.168.0.1
    
where

    ip - IP to be checked for blacklisting
    

### GET /ipblacklisting/allFilters
This API is used to get all the Ip Filter range in the service, it returns a list of Ip Ranges

    /ipblacklisting/allFilters
    
Response

    [
      {
          "notation": "CIDR",
          "cidrAddress": "192.168.0.0/23"
      },
      {
          "notation": "MASK_ADDRESS",
          "address": "192.168.0.0",
          "mask": "255.255.254.0"
      }
    ]
    
where

    ip - IP to be checked for blacklisting
    
## Error codes

Error Response:

    {
       "errorCode": 1001,
       "errorMessage": "Invalid CIDR Notation"
    }

Error codes:

    1001 - Invalid CIDR Notation
    1002 - Invalid Mask Notation
    1003 - Invalid IP Address
    9999 - Internal Server Error
    
## How to start the service

To build the code run below command (You need Java8 and maven installed on your machine)
    
    mvn clean install
    
To start the server (This will run the server on port 8080)
  
    mvn spring-boot:run
    
