# EventAPI

A small project for learning how to implement REST endpoints in Spring Boot/Java. Includes Docker files for running on most environments.

## Usage

1. Download or clone the [EventAPI repository](https://github.com/ColorfulMulberry/EventAPI/archive/refs/heads/master.zip) and unzip the files.

2. Have [Docker Desktop](https://www.docker.com/get-started/) open and running on your computer.

3. Open the main folder in your terminal containing the `Dockerfile` and `compose.yaml`.

4. Run `docker-compose up` in the terminal to create the image.

5. Once the image has been created successfully, navigate to the `Images` section of Docker Desktop.

6. Run the image to start up the microservice, it will be named `eventapi-server` or `eventapi-master-server`.

7. Access the API endpoints using `http://localhost:8080/[method]?[parameter]=[value]&[parameter2]=[value]`. [Postman](https://www.postman.com/downloads/) is recommended for this in order to specify HTTP methods other than GET.

## API Endpoints

### retrieveEvent

Retrieves an event from the list of events given a valid event id. Returns the event's details in JSON format.

Format:

`http://localhost:8080/retrieveEvent?id=[eventID]`

Where `eventID` is a positive integer representing a valid event id.

**The HTTP method specified must be GET.**

Will return code 404 NOT_FOUND if an event with the given id could not be found.

Example:

```
> http://localhost:8080/retrieveEvent?id=3

< id:	3
< name:	"Neighbour Birthday"
< description:	"Attend neighbour's birthday party."
< date:	"2025-04-03"
```

### addEvent

Adds an event to the list of events given an event name and valid date. Returns the newly created event's details in JSON format.

Format:

`http://localhost:8080/addEvent?name=[eventName]&date=[YYYY-MM-DD]&desc=[eventDescription]`

Where the parameters can be in any order, and the `desc` parameter is optional.

**The HTTP method specified must be POST.**

Will return code 400 BAD_REQUEST if the date is incorrectly formatted (does not follow YYYY-MM-DD format or is an invalid calendar date).

Example:

```
> http://localhost:8080/addEvent?date=2029-03-21&name=Moving to new home

< "id": 4
< "name": "Moving to new home"
< "description": "No Description"
< "date": "2029-03-21"
```

### updateEvent

Updates an event given a valid event id, and a description, name, or valid date. Returns the updated event's details in JSON format.

Format:

`http://localhost:8080/updateEvent?id=[eventID]&name=[eventName]&date=[YYYY-MM-DD]&desc=[eventDescription]`

Where the parameters can be in any order, and one of `desc`, `name`, or `date` must be included.

**The HTTP method specified must be PUT.**

Will return code 400 BAD_REQUEST if the date is incorrectly formatted (does not follow YYYY-MM-DD format or is an invalid calendar date), an invalid `eventID` was included, or none of `desc`, `name`, or `date` were included.

Example:

```
> http://localhost:8080/updateEvent?id=1&name=New Event Name&desc=New Description&date=1000-01-01

< "id": 1
< "name": "New Event Name"
< "description": "New Description"
< "date": "1000-01-01"
```

### removeEvent

Removes an event from the event list given a valid event id. Returns an empty body.

Format:

`http://localhost:8080/removeEvent?id=[eventID]`

Where eventID is a positive integer representing a valid event id.

**The HTTP method specified must be DELETE.**

Will return code 404 NOT_FOUND if the event id was invalid. Returns a response with status code 204 NO_CONTENT if the removal was successful.

Example:

```
> http://localhost:8080/removeEvent?id=2

< 
```
