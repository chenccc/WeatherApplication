# WeatherApplication
Welcome to WeatherApplication. For this project, I make use of Hilt, MVVM, Retrofit, Room, Kotlin Coroutine, DrawerLayout and data binding to build the main structure.

## Getting Started

Check out the project.
```
git clone https://github.com/chenccc/WeatherApplication.git
```

In local.properties, add api key for open weather, like the following one.
api.key=xxxxxxxxx

## Main pages
In this project, there are two main pages, namely [HomeFragment](./app/src/main/java/james/weatherapplication/ui/home/HomeFragment.kt) and [DrawerFragment](./app/src/main/java/james/weatherapplication/ui/drawer/DrawerFragment.kt).

For HomeFragment, there is a search bar for user to search weather for specific city and will show the weather for last access city when app restarts.

For DrawerFragment, a list of recent search cities will be there. We can click the city name to get the weather for the city in HomeFragment. 
What is more, we can delete the history of each city.
