package com.stegner.androiddex.data

import com.stegner.androiddex.data.Result.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 *
 * ref: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-nothing.html
 * Nothing -> never returns, always throws an exception
 *
 * ref: https://kotlinlang.org/docs/reference/generics.html
 * ref: https://proandroiddev.com/understanding-generics-and-variance-in-kotlin-714c14564c47
 * out R means that the class Result is not a consumer of R but produces R's
 * R is a covariant type, meaning the success inner class preserves its typing
 *
 * covariant -> produces type T but cannot take type T but can return type T
 * kotlin out keyword, example is an ImmutableList
 *
 * contravariant -> consumes type T but cannot return type T
 * kotlin in keyword, example is Compare method
 *
 * covariant is going specific (Success<T>)  to less specific (Result<T>)
 * ex: List<Animal> = List<dog>, assigning a list of dog to a list of animal
 * produces animal but cannot take animal
 *
 * contravariant is going from generic to specific, works because animal is a superset of dog
 * ex: val dogCompare: compare<Dog> = animalCompare where animalCompare : Compare<animal>
 * takes in dog but returns animal
 */
sealed class Result<out R> {

    /**
     * Good result when data has been retrieved
     *
     * passing in type [T] returns a [Result] of type [T]
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     *  Bad Result when there has been an exception.
     */
    data class Error(val exception: Exception) : Result<Nothing>()

    /**
     * no data has been retrieved yet, "Variables do not exist yet"
     */
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            // * is wildcard for Success<T> but since we do not know the type at that time we play it safe with *
            is Success<*> -> "Success[Data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data]
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null
