package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.testutil.ModuleClassBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteLessonCommand}
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullModuleClassIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteLessonCommand(null, INDEX_FIRST_ITEM));
    }

    @Test
    public void constructor_nullLessonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteLessonCommand(INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_validIndexes_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        ModuleClass editedModuleClass = new ModuleClassBuilder(moduleClass).withLessons().build();
        Lesson lessonToDelete = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(moduleClassIndex, lessonIndex);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete,
                moduleClass);

        ModelManager expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(deleteLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleClassIndex_failure() {
        Index moduleClassIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(moduleClassIndex, lessonIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index lessonIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(moduleClassIndex, lessonIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstLessonCommand = new DeleteLessonCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        DeleteLessonCommand deleteSecondLessonCommand = new DeleteLessonCommand(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstLessonCommand.equals(deleteFirstLessonCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstLessonCommandCopy = new DeleteLessonCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        assertTrue(deleteFirstLessonCommand.equals(deleteFirstLessonCommandCopy));

        // null -> returns false
        assertFalse(deleteFirstLessonCommand.equals(null));

        // different lesson -> returns false
        assertFalse(deleteFirstLessonCommand.equals(deleteSecondLessonCommand));
    }
}
