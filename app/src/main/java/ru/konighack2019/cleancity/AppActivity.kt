package ru.konighack2019.cleancity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseApp
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.presentation.generator.GeneratorFragment
import ru.konighack2019.cleancity.presentation.validation.ImageValidationFragment
import ru.konighack2019.cleancity.presentation.validation.LocationValidationFragment
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.common.AppState

class AppActivity : AppCompatActivity() {

    private val appService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        FirebaseApp.initializeApp(this)

        appService.appState.observe(this, Observer { changeAppState(it) })
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }

    fun changeAppState(state: AppState) =
        when (state) {
            AppState.GENERATION -> replaceFragment(GeneratorFragment())
            AppState.VALIDATION_IMAGE -> replaceFragment(ImageValidationFragment())
            AppState.VALIDATION_LOCATION -> replaceFragment(LocationValidationFragment())
            AppState.HISTORY -> {}
        }

}
