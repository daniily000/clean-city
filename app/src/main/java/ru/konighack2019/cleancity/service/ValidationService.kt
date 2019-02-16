package ru.konighack2019.cleancity.service

import android.arch.lifecycle.LiveData
import android.net.Uri
import ru.konighack2019.cleancity.service.common.OperationState

/**
 * Service for remote db interactions. Contain methods to determine if the location
 * is valid to post a report.
 */
interface ValidationService {

    /**
     *  Detects if supplied images contain a dump (image recognition implementation)
     *  @param imageUris [List] with image [Uri]s to analyze
     *  @return [OperationState] of operation
     */
    fun imageContainsDump(imageUris: List<Uri>): LiveData<OperationState>
    
    /**
     *  Detects if user location is near sanctioned dump
     *  @return [OperationState] of operation
     */
    fun isUnsanctioned(): LiveData<OperationState>

    /**
     *  Detects whether there is an already reported unsanctioned dump on user's location
     *  @return [OperationState] of operation
     */
    fun isNotAlreadyReported(): LiveData<OperationState>

}