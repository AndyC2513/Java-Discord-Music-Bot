package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("welcome")) {
            String userTag = event.getUser().getAsMention();
            event.reply("Welcome to the server, " + userTag + "!").setEphemeral(true).queue();
        } else if (command.equals("count")) {
            List<Member> members = event.getGuild().getMembers();
            int j = 0;
            for (Member member : members) {
                j++;
            }
            String numMembers = Integer.toString(j);
            event.reply("There are " + numMembers + " members currently in the server!").queue();
        } else if (command.equals("say")) {
            OptionMapping messageOption = event.getOption("message");
            OptionMapping channelOption = event.getOption("channel");
            MessageChannel channel;

            if (channelOption != null) {
                channel = channelOption.getAsChannel().asGuildMessageChannel();
            } else {
                channel = event.getChannel();
            }

            if (messageOption != null) {
                String message = messageOption.getAsString();
                channel.sendMessage(message).queue();
                event.reply("Message was sent").setEphemeral(true).queue();
            }
        } else if (command.equals("giverole")) {
            Member member = event.getOption("user").getAsMember();
            Role role = event.getOption("role").getAsRole();

            event.getGuild().addRoleToMember(member, role).queue();
            event.reply(member.getAsMention() + " has been given the " + role.getAsMention() + " role!").queue();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "Get welcomed by bot"));
        commandData.add(Commands.slash("count", "Returns number of members in server"));

        OptionData option1 = new OptionData(OptionType.STRING, "message",
                "The message the bot will say",
                true);
        OptionData option2 = new OptionData(OptionType.CHANNEL, "channel",
                "The channel the bot will say the message in",
                false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        commandData.add(Commands.slash("say", "Say a message").addOptions(option1, option2));

        OptionData option3 = new OptionData(OptionType.USER, "user",
                "User to give role to",
                true);
        OptionData option4 = new OptionData(OptionType.ROLE, "role",
                "Role user will receive",
                true);
        commandData.add(Commands.slash("giverole", "Give someone a role").addOptions(option3, option4));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
