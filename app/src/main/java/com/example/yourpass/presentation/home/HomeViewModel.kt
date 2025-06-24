package com.example.yourpass.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.keemobile.kotpass.constants.BasicField
import app.keemobile.kotpass.constants.PredefinedIcon
import app.keemobile.kotpass.models.Entry
import app.keemobile.kotpass.models.EntryFields
import app.keemobile.kotpass.models.EntryValue
import com.example.yourpass.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _entries = MutableStateFlow<List<Entry>>(emptyList())
    val entries: StateFlow<List<Entry>> = _entries

    init {
        // Initialize the list with sample data or load from a repository
        _entries.value = loadEntries()
    }

    private fun loadEntries(): List<Entry> {
        return listOf(
            Entry(
                UUID.randomUUID(),
                icon = PredefinedIcon.Key,
                fields = EntryFields.of(
                    BasicField.Title() to EntryValue.Plain("pass tele"),
                    BasicField.Password() to EntryValue.Plain("Ht@12354"),
                    BasicField.UserName() to EntryValue.Plain("toan3"),
                )
            ),
            Entry(
                UUID.randomUUID(),
                icon = PredefinedIcon.Key,
                fields = EntryFields.of(
                    BasicField.Title() to EntryValue.Plain("pass tele"),
                    BasicField.Password() to EntryValue.Plain("Ht@12354"),
                    BasicField.UserName() to EntryValue.Plain("toan3"),
                )
            ),
            Entry(
                UUID.randomUUID(),
                icon = PredefinedIcon.Key,
                fields = EntryFields.of(
                    BasicField.Title() to EntryValue.Plain("pass tele"),
                    BasicField.Password() to EntryValue.Plain("Ht@12354"),
                    BasicField.UserName() to EntryValue.Plain("toan3"),
                )
            ),
            Entry(
                UUID.randomUUID(),
                icon = PredefinedIcon.Key,
                fields = EntryFields.of(
                    BasicField.Title() to EntryValue.Plain("pass tele"),
                    BasicField.Password() to EntryValue.Plain("Ht@12354"),
                    BasicField.UserName() to EntryValue.Plain("toan3"),
                )
            ),
            Entry(
                UUID.randomUUID(),
                icon = PredefinedIcon.Key,
                fields = EntryFields.of(
                    BasicField.Title() to EntryValue.Plain("pass tele"),
                    BasicField.Password() to EntryValue.Plain("Ht@12354"),
                    BasicField.UserName() to EntryValue.Plain("toan3"),
                )
            ),
        )
    }
}