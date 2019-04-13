# url-shortener
Is a web site which returns a "short URL" from a URL given, and redirects from a "short URL" to its corresponding URL.

## Technologies
1. Play framework v2.7.0
2. ReactiveMongo v0.16.5-play27
3. sbt v0.13
4. MongoDB v3.6.10

## Documentation
Following the principles of the Play Framework the web page is a RESTful API which exposes
services by creating routes. This framework of Scala is a nice option for this type of web pages,
it is powerful, stateless, type safety and built on Akka as some of the nicest things I can mention about
this framework. I chose it due to its documentation and stable community and is a good fit for this kind of project.

ReactiveMongo is an asynchronous and non-blocking API to connect to MongoDB, a No-SQL Document based database.
This API has integration with play framework, easy to use and implement on different Type levels, using an asynchronous
and non-blocking API allows your code to easy scale and adapt to new paradigms as Streams, that why I've chosen it.

### Endpoints
| Method   | Path          | Description               |
| -------- |:------------- | :------------------------ |
| GET      | /             | index web page            |
| POST     | /shorturl     | short id generator        |
| GET      | /:shortId     | redirects to original URL |

### Shortener Algorithm
The real problem I faced on this project was the way to find a nice solution for:
- Generate a unique id (letting out hashing dut known collision issues and long)
- Generate a short id
- Handle collisions in a distributed system
- Retrieve a short URL id for an existent URL with an amortized performance (avoiding generating a new one)

I did a research about the possible solutions to all these requirements/constraints and after some investigation, I chose
to use the ObjectId that MongoDB to generate ids for its documents, first because I've already have installed Mongo and second
du to the algorithm they use. In fact, I went through the algorithm they use to generate those ids and found that
they already handle my problem of "Generate unique ids" they generate a segmented string of alphanumerics divided by:

4-bytes for the timestamp
3-bytes for the machine
2-bytes for the thread
3-bytes for the increment

Taking into account this it is easy to think that is a unique id for all the possible combinations and factors they contemplate
in the id generation. But, the id is still too long (24 characters), how to handle for a short id? well reading more about
this segments, I noticed that the "increment" segment (6 characters) is a random number in 16777216 (which is a number that can be created
in a 32 bit machine in a loop cycle) and that is converted to alphanumeric, so the possible combinations to create a new
"increment" number is approx 16 million over the conversion of that number. Besides, this increment is consecutive and it is
unique per machine, so even though I got the short id I wanted, I needed to think in the distributed system problem.

Looking into the algorithm I noticed that I could combine the "machine" and the "increment" segments to generate an id
that it is longer (12 characters) but, could handle the collisions between machines, so if the server scales to different
machines or containers it would generate a different and unique id.

I've sliced the problem into two options, you can generate the short id by using only the "increment" segment or the
"machine + increment" segments. By default, I let the "increment" segment since for the purposes of this project it is
a good example, but I've implemented the method to handle the collisions, it is a TODO.

For the last constraint, I decided to use the database as a "cache" and I added an "accessed" field to the document model
so that way I could find the least accessed URLs (like an LRU cache) and clean the database, realizing new short ids to
be used. Each time a new request arrives and asks the same URL be shortened the app checks for the "original" field to
verify it already exists if exists retrieves the "shorted".

A No-SQL database has a better performance than a Relational database, even in the auto-increment id in a table, and they
could also scale horizontally, that's another reason why I used MongoDB.

### Database Model
A single document
```
{
    "_id" : ObjectId("5cb16ed1640000af49ff3dfe"),
 	"original" : "https://www.playframework.com/",
 	"shorted" : "ff3dfe",
 	"created" : NumberLong("1555132113511"),
 	"accessed" : NumberLong("1555132113511")
}
```

## How to use it
- You need sbt and mogodb be installed in your machine.
- The database is ```short-url``` and the collection is ```shorturl```.
  - Use ```mongod``` to start mongo server
  - Use ```mongo``` to connect to the mongo server in a terminal
  - Initilize the database with ```use short-url```
- In the project, you can configure your host and port to connect to your mongo
uri in ```/conf/application.conf```.
- Once you have the database, clone the repo, go to your directory use the ```sbt run```
command in terminal to execute the application.
- Use ```http://localhost:9000/``` default host for the application and use the form to
generate to short URL.

In case, you want to use directly the services and connect to the endpoints with postman, curl, etc use the following:

**Generate short id**

POST

http://localhost:9000/shorturl

application/json

{
  "original":"www.playframework.com/"
}


**Redirect short URL**

GET

http://localhost:9000/:shortId

## TODO
- Add tests for the endpoints and mock the database.
- Implement the configuration for a "distributed" environment to
use the short id generator for collisions.
- Update the "accessed" field for the future clean of the database, this will allow us to have URL expiration time.


