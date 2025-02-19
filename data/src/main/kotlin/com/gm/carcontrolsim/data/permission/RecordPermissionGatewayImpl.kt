package com.gm.carcontrolsim.data.permission

import com.gm.carcontrolsim.domain.entity.PermissionStatus
import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.gateway.RecordPermissionGateway
import javax.inject.Inject

class RecordPermissionGatewayImpl @Inject constructor(
    private val androidPermissionDataSource: AndroidPermissionDataSource
) : RecordPermissionGateway {
    override fun getRecordPermissionStatus(): Response<PermissionStatus> {
        return Response.Success(androidPermissionDataSource.getRecordPermission())
    }
}
