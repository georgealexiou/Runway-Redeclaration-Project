package org.comp2211.group6;

import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.*;

import java.util.*;

public class CLI {


    private ArrayList<Airport> airports = new ArrayList<Airport>();
    private Scanner sc = new Scanner(System.in);

    // current airport and runway that the simulation uses
    private Airport airport;
    private Runway runway;

    /**
     * Creates the appropreate demo objects and runs the main menu
     */
    public void run() {
        try {

            String selection = "temp";

            RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
            LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);

            RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
            LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

            Obstacle obstacle = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 12.0, 0.0,
                            -50.0, 3646.0);

            Runway runway1 = new Runway("Demo Runway");
            runway1.addRunway(logicalRunway1);
            runway1.addRunway(logicalRunway2);
            runway1.setObstacle(obstacle);

            airports.add(new Airport("Demo Airport"));
            airports.get(0).addRunway(runway1);

            System.out.println("Welcome to the Runway Redeclaration App");
            System.out.println("=======================================");

            selectAirport();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Menu for the user to choose an airport or create a new one
     *
     */
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

    /**
     * Menu for the user to select a runway or create a new one
     */
    private void selectRunway() {
        System.out.println("\nSelect a runway for Airport \"" + airport.getName()
                        + "\" or one of the other options");
        Iterator<Runway> iter = airport.getRunways().iterator();
        int i = 1;

        while (iter.hasNext()) {
            Runway runway1 = (Runway) iter.next();
            System.out.println(i + ". " + runway1.getName());
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

    /**
     * Method that displays everything that can be done within a runway
     *
     * ex. View Recalculated parameters Add new logical runway etc.
     */
    private void runwayMenu() {
        System.out.println("\nRunway Menu");
        System.out.println("1. Display Logical Runways");
        System.out.println("2. Add Logical Runway");
        System.out.println("3. Create obstacle");
        System.out.println("4. View Recalculated Values");
        System.out.println("5. Select Different Runway");

        int selection = 0;
        while (selection < 1 || selection > 5) {
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

            case 5:
                selectRunway();
                break;

            default:
                break;
        }

    }

    /**
     * Prints all recalculated Values and asks if the user wants to see a breakdown Prints breakdown
     * if the user aswers YES
     */
    private void recalculatedValues() {
        Calculator calculator = new Calculator(runway);
        calculator.recalculateRunwayParameters();

        Iterator iter = runway.getLogicalRunways().iterator();

        while (iter.hasNext()) {
            LogicalRunway lr = (LogicalRunway) iter.next();
            System.out.println(lr.getIdentifier());
            System.out.println("  TORA: " + lr.getParameters().getTORA() + " -> "
                            + lr.getRecalculatedParameters().getTORA());
            System.out.println("  TODA: " + lr.getParameters().getTODA() + " -> "
                            + lr.getRecalculatedParameters().getTODA());
            System.out.println("  ASDA: " + lr.getParameters().getASDA() + " -> "
                            + lr.getRecalculatedParameters().getASDA());
            System.out.println("  LDA: " + lr.getParameters().getLDA() + " -> "
                            + lr.getRecalculatedParameters().getLDA());
            System.out.println("");
        }

        System.out.println("Do you want to see a breakdown of the calculations?");

        String selection = "hahaha";
        while (selection.charAt(0) != 'Y' && selection.charAt(0) != 'N') {
            System.out.println("\nYes ('Y') or No ('N')");
            selection = sc.next();
        }

        switch (selection.charAt(0)) {
            case 'Y':
                ArrayList<String> breakdowns = calculator.getLogicalRunwayBreakDown();
                Iterator iter1 = breakdowns.iterator();

                while (iter1.hasNext()) {
                    String output = (String) iter1.next();
                    System.out.println(output);
                    System.out.println("");
                }
                break;

            case 'N':
                break;

            default:
                break;
        }

        runwayMenu();
    }

    /**
     * Method that prompts the user to add a new logical runway
     */
    private void addLogicalRunway() {
        System.out.println("\nPlease input logcal runway information for runway \""
                        + runway.getName() + "\"");
        System.out.println("Heading: (1-36)");
        int heading = sc.nextInt();

        System.out.println("Position: (L,R,C)");
        char position = sc.next().charAt(0);

        System.out.println("Displaced Threshold:");
        double displacedThreshold = sc.nextDouble();

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

        while (selection.charAt(0) != 'Y' && selection.charAt(0) != 'N') {
            System.out.println("\nYes ('Y') or No ('N')");
            selection = sc.next();
        }

        if (selection.charAt(0) == 'Y') {
            try {
                runway.addRunway(new LogicalRunway(heading, displacedThreshold, position,
                                new RunwayParameters(TORA, TODA, ASDA, LDA)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        runwayMenu();
    }

    /**
     * Method that displays all the runway parameters of each logical runway
     */
    private void displayLogicalRunways() {
        System.out.println("Logical Runways for runway " + runway.getName());

        if (runway.getLogicalRunways().size() == 0)
            System.out.println("No logical runways");

        else {
            Iterator iter = runway.getLogicalRunways().iterator();
            int i = 1;
            while (iter.hasNext()) {
                LogicalRunway logicalRunway = (LogicalRunway) iter.next();
                System.out.println(i + ". " + logicalRunway.getIdentifier());
                System.out.println("   TORA:" + logicalRunway.getParameters().getTORA());
                System.out.println("   TODA:" + logicalRunway.getParameters().getTODA());
                System.out.println("   ASDA:" + logicalRunway.getParameters().getASDA());
                System.out.println("   LDA:" + logicalRunway.getParameters().getLDA());
                i++;
            }
        }

        runwayMenu();
    }

    /**
     * Method used to create an airport
     */
    private void createAirport() {
        System.out.println("\nPlease input Airport Information");
        System.out.println("Name:");
        String name = sc.next();
        name += sc.nextLine();


        airports.add(new Airport(name));
        selectAirport();
    }

    /**
     * Method used to create a runway
     */
    private void createRunway() {
        System.out.println("Please input Runway Information");
        System.out.println("Name:");
        String name = sc.next();
        name += sc.nextLine();

        airport.addRunway(new Runway(name));
        selectRunway();
    }

    /**
     * Method used to create ab obstacle
     */
    private void createObstacle() {
        System.out.println("\nPlease input obstruction information for runway \"" + runway.getName()
                        + "\"");
        System.out.println("Name:");
        String name = sc.next();
        name += sc.nextLine();

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


        while (selection.charAt(0) != 'Y' && selection.charAt(0) != 'N') {
            System.out.println("\n Yes ('Y') or No ('N')");
            selection = sc.next();
        }


        if (selection.charAt(0) == 'Y') {
            runway.setObstacle(new Obstacle(name, description, length, width, height, dcentreLine,
                            dleftThreshold, drightThreshold));
        }

        else
            createObstacle();

        runwayMenu();
    }
}
