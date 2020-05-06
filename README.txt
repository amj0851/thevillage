------=Gameplay and Advice=-------
Move around using the arrow keys
Walking up to an NPC will initiate dialogue with them. Once the dialogue box is up, you cannot move until you close it
Make sure to step away from NPCs before moving any other direction once closing the dialogue, or they will try and talk to you again.
Additionally, avoid sprinting towards NPCs. Approach them slowly.
You can respond by pressing the yes or no button.
If an NPC seems upset by your response, you can initiate the conversation again and choose a different response. 
Getting to know all the characters will make playing the game easier!

-----=Class Descriptions=--------
NPC --> This object holds the location, name, dialogue, and image file for a NPC and keeps track of what the character needs to say next. 
EnvironObj --> this stores the location and image file for parts of the map that arent NPCs. It also knows whether or not the player can walk on the object.
GameMap --> reads in a .txt file and creates a map and makes all the environment objects and NPCs for the game.
GameFrame --> contains the playerComponent and displays everything to the user. Also listens for arrow keys to move the player
Player --> This contains the location and image file for the player, calculates if the player is touching anything, and helps track the player's progress in the game
PlayerComponent --> this is where everything happens. This class draws the map onto the frame, moves the player, updates NPC dialogue, and contains the Popup class
Pop --> within PlayerComponent. This class creates a popup frame with the character dialogue and buttons. 
GameRunner --> Runs the game


