package com.example.myapplication.data

import com.google.gson.annotations.SerializedName


data class License(
    var key: String? = null,
    var name: String? = null,
    var spdx_Id: String? = null,
    var url: String? = null,
    var node_Id: String? = null

)