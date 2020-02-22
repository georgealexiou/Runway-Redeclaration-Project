package org.comp2211.group6;

public class Runway implements IRunway {
	private Integer heading;
	private Character position;
	private double takeOffRunAvailable;
	private double takeOffDistanceAvailable;
	private double accelerateStopDistanceAvailable;
	private double landingDistanceAvailable;
	private double displacedThreshold;
	private int approachLandingSurface;
	private int takeOffClimbSurface;
	private double clearway;
	private double stopway;
	private IObstacle obstacle; 

	
	public Runway(Integer heading, Character position, double toda, double tora, double asda, double lda, double displacedThreshold, double clearway, double stopway) {
		setHeading(heading);
		setPosition(position);
		setTODA(toda);
		setTORA(tora);
		setASDA(asda);
		setLDA(lda);
		setDisplacedThreshold(displacedThreshold);
		setClearway(clearway);
		setStopway(stopway);
	}
	
	private void setHeading(Integer heading) {
		if (heading < 1 || heading >36) 
			throw new IllegalArgumentException("Error. A runway's heading direction must be between 1 and 36.");
		
		this.heading = heading;
	}
	
	private void setPosition(Character position) {
		if (position.equals('L') || position.equals('R') || position.equals('C'))
			this.position = position;
		
		throw new IllegalArgumentException("Error. The postional identifier of a runway can only be 'L', 'R', 'C'.");
	}
	
	private void setTODA(double toda) {
		if (toda < 0)
			throw new IllegalArgumentException("Error. Take-Off Distance Available cannot be negative.");
		takeOffDistanceAvailable = toda;	
	}
	
	private void setTORA(double tora) {
		if (tora < 0)
			throw new IllegalArgumentException("Error. Take-Off Run Available cannot be negative.");
		takeOffRunAvailable = tora;	
	}
	
	private void setASDA(double asda) {
		if (asda < 0)
			throw new IllegalArgumentException("Error. Accelerate Stop Distance Available cannot be negative.");
		accelerateStopDistanceAvailable = asda;	
	}
	
	private void setLDA(double lda) {
		if (lda < 0)
			throw new IllegalArgumentException("Error. Landing Distance Available cannot be negative.");
		landingDistanceAvailable = lda;	
	}
	
	private void setDisplacedThreshold(double ds) {
		if (ds < 0)
			throw new IllegalArgumentException("Error. Displaced Threshold cannot be negative.");
		displacedThreshold = ds;	
	}
	
	private void setClearway(double clearway) {
		if (clearway < 0)
			throw new IllegalArgumentException("Error. Clearway cannot be negative.");
		this.clearway = clearway;	
	}

	private void setStopway(double stopway) {
		if (stopway < 0)
			throw new IllegalArgumentException("Error. Stopway cannot be negative.");
		this.stopway = stopway;	
	}
	
	@Override
	public void setObstacles(IObstacle obstacle) {
		this.obstacle = obstacle;
	}
	
	@Override
	public String getName() {
		return Integer.toString(heading)+position;
	}

	@Override
	public IObstacle getObstacle() {
		return obstacle;
	}

	@Override
	public double getTODA() {
		return takeOffDistanceAvailable;
	}

	@Override
	public double getTORA() {
		return takeOffRunAvailable;
	}

	@Override
	public double getASDA() {
		return accelerateStopDistanceAvailable;
	}

	@Override
	public double getLDA() {
		return landingDistanceAvailable;
	}

	public double getDisplacedThreshold() {
		return displacedThreshold;
	}
	
	@Override
	public double getRESA() {
		return runwayEndSafetyArea;
	}

	@Override
	public int getALS() {
		return approachLandingSurface;
	}

	@Override
	public int getTOCS() {
		return takeOffClimbSurface;
	}

	@Override
	public double getClearway() {
		return clearway;
	}

	@Override
	public double getStopway() {
		return stopway;
	}
	
}
