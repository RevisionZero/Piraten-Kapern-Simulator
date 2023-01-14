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

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * If the feature in question does exactly what it describes without any errors within the confines of the current business logic. For examples, the features need not work with cards or scorinng based on matching rolls at step #2.

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a dice |  D | 01/01/23 | 13/01/23 |
| x   | F02 | Roll eight dices  |  D | 13/01/23  | 13/01/23 |
| x   | F03 | Print the percentage of wins for each player after simulation end  |  P  |   |
| x   | F04 | end of game with three cranes | P | |
| x   | F05 | Player keeping random dice at their turn | P | 13/01/23 | 
| x   | F06 | Score points: (Number of gold coins and diamonds) x 100 | P | 13/01/23 | 
| ... | ... | ... |

