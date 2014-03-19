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

#### Get the code
```
git clone https://github.com/rmylonas/MetaDB
```
#### Run unit tests
```
grails test-app unit:
```
They should all pass..

#### Prepare your configuration
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

## User documentation

#### Main workflow

![MetaDB workflow](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/workflow.png "MetaDB workflow")

ISAtab files for Metabolomics are best created with ISAcreator from [MetaboLights](http://www.ebi.ac.uk/metabolights/)). ISAtab files can be uploaded to **MetaDB** as a ZIP file. Data will be verified and selected data are imported to **MetaMS**. 
Run sequences of selected Assays are randomized and can be exported as CSV files. CSV files are imported to your MS instruments for data acquisition. Acquired data is than again imported to **MetaMS**, where it can be further processed. The processing includes feature detection and identification against Compound databases. This part is done using the open source R library [MetaMS](https://github.com/rwehrens/metaMS).

#### Search page

![MetaDB search page](https://github.com/rmylonas/MetaDB/raw/master/resources/markdown-resources/search_page.png "MetaDB search page")

After login or when clicking on **MetaDB**, you are redirected to the main search page. You can search for terms such as organism names or sample factors. You can either search on the level of *Studies* or *Assays* (look at the ISAtab documentation for more detailed information about the meaning of those terms). By checking the box *Show all users*, you can also search for entries created by other users.

#### User management

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



## Developer documentation

## License

For all code derived from ISAcreator, the following CPAL licence applies:

CPAL License, available at <http://isatab.sourceforge.net/licenses/ISAcreator-license.html>

Other code sections go under the MIT Licence:

Copyright (C) <2013> <copyright Roman Mylonas>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
