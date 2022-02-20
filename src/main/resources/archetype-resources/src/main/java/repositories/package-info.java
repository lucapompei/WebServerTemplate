#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.repositories;

/*
 * This package contains all the interfaces used to get data from database with queries (both native or not).
 * Like other package, the implementation of the interface, when needed, should be locateded in the impl sub-package.
 *
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/">Spring Data JPA</a>
 *
 */