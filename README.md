MetaDB
======
MetaDB is an open-source web application for metadata management and data processing of metabolomics data. It is based on [ISA tab](http://www.isa-tools.org/) as the input format. The analysis of untargeted data is done using the R package [MetaMS](https://github.com/rwehrens/metaMS). This software is a project from Fondazione Edmund Mach. 

## Prerequisites
Following software has to be installed for proper functioning (other software versions may work, but were not tested):
* Java 1.7
* Tomcat 6
* MySQL 5.5
* Grails 2.2.3

## Installation

### Get the code
```
git clone https://github.com/rmylonas/MetaDB
```
### Run unit tests
```
grails test-app unit:
```
They should all pass..

### Prepare your configuration
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

##### create and deploy your war file:

create a *.war* file with your settings by executing following command from your MetaDB folder:
```
grails war
```

copy the *.war* file to your tomcat *webapps* folder. 

access the main page and login as administrator
```
Username: admin
Password: admin
```

##### installing R packages:




If you want to use the PCA plotting option, you have to install the *PCA* library to your local **R** installation:
```
sudo R -e 'install.packages("./resources/R/PCA_0.0.2.1.tar.gz", repos = NULL, type="source")'
```

## User documentation

### Main workflow

![MetaDB workflow](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/workflow.png "MetaDB workflow")

ISAtab files for Metabolomics are best created with ISAcreator from [MetaboLights](http://www.ebi.ac.uk/metabolights/)). ISAtab files can be uploaded to **MetaDB** as a ZIP file. Data will be verified and selected data are imported to **MetaMS**. 

Run sequences of selected Assays are randomized and can be exported as CSV files. CSV files are imported to your MS instruments for data acquisition. Acquired data is than again imported to **MetaMS**, where it can be further processed (**MetaDB** currently only supports *.CDF* files).

The processing includes feature detection and identification against Compound databases. This part is done using the open source R library [MetaMS](https://github.com/rwehrens/metaMS).

### Login

![MetaDB login page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/login_page.png "MetaDB login page")

Login using your credentials. See the **User management** section for further details on how to create and manage users. 

### Search

![MetaDB search page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/search_page.png "MetaDB search page")

After login or when clicking on **MetaDB**, you are redirected to the main search page. You can search for terms such as organism names or sample factors. You can either search on the level of *Studies* or *Assays* (look at the ISAtab documentation for more detailed information about the meaning of those terms). By checking the box *Show all users*, you can also search for entries created by other users.

### Upload

![MetaDB upload page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/upload_page.png "MetaDB upload page")

ISAtab files can be uploaded as a ZIP file. **Attention: make sure to directly compress the ISAtab files, and not any folders. Compression done by _ISAcreator_ will not work.** Make sure that your Study identifier is unique and that your instrument and methods were created in **MetaDB**.

Once uploaded, you can select the *Assays* you want to import. If you added any *Assay* to an existing ISAtab file and reload this file, they new *Assay* will appear for selection as well. Already imported *Assays* can not be changed. To change them, you first have to remove them and reimport the new versions. 

When uploading, you have to choose your group, project and instrument method. If your setting is missing, an user with administration rights has to insert your settings into **MetaDB**. Depending on your method, randomization of your run sequences will be done. 

### Load and View

#### Load an Assay

In order to be able to enter into the *View* menu, you first have to load your *Assay* of interest. To see the list of *Assays* you can either go from the *Search* page or from the *Load* page. By clicking on the *Access code*, you will automatically redirected to either the *Planned runs* or *Acquired runs* view, depending on the status of this *Assay*. 

#### 3 different views

From the menu *Views* you can access 3 different views:

##### 1. Planned runs

![MetaDB planned runs](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/planned_runs.png "MetaDB planned runs")

This view shows the sequence of *Runs* as they were planned. You can download the CSV file of this sequence by pressing on the *Download CSV* button. This sequence can then be used to setup your instrument. 

During the process of acquiring, you might change the sequences (e.g. add addiotional Quality Controls). You can change the order and add *QC* (quality controls), *STDmix* (standard mix) and *Blanks* as you want. **Attention: you have to make sure that every name is unique. Otherwise MetaDB won't be able to connect your files to the names.**

![MetaDB add acquisitions](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/add_acquisition_sequence.png "MetaDB add acquisitions")

Once you finished your acquisition, you can add your actual run sequence by clicking on *Add acquisition*. Juste copy paste the sequence (newline seperated) into the textfield. You will automatically be redirected to *Acquired runs*. This step can be repeated as many times as you want. 

##### 2. Acquired runs

![MetaDB acquired runs](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/acquired_runs.png "MetaDB acquired runs")

In this view you can see the sequence you actually acquired. Runs can have to different status: *acquired* and *processed*. A blue *acquired* means, that this run is ment to be acquired, but the extracted file (CDF or mzXML) weren't added yet. 

Extracted files can be added by uploading a ZIP file containing files in *CDF* or *mzXML* format (click on *Upload* -> *Extracted files*). Since the *Assay name* is unique, files are automatically connected to the relevant run. Once an extracted file was succesfully added to a run, its status changes to a green *processed*. 

Runs of untargeted data, with status *processed*, can be further submited to MetaMS for feature detection and PCA visualization. See the next section for details.

To get the acquired runs back to your ISAtab file, you can download your data as a CSV file (click on *Download* -> *CSV*). From the CSV file you can copy/paste your data into your ISAtab file.

##### 3. MetaMS submission and PCA plots

![MetaDB acquired runs](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/acquired_runs.png "MetaDB acquired runs")

### Statistics

![MetaDB statistics](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/statistics_runs.png "MetaDB statistics")

On this page you can see some simple statistics about the usage of your resources.

### Settings

#### Users

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

#### Groups and Projects

You can manage groups and projects they belong to. This information is not part of ISA tab. You cannot remove any groups or projects which are used by any *Studies*.

#### Instrument and Methods



#### Organism onthologies


## Connection to *ISAcreator* via plugin




## Developer documentation

## License

For all code derived from ISAcreator, the following CPAL licence applies:

CPAL License, available at <http://isatab.sourceforge.net/licenses/ISAcreator-license.html>

Other code sections go under the MIT Licence:

Copyright (C) <2013> <copyright Roman Mylonas>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
