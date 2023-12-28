package bot.commands;

import bot.SCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class GiveRole implements SCommand {
    @Override
    public String getName() {
        return "giverole";
    }

    @Override
    public String getDescription() {
        return "Give someone a role";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> optionDataList = new ArrayList<>();
        OptionData option3 = new OptionData(OptionType.USER, "user",
                "User to give role to",
                true);
        OptionData option4 = new OptionData(OptionType.ROLE, "role",
                "Role user will receive",
                true);
        optionDataList.add(option3);
        optionDataList.add(option4);
        return optionDataList;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getOption("user").getAsMember();
        Role role = event.getOption("role").getAsRole();

        event.getGuild().addRoleToMember(member, role).queue();
        event.reply(member.getAsMention() + " has been given the " + role.getAsMention() + " role!").queue();
    }
}
