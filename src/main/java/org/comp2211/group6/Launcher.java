package org.comp2211.group6;

import javafx.css.Selector;
import javafx.css.converter.PaintConverter;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Launcher{

    private static ArrayList<Airport> airports = new ArrayList<Airport>();
    private static Scanner sc = new Scanner(System.in);

    private static Airport airport;
    private static Runway runway;

    public static void main(String[] args){

        airports.add(new Airport("Demo Airport"));
        airports.get(0).addRunway(new Runway("Demo Runway"));

        System.out.println("Welcome to the Runway Redeclaration App");
        System.out.println("=======================================");

        selectAirport();

    }

    private static void selectAirport(){
        System.out.println("\nSelect an Airport or one of the other options");
        Iterator iter = airports.iterator();
        int i = 1;

        while (iter.hasNext()){
            Airport airport = (Airport) iter.next();
            System.out.println(i + ". " + airport.getName());
            i++;
        }
        System.out.println(i + ". Create New Airport" );
        System.out.println(i+1 + ". Quit\n" );

        int selection = 0;
        while (selection < 1 || selection > i+1) {
            System.out.println("Input an integer");
            selection = sc.nextInt();
        }


        if (selection == i)
            createAirport();


        if (selection == i+1 ) {
            System.out.println("\nThe program will now quit.");
            System.exit(0);
        }

        if (selection < i) {
            airport = airports.get(selection - 1);
            selectRunway();
        }
    }

    private static void selectRunway(){
        System.out.println("\nSelect a runway for Airport \"" + airport.getName() + "\" or one of the other options");
        Iterator iter = airport.getRunways().iterator();
        int i = 1;

        while (iter.hasNext()){
            Runway runway = (Runway) iter.next();
            System.out.println(i + ". " + runway.getName());
            i++;
        }
        System.out.println(i + ". Create new Runway" );
        System.out.println(i+1 + ". Select Different Airport\n" );

        int selection = 0;
        while (selection < 1 || selection > i+1) {
            System.out.println("Input an integer");
            selection = sc.nextInt();
        }

        if (selection == i)
            createRunway();

        if (selection == i+1)
            selectAirport();

        iter = airport.getRunways().iterator();
        for(i = 0; i < airport.getRunways().size(); i++){
            Runway currentRunway = (Runway) iter.next();
            if(i == selection - 1){
                runway = currentRunway;
                runwayMenu();
            }
        }

    }

    private static void runwayMenu(){
        System.out.println("\nRunway Menu");
        System.out.println("1. Display Logical Runways");
        System.out.println("2. Add Logical Runway");

        if(runway.getLogicalRunways().size() != 3){
            System.out.println("3. Add logical runway");
        }

        System.out.println("\nInput Selection:");

    }

    private static void createAirport(){
        System.out.println("\nPlease input Airport Information");
        System.out.println("Name:");
        String name = sc.next();

        airports.add(new Airport(name));
        selectAirport();
    }

    private static void createRunway(){
        System.out.println("Please input Airport Information");
        System.out.println("Name:");
        String name = sc.next();

        airport.addRunway(new Runway(name));
    }

    private static void createObstacle(){
        System.out.println("\nPlease input obstruction information for runway \"" + runway.getName() + "\"");
        System.out.println("Name:");
        String name = sc.next();

        System.out.println("Description:");
        String description = sc.next();

        System.out.println("Length (double):");
        double length = sc.nextDouble();

        System.out.println("Width (double):");
        double width = sc.nextDouble();

        System.out.println("Height (double):");
        double height = sc.nextDouble();

        System.out.println("Distance to centre line (double):");
        double dcentreLine = sc.nextDouble();

        System.out.println("Distance from left threshold (double):");
        double dleftThreshold = sc.nextDouble();

        System.out.println("Distance from right threshold (double):");
        double drightThreshold = sc.nextDouble();

        System.out.println("\nPlease confirm obstruction information");
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Length: " + length);
        System.out.println("Width: " + width);
        System.out.println("Height " + height);
        System.out.println("Distance to centre line: " + dcentreLine);
        System.out.println("Distance from left threshold: " + dleftThreshold);
        System.out.println("Distance from right threshold: " + drightThreshold);

        String selection = "temp";
        boolean confirm = false;
        System.out.println("\n Yes ('Y') or No ('N')");

        while(selection.charAt(0) != 'Y' || selection.charAt(0) != 'N')
            selection = sc.next();

        if (selection.charAt(0) == 'Y'){
            runway.setObstacle(new Obstacle(name,description,length,width,height,dcentreLine,dleftThreshold,drightThreshold));
        }

        else
            createObstacle();
    }
}
