package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("welcome")) {
            String userTag = event.getUser().getAsMention();
            event.reply("Welcome to the server, " + userTag + "!").setEphemeral(true).queue();
        }
        if (command.equals("count")) {
            List<Member> members = event.getGuild().getMembers();
            int j = 0;
            for (Member member : members) {
                j++;
            }
            String numMembers = Integer.toString(j);
            event.reply("There are " + numMembers + " members currently in the server!").queue();
        }
        if (command.equals("clear")) {
            event.getChannel().delete().queue();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "Get welcomed by bot"));
        commandData.add(Commands.slash("count", "Returns number of members in server"));
        commandData.add(Commands.slash("clear", "Deletes channel"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
