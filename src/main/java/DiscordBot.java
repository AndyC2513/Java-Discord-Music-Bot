import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public static void main(String[] args) throws LoginException {
        final String token = "MTE4ODY3NzgxNTUxMjYxMjkxNQ.GO1LaQ.d7K0KTQBvDO8_z2QNnwXQ6wKi1Bunqf1kGklzk";

        JDA bot = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("a"))
                .setStatus(OnlineStatus.OFFLINE)
                .build();
    }
}
