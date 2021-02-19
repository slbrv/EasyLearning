package com.kushnir.elfc.data;

import android.provider.BaseColumns;

public class CardsContract {

    private CardsContract() {

    }

    public static class LangEntry implements BaseColumns {
        public static final String TABLE_NAME = "langs";
        public static final String COLUMN_NAME_LANG = "lang";
    }

    public static class SubjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_LANG_ID = "lang_id";
        public static final String COLUMN_NAME_SUBJECT = "subject";
    }

    public static class CardEntry implements BaseColumns {
        public static final String TABLE_NAME = "cards";
        public static final String COLUMN_NAME_SUBJECT_ID = "subject_id";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_TRANSCRIPTION = "transcription";
        public static final String COLUMN_NAME_IMAGE_URI = "image_uri";
    }
}
