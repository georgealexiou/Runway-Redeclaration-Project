import java.util.HashMap;
import java.util.Observable;

import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.graalvm.compiler.core.common.FieldsScanner.CalcOffset;

@SuppressWarnings("serial")
public class RedeclarationMap{

    /** Public Properties */

    /** Public Methods */
    public RedeclarationMap(){
        this.map =  new HashMap<String,String>();
    }

    /**
     * Method that generates a new String representing the calculations based on the
     * following parameters and adds it to the map
     * @param logicalRunway A logical runway from which data is extracted
     * @param type The type of calculation that was performed
     * @param obstacle The obstacle in the runway/logical runway
     * @param RESA 
     * @param StripEnd
     * @param CentreLine
     */
    public void add(LogicalRunway logicalRunway, CalculationType type, Obstacle obstacle, int RESA, int StripEnd, int CentreLine){
        this.logicalRunway = logicalRunway;
        this.obstacle = obstacle;
        this.type = type;

        //Parameters in Calculator Class
        this.otherParameters = new HashMap<String, Int>();
        this.otherParameters.put("RESA", RESA);
        this.otherParameters.put("StripEnd", StripEnd);
        this.otherParameters.put("CentreLine", CentreLine);

        String key = generateKey(); //key of the entry to the hashmap
        String value = ""; //value of the entry to the hashmap

        switch (type){
            case CalculationType.landingOver:
                value = landingOver();
                break;

            case CalculationType.landingTowards:
                value = landingTowards();
                break;

            case CalculationType.takeOffAway:
                value = takeOffAway();
                break;

            case CalculationType.takeOffTowards:
                value = takeOffTowards();
                break;
        }
    }

    /**
     * @return Map of all calculationTypes and logicalrunways to String output
     */
    public HashMap<String,String> getMap(){
        return map;
    }
    
    /** Private Methods */

    /**
     * @return The key of an entry to be added to the map
     */
    private String generateKey(){ return logicalRunway.getIdentifier() + "_" + nameOf(type); }

    /**
     * Returns a String that represents the calculations performed
     * @return RLDA = Distance from Threshold - RESA - Strip End
    */
    private String landingOver(){
        String output = "RLDA = Distance from Threshold - RESA - Strip End";
        output.concat("\n     = " + 555 + " - " + otherParameters.get("RESA") + " - " + otherParameters.get("StripEnd"));
        output.concat("\n     = " + (555 - otherParameters.get("RESA") - otherParameters.get("StripEnd")));
        return output;
    }

    /**
     * Returns a string that represents the calculations performed
     * @return RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     *         RASDA = RTORA + STOPWAY
     *         RTODA = RTORA + CLEARWAY
    */
    private String landingTowards(){
        String output = "RLDA = Distance from Threshold - Strip End - Slope Calculation";
        output.concat("\n     = " + 555 + " - " + otherParameters("StripEnd") + " - (" + 55 + "*" + 50 + ")");
        output.concat("\n     = " + (555 - otherParameters("StripEnd") - (55*50)));

        return output;
    }

    /**
     * Returns a string that represents the calculations performed
     * @return RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     *         RASDA = RTORA + STOPWAY
     *         RTODA = RTORA + CLEARWAY
    */
    private String takeOffAway(){
        String output = "RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold";
        output.concat("\n      = " + logicalRunway.getParameters().getTORA() + " - " + 555 + " - " + 100);
        output.concat("\n      = " + 555 + "\n\n");

        output.concat("RASDA = RTORA + STOPWAY");

        output.concat("RTODA = RTORA + CLEARWAY");

        return output;
    }

    /**
     * Returns a string that represents the calculations performed
     * @return RTORA = Distance from Threshold - Slope Calculation
     *         RASDA = RTORA
     *         RTODA = RTORA
    */
    private String takeOffTowards(){
        String output = "RTORA = Distance from Threshold - Slope Calculation";

        output.concat("RASDA = RTORA");
        output.concat("RTODA = RTORA");

        return output;
    }

    /** Private Properties */
    private LogicalRunway logicalRunway;
    private Obstacle obstacle;
    private CalculationType type;
    private HashMap<String,Int> otherParameters;
    private HashMap<String,String> map;
}