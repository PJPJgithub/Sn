/*
* Copyright (C) GM Global Technology Operations LLC 2024
* All Rights Reserved.
* GM Confidential Restricted.
*/

package com.gm.carcontrolsim.data.permission

import com.gm.carcontrolsim.domain.gateway.RecordPermissionGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
interface PermissionModule {

    @Binds
    @Singleton
    fun bindRecordPermissionGateway(impl: RecordPermissionGatewayImpl): RecordPermissionGateway

    @Binds
    @Singleton
    fun bindPermissionDataSource(
        impl: AndroidPermissionDataSource
    ): PermissionDataSource

}
