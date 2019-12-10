# Betting Engine (monolith version)
## How to run
There is a convenient script you can run called run_betting_engine.sh

It runs the following steps:
1. mvn clean install
    
    Compiles, tests and builds the jar for the engine
2. java -jar target/betting_engine-1.0.jar
    
    Runs the betting engine app which runs a quick demo with some demo data   

## Tools I used
* IntelliJ Community Edition
* Maven
* JUnit

## Design Decisions & Assumptions
1. Reading the problem, I wanted to design the modules for a microservices, even if the code isn't deployed as a microservices initially. 
    
    Because of this, I made the following design decisions:
    -  All the objects won't have other linked objects as private variables. However, the objects will have the ids of objects they're linking to. This helps mantain the boundaries between the modules more clearly.

        For example, a Bet object would have an outcomeId and not an outcome object in it

    - This also means that I have to avoid an inheritance structure
    
    - Each of the module's apps would only require their requisite data and not other modules' apps.
    
        In a microservices deployment they would just integrate with the other mictoservice. To navigate around this for the monolith, I used a DependencyInjector to manage the depency inversion in the monolith.

2. I made an arbitrary decision to use LocalDateTime for the dates across the engine. This can be changed and I just chose this for convenience and ease.

3. Decided that objects shouldn't have unnecessary getters and setters. If they're not used, they're just filler code.

4. It is the responsibility of each "module" to ensure they validate the data they persist. 

    The exception to this is validating dates. I decided that any valid LocalDateTime would be valid and I wouldn't bother validating further (just for ease and given time constraints).
    
5. I used the following site to generate valid GUIDs:
   https://www.guidgenerator.com/online-guid-generator.aspx
   
6. Decided not to wrap every field in an object as this a bit overkill for an excercise.

7. Used object Result to make it easier to transition to a Microservices and REST architecture as it will just translate to a Rest Response object

## Future Work
This code wouldn't take much to turn into microservices with REST due to the design decisions above.

To put it into microservices, I think it would need the following:
* Create a betting_commons library that contains the basics that all the modules are using (e.g. BettingUtilities, CommonResults) and the REST tools needed to communicate with the other microservices.
* Split the different modules into different maven projects
* Build into each module the REST piece

