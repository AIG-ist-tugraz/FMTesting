# FMTesting
Automated Feature Model Analysis and Diagnosis: A Plug-in for FeatureIDE

## Prerequisites
Install Eclipse and following plug-ins from the marketplace:
* Eclipse PDE (Plug-in development environment)
* FeatureIDE

Please note that this is only guaranteed to work with Eclipse 4.24 (2022-06).

## Set-up
* In Eclipse, open the "Plug-in developement" perspective.
* Open the folder "FMTesting" (not the repository itself, but the folder inside!) as a project
* Open Manifest.MF, in the now open editor click on "Launch new eclipse application"

## Guideline
* Create a new FeatureIDE project
* If it has not happened automatically, open the FeatureIDE perspective
* In the bottom Editor, go to the Feature Model Diagnosis view
* Choose the anomaly types you want and click on "start diagnosis"

You can change model.xml in any way you want and then analyse it.
Please note that we do not recommend having more than 20 Features due to performance issues.

### Troubleshooting:
If you click on the button and nothing happens, try closing and opening the model.xml file. Also, make sure that you saved your latest changes as Eclipse does not do that automatically.
