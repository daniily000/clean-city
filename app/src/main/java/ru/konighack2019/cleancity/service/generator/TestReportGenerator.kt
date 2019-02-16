package ru.konighack2019.cleancity.service.generator

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.R
import ru.konighack2019.cleancity.model.KSReport
import ru.konighack2019.cleancity.model.UserInfo
import ru.konighack2019.cleancity.service.ReportGenerator
import ru.konighack2019.cleancity.service.common.OperationState
import java.lang.Exception
import java.util.*

class TestReportGenerator : ReportGenerator {
    override fun createEsooReport(imageUris: List<Uri>): LiveData<OperationState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createKSReport(imageUris: List<Uri>): LiveData<OperationState> {
        generationState.postValue(OperationState.PROCESSING)
        generateReport(imageUris)
        return generationState
    }

    private val geocoder = Geocoder(AppDelegate.applicationContext(), Locale.getDefault())
    private val locationProvider: FusedLocationProviderClient by AppDelegate.getKodein().instance()

    private lateinit var pendingReport: KSReport
    private val generationState = MutableLiveData<OperationState>()

    override fun getState() = generationState
    override fun getReport() = pendingReport

    private fun generateReport(photo: List<Uri>) {

        GlobalScope.launch {
            try {
                if (ContextCompat.checkSelfPermission(
                        AppDelegate.applicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    locationProvider.lastLocation.addOnSuccessListener {
                        val addr = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        //val user = dataService.getUserInfoBlocking()
                        val user = UserInfo("test", "test@test.io", "+71234567890")

                        pendingReport = KSReport(
                            AppDelegate.applicationContext().getString(R.string.report_subject),
                            String.format(AppDelegate.applicationContext().getString(R.string.report_description), addr[0].getAddressLine(0)),
                            addr[0].getAddressLine(0),
                            user.name,
                            user.email,
                            user.phone,
                            addr[0].adminArea,
                            "1",
                            "0",
                            it.latitude.toString() + "," + it.longitude.toString(),
                            photo
                        )
                        generationState.postValue(OperationState.SUCCESS)
                    }
                }
            } catch (ex: Exception) {
                generationState.postValue(OperationState.FAILED)
            }
        }
    }



}