package com.homindolentrahar.moncovid.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.homindolentrahar.moncovid.util.Constant

data class CovidDailyResponse(
    @SerializedName("data")
    val data: List<CovidDailyResult>
)

@Entity(tableName = Constant.DAILY_TABLE)
data class CovidDailyResult(
    @PrimaryKey
    @SerializedName("harike")
    val hariKe: Int,
    @SerializedName("tanggal")
    val tanggal: Long,
    @SerializedName("jumlahKasusBaruperHari")
    val kasusBaruPerHari: Int,
    @SerializedName("jumlahKasusSembuhperHari")
    val sembuhPerHari: Int,
    @SerializedName("jumlahKasusMeninggalperHari")
    val meninggalPerHari: Int,
    @SerializedName("jumlahKasusKumulatif")
    val kasusKumulatif: Int,
    @SerializedName("jumlahPasienSembuh")
    val sembuh: Int,
    @SerializedName("jumlahPasienMeninggal")
    val meninggal: Int
)

data class CovidProvinceResponse(
    @SerializedName("data")
    val data: List<CovidProvinceResult>
)

@Entity(tableName = Constant.PROVINCE_TABLE)
data class CovidProvinceResult(
    @PrimaryKey
    @SerializedName("kodeProvi")
    val kodeProvinsi: Int,
    @SerializedName("provinsi")
    val provinsi: String,
    @SerializedName("kasusPosi")
    val positif: Int,
    @SerializedName("kasusSemb")
    val sembuh: Int,
    @SerializedName("kasusMeni")
    val meninggal: Int
)