# Web Project Archetype v.4.0.2

[![Build Status](https://travis-ci.org/lucapompei/WebServerTemplate.svg?branch=master)](https://travis-ci.org/lucapompei/WebServerTemplate) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![CodeFactor](https://www.codefactor.io/repository/github/lucapompei/WebServerTemplate/badge)](https://www.codefactor.io/repository/github/lucapompei/WebServerTemplate)

=============================

This maven project represents a generic web template based on Spring Boot 2 Framework.


Summary features
-------

- Maven project based on Spring Boot 2 Framework;
- Spring MVC configuration;
- Spring Security configuration (BasicAuth or JwtAuth or none);
- Spring JDBC + H2 embedded configuration;
- Untertow embedded;
- CORS configuration;
- Logback basic configuration;
- Swagger auto-generated;
- Dockerfile pre-configuration;
- Jenkins pre-configuration;
- OpenShift pre-configuration;
- Fully customizable through the environment properties.


Usage
-------

Clone or download the maven archetype and then:

- Install the archetype

```
  mvn install
```

- Create a new project starting from the archetype
	
```
  mvn archetype:generate
```


License
-------

  Copyright (C) 2019 lucapompei
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
