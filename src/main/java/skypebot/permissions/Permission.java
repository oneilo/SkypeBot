package skypebot.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Kyle on 9/10/2015.
 */
@AllArgsConstructor
public enum Permission {
    OWNER(4), ADMIN(3), MODERATOR(2), PLUS(1), DEFAULT(0);
    
    @Getter
    private final int level;
}
