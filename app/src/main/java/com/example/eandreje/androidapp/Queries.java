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

//    static Event getDocument(Event doc) {
//        return new Select()
//                .from(Event.class)
//                .where(" = ?", doc.getId())
//                .executeSingle();
//    }
//
//    static AttendantEvent getPerson(Long id, Event doc) {
//        return new Select()
//                .from(AttendantEvent.class)
//                .where("id = ?", doc.getId())
//                .executeSingle();
//    }

    static List<Attendant> getRelation(Event event) {
        return new Select()
                .from(Attendant.class)
                .innerJoin(AttendantEvent.class).on("AttendantTable.id = Attendant")
                .where("AttendantEventTable.Event = ?", event.getId())
                .execute();
    }

//    static void delete(Event doc) {
//        new Delete()
//                .from(Attendant.class)
//                .innerJoin(AttendantEvent.class).on("Attendant.id = Attendant")
//                .where("AttendantEvent.Event = ?", doc.getId())
//                .execute();
//    }


    static AttendantEvent getAttendantEventRelation(Long id, Event doc) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Attendant = ? and Event = ?", id, doc.getId())
                .executeSingle();
    }

    static List<Columns> getColumnHeaders(Event doc) {
        return new Select()
                .from(Columns.class)
                .where("ParentEvent = ?", doc.getId())
                .execute();
    }

    static CellValue getSingleCellValue(Attendant attendant, Columns activeColumn, Event document) {
        return new Select()
                .from(CellValue.class)
                .where("ParentAttendant = ? and ParentColumn = ? and ParentEvent = ?", attendant.getId(), activeColumn.getId(), document.getId())
                .executeSingle();
    }

//    static List<CellValue> fetchColumnCellData(Columns column, Event document) {
//        return new Select()
//                .from(CellValue.class)
//                .where("ParentColumn = ? and ParentDoc = ?", column.getId(), document.getId())
//                .execute();
//    }

    static List<AttendantEvent> getPersonCell(Event doc) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Event = ?", doc.getId())
                .execute();
    }

    static CellValue fetchCellValueForCSV(Columns column, Attendant attendant) {
        return new Select()
                .from(CellValue.class)
                .where("ParentColumn = ? and ParentAttendant = ?", column.getId(), attendant.getId())
                .executeSingle();
        //"ParentColumn = ?", column.getId(),
    }


    static List<AttendantEvent> getAllAttendantEvent(Event doc) {
        return new Select()
                .from(AttendantEvent.class)
                .where("Event = ?", doc.getId())
                .execute();
    }
}
//    static void deletePersonInDoc(Event doc)
//    {
//        new Delete()
//                .from(AttendantEvent.class)
//                .where("Event = ?", doc.getId())
//                .execute();
//    }
//
//    static void deleteColumn(Columns column)
//    {
//            new Delete()
//                    .from(Columns.class)
//                    .where("Id = ?", column.getId())
//                    .execute();
//    }
//
//    static void deleteColumnValues(Columns column)
//    {
//            new Delete()
//                    .from(CellValue.class)
//                    .where("ParentColumn = ?", column.getId())
//                    .execute();
//    }
//
//    static void deleteInPersonClass(Long id)
//    {
//            new Delete()
//                    .from(Attendant.class)
//                    .where("Id = ?", id)
//                    .execute();
//    }
//
//    static void deleteInDocItemClass(Long id)
//    {
//            new Delete()
//                    .from(AttendantEvent.class)
//                    .where("Attendant = ?", id)
//                    .execute();
//    }
//
//    static void deleteInColumnContent(long id)
//    {
//            new Delete()
//                    .from(CellValue.class)
//                    .where("ParentPerson = ?", id)
//                    .execute();
//    }
//}
