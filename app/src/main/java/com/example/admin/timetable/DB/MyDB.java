package com.example.admin.timetable.DB;

import android.provider.BaseColumns;

public final class MyDB {
    public static final class Create implements BaseColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String TIME = "time";
        public static final String GENRE = "genre";
        public static final String LINK = "link";
        public static final String ABSENT = "absent";
        public static final String SD = "sd";
        public static final String ED = "ed";
        public static final String FAVORITE = "favorite";

        public static final String _TABLENAME = "anissia_ani";
        public static final String _CREATE =
                "CREATE TABLE " + _TABLENAME + "(" +
                        ID + "INTEGER PRIMARY KEY," +
                        TITLE + "VARCHAR NOT NULL, " +
                        TIME + "VARCHAR NOT NULL, " +
                        GENRE + "VARCHAR NOT NULL, " +
                        LINK + "VARCHAR NOT NULL, " +
                        ABSENT + "INTEGER DEFAULT 0" +
                        SD + "VARCHAR NOT NULL, " +
                        ED + "VARCHAR NOT NULL, " +
                        FAVORITE + "INTEGER DEFAULT 0" +
                        ");";

    }
}
