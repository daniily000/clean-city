package ru.konighack2019.cleancity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val REQUEST_CODE = 101


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("kek_tag", "Permission  denied")
            makeRequest()
        } else{
            Log.i("lol_tag", "Permission granted")
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.READ_PHONE_STATE),
            REQUEST_CODE
        )
    }








    private val appService: AppStateService by AppDelegate.getKodein().instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        FirebaseApp.initializeApp(this)

        setupPermissions()

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



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("kek_tag", "Permission has been denied by user")
                } else {
                    Log.i("lol_tag", "Permission has been granted by user")

                }
            }
        }
    }

}
