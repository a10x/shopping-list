package uk.ac.le.co2103.hw4;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Unit fromString(String unit){
        return unit == null ? null : Unit.valueOf(unit);
    }

    @TypeConverter
    public static String unitToString(Unit unit){
        return unit == null ? null : unit.name();
    }

}
