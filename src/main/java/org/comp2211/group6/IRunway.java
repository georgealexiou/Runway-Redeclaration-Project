package org.comp2211.group6;

interface IRunway {
	double runwayEndSafetyArea = 240;
	
	void setObstacles(IObstacle obstacle);
	
	String getName();
	
	IObstacle getObstacle();
	
	double getTODA();
	
	double getTORA();
	
	double getASDA();
	
	double getLDA();
	
	double getDisplacedThreshold();
	
	double getRESA();
	
	int getALS();
	
	int getTOCS();

	double getClearway();
	
	double getStopway();
}
