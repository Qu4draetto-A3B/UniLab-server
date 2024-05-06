# UniLab-server
Server monitoraggio climatico

## Database
### CoordinateMonitoraggio
- geoid **PK**
- name
- countrycode
- latitude
- longitude

### OperatoriRegistrati
- userid **PK**
- name
- surname
- CF
- email
- password
- center **FK**

### CentriMonitoraggio
- centerid **PK**
- name 
- address
- areas

### ParametriClimatici
- center **FK**
- area **FK PK**
- date **PK**
- operator **FK PK**
- wind
- humidity
- pressure
- temperature
- precipitation
- glaciersAltitude
- glaciersMass