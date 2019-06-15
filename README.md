[![Build Status](https://travis-ci.com/mariodavid/cuba-component-db-localization.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-db-localization)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-db-localization/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-db-localization/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

CUBA component - Db Localization
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
![translations-overview](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/translations-overview.png)

### Initial Translation Import
![initial-localization-import](https://github.com/mariodavid/cuba-component-db-localization/blob/master/img/initial-localization-import.png)


### Example usage
To see this application component in action, check out this example: [cuba-example-using-db-localization](https://github.com/mariodavid/cuba-example-using-db-localization).
