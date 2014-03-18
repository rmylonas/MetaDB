MetaDB
======
MetaDB is an open-source web application for metadata management and data processing of metabolomics data. It is based on [ISA tab](http://www.isa-tools.org/) as the input format. The analysis of untargeted data is done using the R package [MetaMS](https://github.com/rwehrens/metaMS).

## Prerequisites
Following software has to be installed for proper functioning (other software versions may work, but were not tested):
* Java 1.7
* Tomcat 6
* MySQL 5.5
* Grails 2.2.3

## Installation

###### Get the code
```
git clone https://github.com/rmylonas/MetaDB
```
###### Run unit tests
```
grails test-app unit:
```
They should all pass..

##### Prepare your configuration
###### set correct folders in *grails-app/conf/Config.groovy*:

```metadb.isatab.metabolConfigFile = [path to your ISAcreator configuration file]``` 
(configuration folder can be copied from *resources/conf/MetaboLightsConfig20130507* or taken from [MetaboLights](http://www.ebi.ac.uk/metabolights/))

```metadb.dataPath = [your data folder]``` (make sure this directory exists and is empty)

2. set your database



###### Setup your environment



## User documentation

## Developer documentation

## License

For all code derived from ISAcreator, the following CPAL licence applies:

CPAL License, available at <http://isatab.sourceforge.net/licenses/ISAcreator-license.html>

Other code sections go under the MIT Licence:

Copyright (C) <2013> <copyright Roman Mylonas>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
