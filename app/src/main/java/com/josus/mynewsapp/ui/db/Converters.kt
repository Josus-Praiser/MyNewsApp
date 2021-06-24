package com.josus.mynewsapp.ui.db

import androidx.room.TypeConverter
import com.josus.mynewsapp.ui.data.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }

}