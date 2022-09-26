package com.gordon.study

data class TraceDataRequest(val eventType: String, val eventName: String, val list: List<EventEntity>)

data class EventEntity(val name: String, val isList: Boolean, val isArray: Boolean,val componentType:Class<*>?)
