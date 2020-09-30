package seedu.address.model.moduleclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_LAB;
import static seedu.address.testutil.TypicalModuleClass.CS2100_LAB;
import static seedu.address.testutil.TypicalModuleClass.CS2103T_TUTORIAL;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.AMY;
import static seedu.address.testutil.TypicalStudent.BENSON;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.components.name.Name;
import seedu.address.testutil.ModuleClassBuilder;

public class ModuleClassTest {

    @Test
    public void constructor_nameOnly_hasNoStudents() {
        Name name = new Name(VALID_NAME_CS2100_LAB);
        ModuleClass moduleClass = new ModuleClass(name);
        assertEquals(0, moduleClass.getStudentUuids().size());
    }

    @Test
    public void constructor_withStudents_hasCorrectStudents() {
        Name name = new Name(VALID_NAME_CS2100_LAB);
        Set<UUID> studentUuids = new HashSet<>(Collections.singletonList(ALICE.getUuid()));
        ModuleClass moduleClass = new ModuleClass(name, studentUuids);
        assertEquals(studentUuids.size(), moduleClass.getStudentUuids().size());
        assertTrue(moduleClass.getStudentUuids().contains(ALICE.getUuid()));
    }

    @Test
    public void hasStudentUuid_studentInModuleClass_returnsTrue() {
        ModuleClass moduleClass = new ModuleClassBuilder().withStudentUuids(ALICE.getUuid(), BENSON.getUuid()).build();
        assertTrue(moduleClass.hasStudentUuid(ALICE.getUuid()));
    }

    @Test
    public void hasStudentUuid_nullUuid_throwsNullPointerException() {
        ModuleClass moduleClass = new ModuleClassBuilder().build();
        assertThrows(NullPointerException.class, () -> moduleClass.hasStudentUuid(null));
    }

    @Test
    public void hasStudentUuid_studentNotInModuleClass_returnsFalse() {
        ModuleClass moduleClass = new ModuleClassBuilder().withStudentUuids(ALICE.getUuid()).build();
        assertFalse(moduleClass.hasStudentUuid(BENSON.getUuid()));
    }

    @Test
    public void getStudentUuids_modifyList_throwsUnsupportedOperationException() {
        ModuleClass moduleClass = new ModuleClassBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> moduleClass.getStudentUuids().remove(0));
    }

    @Test
    public void isSameModuleClass() {
        // same object -> returns true
        assertTrue(CS2103T_TUTORIAL.isSameModuleClass(CS2103T_TUTORIAL));

        // null -> returns false
        assertFalse(CS2103T_TUTORIAL.isSameModuleClass(null));

        // different name -> returns false
        ModuleClass editedCs2103t = new ModuleClassBuilder(CS2103T_TUTORIAL).withName(VALID_NAME_CS2100_LAB)
                .build();
        assertFalse(CS2103T_TUTORIAL.isSameModuleClass(editedCs2103t));

        // different students -> returns true
        editedCs2103t = new ModuleClassBuilder(CS2103T_TUTORIAL).withStudentUuids(AMY.getUuid()).build();
        assertTrue(CS2103T_TUTORIAL.isSameModuleClass(editedCs2103t));
    }

    @Test
    public void equals() {
        // same values -> returns true
        ModuleClass cs2103tCopy = new ModuleClassBuilder(CS2103T_TUTORIAL).build();
        assertTrue(CS2103T_TUTORIAL.equals(cs2103tCopy));

        // same object -> returns true
        assertTrue(CS2103T_TUTORIAL.equals(CS2103T_TUTORIAL));

        // null -> returns false
        assertFalse(CS2103T_TUTORIAL.equals(null));

        // different type -> returns false
        assertFalse(CS2103T_TUTORIAL.equals(5));

        // different moduleClass -> returns false
        assertFalse(CS2103T_TUTORIAL.equals(CS2100_LAB));

        // different name -> returns false
        ModuleClass editedCs2103t = new ModuleClassBuilder(CS2103T_TUTORIAL).withName(VALID_NAME_CS2100_LAB)
                .build();
        assertFalse(CS2103T_TUTORIAL.equals(editedCs2103t));

        // different students -> returns false
        editedCs2103t = new ModuleClassBuilder(CS2103T_TUTORIAL).withStudentUuids(AMY.getUuid()).build();
        assertFalse(CS2103T_TUTORIAL.equals(editedCs2103t));
    }
}
