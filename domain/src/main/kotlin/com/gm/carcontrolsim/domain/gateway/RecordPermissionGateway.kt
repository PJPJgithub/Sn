package com.gm.carcontrolsim.domain.gateway

import com.gm.carcontrolsim.domain.entity.PermissionStatus
import com.gm.carcontrolsim.domain.entity.Response

interface RecordPermissionGateway {
    fun getRecordPermissionStatus(): Response<PermissionStatus>
}
