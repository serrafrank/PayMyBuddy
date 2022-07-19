package org.erlik.pay_my_buddy.application.consumer;

import org.erlik.pay_my_buddy.application.AbstractUseCase;
import org.erlik.pay_my_buddy.application.AuthenticationInfo;
import org.erlik.pay_my_buddy.domains.consumer.commands.AddFriendCommand;
import org.erlik.pay_my_buddy.domains.consumer.commands.ConsumerCommandService;
import org.erlik.pay_my_buddy.domains.consumer.commands.CreateNewConsumerCommand;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.presentation.consumer.AddFriendInput;
import org.erlik.pay_my_buddy.presentation.consumer.CreateNewConsumerInput;
import org.springframework.stereotype.Service;

@Service
public class ConsumerCommandUseCaseImpl
    extends AbstractUseCase
    implements ConsumerCommandUseCase {

    private final ConsumerCommandService consumerCommandService;

    public ConsumerCommandUseCaseImpl(AuthenticationInfo authenticationInfo,
                                      ConsumerCommandService consumerCommandService) {
        super(authenticationInfo);
        this.consumerCommandService = consumerCommandService;
    }

    @Override
    public Id createNewConsumer(CreateNewConsumerInput input) {
        throwExceptionIfAuthenticated();

        var newUser = new CreateNewConsumerCommand(input.firstname(),
            input.lastname(),
            input.emailAddress(),
            input.password());

        return consumerCommandService.createConsumer(newUser);
    }

    @Override
    public void addFriend(AddFriendInput addFriendInput) {
        var currentUserId = getAuthenticatedConsumerIdOrThrowException();
        var addFriend = new AddFriendCommand(currentUserId, addFriendInput.friendId());

        consumerCommandService.addFriend(addFriend);
    }

}
