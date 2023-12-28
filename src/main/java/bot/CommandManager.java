package bot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private List<SCommand> commands = new ArrayList<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for(SCommand command : commands) {
            if(command.getName().equals(event.getName())) {
                command.execute(event);
                return;
            }
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            for (SCommand command : commands) {
                if (command.getOptions() == null) {
                    guild.upsertCommand(command.getName(), command.getDescription()).queue();
                } else {
                    guild.upsertCommand(command.getName(), command.getDescription()).addOptions(command.getOptions()).queue();
                }
            }
        }
    }

    public void add(SCommand command) {
        commands.add(command);
    }
}
