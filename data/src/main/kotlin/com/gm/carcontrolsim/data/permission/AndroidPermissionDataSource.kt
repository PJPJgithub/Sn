package com.gm.carcontrolsim.data.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.gm.carcontrolsim.domain.entity.PermissionStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidPermissionDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionDataSource {
    override fun getRecordPermission(): PermissionStatus {
        val permissionValue =
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        return when (permissionValue) {
            PackageManager.PERMISSION_GRANTED -> PermissionStatus.GRANTED
            PackageManager.PERMISSION_DENIED -> PermissionStatus.NOT_GRANTED
            else -> {
                Log.e(TAG, "Unknown permission value: $permissionValue")
                PermissionStatus.NOT_GRANTED
            }
        }
    }

    companion object {
        private const val TAG = "AndroidPermissionDataSource"
    }
}
