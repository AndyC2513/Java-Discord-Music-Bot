package bot;

import bot.commands.Count;
import bot.commands.GiveRole;
import bot.commands.Say;
import bot.commands.Welcome;
import bot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    private final Dotenv config;
    private final ShardManager shardManager;

    // EFFECTS: builds the discord bot with the correct bot token
    public Main() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        CommandManager commandManager = new CommandManager();
        commandManager.add(new Welcome());
        commandManager.add(new Count());
        commandManager.add(new Say());
        commandManager.add(new GiveRole());

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Driving"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_VOICE_STATES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY);
        shardManager = builder.build();

        shardManager.addEventListener(new EventListener(), commandManager);

    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    // EFFECTS: main executable
    public static void main(String[] args) {
        try {
            new Main();
        } catch (LoginException e) {
            System.out.println("Token is invalid");
        }
    }
}
