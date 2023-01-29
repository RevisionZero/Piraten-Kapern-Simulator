# A1 - Piraten Karpen

  * Author: Omar Shehada
  * Email: shehadao@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar`
  * To run the project with trace mode:
    * `mvn -q exec:java -DTRACING_ON`

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * If the feature in question does exactly what it describes without any errors within the confines of the current business logic. To make sure that's the case, multiple runs of a game are checked to see if the feature is affecting the game as required. For example, the features need not work with cards or scorinng based on matching rolls at step #2.

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  D | 01/01/23 | 13/01/23 |
| x   | F02 | Roll eight dices  |  D | 13/01/23  | 13/01/23 |
| x   | F03 | Select how many games as command-linne arg.  |  B(F03v2)  |   |
| x   | F04 | end of game with three cranes | D | 13/01/23 | 15/01/23 |
| x   | F05 | Player keeping random dice at their turn | D | 13/01/23 | 19/01/23 |
| x   | F06 | Score points: 3-of-a-kind | B(F06v2) | 13/01/23 | 
| x   | F06v2 | Score points: (Number of gold coins and diamonds) x 100 | D | 13/01/23 | 19/01/23 |
| x   | F03v2 | Play 42 games during a simulation | D | 15/01/23 | 15/01/23 |
| x   | F07 | Print the percentage of wins for each player after simulation end | D | 15/01/23 | 19/01/23 |
|     | F08 | Better player strategy | D | 22/01/23 | 23/01/23
|     | F09 | Score points based on 3,4,...,8 of-a-kind | D | 21/01/23 | 22/01/23 |
|     | F10 | Select what strategy to use for each player as a command-line arg | D | 23/01/23 | 23/01/23 |
|     | F11 | Select whether to activate trace mode as a command-line arg | D | 22/01/23 | 23/01/23 |
|     | F12 | Sea Battle Card | S | 28/01/23 |
|     | F13 | Monkey Business Card | S | 28/01/23 |
|     | F14 | Card Deck | S | 28/01/23 |
| ... | ... | ... |

