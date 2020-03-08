This is the ReadMe file for Yusuf and Sujoy's Game:

Folders and files you need to have:

resources:
   - animationSheets
	- boss.png
	- drone_sprite_2.png
	- officer_walk.png
	- player_walk.png
	- strong_defense_walk.png
   - backgrounds
	modern_city.png
   - textures
	- cursor.png
	- flag.png
	- floor.png
	- key_a.png
	- key_d.png
	- laser.png
	- laser_gun.png
	- mouse_leftclick.png
	- space_bar.png
	- stone.png
	- stone_1.png
	- tank_gun_1.png
	- tank_gun_2.png
	- wall_2.png
	- wasd_keys.png
   - levels
	- world_1.txt

main_game_code:
   - Boss.java
   - Boss_Gun.java
   - Camera.java
   - Enemy_Gun.java
   - Enemy_Stats.java
   - Frame.java
   - Game.java
   - Gun.java
   - Handler.java
   - ImageLoader.java
   - MenuState.java
   - MouseManager.java
   - Player.java
   - Player_Gun.java
   - Projectile.java
   - Test.java
utils:
   - Utils.java
entities_and_objects:
   - Background_Object.java
   - Foreground_Object.java
   - Entities.java
worlds:
   - World.java

The resources folder contains all the sprites and level information. The rest are classes you need to run the game.
In order to run the game, you need to go to Test.java because it contains main() and run the game from there. All the previously listed java classes
are required to run the game properly

Tips:
 - When you run the game, click on Controls in order to learn the keys to not be surprised when playing the game.
 - Click on the Start button to start the game. Game is over when you beat the boss or when you die 3 times.
 - Click on the quit button to end the game.