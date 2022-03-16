import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/chat")
public class ChatServer{
    public Logger logger;
    private static Session ServerSession;
    @OnOpen
    public void peerConnected(Session session){
        //armazena a sessão
        logger.log(Level.INFO, "Sessão de ID "+session.getId()+" estabelecida.");
    }

}

