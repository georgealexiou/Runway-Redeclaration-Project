# Scenarios

## Top-Down View - GUI

### No Obstacle
Chris starts the runway redeclaration tool and loads in 'Heathrow Airport'.
He is presented with a top-down view of the first runway in 'Heathrow' - '09L27R'.
On the right hand side of the screen is a panel where he can change the runway and see the
original values for each logical runway.
He can select the logical runway to focus on in the top left.
An arrow in the top right updates to reflect the landing and takeoff direction when he changes logical runway.
The currently selected threshold should be highlighted with an orange box.
The original runway values should be displayed, including a displaced threshold if one is present.
The runway centre line should be visible, as should the clearway and stopway at each end of the runway if applicable.

### Obstacle
Given that Chris has started the application, loaded an airport and selected a runway he should be able to choose a
predefined obstacle from a list on the lower right hand side. 
Upon selecting the obstacle, it should appear on the runway in the expected location.
The recalculated values for the currently selected threshold should be displayed based on the obstacle.
Factors such as slope calculation, blast protection, strip end and runway end safety area should also be displayed.
All values should be labelled.
Chris can select to change the currently selected logical runway in the top left.
Upon doing so, the values should all update to reflect the impact the obstacle has on this logical runway.

## Side-On View - GUI

### No Obstacle
Hallie starts the runway redeclaration tool and loads in 'Heathrow Airport'
She is presented with the side-on view of the first runway in 'Heathrow' - '09L27R'
On the right hand side of the screen is a panel where she can change the runway and see the original values for each logical runway.
She has the choice of two thresholds to focus on: '09L' and '27R', these are listed in the top left corner and can be selected.
Upon selecting '27R' the values on the 2D side-on view should update to reflect the differing values for '27R'.
Each of the original parameters should be visible: TORA, TODA, ASDA and LDA. In addition to this, the displaced threshold should be shown.
Since there is no obstacle present, there should be NO representation of the takeoff and landing slope.
The currently selected threshold should be highlighted so that it is clear to Hallie which logical runway she is prioritising.

### Obstacle
Given the Hallie has loaded the application and an airport and selected a logical runway, she should be given the choice to load an obstacle from a list of predefined obstacles.
Hallie chooses to click on the button, this causes a menu to appear where she selects the obstacle and gives it some required values: 'Distance from Left', 'Distance from Right' and 'Distance from Centre Line'. 
She can then click on the 'Add to Runway' button to complete the process and add it to the runway.
The recalculated values should appear on the visualisation, and where necessary be broken down into the factors that cause the values to change such as the 'blast protection', 'strip end' and 'slope calculation'.
She should also see a representation of the obstacle.
She opts to change the logical runway and sees that the values change to reflect this.

## Airport Configuration - GUI

### Existing Airport - Editing
Chris wants to change some details about a previously defined runway. He clicks the 'Edit' button from the sidebar and is presented with the airport configuration
view with the details of the runway loaded in.
He is able to change all of the values, including the deletion of a runway.
Upon completing the changes he is able to save the runway for the current session or export it to the file it was loaded from.
He chooses to export it to the file as it is not a temporary change. This updates it for the current session and the changes are written to disk.

### New Airport - Creating
Jules wants to configure a new airport on the tool so that she can roll out the software in other airports.
She starts the application and clicks the 'Add Airport' button.
She is able to enter a name and select a number of runways. The number of runways must be at least 1.
At this point, she sees that the 'Save' and 'Export' buttons are greyed out since the airport is incomplete.
She enters a name and selects to have 2 runways.
A stacked list of Runways appears with 'Runway 1 -' and 'Runway 2 -' as their names. She clicks 'Runway 1 -' and the runway configuration opens. 
She can now select the number of logical runways/thresholds from a number picker that limits her to between 1 and 3.
For each logical runway, a form appears allowing her to choose the 'Heading', 'Position' and Runway Parameters for each runway and optionally the 'Displaced Threshold'.
She completes the values for one of the logical runways but moves on to the other runway before completing the other.
She sees a message next to 'Runway 1 -' that tells her there are incomplete logical runways.
She completes all of the required fields and sees that the 'Save' and 'Export' buttons are now available.
She chooses to 'Export' as this is to be used for more than one session. A file dialog opens and she selects a location. The file is written to disk and loaded for the current session.

## Calculation Breakdown - GUI

Chris wants to view the breakdown of calculations for his current sessions so that he can compare them with his paper calculations to further his training.
He has added an obstacle and sees the option to select 'View Breakdown' from the sidebar. Upon clicking this, he is presented with breakdowns for all of the logical runways.
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

### Load Obstacle - Loading

Chris wants to load a predefined obstacle to help in his understanding of the program's function. He clicks the 'Load Obstacle' button from the side panel. 
The 'Load an Obstacle' screen (variation of Create an Obstacle) is shown with the name, description and height preloaded.
He is asked to provide the distances from the centre line, left and right before continuing.
Upon completing the fields he can press 'Save' and the obstacle will be available in the current session.

### Edit Obstacle - Editing

Chris has noticed that the values he entered previously are incorrect. He selects to edit the current obstacle.
The 'Edit Obstacle' view is shown and he enters the modified values. He cannot 'Export' as the name, description and height did not change.
He chooses 'Save' and immediately sees the values on the current runway have been updated and the representation of the obstacle has changed.

## Main view wrapper - GUI

Hallie opens the application and sees the sidebar. It contains several buttons, initially the only ones available are 'Load Airport' and 'Create Airport'.
After an airport is loaded the 'Load Obstacle' and 'Create Obstacle' buttons are no longer greyed out and the airport details are shown in a panel of the sidebar.
A drop-down list of obstacles is available to select from a list of predefined obstacles. She can select an obstacle and the recalculated values for the current
logical runway are shown.
She can also select to toggle the view from 'Top-Down' to 'Side-On' and vice versa or see the calculation breakdown.


