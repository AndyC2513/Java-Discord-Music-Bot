package bot.commands.music;

import bot.SCommand;
import bot.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Play implements SCommand {
    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Will play a song given the URL or name of song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        optionDataList.add(new OptionData(OptionType.STRING, "name", "Enter the URL or name of the song", true));
        return optionDataList;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();

        if (!voiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel").setEphemeral(true).queue();
            return;
        }

        Member bot = event.getGuild().getSelfMember();
        GuildVoiceState botVoiceState = bot.getVoiceState();

        if (!botVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(voiceState.getChannel());
        } else {
            if (botVoiceState.getChannel() != voiceState.getChannel()) {
                event.reply("You need to be in the same voice channel").setEphemeral(true).queue();
                return;
            }
        }

        String name = event.getOption("name").getAsString();
        try {
            new URI(name);
        } catch (URISyntaxException e) {
            name = "ytsearch:" + name;
        }

        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.play(event.getGuild(), name);
        event.reply("Playing Music!").queue();
    }
}
