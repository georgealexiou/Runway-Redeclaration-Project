# Scenarios

## Colour Schemes - GUI (07/05)
Chris is using the tool in the evening and is finding the brightness of the original colour scheme taxing on his eyes.
He selects the 'Inverts Colours' option from the 'View' menu on the top bar. 
The colours are inverted and apply to the entire application across all views.
 
## Zooming and Panning - GUI (07/05)
Hallie wants to focus on a particular area of the Runway view.
She uses the scroll bar to zoom in on the area she is interested in and then drags with the mouse to pan to the correct region.
When she is finished she chooses to zoom out using the zoom slider at the bottom of the screen.
After zooming out she realises that the view is now off centre and double clicks to reset the view back to its original state.

## Top-Down View - GUI (07/05)

### No Obstacle
Chris starts the runway redeclaration tool and loads in 'Heathrow Airport'.
He is presented with a top-down view of the first runway in 'Heathrow' - '09L27R'.
There are dropdown menus showing the currently selected Runway and Threshold as well as the current obstacle.
An arrow in the top right updates to reflect the landing and takeoff direction when he changes logical runway.
The currently selected threshold should be highlighted with an orange box.
The original runway values should be represented by arrows, including a displaced threshold if one is present.
The runway centre line should be visible, as should the clearway and stopway at each end of the runway if applicable.

### Obstacle
Upon selecting a previously added obstacle, it should appear on the runway in the expected location.
The recalculated values for the currently selected threshold should be displayed based on the obstacle.
Factors such as slope calculation, blast protection, strip end and runway end safety area should also be displayed.
All values should be labelled with their magnitude and type such as 'TORA - 3646m'.
Chris can select to change the currently selected logical runway in the top left.
Upon doing so, the values should all update to reflect the impact the obstacle has on this logical runway.

## Side-On View - GUI (07/05)

### No Obstacle
Hallie chooses to view the side-on view by selecting 'Toggle View' from the top menu bar.
Each of the original parameters should be visible: TORA, TODA, ASDA and LDA. In addition to this, the displaced threshold should be shown.
Since there is no obstacle present, there should be no representation of the takeoff and landing slope.
The currently selected threshold should be highlighted so that it is clear to Hallie which logical runway she is focused on.

### Obstacle
After Hallie adds an obstacle to the current runway she will see a shape appear on the runway in the expected location.
The recalculated values should appear on the visualisation, and where necessary be broken down into the factors that cause the values to change such as the 'blast protection', 'strip end' and 'slope calculation'.
If she chooses the change the logical runway the values will update accordingly.

## Airport Configuration - GUI (07/05)

### Existing Airport - Editing
Chris wants to change some details about a previously defined runway. 
He clicks the 'Edit' button from the Airport section of the menu bar and is presented with the airport configuration view with the details of the runway loaded in.
He is able to change all of the values, including the deletion of a runway.
Upon completing the changes he is able to save the runway for the current session or export it to the file it was loaded from.

### New Airport - Creating
Jules wants to configure a new airport on the tool so that she can roll out the software in other airports.
She chooses the 'Create Airport' option from the top menu bar.
She is able to enter a name and select a number of runways. The number of runways must be at least 1.
At this point, she sees that the 'Save' and 'Export' buttons are greyed out since the airport is incomplete.
She enters a name and selects to have 2 runways.
A stacked list of Runways appears with 'Runway 1 -' and 'Runway 2 -' as their names. She clicks 'Runway 1 -' and the runway configuration opens. 
She can now select the number of logical runways/thresholds from a number picker that limits her to between 1 and 3.
For each logical runway, a form appears allowing her to choose the 'Heading', 'Position' and Runway Parameters for each runway and optionally the 'Displaced Threshold'.
She completes the values for one of the logical runways but moves on to the other runway before completing the other.
She sees a message next to 'Runway 1 -' that tells her there are incomplete logical runways.
She completes all of the required fields and sees that the 'Save' and 'Export' buttons are now available.

## Calculation Breakdown - GUI (07/05)

Chris wants to view the breakdown of calculations for his current sessions so that he can compare them with his paper calculations to further his training.
He has added an obstacle and sees the option to select 'View Calculations' from the top menu bar.
Upon clicking this, he is presented with breakdowns for all of the logical runways.
He chooses to limit this to just one as he is only concerned with '09R' for now. The drop-down list at the top of the view allows him to select this logical runway. 
The original and recalculated values are shown for each of the runway parameters.
Each of the parameters is shown below this with each of the factors used to calculate it displayed.

## Obstacle Configuration - GUI

### New Obstacle - Creating

Hallie wants to add a new obstacle to the system and clicks the button 'Create Obstacle'. 
This opens a new view where she is able to enter the name of the obstacle, a short (optional) description and the parameters of the obstacle.
She enters the height of the obstacle and sees the 'Save' and 'Export' buttons are now clickable. She can export here as all the data required to export
has been provided. She chooses to continue as she will be using the obstacle in the current session. She completed the remaining fields and clicks 'Export'.
This prompts her to select a file to save the XML in and write the data to the file (excluding the distances from left, right and centre line).
The obstacle is now loaded on to the runway if one is present or saved until a runway is loaded otherwise.

### Load Obstacle - Loading (07/05)

Chris wants to load a predefined obstacle to help in his understanding of the program's function. He clicks the 'Load Obstacle' button from the top menu bar. 
He selects the XML file corresponding to the predefined obstacle.
The 'Load an Obstacle' screen (variation of Create an Obstacle) is shown with predefined data.
He is asked to confirm and edit the data before continuing.
Upon completing the fields he can press 'Save' and the obstacle will be available in the current session.

### Edit Obstacle - Editing

Chris has noticed that the values he entered previously are incorrect. He selects to edit the current obstacle.
The 'Edit Obstacle' view is shown and he enters the modified values. He cannot 'Export' as the name, description and height did not change.
He chooses 'Save' and immediately sees the values on the current runway have been updated and the representation of the obstacle has changed.

## Main view wrapper - GUI (07/07)

Hallie starts the application and is greeted with a splash screen that prompts her to load an airport to begin.
In the top menu bar the only options she has are to create or load an airport.
She chooses one of these options and sees that the menu items update according the view she is on.
She has the option to return to the runway view or toggle the colour scheme from the menu bar.

