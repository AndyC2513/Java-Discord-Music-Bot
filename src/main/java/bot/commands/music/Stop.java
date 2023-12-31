package bot.commands.music;

import bot.SCommand;
import bot.lavaplayer.GuildMusicManager;
import bot.lavaplayer.PlayerManager;
import bot.lavaplayer.TrackScheduler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Stop implements SCommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stops bot from playing music";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            event.reply("Not in voice channel!").setEphemeral(true).queue();
            return;
        }

        Member bot = event.getGuild().getSelfMember();
        GuildVoiceState botVoiceState = bot.getVoiceState();

        if (!botVoiceState.inAudioChannel()) {
            event.reply("I am not in voice channel!").setEphemeral(true).queue();
            return;
        }

        if (botVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("Voice channels are mismatched!").setEphemeral(true).queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
        trackScheduler.getQueue().clear();
        trackScheduler.getPlayer().stopTrack();
        event.reply("Bot stopped playing!").queue();
    }
}
