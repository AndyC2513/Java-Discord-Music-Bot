package bot.commands;

import bot.SCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Welcome implements SCommand {
    @Override
    public String getName() {
        return "welcome";
    }

    @Override
    public String getDescription() {
        return "Get welcomed by bot";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String userTag = event.getUser().getAsMention();
        event.reply("Welcome to the server, " + userTag + "!").setEphemeral(true).queue();
    }
}
