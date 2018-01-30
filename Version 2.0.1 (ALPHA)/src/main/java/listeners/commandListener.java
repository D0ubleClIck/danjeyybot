
package listeners;
import core.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.Statics;


public class commandListener extends ListenerAdapter{

    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith(Statics.KeySymbol)){
            commandHandler.handleCommand(commandHandler.parser.parse(event.getMessage().getContentRaw(), event));

        }
    }
}