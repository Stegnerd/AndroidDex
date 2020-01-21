# AndroidDex

This is an attempt to learn Kotlin/Android development. It was based majorly on https://github.com/android/architecture-samples . Many things were learned. Two way binding with ui interaction. Database is offline and loads from a json asset file. 

# Objectives
1. Learn the basics of MVVM in android 
2. Basics of dependency injection with Dagger 2
3. Navigation/State management
4. Testing (Instrumentation/Unit)
5. File/Asset manipulation

# Libraries
1. com.amitshekhar.android:debug-db - Debugging Room Databases
2. Espresso - Testing Runner
3. Hamcrest - Tetsing Assertion
3. Room - Databases
4. Timber - Logging

# Achievments
1. Using workmanager seed database on install only, from json asset file. (https://github.com/Stegnerd/AndroidDex/blob/master/app/src/main/java/com/stegner/androiddex/dependencyinjection/workers/DataBaseSeedWorker.kt)
2. Dynamically build resourceid for use of binding ui images and links.(https://github.com/Stegnerd/AndroidDex/blob/master/app/src/main/java/com/stegner/androiddex/pokemonlist/PokemonListAdapter.kt)
3. Grasped a basic understanding of mvvm architecture. 
4. Designed Custom Recycler ItemDecoration 
5. Created popup menu filter (https://github.com/Stegnerd/AndroidDex/blob/master/app/src/main/java/com/stegner/androiddex/pokemonlist/PokemonListFragment.kt)


# Todos
1. Complete Instrumentation tests. Current issue applies - Tests pass one at a time fail together. (check current issues)

# Future plans
Once I get better at android development I have several goals for this app. I am taking a break from this app for a little while to experiment with different ideas.
1. Create a more modular design, instead of a monolith approach.
2. Create Instrumentation unit tests when I get better at testing.
3. Implement a caught/stat to let user keep track of the ones caught
4. Add more detail, like moves or routes caught on the detail page
5. Add different sections, like moves/items

# Screenshots

![Landing Page](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/LandingPage.png) ![List View](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/List.png)
![Detail Vertical](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/Portrait%20Detail.png) ![Detail Horizontal](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/Landscape%20Detail.png)
![Drawer Actions](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/Drawer.png) ![Menu Filter](https://github.com/Stegnerd/AndroidDex/blob/master/Screenshots/Filter.png)
