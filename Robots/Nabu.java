package MyRobots;

import static robocode.util.Utils.normalRelativeAngleDegrees; //Angles above 360

import java.awt.Color;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Nabu extends Robot {
	public void run() {

		setAllColors(Color.magenta);
		setAdjustGunForRobotTurn(true);

		while (true) {
			double X = getX();
			if (X > getX()) {
				out.println("Macham se lufo w prawo");
				turnGunRight(10);
			} else {
				out.println("Macham se lufo w lewo");
				turnGunLeft(10);
			}
		}
	}

	public void onScannedRobot(ScannedRobotEvent event) {
		double turnGunAngle = normalRelativeAngleDegrees(getHeading() + event.getBearing() - getGunHeading());
		turnGunRight(turnGunAngle);
		if (Math.abs(turnGunAngle) < 4) {
			if (getGunHeat() == 0) {
				out.println("GunHeat = 0");
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
				ahead(100);
			}
		}
	}

	public void onHitWall(HitWallEvent event) {
		back(10);
		turnLeft(180 - event.getBearing());
	}

}
