package ru.konighack2019.cleancity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseApp
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.presentation.camera.CameraFragment
import ru.konighack2019.cleancity.presentation.generator.ReportFragment
import ru.konighack2019.cleancity.presentation.results.ResultsFragment
import ru.konighack2019.cleancity.presentation.validation.ImageValidationFragment
import ru.konighack2019.cleancity.presentation.validation.LocationValidationFragment
import ru.konighack2019.cleancity.presentation.validation.PostingReportFragment
import ru.konighack2019.cleancity.presentation.validation.ReportGenerationFragment
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.common.AppState

class AppActivity : AppCompatActivity() {

    private val appService: AppStateService by AppDelegate.getKodein().instance()

    private val permissions = listOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        FirebaseApp.initializeApp(this)
        appService.appState.observe(this, Observer { changeAppState(it) })
        appService.showCamera()
        checkPermissions(permissions) { init() }
    }

    private fun init() {
        FirebaseApp.initializeApp(this@AppActivity)
        appService.appState.observe(this@AppActivity, Observer { changeAppState(it) })
    }

    private fun checkPermissions(permissions: Collection<String>, onSuccess: () -> Unit) {
        Dexter
            .withActivity(this)
            .withPermissions(permissions)
            .withListener(createPermissionListenerWithCustomOnSuccess(onSuccess))
            .check()
    }

    private fun createPermissionListenerWithCustomOnSuccess(onSuccess: () -> Unit): MultiplePermissionsListener =
        object: MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report?.areAllPermissionsGranted() != false) {
                    onSuccess.invoke()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }



    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }

    fun changeAppState(state: AppState) =
        when (state) {
            AppState.GENERATION -> replaceFragment(ReportGenerationFragment())
            AppState.VALIDATION_IMAGE -> replaceFragment(ImageValidationFragment())
            AppState.VALIDATION_LOCATION -> replaceFragment(LocationValidationFragment())
            AppState.REPORT_READY -> replaceFragment(ReportFragment())
            AppState.HISTORY -> replaceFragment(ResultsFragment())
            AppState.POSTING_REPORT -> replaceFragment(PostingReportFragment())
            AppState.CAMERA -> replaceFragment(CameraFragment())
        }

}
