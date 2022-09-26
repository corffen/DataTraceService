package com.gordon.study

fun main(args: Array<String>) {
    val sensorService = DataTrackServiceManager2.create(SensorService::class.java)
    sensorService.enterLive("zhang san", 18, mutableListOf("a", "b", "c"), intArrayOf(1, 2, 3))
//    println("1=$sensorService")
    val sensorService2 = DataTrackServiceManager2.create(SensorService::class.java)
//    println("2=$sensorService2")
    sensorService2.exitLive("lisi", 28, mutableListOf("a", "b", "c"), intArrayOf(1, 2, 3))
}