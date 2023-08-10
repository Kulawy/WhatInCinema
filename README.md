
<!-- # What In Cinema -->
<div align="center"><img src="assets/WhatInCinemaLogo.png" height=320px alt="WhatInCinema application logo" style=" background-image: radial-gradient(white 0%, transparent 75%, transparent 40%)"></div>


***My Android project Built with Coroutines, Flow and Jetpack Components, Jetpack Compose. Experimenting with some MVI/MVVM architecture. Probably current architecture is MVI/MVP as state is separate by Screens and each Screen has it's own ViewModel.
Try to implement it using Clean Architecture but not everything is as much clean as it
should be - need to improve.
I made it to update my knowledge about newest Tools and Trends in Android Development.
I will try to update code to use latest stuff but I treat it more like training room or
something.***

## <br/><br/>Screenshot :rice_scene:
![Movie list screenshot](assets/MovieListScreen.png?raw=true)
![Movie details screenshot](assets/MovieDetailsScreen.png?raw=true)
![Movie search screenshot](assets/SearchScreen.png?raw=true)

## <br/><br/>Built With :toolbox:

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Flow](https://kotlinlang.org/docs/flow.html)
- [Compose](https://developer.android.com/jetpack/compose)
- [Navigation](https://developer.android.com/guide/navigation)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Hilt](https://dagger.dev/hilt/)
- [Paging 3.0](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Retrofit](https://square.github.io/retrofit/)
- [Kotlin Serializer](https://)
- [Coil](https://github.com/chrisbanes/accompanist/blob/main/coil/README.md)
- [Material 3](https://m3.material.io/)
- [Material 3 Search](https://m3.material.io/components/search/overview)

## <br/>Useful links

- [Material 3 for Compose] (https://developer.android.com/jetpack/compose/designsystems/material3)

## <br/>How to run this app :runner:

- Clone this repository
- Register in  [TMDB](https://developers.themoviedb.org/) and get the authorization token. Copy it
  and paste to local.properties in main project catalog.
  <img src=https://www.themoviedb.org/assets/2/v4/logos/v2/blue_short-8e7b30f73a4020692ccca9c88bafe5dcb6f8a62a4c6bc55cd9ba82bb2cd95f6c.svg alt="themoviedb api logo" height="50px">
- Build the application

#### <br/><br/> I faced couple interesting things while app creation process. Most of them are related to using Arndroid specific tools like Pagging 3 or Jetpack Compose in term of Clean MVI/MVVM architecture. Maybe some article ? We will see. Stay tuned"
## <br/>Conclusions 
- ### Architecture not so clean :see_no_evil:
> As I experiment a little with Paging 3.0 library, I don't think It would be a way (or it's not so
> obvious) to implement it with domain layer. The Domain layer ( talking about UseCase) that
> communicate with UI layer by Result. I need to dig further and think about it.


