package dev.stas.mvidecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import dev.stas.mvidecompose.presentation.component.DefaultRootComponent
import dev.stas.mvidecompose.ui.content.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = DefaultRootComponent(defaultComponentContext())
        setContent {
            RootContent(root)
        }
    }
}