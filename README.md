## Overview
This is a sample Android application that demonstrates an architectural design approach. 

## Modules
The application is split into the following modules. 

### Common module
This module contains:
- Class extensions
- Helper classes
- Some common dependencies

### ApiClient module
This module contains:
- Data classes describing requests/responses 
- Network services
- Retrofit interfaces,
- Interceptors

### Domain module
This is domain module with:
- Business logic
- Models
- Gateway interfaces
- Interactors

### App module
This is main app module with:
- Toothpick modules
- Toothpick scopes
- Navigation schemes
- Fragments
- View models
- Custom views
- Gateways implementation

A diagram of the modules is presented below: 
![](https://github.com/SergeyBukarev/MovieApp/blob/master/images/scheme_1.png?raw=true)


## Features of this implementation:

### RxAction
This is a library that creates `Action` from any `Rx` `Observable`. `Action` contains additional Observables that helps you to bind to the ui. 
These Observables are: 
- isExecuting
- errors
- values

This idea is taken from the iOS library `ReactiveCocoa`. This greatly simplifies the work with ui binding to show the current state of the application. 

Example:
```
model.loadDetailsAction.isExecuting.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(views.activityIndicatorView.visibility())
model.loadDetailsAction.errors.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::showError)
model.loadDetailsAction.values.observeOn(AndroidSchedulers.mainThread()).autoDispose(this).subscribe(::initViews)
```
The `model.loadDetailsAction` declared as

```
val loadDetailsAction: Action<Unit, DetailedMovie> = Action.fromSingle(AndroidSchedulers.mainThread()) {
	movieDetailsInteractor.getMovieDetails() // This is Single that return details through network api
}
```

### Toothpick Scopes
This library is needed to open child toothpick scopes whose life cycle is bonded to the fragments. When the fragment is opened, a child scope is opened, and when the user closes the fragment and it is permanently destroyed, then the scope is closed too.
For example, on the details screen, a child scope is opened that contains `@MovieId`. `@MovieId` can be injected anywhere within this fragment scope. 

## State saving 
This app can handle screen rotation but can't handle process killing. For properly handling the death of a process you can use one of the methods below
 - Android Architecture Components View Model
 - Room + onSaveInstanceState method
 - Or something else
 
## How it's looks like
![](https://github.com/SergeyBukarev/MovieApp/blob/master/images/screen_1.png?raw=true)
![](https://github.com/SergeyBukarev/MovieApp/blob/master/images/screen_2.png?raw=true)

## How to run this app
To run this application, use this api key in `build.gradle` `c963063f038aefdac3efe5083ea6d962` and replace all `eights` to `sevens` in this api key. 

