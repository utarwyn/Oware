<div align="center">
    <img src="https://i.imgur.com/PTztmCL.png" alt="Modern Oware logo" width="349">
    <h1 align="center">Modern Oware</h1>
    <h4 align="center">
        Modern adaptation of the well-known abstract strategy game for two players.
        <br/>
        First year engineering school project
    </h4>
</div>

### Project description

Oware is an abstract strategy game played worldwide. This version is the abapa variation, the most appropriate for serious, adult play.
The game requires an oware board and 48 seeds. A typical oware board has two straight rows of six pits, called "houses", and optionally one large "score" house at either end. Each player controls the six houses on their side of the board, and the score house on their end. The game begins with four seeds in each of the twelve smaller houses.

> You can find the entire description of the game on [Wikipedia](https://en.wikipedia.org/wiki/Oware).\
> Made by **Maxime Malgorn** and **Pierre Poulain**.


### Play the game

You need at least **Gradle 3.0** to build the game.\
Here is the procedure to start an awale game between two players:

1. Navigate to the root project folder
2. Type `./gradlew server:run` to run the Oware server
3. Type `./gradlew client:run` on two devices for players
4. Find the device's ip where the server is hosted and use it to log players into the game
5. Both players can play.


### Deploy the game

You can also deploy the server and the client to an external Jar file.\
To do so, you just have to type `./gradlew jar`. You will find both client and server jars in the `out/artifacts/Oware/` folder.
