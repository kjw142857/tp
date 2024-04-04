package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVE_LOANS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LOANS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_PERSON;

import seedu.address.model.Model;

/**
 * Lists all active loans in the address book to the user.
 */
public class ViewLoansCommand extends ViewLoanRelatedCommand {
    public static final String COMMAND_WORD = "viewloans";

    public static final String MESSAGE_SUCCESS = "Listed all loans";

    public ViewLoansCommand(boolean isShowAllLoans) {
        super(isShowAllLoans);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_NO_PERSON);
        if (isShowAllLoans) {
            model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        } else {
            model.updateFilteredLoanList(PREDICATE_SHOW_ALL_ACTIVE_LOANS);
        }
        model.setIsLoansTab(true);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
