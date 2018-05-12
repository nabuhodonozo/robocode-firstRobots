package MyRobots.Robots;

import static robocode.util.Utils.normalRelativeAngleDegrees; //Angles above 360

import java.awt.Color;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Nabu extends Robot {
	double direction = -1;
	double enemySide = 1;
	boolean recentlyChanged = false;
	
	public void run() {
		setAllColors(Color.magenta);
		setAdjustGunForRobotTurn(true);
		
		while (true) {
			
			if(recentlyChanged) {
				if((enemySide>0 && direction>0) || (enemySide<0 && direction<0)) {
					turnGunRight(360);
				}else if((enemySide<0 && direction>0) || (enemySide>0 && direction<0)) {
					turnGunLeft(360);
				}
				recentlyChanged = false;
			}else {
				if((enemySide>0 && direction>0) || (enemySide<0 && direction<0)) {
					turnGunLeft(360);
				}else if((enemySide<0 && direction>0) || (enemySide>0 && direction<0)) {
					turnGunRight(360);
				}
			}		
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent event) {
		double turnGunAngle = normalRelativeAngleDegrees(getHeading() + event.getBearing() - getGunHeading());
		turnGunRight(turnGunAngle);
		if (Math.abs(turnGunAngle) < 4) {
			if (getGunHeat() == 0) {
				if (getEnergy() > 10) {
					fire(Math.max(300 / event.getDistance(), 1));
				} else {
					fire(Math.min(getEnergy() / 10, 0.1));
				}
				if (event.getBearing() >= 0) {
					turnLeft(90 - event.getBearing());
				} else {
					turnRight(90 + event.getBearing());
				}
				ahead(100*direction);
			}
		}
	}

	public void onHitWall(HitWallEvent event) {
		direction *= -1;
		recentlyChanged = true;
	}

}
