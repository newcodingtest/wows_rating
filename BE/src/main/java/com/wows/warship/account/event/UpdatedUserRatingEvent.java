package com.wows.warship.account.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdatedUserRatingEvent {
    private String accountId;

    private int rating;
}
