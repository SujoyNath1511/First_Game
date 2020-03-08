/* Name:Yusuf Ali
 * LastDateofEdit:2020-01-11
 * Description: This class is where the enemies get their guns and are able to shoot at the player
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Enemy_Gun extends Gun {
	private int ticks;//A timer to prevent the enemies from spam shooting
	boolean shoot;// Variable to check if the enemy recently shot
	private Player player;//Instance of the player class
	private Enemy_Stats enemy;//Instance of the Enemy_Stats class
	/*Pre: three integer variables passed as an argument, Camera object, Player object and Enemy_Stats object 
	 *post: Sets the values for the enemies hitpoints, damagepoints, enemy type. Also sets the camera object, player object and the direction the enemy faces as an int value (1 = left) (2 = right)  
	 */
	public Enemy_Gun(Camera cam, BufferedImage image, Enemy_Stats enemy, int width, int height, Player player, int direction) {
		
		super(enemy.getEnemyXValue() , enemy.getEnemyYValue() ,cam, image, width, height);
		this.enemy = enemy;
		this.player = player;
		ticks = 0;
	}

	

	@Override
	/*Pre: Takes in a Graphics2D object
	 * Post: rotates the enemie sgun to be facing at the player
	 */
	public void render(Graphics2D g2d) {
		if (enemy.getHitpoints() > 0) {//Checks if the enemy is not dead
			if (enemy.getEnemyXValue()  <  player.getX() ){//Checks if the player is to the right of the enemy then draws the gun at the appropriate angle and direction
				g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
				g2d.drawImage(img,x + camera.getXOffset() + 17, y, width, height, null);
				g2d.rotate(angle, x + camera.getXOffset() + 17 , y);
			}
			else {//Checks if the player is to the left of the enemy then draws the gun at the appropriate angle and direction
				if(enemy.getEnemyXValue() >  player.getX() ) {
					g2d.rotate(angle, x + camera.getXOffset() + 17, y);
					g2d.drawImage(img,x + camera.getXOffset()+ 17, y, -width, height, null);
					g2d.rotate(-angle, x + camera.getXOffset() + 17, y);
				}
			}
		}
	}

	@Override
	/*Pre: none
	 * Post: Gets the x and y position in which the bullet should come out of
	 */
	public void tick() {
		if (enemy != null) {//If statement to check if the player is not dead
			if(enemy.getEnemyType()==1) {//Checks if the type of enemy is the air unit
				x = enemy.getEnemyXValue() + enemy.getEnemy_width()[0]/2;
				y = enemy.getEnemyYValue() + enemy.getEnemy_height()[0]/2;//Setting bullet start position
			}
			else {//Else meaning for the other types of enemies
				x = enemy.getEnemyXValue();//Setting bullet start position
				y = enemy.getEnemyYValue() + 24;
			}
			if (Math.abs(enemy.getEnemyXValue() - player.getX()) <= enemy.getFreezeDistance())//Checks if the enemy is in shoot range
				findAngle();//Then finds the angle to fire towards the player
			ticks ++;//Increments one to tick used for delay later
		}
		
	}

	@Override
	/*Pre: none
	 * Post: Determines the angle from the enemy towards the player
	 */
	protected void findAngle() {
		int tempX;
		int tempY;
		tempX = player.getX() - enemy.getEnemyXValue();
		tempY = -(player.getY() - enemy.getEnemyYValue());
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);//Gets the angle towards the player
		}
	}

	@Override
	/*Pre: none
	 * Post: Add a bullet to the ArrayList for rendering
	 */
	public void fire() {
		if (ticks >= 110 && shoot == false) {//If statement to check if the enemy has not fired in the passed minute and 40 seconds
			shoot = true;//shoot becomes true to allow the enemy to fire again	
			ticks = 0;//resets the tick timer
		}
		else//Else meaning the enemy has shot with in the past minute and 40 seconds
			shoot = false;

		if (enemy.getEnemyXValue()  <  player.getX() && enemy.getEnemyXValue() - player.getX() > -(enemy.getFreezeDistance())) {//Checks if the player is to the right of the enemy and the enemy is in shooting range
			if (shoot == true) {//if statement to check if the enemy is allowed to shoot
				bullets.add(new Projectile(x, y, 5, angle, 25,10,camera,enemy.getDamagePoints(),2));//If yes then adds a bullet to the ArrayList for rendering
				shoot = false;//Sets shoot to false so the enemy can't shoot for the next minute and 40 seconds
			}	
		}
		else
			if(enemy.getEnemyXValue() >  player.getX()  && enemy.getEnemyXValue() - player.getX() < enemy.getFreezeDistance()) {//Checks if the player is to the left of the enemy and the enemy is in shooting range
				if (shoot == true) {//if statement to check if the enemy is allowed to shoot
					bullets.add(new Projectile(x, y, -5, -angle, -25,10,camera,enemy.getDamagePoints(),2));//Adds a bullet to the ArrayList for rendering
					shoot = false;//Sets shoot to false so the enemy can't shoot for the next minute and 40 seconds
			}
				
		}
	}

		
	

}
