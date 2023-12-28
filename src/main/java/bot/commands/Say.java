package bot.commands;

import bot.SCommand;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Say implements SCommand {
    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Say a message";
    }

    @Override
    public List<OptionData> getOptions() {
        OptionData option1 = new OptionData(OptionType.STRING, "message",
                "The message the bot will say",
                true);
        OptionData option2 = new OptionData(OptionType.CHANNEL, "channel",
                "The channel the bot will say the message in",
                false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        List<OptionData> optionDataList = new ArrayList<>();
        optionDataList.add(option1);
        optionDataList.add(option2);
        return optionDataList;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
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
    }
}
