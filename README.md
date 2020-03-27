[![Build Status](https://travis-ci.com/mariodavid/cuba-component-db-localization.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-db-localization)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-db-localization/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-db-localization/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

CUBA component - DB Localization
======================

This application component let's you manage your localization in the DB instead of properties files in the application.
This way you can at runtime of the application create and change translations. Since it is possible to do in application
screens, translations can be easily managed by people outside of development.

## Installation

1. `db-localization` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.2.x            | 0.4.x          |
| 7.1.x            | 0.3.x          |
| 7.0.x            | 0.1.x - 0.2.x  |

The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-db-localization/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-db-localization/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.dblocalization`
* Artifact name: `dblocalization-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.dblocalization:dblocalization-global:*addon-version*")
}
```

### Example usage

To see this application component in action, check out this example: [cuba-example-using-db-localization](https://github.com/mariodavid/cuba-example-using-db-localization).


## Supported DBMS

This application component ships with DB init / update scripts for the following DBMS:

* HSQLDB
* PostgreSQL
* MySQL

Any other DBMS is still supported, but the DDL statements have to be provided manually. CUBA Studio automatically
generates the DDL scripts when the application component is used but for the selected DBMS no DDL scripts are automatically
delivered.


## Using the application component

Once the application component is installed the following new Menu entry is available in the Administration menu: `Localizations`.

![overview](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/0-overview.gif)


### Application properties

In order to activate the DB based lookup for message keys, the following configuration property has to be placed in `web-app.properties` and `app.properties`:

```
dblocalization.enabled = true
```

By default, the DB based lookup is deactivated.

### Translation Overview

The translation overview screen is designed to manage the translations. New translations can be created or existing translations
can be changed.

![translations-overview](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/1-translations-overview.png)

Once the translations are created or changed, the button `Apply changes` will store the changes in the database. Furthermore
the `Messages` bean will clear its cache and now serve the changed translations.

### Initial Localization Import

The initial localization import is available to import the existing localization files (messages.properties) from the [translations github repository](https://github.com/cuba-platform/translations/).

![initial-localization-import](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/2-initial-localization-import.png)

The files from the translations repository have to be imported for every module and every language:

```
.
└── modules
    ├── core
    │   └── src
    │       └── com
    │           └── haulmont
    │               └── cuba
    │                   └── core
    │                       ├── messages.properties
    │                       └── messages_de.properties
    ├── desktop
    │   └── src
    │       └── com
    │           └── haulmont
    │               └── cuba
    │                   └── desktop
    │                       ├── messages.properties
    │                       └── messages_de.properties
    ├── global
    │   └── src
    │       └── com
    │           └── haulmont
    │               └── cuba
    │                   ├── messages.properties
    │                   └── messages_de.properties
    ├── gui
    │   └── src
    │       └── com
    │           └── haulmont
    │               └── cuba
    │                   └── gui
    │                       ├── messages.properties
    │                       └── messages_de.properties
    └── web
        └── src
            └── com
                └── haulmont
                    └── cuba
                        └── web
                            ├── messages.properties
                            └── messages_de.properties
```

### Message Resolution

The application component extends the existing message lookup functionality in the following form:

It will lookup the message key in the database. If it is found, it will use that translation and cache the result
in the corresponding module (core / web).

If it is not found in the database, it will pass the message lookup on to the frameworks functionality, which will
then try to find the message either in the classpath (via messages.properties files) or in the config directory.

More information on the message lookup can be found in the CUBA documentation on [Messages Localization](https://doc.cuba-platform.com/manual-7.0/localization.html).

#### Performance

Since the messages are lookup up from the database, the lookup takes longer then regular message lookups.
Furthermore it passes on message lookups on from the frontend layer to the backend layer, so that the DB can be taken into
consideration.

Practically this means, that in case a screen is opened up for the first time after the application server starts,
it will take longer to load. Once the screen was loaded, the messages are cached (either in the backend or the frontend),
which means that the speed from that moment on is back to normal.

In a phase of development, were a lot of application restarts occur, this slows down the runtime of the application.
Therefore it is a good idea, to turn off the application component during initial development phases.

