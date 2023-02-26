package com.me.EFF;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Limit {

	private String figure;
	private float x1;
	private float x2;
	private float y1;
	private float y2;
	private float radius;
	private float angle;
	private float sizeX;
	private Random rand;
	private float sizeY;
	private Body limit;
	private String data;
	private int goalID;
	private boolean isActive;
	private boolean isTraspassable;
	private boolean isTouchingGoal;
	private UserDataWrapper userW;
	private Sounds sounds;

	public Limit(String figure, float x1, float y1, float x2, float y2, float radius, float angle, float sizeX,
			float sizeY, World world, int goalID, boolean isTraspassable, Sounds sounds) {

		this.sounds = sounds;
		this.isTraspassable = isTraspassable;
		this.isTouchingGoal = false;
		this.data = "void";
		this.figure = figure;
		this.x1 = x1;
		this.rand = new Random();
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.radius = radius;
		this.angle = angle;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.goalID = goalID;
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.type = BodyType.StaticBody;
		if(goalID == 999) {
			fixtureDef.isSensor = true;
		}
		if (figure.equals("line")) {

			ChainShape line = new ChainShape();
			line.createChain(new Vector2[] { new Vector2(x1, y1), new Vector2(x2, y2) });
			fixtureDef.shape = line;
			limit = world.createBody(bodyDef);
			userW = new UserDataWrapper("limit", this);
			limit.createFixture(fixtureDef).setUserData(userW);
			line.dispose();
		} else if (figure.equals("box")) {

			PolygonShape box = new PolygonShape();
			box.setAsBox(sizeX, sizeY, new Vector2(x1, y1),  angle * MathUtils.degreesToRadians);
			fixtureDef.shape = box;
			limit = world.createBody(bodyDef);
			userW = new UserDataWrapper("limit", this);
			limit.createFixture(fixtureDef).setUserData(userW);
			box.dispose();
		} else if (figure.equals("circle")) {

			CircleShape circle = new CircleShape();
			circle.setPosition(new Vector2(x1, y1));
			circle.setRadius(radius);
			fixtureDef.shape = circle;
			limit = world.createBody(bodyDef);
			userW = new UserDataWrapper("limit", this);
			limit.createFixture(fixtureDef).setUserData(userW);
			circle.dispose();
		}limit.setUserData("active");
		isActive = true;
	}

	public boolean isTraspassable() {
		return isTraspassable;
	}
	
	public void testLimits() {
		
		limit.setUserData(userW);
	}
	
	public void punched() {
		
		int randAux = rand.nextInt(3);
		if(randAux == 0) {
			sounds.puc1.play();
		}else if (randAux == 1) {
			sounds.puc2.play();
		}else if(randAux == 2) {
			sounds.puc3.play();
		}
	}
	
	public String getLimitData() {

		if (figure.equals("line")) {
			data = figure + "/" + x1 + "/" + y1 + "/" + x2 + "/" + y2;
		} else if (figure.equals("box")) {
			data = figure + "/" + x1 + "/" + y1 + "/" + angle + "/" + sizeX + "/" + sizeY;
		} else if (figure.equals("circle")) {
			data = figure + "/" + x1 + "/" + y1 + "/" + radius;
		}
		return data;
	}

	public void setTouchingGoal() {
		
		if(isActive) {
			isTouchingGoal = true;
		}
	}
	
	public float getMayorX(){
		if(x1>=x2){
			return x1;
		}return x2;
	}
	
	public float getMayorY(){
		if(y1>=y2){
			return y1;
		}return y2;
	}
	
	
	
	public void setTouchingGoalInactive() {
		isTouchingGoal = false;
	}
	
	public boolean isTouchingGoal() {
		return isTouchingGoal;
	}
	
	public void setInactive() {
		setTouchingGoalInactive();
		isActive = false;
		limit.setUserData("inactive");
	}

	public void setActive() {
		isActive = true;
		limit.setUserData("active");
	}

	public boolean isActive() {
		return isActive;
	}

	public int getgoalID() {
		return goalID;
	}

	public void delete() {
		setTouchingGoalInactive();
		isActive = false;
		limit.setUserData("destroy");
	}
}
