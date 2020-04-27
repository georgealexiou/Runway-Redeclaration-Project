package org.comp2211.group6;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import org.comp2211.group6.Model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class XMLHandler {

    public void saveAirportToXML(String xml, Airport airport) {
        Document dom;

        // Instance of a DocumentBuilderFactory.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Use factory to get an instance of document builder.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Create instance of DOM.
            dom = db.newDocument();

            // Create the root element.
            Element rootEle = dom.createElement("airport");

            Element airportNameElem = dom.createElement("name");
            airportNameElem.appendChild(dom.createTextNode(airport.getName()));
            rootEle.appendChild(airportNameElem);

            Set<Runway> runways = airport.getRunways();

            for (Runway runway : runways){
                Element runwayElem = dom.createElement("runway");
                rootEle.appendChild(runwayElem);

                Element nameElem = dom.createElement("name");
                nameElem.appendChild(dom.createTextNode(runway.getName()));
                runwayElem.appendChild(nameElem);

                for (LogicalRunway logicalRunway : runway.getLogicalRunways()){
                    Element logicalRunElem = dom.createElement("logicalRunway");
                    runwayElem.appendChild(logicalRunElem);

                    RunwayParameters params = logicalRunway.getParameters();

                    Element logicalTORAElem = dom.createElement("tora");
                    logicalTORAElem.appendChild(dom.createTextNode(Double.toString(params.getTORA())));
                    runwayElem.appendChild(logicalTORAElem);

                    Element logicalTODAElem = dom.createElement("toda");
                    logicalTODAElem.appendChild(dom.createTextNode(Double.toString(params.getTODA())));
                    runwayElem.appendChild(logicalTODAElem);

                    Element logicalASDAElem = dom.createElement("asda");
                    logicalASDAElem.appendChild(dom.createTextNode(Double.toString(params.getASDA())));
                    runwayElem.appendChild(logicalASDAElem);

                    Element logicalLDAElem = dom.createElement("lda");
                    logicalLDAElem.appendChild(dom.createTextNode(Double.toString(params.getLDA())));
                    runwayElem.appendChild(logicalLDAElem);

                    Element logicalHeadingElem = dom.createElement("heading");
                    logicalHeadingElem.appendChild(dom.createTextNode(Double.toString(logicalRunway.getHeading())));
                    runwayElem.appendChild(logicalHeadingElem);

                    Element logicalDispThresElem = dom.createElement("displacedThreshold");
                    logicalDispThresElem.appendChild(dom.createTextNode(Double.toString(logicalRunway.getDisplacedThreshold())));
                    runwayElem.appendChild(logicalDispThresElem);

                    Element logicalPosElem = dom.createElement("position");
                    logicalPosElem.appendChild(dom.createTextNode(Character.toString(logicalRunway.getPosition())));
                    runwayElem.appendChild(logicalPosElem);
                }
            }

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        catch (ParserConfigurationException pce) {
            System.out.println("Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public void saveObstacleToXML(String xml, Set<Obstacle> obstacles) {
        Document dom;

        // Instance of a DocumentBuilderFactory.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Use factory to get an instance of document builder.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Create instance of DOM.
            dom = db.newDocument();

            // Create the root element.
            Element rootEle = dom.createElement("obstacles");

            for (Obstacle obstacle : obstacles) {
                Element obstacleElem = dom.createElement("obstacle");
                rootEle.appendChild(obstacleElem);

                Element obstacleNameElem = dom.createElement("name");
                obstacleNameElem.appendChild(dom.createTextNode(obstacle.getName()));
                obstacleElem.appendChild(obstacleNameElem);

                Element obstacleDescElem = dom.createElement("description");
                obstacleDescElem.appendChild(dom.createTextNode(obstacle.getDescription()));
                obstacleElem.appendChild(obstacleDescElem);

                Element obstacleLenghtElem = dom.createElement("length");
                obstacleLenghtElem.appendChild(dom.createTextNode(Double.toString(obstacle.getLength())));
                obstacleElem.appendChild(obstacleLenghtElem);

                Element obstacleWidthElem = dom.createElement("width");
                obstacleWidthElem.appendChild(dom.createTextNode(Double.toString(obstacle.getWidth())));
                obstacleElem.appendChild(obstacleWidthElem);

                Element obstacleHeightElem = dom.createElement("height");
                obstacleHeightElem.appendChild(dom.createTextNode(Double.toString(obstacle.getHeight())));
                obstacleElem.appendChild(obstacleHeightElem);

                Element obstacleCDistElem = dom.createElement("distanceToCentreLine");
                obstacleCDistElem.appendChild(dom.createTextNode(Double.toString(obstacle.getDistanceToCentreLine())));
                obstacleElem.appendChild(obstacleCDistElem);

                Element obstacleLThresElem = dom.createElement("distanceFromLeftThreshold");
                obstacleLThresElem.appendChild(dom.createTextNode(Double.toString(obstacle.getDistanceFromLeft())));
                obstacleElem.appendChild(obstacleLThresElem);

                Element obstacleRThresElem = dom.createElement("distanceFromRightThreshold");
                obstacleRThresElem.appendChild(dom.createTextNode(Double.toString(obstacle.getDistanceFromRight())));
                obstacleElem.appendChild(obstacleRThresElem);

            }

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        catch (ParserConfigurationException pce) {
            System.out.println("Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public Airport readAirportXML(String xml) {
        Document dom;

        // Declaring the airport.
        Airport airport = null;

        // Make an  instance of the DocumentBuilderFactory.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            File xmlFile = new File(xml);

            dom = db.parse(xmlFile);

            // Normalize the XML Structure.
            dom.getDocumentElement().normalize();

            Element doc = dom.getDocumentElement();

            String airportName = null;
            airportName = getTextValue(airportName, doc, "name");
            if (airportName != null) {
                if (!airportName.isEmpty())
                    airport = new Airport(airportName);
            }
            
            role2 = getTextValue(role2, doc, "role2");
            if (role2 != null) {
                if (!role2.isEmpty())
                    rolev.add(role2);
            }
            role3 = getTextValue(role3, doc, "role3");
            if (role3 != null) {
                if (!role3.isEmpty())
                    rolev.add(role3);
            }
            role4 = getTextValue(role4, doc, "role4");
            if ( role4 != null) {
                if (!role4.isEmpty())
                    rolev.add(role4);
            }
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }

    // Similar to readAirportXML.
    public Set<Obstacle> readObstaclesXML(String xml) {

    }

    private String getTextValue(String def, Element doc, String tag) {
        String value = def;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }
}
