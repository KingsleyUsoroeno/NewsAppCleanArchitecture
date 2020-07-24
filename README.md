# NewsAppCleanArchitecture
### Getting Started 

## Installation
NewsAppCleanArchitecture is my playground and defination on how to go about building a modern day android Application following best practises such as a clean Architecture and 
making use of todays jetpack libraries, it requires a  minimum API level of 16, feel free to clone the repository and shoot in a PR if you feel like something has or a pattern has been done
wrong.
The App Makes use of some of the most popular Jetpack libraries which has been listed below

## Libraries Used

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
* [Navigation Component](https://developer.android.com/guide/navigation) to handle all navigations and also passing of data between destinations.
* [Material Design](https://material.io/develop/android/docs/getting-started/)
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. `writing to and reading from the database`. Coroutines help in managing background threads and reduces the need for callbacks.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) to declaratively bind UI components in layouts to data sources.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.

## Architecture
The architecture of this application relies and complies with the following points below:
* A single-activity architecture, using the [Navigation Components](https://developer.android.com/guide/navigation) to manage fragment operations.
* Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)(MVVM) which facilitates a separation of development of the graphical user interface.
* [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable

#### Modules
The App is very much modularised into different packages to ensure a clean design code all of which performs different roles in the MVVM Structure of the App.
Those packages includes.
* business
* di
* framework

Which encapsulates the apps core features with business handling most of the Apps core logic such as network request and database caching.

### Screenshots

<p float="left">
  <img src="https://github.com/KingsleyUsoroeno/NewsAppCleanArchitecture/blob/kius/support/refactor-news-model/app/src/main/res/screenshots/book_mark_screen.png" width="300" />
  <img src="https://github.com/KingsleyUsoroeno/NewsAppCleanArchitecture/blob/kius/support/refactor-news-model/app/src/main/res/screenshots/home_screen.png" width="300" /> 
  <img src="https://github.com/KingsleyUsoroeno/NewsAppCleanArchitecture/blob/kius/support/refactor-news-model/app/src/main/res/screenshots/search_loading_screen.png"   width="300" />
</p>

<p float="left">
  <img src="https://github.com/KingsleyUsoroeno/NewsAppCleanArchitecture/blob/kius/support/refactor-news-model/app/src/main/res/screenshots/search_loading_screen.png" />
  <img src="https://github.com/KingsleyUsoroeno/NewsAppCleanArchitecture/blob/kius/support/refactor-news-model/app/src/main/res/screenshots/news_search_result.png" />
</p>
