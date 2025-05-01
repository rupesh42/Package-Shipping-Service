# Package-Shipping-Service
Service used to send packages to certain addresses.

### Requirement

### Functional Requirements:
The API needs to be able to do the following:
- List available receivers.  
  _For the sake of simplicity, you can hard-code a list of available users and their address details in your application._  

- Submit Package for sending using the following parameters:
  - Name of the package for future reference.
  - Weight of the package to be sent in grams.
  - Employee ID of the receiver.
  - Employee ID of the sender.
  
- List all the package-details for a sender using the following parameters:
  - Employee ID of the sender.
  - Optional status-type.

- List the details of an individual package.
  - Date of registration.
  - Package status.
  - Date of receipt (when status is DELIVERED)
  

## Build and Run
 
	 mvn clean install
	 
## Code Quality Factors that are taken care:

- Total 77% code coverage by Junit
- Using SonarLint all "Major" issues are resolved. 	 
- No external library from untrusted source dependency is used.
- Code smell is as low as possible

## SWAGGER URL:
	http://localhost:8081/swagger-ui/index.html  