# wiki-school

This application is a work in progress; at the moment it does not 'do' anything. 
The end result will be a spring web application that will store various resources about a topic in my university course.    

#### Architecture:
The following are the most complex bits of this codebase:
  * Connection Objects package contains a set of packages that
   allow for easily changeable jdbc connection implementations.
   There are Three core packages within the ConnectionObjects package: 
    * Connection Abstraction: Only one class here is instantiated. It follows the strategy pattern, so that different implementations of a database may be used instead (Postgresql/Mongodb), however this behaviour is not completely implemented yet. 
    * Properties: This package follows a strategy pattern also, so that different implementations of the interface may be used. For example, reading properties from a file vs from a database.
    * Queries: Uses both the properties package, and ConnectionAbstraction package to (1) get properties, (2) execute queries. All queries are inside of a SqlQueryInformation object.
    
  * String formatting package inserts variable values into base sql statements, or into any string, using a delimiter.
   Not used for user provided information, only for known variables, like table names, column names etc...
  
  * The GeneralService class inside of the ServiceAbstraction package gives any service extending it a way to work with the database through the Query objects. 
 
  * The rest of the packages are either constants, simple services, or models.