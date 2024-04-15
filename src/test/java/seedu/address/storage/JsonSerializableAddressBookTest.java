package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalPersonsWithLoans;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path INVALID_LOAN_FILE = TEST_DATA_FOLDER.resolve("invalidLoanAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_LOAN_FILE = TEST_DATA_FOLDER.resolve("duplicateLoanAddressBook.json");
    private static final AddressBook TYPICAL_ADDRESS_BOOK = TypicalPersonsWithLoans.getTypicalAddressBook();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook ab =
                JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBook = ab.toModelType();
        AddressBook expectedAddressBook = TYPICAL_ADDRESS_BOOK;
        assertEquals(addressBook, expectedAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook ab =
                JsonUtil.readJsonFile(INVALID_PERSON_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, ab::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook ab =
                JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                ab::toModelType);
    }

    @Test
    public void toModelType_invalidLoanFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook ab =
                JsonUtil.readJsonFile(INVALID_LOAN_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, ab::toModelType);
    }

    @Test
    public void toModelType_duplicateLoans_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook ab =
                JsonUtil.readJsonFile(DUPLICATE_LOAN_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_LOAN,
                ab::toModelType);
    }

}
