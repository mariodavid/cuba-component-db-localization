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
| 7.0.x            | 0.1.x          |

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


## Using the application component

Once the application component is installed the following new Menu entry is available in the Administration menu: `Localizations`.


### Translation Overview

The translation overview screen is designed to manage the translations. New translations can be created or existing translations
can be changed.

![translations-overview](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/translations-overview.png)

Once the translations are created or changed, the button `Apply changes` will store the changes in the database. Furthermore
the `Messages` bean will clear its cache and now serve the changed translations.

### Initial Localization Import

The initial localization import is available to import the existing localization files (messages.properties) from the [translations github repository](https://github.com/cuba-platform/translations/).

![initial-localization-import](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/initial-localization-import.png)

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

### Example usage
To see this application component in action, check out this example: [cuba-example-using-db-localization](https://github.com/mariodavid/cuba-example-using-db-localization).
