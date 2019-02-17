package ru.konighack2019.cleancity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_app.*
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
    private val locationProvider: FusedLocationProviderClient by AppDelegate.getKodein().instance()
    private lateinit var googleApiClient: GoogleApiClient

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
        googleApiClient = GoogleApiClient.Builder(AppDelegate.applicationContext()).addApi(LocationServices.API).build()
        googleApiClient.connect()
        requestUpdates()
        appService.showHistory()
        checkPermissions(permissions) { init() }
    }

    private fun requestUpdates() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10
        locationRequest.fastestInterval = 50
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
            }
        }
        if (ContextCompat.checkSelfPermission(AppDelegate.applicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationProvider.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private fun init() {
        FirebaseApp.initializeApp(this@AppActivity)
        appService.appState.observe(this@AppActivity, Observer { changeAppState(it) })
        appService.errors.observe(this@AppActivity, Observer { showSnackBar(it) })
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

    override fun onDestroy() {
        super.onDestroy()
        googleApiClient.disconnect()
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
    private fun showSnackBar(text: String) {
        if (text.isNotEmpty()) {
            Snackbar.make(root_layout, text, Snackbar.LENGTH_SHORT).show()
        }
    }
}
