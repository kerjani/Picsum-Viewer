# Picsum Viewer :camera:

An image viewer application implemented using the MVVM pattern, Hilt, Retrofit2, LiveData, ViewModel, Coroutines, Room, Navigation Components, Data Binding and some other libraries from the [Modern Android Development](https://developer.android.com/modern-android-development) blueprint.

## Features

The Picsum Viewer app fetches data from the [Lorem Picsum API](https://picsum.photos/) and shows the images.

* The main screen is a grid the with the images shown along with their author and id.
    * The count of the columns is based on the device and its orientation.  
      * On phones there
        * is 1 column in portrait mode, and
        * are 2 columns in landscape mode.
      * On tablets
        * in portrait mode there are 2 columns, while
        * in landscape there are 3 columns.
    * The grid supports lazy loading of the items. The number of the items loaded at a time is customizable, by default its value is 30.
    * Ability to refresh the result from the remote API with a swipe to refresh gesture.
* There is a detail screen where more data is shown about the specific image .
    * The animation between the grid and the detail is based on the [shared element transition](https://material.io/develop/android/theming/motion#container-transform).
    * The image in the details screen fades out smoothly as the Toolbar is scrolled up.
    * There are 3 options on the screen to support some basic popular filters on the image:
      * blur (photo icon): it switches a blurring filter on the image.
      * grayscale (dots icon): it switches a grayscale filter on the image
      * reset (recycle icon): resets both 2 filters mentioned above

## Technologies used:

The main purpose of the application is to showcase the recent architecture best practices

* [Retrofit](https://square.github.io/retrofit/) a REST Client for Android.
* [Hilt](https://dagger.dev/hilt/) for dependency injection.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
* [Navigation Component](https://developer.android.com/guide/navigation) a single-activity architecture to handle all navigation and also passing of data between destinations with [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data) plugin.
* [Glide](https://bumptech.github.io/glide/) for image loading.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. `writing to and reading from the database`. Coroutines help in managing background threads and reduces the need for callbacks.
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) to declaratively bind UI components in layouts to data sources.
* [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Android KTX](https://developer.android.com/kotlin/ktx) provides concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.

## LICENSE
```
MIT License

Copyright (c) 2021 János Kernács

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
