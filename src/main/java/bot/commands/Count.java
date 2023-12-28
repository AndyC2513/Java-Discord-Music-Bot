package bot.commands;

import bot.SCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Count implements SCommand {
    @Override
    public String getName() {
        return "count";
    }

    @Override
    public String getDescription() {
        return "Returns number of members in server";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        List<Member> members = event.getGuild().getMembers();
        int j = 0;
        for (Member member : members) {
            j++;
        }
        String numMembers = Integer.toString(j);
        event.reply("There are " + numMembers + " members currently in the server!").queue();
    }
}
