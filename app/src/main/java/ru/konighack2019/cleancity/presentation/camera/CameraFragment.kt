package ru.konighack2019.cleancity.presentation.camera

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.fotoapparat.Fotoapparat
import io.fotoapparat.result.PhotoResult
import kotlinx.android.synthetic.main.camera_layout.*
import org.kodein.di.generic.instance
import ru.konighack2019.cleancity.AppDelegate
import ru.konighack2019.cleancity.service.AppStateService
import ru.konighack2019.cleancity.service.DataService
import java.io.File

const val TEMP_FILE_NAME = "temp_file"
const val TEMP_FILE_SUFFIX = "tmp"
class CameraFragment: Fragment() {

    private val appStateService: AppStateService by AppDelegate.getKodein().instance()
    private val dataService: DataService by AppDelegate.getKodein().instance()

    lateinit var fotoapparat: Fotoapparat
    lateinit var photoResult: PhotoResult

    private val tempFile = File.createTempFile(TEMP_FILE_NAME, TEMP_FILE_SUFFIX)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.camera_layout, container, false)

        fotoapparat = Fotoapparat(
            context = view.context,
            view = view.camera_view
        )

        return view
    }

    override fun onStart() {
        super.onStart()
        fotoapparat.start()

        fotoapparat
            .takePicture()
            .saveToFile(tempFile)

        button_view.setOnClickListener {
            photoResult = fotoapparat.takePicture()

            photoResult.saveToFile(tempFile)

            photoResult
                .toBitmap()
                .whenAvailable { bitmapPhoto ->
                    dataService.imageUri = Uri.parse(tempFile.path)
                }

            appStateService.showHistory()
        }
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }
}