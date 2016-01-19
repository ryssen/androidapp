package com.example.eandreje.androidapp;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import java.util.List;

public class Queries {
    static List<Category> getCategories() {
        return new Select()
                .from(Category.class)
                .orderBy("Name ASC")
                .execute();
    }

    static List<Event> getEvents(Category parent) {
        return new Select()
                .from(Event.class)
                .where("ParentCategory = ?", parent.getId())
                .orderBy("Name ASC")
                .execute();
    }

    static List<Attendant> getRelation(Event event) {
        return new Select()
                .from(Attendant.class)
                .innerJoin(AttendantEvent.class).on("AttendantTable.id = Attendant")
                .where("AttendantEventTable.Event = ?", event.getId())
                .execute();
    }

    static AttendantEvent getAttendantEventRelation(Long id, Event event) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Attendant = ? and Event = ?", id, event.getId())
                .executeSingle();
    }

    static void deleteAttendant(Long id, Event event) {
         new Delete()
                .from(AttendantEvent.class)
                .where("Attendant = ? and Event = ?", id, event.getId())
                .execute();
    }



    static List<Columns> getColumnHeaders(Event event) {
        return new Select()
                .from(Columns.class)
                .where("ParentEvent = ?", event.getId())
                .execute();
    }

    static CellValue getSingleCellValue(Attendant attendant, Columns activeColumn, Event document) {
        return new Select()
                .from(CellValue.class)
                .where("ParentAttendant = ? and ParentColumn = ? and ParentEvent = ?", attendant.getId(), activeColumn.getId(), document.getId())
                .executeSingle();
    }

    static List<AttendantEvent> getAttendCell(Event event) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Event = ?", event.getId())
                .execute();
    }

    static CellValue fetchCellValueForCSV(Columns column, Attendant attendant) {
        return new Select()
                .from(CellValue.class)
                .where("ParentColumn = ? and ParentAttendant = ?", column.getId(), attendant.getId())
                .executeSingle();
    }

    static List<AttendantEvent> getAllAttendantEvent(Event event) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Event = ?", event.getId())
                .execute();
    }
}
