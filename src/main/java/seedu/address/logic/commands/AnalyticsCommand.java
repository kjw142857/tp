package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Analytics;
import seedu.address.model.person.Person;

/**
 * Represents a command to view the loans analytics data associated with a contact.
 */
public class AnalyticsCommand extends Command {
    public static final String COMMAND_WORD = "analytics";

    public static final String MESSAGE_SUCCESS = "Analytics generated";
    private final Index targetIndex;

    public AnalyticsCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        model.updateFilteredLoanList(loan -> loan.isAssignedTo(targetPerson) && loan.isActive());
        Analytics targetAnalytics = Analytics.getAnalytics(model.getUniqueLoanList());
        // TODO: Implement analytics GUI display logic
        return new CommandResult(MESSAGE_SUCCESS + targetAnalytics, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnalyticsCommand)) {
            return false;
        }

        AnalyticsCommand otherViewLoanCommand = (AnalyticsCommand) other;
        return targetIndex.equals(otherViewLoanCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}