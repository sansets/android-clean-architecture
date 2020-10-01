
# android-clean-architecture
This is a sample project that presents a modern, 2020 approach to [Android](https://en.wikipedia.org/wiki/Android_(operating_system)) application development with up to date tech-stack.

The goal of the project is to demonstrate best practices by using up to date tech-stack and presenting modern Android application [Architecture](#architecture) that is modular, scalable, maintainable, and testable. This application may look quite simple, but it has all of these small details that will set the rock-solid foundation for the larger app suitable for bigger teams and long [application lifecycle](https://en.wikipedia.org/wiki/Application_lifecycle_management).


## Screenshots
Home	|	Favorite	|	Food Recipe List	|	Filter	|
:------:|:---------------------:|:-----------------------------:|:-------------:|
![](https://github.com/sansets/android-clean-architecture/blob/master/screenshots/Screenshot_20201001-164331388.jpg?raw=true)  |  ![](https://github.com/sansets/android-clean-architecture/blob/master/screenshots/Screenshot_20201001-164438602.jpg?raw=true)  |  ![](https://github.com/sansets/android-clean-architecture/blob/master/screenshots/Screenshot_20201001-164500548.jpg?raw=true?raw=true)  |  ![](https://github.com/sansets/android-clean-architecture/blob/master/screenshots/Screenshot_20201001-164508234.jpg?raw=true)


## Environment Setup
First off, you require the latest Android Studio 4.0.1 (or newer) to be able to build the app.

You need to supply API key for food recipe content displayed in the app. That is currently spoonacular API. You can find information about how to gain access via this link https://spoonacular.com/food-api.

When you obtain the key, you can provide them to the app by putting the following in the `local.properties` project root file:
```properties
#spoonacular API KEYS
spoonacular.key = "<insert>"
```


## Architecture

The architecture of the application is based, apply and strictly complies with each of the following 5 points:
-   A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage fragment operations.
-   [Android architecture components](https://developer.android.com/topic/libraries/architecture/), part of Android Jetpack for give to project a robust design, testable and maintainable.
-   Pattern  [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)  (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) of development of the graphical user interface.
-   [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID)  design principles intended to make software designs more understandable, flexible and maintainable.
-   [Modular app architecture](https://proandroiddev.com/build-a-modular-android-app-architecture-25342d99de82)  allows to be developed features in isolation, independently from other features.

## Tech-stack
Min API level is set to 21, so the presented approach is suitable for over 94% of devices running Android. This project takes advantage of many popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version unless there is a good reason to use non-stable dependency.
-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html)  - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx)  - major improvement to the original Android  [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [View Binding](https://developer.android.com/topic/libraries/view-binding)  - allows you to more easily write code that interacts with views/
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)  - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  - lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    -   [Navigation](https://developer.android.com/guide/navigation/)  - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room)  - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)  - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - managing background threads with simplified code and reducing needs for callbacks.
-    [Coroutines Flow](https://kotlinlang.org/docs/reference/coroutines-overview.html)  - cold asynchronous data stream that sequentially emits values and completes normally or with an exception
-   [Dagger2](https://dagger.dev/)  - dependency injector for replacement all Factory classes.
-   [Retrofit](https://square.github.io/retrofit/)  - type-safe HTTP client.
-   [Glide](https://github.com/bumptech/glide)  - image loading and caching library

## Authors
<img src="https://avatars3.githubusercontent.com/u/29242358?s=460&u=0dcec2c2073265c913f1fe30d163dab9941f3502&v=4" width="70" align="left">

**Sandi Setiawan**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/sandisetiawan444/) [![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@sandisetiawan444)

## License
```
Copyright 2020 Sandi Setiawan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
