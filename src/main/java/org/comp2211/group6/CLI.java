package org.comp2211.group6;

import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.*;

import java.util.*;

public class CLI {


    private ArrayList<Airport> airports = new ArrayList<Airport>();
    private Scanner sc = new Scanner(System.in);

    private Airport airport;
    private Runway runway;

    public void run() {
        try {

            RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
            LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);

            RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
            LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

            Runway runway1 = new Runway("Demo Runway");
            runway1.addRunway(logicalRunway1);
            runway1.addRunway(logicalRunway2);

            airports.add(new Airport("Demo Airport"));
            airports.get(0).addRunway(runway1);

            System.out.println("Welcome to the Runway Redeclaration App");
            System.out.println("=======================================");

            selectAirport();

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void selectAirport() {
        System.out.println("\nSelect an Airport or one of the other options");
        Iterator<Airport> iter = airports.iterator();
        int i = 1;

        while (iter.hasNext()) {
            Airport airport = (Airport) iter.next();
            System.out.println(i + ". " + airport.getName());
            i++;
        }
        System.out.println(i + ". Create New Airport");
        System.out.println(i + 1 + ". Quit\n");

        int selection = 0;
        while (selection < 1 || selection > i + 1) {
            System.out.println("Input an integer");
            selection = sc.nextInt();
        }


        if (selection == i)
            createAirport();


        if (selection == i + 1) {
            System.out.println("\nThe program will now quit.");
            System.exit(0);
        }

        if (selection < i) {
            airport = airports.get(selection - 1);
            selectRunway();
        }
    }

    private void selectRunway() {
        System.out.println("\nSelect a runway for Airport \"" + airport.getName()
                        + "\" or one of the other options");
        Iterator<Runway> iter = airport.getRunways().iterator();
        int i = 1;

        while (iter.hasNext()) {
            Runway runway = (Runway) iter.next();
            System.out.println(i + ". " + runway.getName());
            i++;
        }
        System.out.println(i + ". Create new Runway");
        System.out.println(i + 1 + ". Select Different Airport\n");

        int selection = 0;
        while (selection < 1 || selection > i + 1) {
            System.out.println("Input an integer");
            selection = sc.nextInt();
        }

        if (selection == i)
            createRunway();

        if (selection == i + 1)
            selectAirport();

        iter = airport.getRunways().iterator();
        for (i = 0; i < airport.getRunways().size(); i++) {
            Runway currentRunway = (Runway) iter.next();
            if (i == selection - 1) {
                runway = currentRunway;
                runwayMenu();
            }
        }

    }

    private void runwayMenu() {
        System.out.println("\nRunway Menu");
        System.out.println("1. Display Logical Runways");
        System.out.println("2. Add Logical Runway");
        System.out.println("3. Create obstacle");
        System.out.println("4. View Recalculated Values");

        int selection = 0;
        while (selection < 1 || selection > 4) {
            System.out.println("Input an integer");
            selection = sc.nextInt();
        }

        switch (selection) {
            case 1:
                displayLogicalRunways();
                break;

            case 2:
                if (runway.getLogicalRunways().size() == 3)
                    System.out.println("Already reached maximum size of logical runways");
                else
                    addLogicalRunway();
                break;

            case 3:
                createObstacle();
                break;

            case 4:
                recalculatedValues();
                break;

            default:
                break;
        }

    }

    private void recalculatedValues(){
        Calculator calculator = new Calculator(runway);
        calculator.recalculateRunwayParameters();

        Map<String,String> reversedMap = new TreeMap<String,String>(calculator.getOutputMap());
        for (Map.Entry entry : reversedMap.entrySet()) {
            System.out.println(entry.getKey() + "\n" + entry.getValue() + "\n");
        }
    }

    private void addLogicalRunway(){
        System.out.println("\nPlease input logcal runway information for runway \"" + runway.getName() + "\"");
        System.out.println("Heading: (1-36)");
        int heading = sc.nextInt();

        System.out.println("Position: (L,R,C)");
        char position = sc.next().charAt(0);

        System.out.println("Displaced Threshold:");
        char displacedThreshold = sc.next().charAt(0);

        System.out.println("TORA (double):");
        double TODA = sc.nextDouble();

        System.out.println("TODA (double):");
        double TORA = sc.nextDouble();

        System.out.println("ASDA (double):");
        double ASDA = sc.nextDouble();

        System.out.println("LDA (double):");
        double LDA = sc.nextDouble();

        System.out.println("\nPlease confirm logical runway information");
        System.out.println("Identifier: " + heading + position);
        System.out.println("Displaced Threshold: " + displacedThreshold);
        System.out.println("TORA: " + TORA);
        System.out.println("TODA " + TODA);
        System.out.println("ASDA: " + ASDA);
        System.out.println("LDA: " + LDA);

        String selection = "temp";

        while (selection.charAt(0) != 'Y' || selection.charAt(0) != 'N')
            System.out.println("\n Yes ('Y') or No ('N')");
            selection = sc.next();

        if (selection.charAt(0) == 'Y') {
            try {
                runway.addRunway(new LogicalRunway(heading, position, displacedThreshold, new RunwayParameters(TORA, TODA, ASDA, LDA)));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        else
            createObstacle();
    }

    private void displayLogicalRunways(){
        System.out.println("Logical Runways for runway " + runway.getName());

        if(runway.getLogicalRunways().size() == 0)
            System.out.println("No logical runways");

        else{
            Iterator iter = runway.getLogicalRunways().iterator();
            int i = 1;
            while(iter.hasNext()){
                LogicalRunway logicalRunway = (LogicalRunway) iter.next();
                System.out.println(i + ". " + logicalRunway.getIdentifier());
                System.out.println("   TORA:" + logicalRunway.getRecalculatedParameters().getTORA());
                System.out.println("   TODA:" + logicalRunway.getRecalculatedParameters().getTODA());
                System.out.println("   ASDA:" + logicalRunway.getRecalculatedParameters().getASDA());
                System.out.println("   LDA:" + logicalRunway.getRecalculatedParameters().getLDA());
                i++;
            }
        }
    }

    private void createAirport() {
        System.out.println("\nPlease input Airport Information");
        System.out.println("Name:");
        String name = sc.next();

        airports.add(new Airport(name));
        selectAirport();
    }

    private void createRunway() {
        System.out.println("Please input Airport Information");
        System.out.println("Name:");
        String name = sc.next();

        airport.addRunway(new Runway(name));
    }

    private void createObstacle() {
        System.out.println("\nPlease input obstruction information for runway \"" + runway.getName()
                        + "\"");
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

        while (selection.charAt(0) != 'Y' || selection.charAt(0) != 'N')
            selection = sc.next();

        if (selection.charAt(0) == 'Y') {
            runway.setObstacle(new Obstacle(name, description, length, width, height, dcentreLine,
                            dleftThreshold, drightThreshold));
        }

        else
            createObstacle();
    }
}
