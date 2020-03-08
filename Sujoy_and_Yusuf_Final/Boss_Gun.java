/*
 * Name: Sujoy Deb Nath
 * Date Last Editied: January 13, 2020
 * Description: This class runs the boss's turrets and attack algotrithms
 */


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Boss_Gun extends Gun{
	private static int ticks;	//timer for the main turret
	private static int ticks2; //timer for area attack
	private static int ticks3; //tiemr for machine gun
	private boolean shoot; //a variable used to determine whether to shoot the main turret
	private boolean shoot2; //a boolean used to determine whether to do the aoe attack
	private Player player;
	private Boss boss;		//an instance of the boss this gun is assigned to
	private int damage;
	private int type;			//used to determine which type of gun
	public Boss_Gun(int x, int y, Camera cam, BufferedImage image, int width, int height, Boss boss, Player player, int damage, int type) {
		super(x, y, cam, image, width, height);	//runs the constructor for gun
		this.boss = boss;
		this.player = player;
		this.damage = damage;
		this.type = type;
		//start of the timers at 0
		ticks = 0;
		ticks2 = 0;
		ticks3 = 0;
	}

	/*
	 * pre: Boss_Gun has been instantiated
	 * post: the gun's x and y positions have been updated and updates the timers
	 * Description: Updates the boss's x and y position and updates the timers
	 */
	public void tick() {
		ticks++;
		ticks2++;
		ticks3++;
		if (type == 1) {	//position for the main turret
			x = boss.getBossXValue() + 150;
			y = boss.getBossYValue();
		}
		else if (type == 2){		//starting postion (middle) of the aoe
			x = boss.getBossXValue() + 100;
			y = boss.getBossYValue() + 10;
		}
		else {				//position of the machine gun
			x = boss.getBossXValue() + 150;
			y = boss.getBossYValue() + 45;
		}
		if (Math.abs(boss.getBossXValue() - player.getX()) <= 1000)
			findAngle();		//find the angle to aim the turrets
	}


	/*
	 * pre: this class has been instantiated
	 * post: the guns have been drawn onto the screen
	 * Description: Renders the boss's weapons
	 */
	@Override
	public void render(Graphics2D g2d) {
		if (type != 2) {
			if (boss.getBossXValue()  <  player.getX() ){
				g2d.rotate(-angle, x + camera.getXOffset(), y);  //make it so that all future objects rendered will be rotated at that anchor point
				g2d.drawImage(img,x + camera.getXOffset(), y, width, height, null);
				g2d.rotate(angle, x + camera.getXOffset(), y);		//counter rotate it to make everything straight again. its like rotating the canvas to draw
																	// a line and then rotating it back to draw the rest.
			}
			else {
				if(boss.getBossXValue() >  player.getX()) {
					g2d.rotate(angle, x + camera.getXOffset(), y);
					g2d.drawImage(img,x + camera.getXOffset(), y, -width, height, null);
					g2d.rotate(-angle, x + camera.getXOffset(), y);
				}
			}
		}
	}

	/*
	 * pre: boss has been instantiated and the player is in the boss arena
	 * post: angle has been set
	 * Description: Finds the angle at which to rotate the turret and fire projectiles at the player
	 */
	@Override
	public void findAngle() {
		int tempX;
		int tempY;
		tempX = player.getX() - x;
		tempY = -(player.getY() - y);
		double tempHype = Math.sqrt((tempX * tempX) + (tempY * tempY));// the hypoteneuse
		if (tempX != 0) {
			angle = Math.asin(tempY/tempHype);
			if (angle < -Math.PI/6) {		//if the angle is less than 30 degrees (30 degrees below the horizontal), than set the angle to 30 degrees.
				angle = -Math.PI/6;
			}
		}

	}


	/*
	 * pre: player is inside the boss arena and the boss isn't dead
	 * post: this class created projectiles, aimed towards the player
	 * Description: The boss attacks, depending on the type and the timer
	 */
	public void attack() {
		if (ticks >= 180 && shoot == false && type ==1) {	//if essentially 2 - 3 seconds have passed, set shoot to true (main turret)
			ticks = 0;
			shoot = true;		
		}
		else
			shoot = false;
		if (ticks2 >= 480 && shoot2 == false && type == 2) { //if 4 - 8 seconds have passed, set shoot2 to true (aoe)
			ticks2 = 0;
			shoot2 = true;
		}
		else
			shoot2 = false;
		if (ticks3 >= 30 && type == 3) {	//if 1/3 to 1/2 a second has passed, reset the timer and shoot a projectile (machine gun)
			ticks3 = 0;
			if (boss.getBossXValue()  <  player.getX()) //if the boss is to the left of the player
				bullets.add(new Projectile(x + 2, y, 10, angle, 30,10,camera,2,2));
			else
				bullets.add(new Projectile(x + 2, y, -10, -angle, 30,10,camera,2,2));
		}
		if (type == 1) {
			if (boss.getBossXValue()  <  player.getX() && boss.getBossXValue() - player.getX() > -500) {		//if the boss is to the left of the player and
																												//within a certain distance, fire
				if (shoot == true) {
					bullets.add(new Projectile(x + 2, y, 10, angle, 50,10,camera,damage * type,2));	
					shoot = false;
				}	
			}
			else
				if(boss.getBossXValue() >  player.getX()  && boss.getBossXValue() - player.getX() < 500) {
					if (shoot == true) {
						bullets.add(new Projectile(x + 2, y, -10, -angle, -50,10,camera,damage * type,2)); //for a projectile to be rendered and used properly
																											//when it shoots left, we need to use negative 
																											//scaling for angle and width
						shoot = false;		//reset shoot
					}

				}
		}
		else if (shoot2 == true) {
			shoot2 = false;				//reset shoot2
			recursiveAreaAttack(0);		//run the aoe attack
		}
	}
	/*
	 * pre: shoot2 is true
	 * post: shoots projectiles in an arc
	 * Description: Shoots projectiles in an arc that ignores platforms
	 */
	public void recursiveAreaAttack(int angleIndex) {
		if (angleIndex == 180) {		// if the angle is 180 degrees, shoot left
			bullets.add(new Projectile(x,y,-2, -Math.PI, -25,20,camera,damage * type, 3));
		}	
		else {
			if (angleIndex <= 90) {	//if the angle is less than 90, use positive scaling, else use negative scaling
				bullets.add(new Projectile(x,y,6, Math.toRadians(angleIndex), 25, 20, camera,damage/type,3));
			}
			else {
				bullets.add(new Projectile(x,y,-6, -Math.toRadians(angleIndex - 90), -25, 20, camera,damage/type,3));
			}
			recursiveAreaAttack(angleIndex + 5);	//increase the angle by 5 degrees. the lower the number of degrees added, the more projectiles
		}
	}
	/*
	 * pre: none
	 * post: none 
	 * Description: this method was not used but was required to extend Gun
	 */
	@Override
	public void fire() {}
}
