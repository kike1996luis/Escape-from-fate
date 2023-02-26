# Escape-from-fate
Video game project for the video game development subject taught by Professor Jose Sanchez, at the University of Zulia (LUZ)

1- General concept of the game

a- Thematic focus of the game i- Dream to fulfill: Escape from the psychiatric prison by overcoming the challenges in each level (room). ii- Game environment: It takes place in a psychiatric prison. iii- Perspective: Top down camera (view from above).

b- Game mechanics i- Player objective: Get out of prison alive, overcome obstacles. ii- Player actions: Solve puzzles and defeat enemies, using weapons. iii- Challenges to be faced by the player: The challenges will be intrinsic. c- Similar games:

Hotline Miami.

Adventures of Lolo.

The game consists of escaping from a psychiatric prison, in which the player is a mental patient who lives in the "real" world and a "surreal" one, in which the surreal world interprets the other members of the game as video game characters, or famous cartoons (mario bros, Mickey mouse, Son Goku...), and that to escape from prison he must kill each one of them and solve puzzles to achieve the objective. The game ends when the player is killed or when he is caught.

3- Gameplay The player ends the level when he meets the specific objective of the map (kill the enemies and/or overcome the puzzle, as the case may be). When the specific objective of the map is met, the player must be placed at a specific point in order to advance to the next level.

The player will be able to interact with many objects dynamically and in real time for his own strategy, such as moving a chair while shooting to cover himself from enemies. You will also be able to use several of these objects to solve specific puzzles on the map (the use of a key to open a door, for example…). There will also be destructible objects on the maps.

Enemies will try to eliminate the player, they will be in a specific formation (walking around the map, in circles…), or just standing somewhere waiting for the player. They will be able to push objects to be able to pass if it means an obstacle for him.

The player will have the option of shooting or throwing the weapon in order to change it, he will have an inventory of items on the map and if the weapon runs out of ammunition, he must look for another one.

4- Technical section

The video game is programmed in the Java language with the libGDX 0.9.9 framework, the box2D physics engine is used to implement collisions, interactions, the use of particles and impulses to have more dynamism and realism in the game in real time with the use of gravity and other things that library offers.

For artificial intelligence, the A* graph is used with slight modifications to adapt it to the game. The maps are tiled files with 16x16 matrices, where you will only have the floor and the walls that are loaded only as background in the same game. Objects such as chairs, desks... are dynamically loaded into the program so that interaction with them is possible in real time.
