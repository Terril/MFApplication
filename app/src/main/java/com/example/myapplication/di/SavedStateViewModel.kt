package com.example.myapplication.di

import androidx.lifecycle.SavedStateHandle

interface SavedStateViewModel {
    fun setSavedStateHandle(savedStateHandle: SavedStateHandle)
}