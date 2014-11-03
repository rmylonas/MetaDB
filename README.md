MetaDB
======
MetaDB is an open-source web application for Metabolomics metadata management and data processing. It is based on [ISA tab](http://www.isa-tools.org/) as metadata input format. The analysis of untargeted data is done using the R package [MetaMS](https://github.com/rwehrens/metaMS). This software is a project of Fondazione Edmund Mach. 


# Overview

1. Workflow
	* Introduction
	* ISAtab creation
	* MS data acquisition and conversion
	* Data processing and visualization
	* Data submission to public repositories
2. User documentation
	* Access
	* Login
	* Search
	* Upload
	* Load and View
	* Settings
3. Installation
	* Prerequisites
	* Get the code
	* Run unit tests
	* Prepare your configuration
	* Install R packages
4. License


# 1. Workflow

## Introduction

![MetaDB workflow](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/workflow.png "MetaDB workflow")

ISAtab files for Metabolomics are best created with ISAcreator from [MetaboLights](http://www.ebi.ac.uk/metabolights/)). ISAtab files can be uploaded to **MetaDB** as a ZIP file. Selected Assays are then imported to **MetaMS**. 

MetaDB takes care of the randomization of given samples. The randomized sample list can be exported in a CSV format and afterwards be used to setup your MS acquisition. Acquired runs are imported back to **MetaMS**, where they can be further processed (**MetaDB** currently supports *.CDF*, *.MzXML* and *.MzData* files).

The data processing is based on the open source R library [MetaMS](https://github.com/rwehrens/metaMS). The processing includes feature detection and identification against compound databases, followed by some visualizations for data quality control. 

## ISAtab creation

Before starting your experiment, an ISAtab file containing the metadata information of your experiment(s) has to be created. To assure compatibility, ISAtab files should be created using MetaboLights. *Investigation*, *Studies* and *Assays* have to be created as described in the [MetaboLights documentation](http://www.ebi.ac.uk/metabolights/). 

While entering the *Assay* informations, you can leave free the field *MS Assay Names*. *MetaDB* will take care of sample randomization and adding standard mixes and blanks (see section *Instrument and Methods* for further details). 

After all relevant metadata was entered and saved, the folder containing the ISAtab files can be zipped and uploaded to *MetaDB*. Make sure to directly zip the files and not the superfolder. The uploaded ISAtab will be validated against the installed validation setting.


## MS data acquisition and conversion

After data upload, samples will be randomized and blanks and standard mixes added to the final acquisition sequence. The created acquisition sequence can be exported as an Excel (.csv) file. This format can be imported or copy-pasted into, to our knowledge, all MS instrument software. By using this acquisition sequence, you assure proper *MS Assay Name's*, which is important for further data-processing.

During the acquisition step you might be obliged to repeat certain parts or add additional blank injections. Since unique *MS Assay Name's* are essantial for proper filename mapping, additional injections should have a unique name. This can for instance be acheaved by adding a *_2* tag at the end of every *MS Assay Name* you want to repeat. 

Once you finished your acquisition, you have to load back the final acquisition to *MetaDB*. This is done in two consecutive steps. First by indicating the final order and naming of your acquisitions, and second by uploading raw and extracted files. 

For the final naming, *MetaDB* expects a list of all *MS Assay Name* in same order as you acquired your samples. This list can be copy/pasted directly from the MS instrument control software, or alternatively from an Excel sheet. This step can be repeated as much as needed. This allows you to change or repeat some of the acquisition, in case their quality was not satisfactional in the first place. 

Spectra have to be packed into a ZIP archive and uploaded trough the Web-interface. Alternatively, if lab scientists and *MetaDB* have access to a shared disk, files can directly be copied into the relevant directory. Extracted files have to be created manually using the specific option of the MS instrument software, or using external tools like [Proteowizard](http://proteowizard/sourceforge.net/). 


## Data processing and visualization

Once extracted files are added (indicated by a blue *processed* tag), data can be analyzed using *MetaMS*. Runs are selected from the user interface and a description can be added. The retention time can be restricted (e.g. ignoring the first and last minutes for the analysis) and feature annotation can be activated, in case there is a valid database installed. Data can be processed using different settings, as many times as desired. 

After data processing is finished, the experiment can be visualized for quality control. Data can be colored according to factors defined in the ISAtab file. Data can be normalized using square root scaling and TIC normalizationn. There are 5 different plots available. 3 variations of PCA plots, an importance plot and a plot showing the total intensity sum of every MS run. 

![PCA plot](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/PCA_1.png "PCA plot")
One can see a good seperation for the factor *Variety* and the quality controls (a mixture of all samples) are located well in the middle of this PCA plot. 

![PCA plot showing 4 PC](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/PCA_2.png "4 PC plot")
Sometimes the first 2 PC do not show the factors you are looking for. This might be due to the fact, that the first two PC's show artifacts, such as different sample handling or time of acquisition. Showing the first 4 PC is also interesting in the situation where you have many different factors (which might be a sign of a bad experimental design though..).

![PCA plot with names](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/PCA_3.png "PCA plot with names")
This is again a plot of the first 2 PC, except for that the dot's are replaced by the *MS Assay Name's*. This can be useful if outliesrs in an experiment have to be identified. 

![Intensity sum plot](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/Intensity_sum_plot.png "Intensity sum plot"). This plot shows the sum of intenisities of all features together. It gives an indication weither there was a shift in the experiments. 

![Importance_plot.png](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/Importance_plot.png "Biplot"). This plot visualizes the features with the biggest variation. The direction of arrow shows by which PC they are predominantly seperated. 

![RSD_plot.png](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/RSD_plot.png "RSD"). The Relative Standard Deviation (RSD) plot of the QC injections gives an overall idea of the acquisition quality. 


RData and CSV files containing containing the results can be downloaded for further visualization and statistical analysis. Please have a look at [MetaMS documenetation](https://github.com/rwehrens/metaMS) for further details about data structure of the resulting files. 

## Data submission to public repositories

Before submission to a public data repository, such as *MetaboLights*, the final ISAtab file including the data has to be constructed. For this purpose data has to be loaded from *MetaDB* back to *ISATab creator*. Currently you download a CSV file containing all relevant columns, which can be copied to the ISATab creator. Raw spectral data files can be re-downloaded in a ZIP compressed format, if required. The links to the files might have to be adapted in *ISATab creator* according to their location on your local hard drive. 


# 2. User documentation

## Access

After installation MetaDB can be accessed using a web browser by following URL (you might have to adapt the server adress):
```
http://localhost:8080/MetaDB
```

## Login

![MetaDB login page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/login_page.png "MetaDB login page")

Login using your credentials. To test the software, you can use the Test user:

```
Username: test
Password: test
```

See the **User management** section for further details on how to create and manage users. 

## Search

![MetaDB search page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/search_page.png "MetaDB search page")

After login, or when clicking on **MetaDB**, you are redirected to the main search page. You can search for terms such as organism names or sample factors. You can either search on the level of *Studies* or *Assays* (look at the ISAtab documentation for more detailed information about the meaning of those terms). By checking the box *Show all users*, you can also search for entries created by other users.

## Upload

![MetaDB upload page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/upload_page.png "MetaDB upload page")

ISAtab files can be uploaded in the ZIP format. **Attention: make sure to directly compress the ISAtab files, and not any folders containing these files. Compression done by _ISAcreator_ will not work.** Make sure that your Study identifier is unique and that your instrument and methods exist in **MetaDB**.

Once uploaded, you can select the *Assays* you want to import. If you added any *Assay* to an existing ISAtab file and reload this file again, the new *Assay* will appear for selection as well. Already imported *Assays* can not be changed. To change them, you first have to remove them and reimport the new versions. 

When uploading, you have to choose group, project and instrument method. If your setting is missing, an user with administration rights has to insert your settings into **MetaDB**. Depending on your method, randomization of your samples will be done while imported. 

## Load and View

### Load an Assay

In order to be able to enter into the *View* menu, you first have to load your *Assay* of interest. To see the list of *Assays* you can either go from the *Search* page or from the *Load* page. By clicking on the *Access code* you will redirected to the *Planned runs* view. 

### 3 different views

From the menu *Views* you can access 3 different views:

#### 1. Planned runs

![MetaDB planned runs](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/planned_runs.png "MetaDB planned runs")

This view shows the sequence of MS acquisitions as they were planned. If your import method is conifgurated to do the randomization for you, this sequence will differ from the one in the original ISAtab file. You can download a CSV file containing the planned MS acquisition names by pressing on the *Download CSV* button. This sequence can then be used to setup your MS instrument using your propriatary instrument management software.

During the process of acquiring, you might have to change the sequences and number of MS acquisitions (e.g. add addiotional Quality Controls). You can change the order and add *QC* (quality controls), *STDmix* (standard mix) and *Blanks* as you want. **Attention: you have to make sure that every name is unique. Otherwise MetaDB won't be able to connect your raw files to the assay names.**

![MetaDB add acquisitions](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/add_acquisition_sequence.png "MetaDB add acquisitions")

Once you finished acquiring your data on your MS instrument, you can add the final list of runs by clicking on *Add acquisition*. Juste copy paste the list of your MS assay names (newline seperated) into the textfield. You will automatically be redirected to *Acquired runs*. This step can be repeated as many times as you want, in case you have to add some further runs to your Assay. 

#### 2. Acquired runs

![MetaDB acquired runs](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/acquired_runs.png "MetaDB acquired runs")

In this view you can see the sequence you actually acquired. Runs can have two different status: *acquired* and *processed*. A blue *acquired* means, that this run was acquired, but the extracted file (CDF or mzXML) weren't added yet. 

Extracted files can be added by uploading a ZIP file containing files in *CDF* or *mzXML* format (click on *Upload* -> *Extracted files*). Since the *Assay name* is unique, files are automatically connected to the relevant run. Once an extracted file was succesfully added to a run, its status changes to a green *processed*. 

Runs of status *processed* can be further submited to MetaMS for feature detection and PCA visualization. Please see the next section for details.

To get the final list of acquired runs back to your ISAtab file, you can download your data as a CSV file (click on *Download* -> *CSV*). From the CSV file you can copy/paste your data into your ISAtab file.

#### 3. MetaMS submission and PCA plots

![MetaMS submission](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/metams_submission.png "MetaMS submission")

 Acquired runs can be submitted to MetaMS for feature detection and PCA visualization. From the view *Acquired runs* you can check *Processed* runs you want to submit, and then start a MetaMS submission by clicking on the *Start MetaMS* button. You can set a description, limit the retention time window and activate feature annotation. For feature annotation, a database has to be installed (look in the *Settings -> MetaMS* for more details). 

 Once started, the status of MetaMS submissions can be observed from the MetaMS view. You can see more details by clicking on the submission itself. After having finished, the results can be visualized using PCA plots or a ZIP file containing results in CSV and RData format can be downloaded. 

## Settings

### Users

Two users are created by default:
```
Username: test
Password: test
```
and
```
Username: admin
Password: admin
```
The administrator has additional rights, like managing Instruments, Methods and Projects. He is also allowed to delete entries from all users.

When creating a new User, you also have to indicate a foldername (typically same as username), which will be created automatically. 

### Groups and Projects

You can manage groups and projects. This allows you to better classify your data.

Groups are typically different work entities, such as laboratory sections. Projects allow you to organize your entries according to common topics.

Both information, Groups and Projects, are neither parsed from ISA tab, nor kept when exporting. This information is mainly used to better organize your data.


### Instrument and Methods

All your instruments have to be specified before importing data from ISAtab files. Please make sure that your *ISAtab name* corresponds to the instrument name indicated in the ISAtab files you upload. 

For every instrument you can add as many *Methods* as you want. For each *Method* there are a certain number fields to set:
- **Name**: the name shown in the interface
- **Tag at end of filenames**: this tag will be added to every *MS Assay Name* automatically created by *MetaDB*
- **Randomization start pattern**: here you can change the geometry of randomized seqeunces generated. *5.blank-1.STDmix-2.QC* means that at the beginning of every sequences there will be 5 blank injections, followed by 1 standart mix and 2 quality controls.
- **Randomization repeat pattern**: here the sequence randomization pattern is defined. *3.sample-1.QC* means that after every 3 randomized samples, there will be one quality control.
- **Randomization end pattern**: the sequence add the end. *1.STDmix-5.blank* means that after all sequences were randomized, an additional standard mix followed by 5 blank injections will be added.
- **MetaMS database**: any installed MetaMS database can be selected.
- **MetaMS settings name**: the name of the MetaMS setting. The R objects containing the corresponding settings have to be placed in the MetaDB configuration directory under *conf/metaMS/InstrumentSettings/*.

### MetaMS

For feature detection and annotation, the R package MetaMS is used. This package gives the possibility to add your own database of compounds which are used for the annotation of detected features. Such a database has to be constructed as a specific R object and saved as an RData file. Please see MetaMS documentation for further details about how to construct a database.

From this interface you simply indicate the local path to your database.
	
### Organism onthologies

From the Organism onthology interface, you can define new onthologies for your ISATab creator. Please make sure to install  and configure the necessary ISATab creator plugin as described in *Connection to ISATab creator via plugin*. 

Organisms which are added using this interface, will get available as proposed organisms when searching for onthologies in ISATab creator. Alternative names work like keywords, and will make show up the relevant entries. 

### Usage statistics

![MetaDB statistics](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/statistics_runs.png "MetaDB statistics")

On this page you can see some simple statistics about the usage of your resources.


# 3. Installation

## Prerequisites
The following software are needed for proper functioning (MetaDB may also work with other software versions, but was not tested):
* Java 1.7
* Tomcat 6
* MySQL 5.5
* Grails 2.2.3
* R 3.0.1

## Get the code
```
git clone https://github.com/rmylonas/MetaDB
```
## Run unit tests
```
grails test-app unit:
```
They should all pass..

## Prepare your configuration
##### set correct folders in *grails-app/conf/Config.groovy*:

```metadb.isatab.metabolConfigFile = [path to your ISAcreator configuration file]``` 
(configuration folder can be copied from *resources/conf/MetaboLightsConfig20130507* or taken from [MetaboLights](http://www.ebi.ac.uk/metabolights/))

```metadb.dataPath = "[your data folder]"``` (make sure this directory exists and is empty)

```metadb.conf.metams.script = "[R script folder]"``` (copy folder from *resources/conf/metaMS*)

```metadb.conf.metams.instrumentSettings = "[RData MetaMS instrument settings]"``` (copy folder from *resources/conf/instrumentSettings*)

```metadb.conf.metams.databases = "[RData MetaMS databases]"``` (copy folder from *resources/conf/databases*)


##### set your database settings in *grails-app/conf/DataSource.groovy*:
Set user, password and the correct URL to your database. Make sure to create a database called *MetaMS*. That's also the place to change settings, in case you want to use PostgreSQL instead (look at Grails documentation for further info).
```
    url = "jdbc:mysql://localhost/MetaDB"
    username = "root"
    password = "1234"
```

##### set directory for search indexing in *grails-app/conf/Searchable.groovy*:
Create and set directory, where MetaDB can save it's indexes
```
    compassConnection = new File(
        "[path to indexes]"
    ).absolutePath
```

##### create and deploy war file:

create a *.war* file with your settings by executing following command from MetaDB folder:
```
grails war
```

copy the *.war* file to your local tomcat *webapps* folder. 

access the main page and login as administrator
```
Username: admin
Password: admin
```

## install R packages

Install MetaMS from Bioconductor. 

For PCA plotting, install the *PCA* library to your local **R** installation:
```
sudo R -e 'install.packages("./resources/R/PCA_0.0.2.1.tar.gz", repos = NULL, type="source")'
```


# 4. License

For all code derived from ISAcreator, the following CPAL licence applies:

CPAL License, available at <http://isatab.sourceforge.net/licenses/ISAcreator-license.html>

Other code sections go under the MIT Licence:

Copyright (C) <2013> <copyright Roman Mylonas>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
