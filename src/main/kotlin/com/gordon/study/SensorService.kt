package com.gordon.study

interface SensorService {
    @TrackMethod2("sensorTrack_Name")
    fun enterLive(
        @TrackMethodPara2 name: String,
        @TrackMethodPara2 age: Int,
        @TrackMethodPara2 dataList: List<String>,
        @TrackMethodPara2
        dataArray: IntArray
    )

    @TrackMethod2("sensorTrack_exit")
    fun exitLive(
        @TrackMethodPara2 name: String,
        @TrackMethodPara2 age: Int,
        @TrackMethodPara2 dataList: List<String>,
        @TrackMethodPara2
        dataArray: IntArray
    )
}