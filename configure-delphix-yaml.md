# Configure delphix.yaml

At the center of the Delphix Automation Framework is the `delphix.yaml` file. It defines the datasources for your application environments and what actions Delphix should take during CI/CD automation. As with other 'configuration as code' and project files, the `delphix.yaml` file is versioned in your repository.

If you are not familiar with the YAML file format, there are a few things to keep in mind such as you must indent with spaces and not tabs. More information is available [here](http://yaml.org/).

## Key Concepts

The `delphix.yaml` file defines a one to one relationship between an application and a Delphix Self-Service Template.

*   Templates are created by the Delphix administrator and consist of the data sources that users need in order to manage their data playground and their testing and/or development environments.
*   Templates serve as the parent for a set of data containers that the administrator assigns to users.
*   Templates enforce the boundaries for how data is shared.

Each application environment should have a corresponding definition in the environments collection

*   Environments can have multiple Actions
*   Environments must use a GIT branch to relate to the application environment

### Given -> When -> Then

Actions for environments are defined using the Given -> When -> Then convention. For example, given the `staging` environment is being built, when the event that triggered the build is a GIT `push`, then `refresh` the datapod for the `staging` environment.

```
environments:
  - given:
    when:
      - event: then
  - staging:
    when:
      - push: refresh
```

## Keywords
|Keyword|Description
|---|---
|[template](#template)|The name of the Delphix Self-Service template for this project.
|[connectors](#connectors)|A collection of database connector references.
|[environments](#environments)|A collection of application environments, their datapods, and the actions to take during the CI/CD pipeline.
|[host](#host)|Reference for the application host in KMS.
|[port](#port)|Reference for the application port in KMS.
|[database](#database)|Reference for the application database in KMS.
|[username](#username)|Reference for the application username in KMS.
|[password](#password)|Reference for the application password in KMS.
|[branch](#branch)|The source GIT branch for a specific Application Environment.
|[datapod](#datapod)|The Delphix Datapod for a specific Application Environment.
|[when](#when)|The collection of Actions for a specific Application Environment.
|[then](#then)|The Delphix Self Service action executed for this `when` trigger.


### <a id="template"></a>template

The name of the Delphix Self-Service template.

### <a id="connectors"></a>connectors

The collection of database connector key references for each data source. Do not commit any connector values to your version control. These are only key references, not values. E.g. database_name vs dbName.

### <a id="environments"></a>environments

The collection of application environments, their GIT branches, the datapod used as the database, and the collection of Actions to be executed during the CI/CD build phase. The names of the collections are arbitrary and only used for developer identification.


### <a id="host"></a>host

Application database configuration for the host name. This is the name of the configuration, not the value. In the case of `spring.datasource.url=jdbc:postgresql://localhost/questionmarks`, `spring.datasource.url` is the host alias that should be stored.

### <a id="port"></a>port

Application database configuration for the port name.

### <a id="database"></a>database

Application database configuration for the host name.

### <a id="username"></a>username

Application database configuration for the username. This is the name of the configuration, not the value. In the case of `spring.datasource.username=postgres`, `spring.datasource.username` is the username alias that should be stored.

### <a id="password"></a>password

Application database configuration for the password. This is the name of the configuration, not the value. In the case of `spring.datasource.password=mysecretpassword`, `spring.datasource.password` is the password alias that should be stored.

### <a id="branch"></a>branch

The GIT branch from which the environment will be built. E.g. The `production` environment is built from the `master` branch.

### <a id="datapod"></a>datapod

The name of the datapod used for the database of the environment. Must have been created from the template for the project.

### <a id="when"></a>when

The collection of Actions for an environment based on the Given -> When -> Then format described above.

### <a id="then"></a>then

The specific Self Service API call to be executed during this Action. Acceptable values: `datapod.refresh`, `datapod.create`, `datapod.delete`, `bookmark.share`, `bookmark.create`, `datapod.undo`, and `bookmark.delete`.

## Overwrites

Each environment has the ability to overwrite individual values for connectors or data-sources. A specific environment might need to use a different AMI or have different database connector keys.

```
environments:
  - staging:
      branch: staging
      datapod: staging-pod
      connectors:
        - postgres:
            database: differentDbName
      when:
        - push: refresh

```

## Sample

```
template: Sample Template
parent: dSource Name
connectors:
  - postgres:
      host: host_ref_in_kms
      port: kms_ref
      database: db_name
      username: kms_ref
      password: kms_ref
environments:
  - staging:
      branch: origin/staging
      datapod: Staging
      when:
        - push: datapod.refresh
  - uat:
      branch: origin/testing
      datapod: test
      when:
        - push: datapod.refresh
        - pull-request-opened: datapod.create
        - pull-request-closed: datapod.delete
  - develop:
      branch: origin/feature/unit-tests
      datapod: Develop
      when:
        - custom: datapod.refresh
        - build-failure: datapod.undo
```
