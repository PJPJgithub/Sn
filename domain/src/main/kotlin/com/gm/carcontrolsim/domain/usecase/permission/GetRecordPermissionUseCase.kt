package com.gm.carcontrolsim.domain.usecase.permission

import com.gm.carcontrolsim.domain.entity.PermissionStatus
import com.gm.carcontrolsim.domain.entity.Response
import com.gm.carcontrolsim.domain.gateway.RecordPermissionGateway
import javax.inject.Inject

// Actually, Permission can be implemented into the Activity.
// This is just to show how UseCase can be designed and implemented.
class GetRecordPermissionUseCase @Inject constructor(
    private val recordPermissionGateway: RecordPermissionGateway,
) {
    operator fun invoke(): Response<PermissionStatus> {
        return recordPermissionGateway.getRecordPermissionStatus()
    }
}
