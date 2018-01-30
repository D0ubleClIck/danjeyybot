package listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.Statics;

/**
 * @author Daniel Lambrecht
 */

public class readyListener extends ListenerAdapter {

    /**
     * @param event
     *
     * */
    public void onReady(ReadyEvent event) {

        String out = "\nThis bot is running on following servers: \n";

        for (Guild g : event.getJDA().getGuilds() ) {
            out += g.getName() + " (" + g.getId() + ") \n";
        }

        System.out.println(out);

    }

}