package com.example.together_watch.promise

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.together_watch.data.Promise

interface PromiseAcceptContract {

    interface View {
        @Composable
        fun Buttons(context: Context)
        @Composable
        fun PromiseAcceptScreen(promise: Promise)
    }

    interface Model {
        suspend fun getGroupPromise(ownerId: String, groupId: String): Promise
    }
}