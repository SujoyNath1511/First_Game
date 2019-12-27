/* Name:Yusuf Ali
 * FileName:Level1
 * LastDateofEdit:2019-12-20
 * Description:This class is used to set the position of the enemy players, set their velocities
 * also the ai movement
 *
 */
package yusuf_Code;

import sujoy_Code.Player;

public class Level1 extends Enemy_Stats {
	private int enemyXValue;
	private int enemyYValue;
	private int freezeDistance = 50,detectDistance = 100;
	private Player player;
	/*Pre: None
	 * Post: sets the default values for the superclass constructor
	 */
	public Level1(Player player) {
		super (1,1,1,1);
		this.player = player;
	}
	/*Pre: An integer value passed as an argument
	 * Post: Assigns the new xValue for the enemy ai
	 */
	public void setEnemyXValue(int newEnemyXValue) {
		enemyXValue = newEnemyXValue;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's x position for other code access 
	 */
	public int getEnemyXValue() {
		return enemyXValue;
	}
	/*Pre: An integer value pass as an argument
	 * Post: Assigned the new Y value to the enemy ai 
	 */
	public void setEnemyYValue(int newEnemyYValue) {
		enemyYValue = newEnemyYValue;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's y position for other code to access 
	 */
	public int getEnemyYValue() {
		return enemyYValue;
	}
	/*Pre: None
	 * Post: Gets the enemy ai to move towards the player for action
	 */
	public void aiMovement(){
		if(enemyXValue - player.getX()  < 0 && enemyXValue - player.getX() > -detectDistance) {//if statement to check if the player is to the right of the enemy
			if (super.getEnemyType() == 1) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
				super.setxVelocity(3);
				if (enemyXValue - player.getX() >-2)//if statement to check if the enemy is close enough to attack
					super.setxVelocity(0);//Anything with super. in this class is just here to be set as a basis for now and will be changed
			}
			else
				if(super.getEnemyType() == 2) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
					super.setxVelocity(5);
					if (enemyXValue - player.getX() >-freezeDistance)//if statement to check if the enemy is close enough to attack
						super.setxVelocity(0);
				}
				else
					if(super.getEnemyType() == 3) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
						super.setxVelocity(1);
						if (enemyXValue - player.getX() >-freezeDistance)//if statement to check if the enemy is close enough to attack
							super.setxVelocity(0);
					}
					else
						if(super.getEnemyType() == 4) {//if statement to check what type of enemy therefore assigning it's appropriate velocity
							super.setxVelocity(4);
							if (enemyXValue - player.getX() >-freezeDistance)//if statement to check if the enemy is close enough to attack
								super.setxVelocity(0);
						}
		}
		else
			if(enemyXValue - player.getX()  > 0 && enemyXValue - player.getX() < detectDistance) {//If statement to check if the player is to the left of the enemy 
				if (super.getEnemyType() == 1) {//These sets of if statements have the same purpose as the ones above
					super.setxVelocity(-3);
					if (enemyXValue - player.getX() <2)
						super.setxVelocity(0);
				}
				else
					if(super.getEnemyType() == 2) {
						super.setxVelocity(-5);
						if (enemyXValue - player.getX() <freezeDistance)
							super.setxVelocity(0);
					}
					else
						if(super.getEnemyType() == 3) {
							super.setxVelocity(-1);
							if (enemyXValue - player.getX() < freezeDistance)
								super.setxVelocity(0);
						}
						else
							if(super.getEnemyType() == 4) {
								super.setxVelocity(-4);
								if (enemyXValue - player.getX() <freezeDistance)
									super.setxVelocity(0);
							}
			}
			
	}

}

