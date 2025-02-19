/*
 * Copyright (C) GM Global Technology Operations LLC 2024.
 * All Rights Reserved.
 * GM Confidential Restricted.
 */

package com.gm.carcontrolsim.data.permission

import com.gm.carcontrolsim.domain.entity.PermissionStatus

interface PermissionDataSource {
    fun getRecordPermission(): PermissionStatus
}
