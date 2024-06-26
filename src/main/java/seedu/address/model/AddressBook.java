package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.LinkLoanCommand;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueLoanList;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueLoanList loans;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        persons = new UniquePersonList();
        loans = new UniqueLoanList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the loan list with {@code loans}.
     * {@code loans} must not contain duplicate loans.
     */
    public void setLoans(List<Loan> loans) {
        this.loans.setLoans(loans);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setLoans(newData.getLoanList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasExactPerson(Person person) {
        requireNonNull(person);
        return persons.containsExact(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        loans.modifyLoanAssignee(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        loans.removeLoansAttachedTo(key);
    }

    //// loan-level operations

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the address book.
     */
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return loans.contains(loan);
    }

    /**
     * Adds a loan to the address book.
     * The loan must not already exist in the address book.
     */
    public void addLoan(Loan l) {
        loans.addLoan(l);
    }

    public Loan addLoan(LinkLoanCommand.LinkLoanDescriptor loanDescription, Person assignee) {
        return loans.addLoan(loanDescription, assignee);
    }
    /**
     * Marks a loan in the address book.
     * The loan must exist in the address book.
     */
    public void markLoan(Loan loanToMark) {
        loans.markLoan(loanToMark);
    }
    /**
     * Unmarks a loan in the address book.
     * The loan must exist in the address book.
     */
    public void unmarkLoan(Loan loanToUnmark) {
        loans.unmarkLoan(loanToUnmark);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeLoan(Loan key) {
        loans.removeLoan(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Loan> getLoanList() {
        return loans.asUnmodifiableObservableList();
    }

    public UniqueLoanList getUniqueLoanList() {
        return loans;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons) && loans.equals(otherAddressBook.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, loans);
    }
}
