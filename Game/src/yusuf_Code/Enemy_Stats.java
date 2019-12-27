/* Name:Yusuf Ali
 * FileName:Enemy_Stats
 * LastDateofEdit:2019-12-19
 * Description: This class is where you can set and change the enemies HP, DPS and damage type(melee or ranged);
 */

package yusuf_Code;

public class Enemy_Stats {
	private int hitPoints;
	private int damagePoints, damageType, enemyType;
	private int xVelocity;
	private int yVelocity;
	/*Pre: four integer variables passed as an argument
	 *post: Sets the values for the enemies hitpoints, damage, enemy type and damage type(melee/ranged)  
	 */
	public Enemy_Stats(int newHitPoints, int newDamagePoints, int newDamageType, int newEnemyType) {
		hitPoints = newHitPoints;
		damagePoints = newDamagePoints;
		damageType = newDamageType;
		enemyType = newEnemyType;
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies x velocity
	 */
	public void setxVelocity(int newxVelocity) {
		xVelocity = newxVelocity;
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies hitpoints
	 */
	public void setHitPoints(int newHitPoints) {
		hitPoints = newHitPoints;  
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies y velocity
	 */
	public void setyVelocity(int newyVelocity) {
		yVelocity = newyVelocity;
	}
	/*Pre: Integer value passed as an argument
	 * Post: Sets the enemies damage type (melee or ranged)
	 */
	public void setDamageType(int newDamageType) {
		damageType = newDamageType;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's damage type for other code access 
	 */
	public int getDamageType() {
		return damageType;
	}
	/*Pre: None
	 * Post: Gives access to the enemy type for other code access 
	 */
	public int getEnemyType() {
		return enemyType;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's x velocity for other code access 
	 */
	public int getxVelocity() {
		return xVelocity;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's hitpoints for other code access 
	 */
	public int getHitpoints() {
		return hitPoints;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's y velocity for other code access 
	 */
	public int getyVelocity() {	
		return yVelocity;
	}
	/*Pre: None
	 * Post: Gives access to the enemy's damage points for other code access 
	 */
	public int getDamagePoints() {
		return damagePoints;
	}
	/*Pre: None
	 * Post: Shows the enemy's current health pints 
	 */
	public String toString() {
		return("Enemy: " + Integer.toString(hitPoints) + "HP");
	}
}
