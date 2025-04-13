package com.example.myapplication.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import javax.inject.Provider


class SavedStateViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) {
    /**
     * Creates the actual factory instance needed by ViewModelProvider.
     * Call this method from your Fragment or Activity.
     * The Android framework provides 'owner' and 'defaultArgs' when you request
     * a ViewModel using ViewModelProvider(owner, factory).
     *
     * @param owner The SavedStateRegistryOwner (usually the Fragment or Activity 'this').
     * @param defaultArgs Optional default arguments Bundle (e.g., fragment.arguments).
     * @return A ViewModelProvider.Factory capable of handling SavedStateHandle.
     */
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle?): ViewModelProvider.Factory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            /**
             * This method is called by the framework to create the ViewModel.
             * It receives the SavedStateHandle instance.
             */
            override fun <T : ViewModel> create(
                key: String, // Key used internally by SavedStateHandle
                modelClass: Class<T>,
                handle: SavedStateHandle // Provided by AbstractSavedStateViewModelFactory!
            ): T {
                // Find the Dagger provider for the requested ViewModel class
                val creator = creators[modelClass] ?: creators.entries.firstOrNull {
                    modelClass.isAssignableFrom(it.key)
                }?.value ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass for DaggerSavedStateViewModelFactory")

                // Get the ViewModel instance from Dagger's provider
                try {
                    @Suppress("UNCHECKED_CAST")
                    val viewModel = creator.get() as T

                    if (viewModel is SavedStateViewModel) {
                        viewModel.setSavedStateHandle(handle)
                    }

                    return viewModel
                } catch (e: Exception) {
                    throw RuntimeException("Failed to create ViewModel for $modelClass", e)
                }
            }
        }
    }
}

