package bss.intern.calendarandagenda.Util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import bss.intern.calendarandagenda.Model.AgendaEvent;
import bss.intern.calendarandagenda.Model.AgendaEventDao;

@Database(entities = {AgendaEvent.class}, version = 1)
public abstract class AgendaDatabase extends RoomDatabase {

    private static AgendaDatabase INSTANCE;

    public abstract AgendaEventDao agendaEventDao();

    public static AgendaDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AgendaDatabase.class, "agenda-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyINSTANCE(){
        INSTANCE = null;
    }

}
